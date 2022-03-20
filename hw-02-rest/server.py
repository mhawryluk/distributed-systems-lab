from flask import Flask, request, Response, render_template
from flask_cors import CORS
from secret import translator_headers, translator_url, translator_querystring
from typing import List, Tuple, Optional, Any
from collections import Counter
import asyncio
import aiohttp

app = Flask(__name__)
CORS(app)


def get_lyric_stats(lyrics: List[str]):
    return map(lambda item: f'{item[0]}: {item[1]}', Counter(' '.join(lyrics).lower().split(' ')).most_common(10))


def get_translate_tasks(lyrics: List[str], session: aiohttp.ClientSession):
    return [
        session.post(
            translator_url,
            data="[{\"Text\": \"" + text + "\"}]",
            headers=translator_headers,
            params=translator_querystring
        )

        for text in lyrics
    ]


async def get_translated_lyrics(lyrics: List[str]) -> List[str]:
    translated = []
    async with aiohttp.ClientSession() as session:
        tasks = get_translate_tasks(lyrics, session)
        responses = await asyncio.gather(*tasks)
        for response in responses:
            translated.append((await response.json())[0]['translations'][0]['text'])
    return translated


async def get_lyrics_and_info(artist: str, title: str) -> tuple[Optional[List[str]], Optional[list[Any]]]:
    async with aiohttp.ClientSession() as session:
        tasks = [
            session.get(f'https://api.lyrics.ovh/v1/{artist}/{title}'),
            session.get(f'https://tastedive.com/api/similar?q={artist}&info=1')
        ]

        responses = await asyncio.gather(*tasks)

        lyrics = None
        info = (None, None)

        if responses[0].status == 200:
            lyrics = (await responses[0].json())['lyrics'].replace('\n\n', '|').replace('\r\n', '|').split('|')

        if responses[1].status == 200:
            info_json = (await responses[1].json())['Similar']
            info = [info_json['Info'][0]['wTeaser'], [rec['Name'] for rec in info_json['Results'][:5]]]

    return lyrics, info


@app.route('/', methods=['GET'])
def webpage():
    return render_template('index.html')


@app.route('/service', methods=['GET'])
def service():
    artist: str = request.args.get('artist', '')
    title: str = request.args.get('title', '')

    if not (artist and title):
        return Response(f'<p>wrong arguments (artist: {artist}, title: {title})</p>', status=400, mimetype='text/html')

    html = f'<h1>{artist} - {title}</h1>'

    lyrics, (info, recs) = asyncio.run(get_lyrics_and_info(artist, title))

    if lyrics:
        html += f'<h2>Lyrics</h2> <p>{"<br>".join(lyrics)}</p>'

        lyric_stats = get_lyric_stats(lyrics)
        html += f'<h2> Lyric word stats </h2><p>{"<br>".join(lyric_stats)}</p>'

        translated_lyrics: List[str] = asyncio.run(get_translated_lyrics(lyrics[:5]))
        if translated_lyrics:
            html += f'<h2>Translated lyrics</h2> <p>{"<br>".join(translated_lyrics)}(...)</p>'

    if info:
        html += f'<h2> Artist Info </h2> <p>{info}</p>'
    if recs:
        html += f'<h2> Similar to {artist} </h2> <p>{"<br>".join(recs)}</p>'

    return Response(html, status=200, mimetype='text/html')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
