from flask import Flask, request, Response
from flask_cors import CORS
from requests import get, request as send_request, Response as response_t
from secret import translator_headers, translator_url, translator_querystring
from typing import List, Tuple
from collections import Counter

app = Flask(__name__)
CORS(app)


def get_lyric_stats(lyrics: List[str]):
    return map(lambda item: f'{item[0]}: {item[1]}', Counter(' '.join(lyrics).lower().split(' ')).most_common(10))


def translate(lyrics: List[str]):
    translated = []
    for text in lyrics:
        response: response_t = send_request(
            "POST",
            translator_url,
            data="[{\"Text\": \"" + text + "\"}]",
            headers=translator_headers,
            params=translator_querystring
        )

        if response.status_code == 200:
            translated.append(response.json()[0]['translations'][0]['text'])

    if translated:
        return translated


def get_lyrics(artist: str, title: str) -> List[str]:
    response: response_t = get(f'https://api.lyrics.ovh/v1/{artist}/{title}')
    if response.status_code == 200:
        return response.json()['lyrics'].replace('\n\n', '|').replace('\r\n', '|').split('|')


def get_artist_info_recs(artist: str) -> Tuple[str, List[str]]:
    response: response_t = get(f'https://tastedive.com/api/similar?q={artist}&info=1')
    if response.status_code == 200:
        response_json = response.json()['Similar']
        return response_json['Info'][0]['wTeaser'], [rec['Name'] for rec in response_json['Results'][:5]]

    return '', []


@app.route('/service', methods=['GET'])
def service():
    artist: str = request.args.get('artist', '')
    title: str = request.args.get('title', '')

    if not (artist and title):
        return Response(f'<p>wrong arguments (artist: {artist}, title: {title})</p>', status=400, mimetype='text/html')

    html = f'<h1>{artist} - {title}</h1>'

    lyrics = get_lyrics(artist, title)
    if lyrics:
        html += f'<h2>Lyrics</h2> <p>{"<br>".join(lyrics)}</p>'

        lyric_stats = get_lyric_stats(lyrics)
        html += f'<h2> Lyric word stats </h2><p>{"<br>".join(lyric_stats)}</p>'

        translated_lyrics = translate(lyrics[:5])
        if translated_lyrics:
            html += f'<h2>Translated lyrics</h2> <p>{"<br>".join(translated_lyrics)}(...)</p>'

    info, recs = get_artist_info_recs(artist)
    if info:
        html += f'<h2> Artist Info </h2> <p>{info}</p>'
    if recs:
        html += f'<h2> Similar to {artist} </h2> <p>{"<br>".join(recs)}</p>'

    return Response(html, status=200, mimetype='text/html')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
