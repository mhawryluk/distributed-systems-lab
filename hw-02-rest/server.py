from flask import Flask, request, Response, render_template
from flask_cors import CORS
from secret import translator_headers, translator_url, translator_querystring
from typing import List, Optional, Any
from collections import Counter
from text_matching import kmp
import asyncio
import aiohttp

app = Flask(__name__)
CORS(app)


async def get_lyrics_and_info(artist: str, title: str) -> tuple[Optional[List[str]], Optional[list[Any]]]:
    async with aiohttp.ClientSession() as session:
        tasks = [
            session.get(f'https://api.lyrics.ovh/v1/{artist}/{title}'),  # get lyrics
            session.get(f'https://tastedive.com/api/similar?q={artist}&info=1')  # get arist info and recs
        ]

        responses = await asyncio.gather(*tasks)

        lyrics = None
        info = [None, None]

        if responses[0].status == 200:
            lyrics = (await responses[0].json())['lyrics'].replace('\n\n', '|').replace('\r\n', '|').split('|')

        if responses[1].status == 200:
            info_json = (await responses[1].json())['Similar']
            if info_json.get('Info', []):
                info[0] = info_json['Info'][0].get('wTeaser', None)

            if info_json.get('Results', []):
                info[1] = [rec['Name'] for rec in info_json['Results'][:5]]

    return lyrics, info


async def get_translated_lyrics(lyrics: List[str]) -> Optional[List[str]]:
    translated = []
    async with aiohttp.ClientSession() as session:

        tasks = [
            session.post(
                translator_url,
                data="[{\"Text\": \"" + text + "\"}]",
                headers=translator_headers,
                params=translator_querystring
            )
            for text in lyrics
        ]

        responses = await asyncio.gather(*tasks)

        for response in responses:
            if response.status == 200:
                response_json = await response.json()
                if response_json and 'detectedLanguage' in response_json[0] and response_json[0]['detectedLanguage']['language'] == 'pl':
                    return None
                translated.append(response_json[0]['translations'][0]['text'])

    return translated


def get_word_list(lyrics: List[str]) -> List[str]:
    words = list(filter(lambda x: (x.strip() != ''), ' '.join(lyrics).lower().replace(',', '').split(' ')))
    return words


def get_lyric_stats(lyrics: List[str]) -> List[str]:
    return list(map(lambda item: f'{item[0]}: {item[1]}', Counter(get_word_list(lyrics)).most_common(10)))


def get_lyric_count(lyrics: List[str]) -> int:
    return len(set(get_word_list(lyrics)))


def get_title_in_lyrics(lyrics: List[str], title: str) -> list[int]:
    return kmp(get_word_list(lyrics), title.lower().split(' '))


@app.route('/', methods=['GET'])
def webpage():
    return render_template('index.html')


@app.route('/service', methods=['GET'])
def service():
    artist: str = request.args.get('artist', '')
    title: str = request.args.get('title', '')

    if not (artist and title):
        return Response(f'<p>wrong arguments (artist: \'{artist}\', title: \'{title}\')</p>', status=400,
                        mimetype='text/html')

    html = f'<h1>{artist} - "{title}"</h1>'

    try:
        lyrics, (info, recs) = asyncio.run(get_lyrics_and_info(artist, title))
    except Exception as e:
        print(e)
        return Response(f'<p>Couldn\'t communicate with an external server</p>', status=501, mimetype='text/html')

    if lyrics is None and info is None and recs is None:
        return Response(f'<p>No information found about: {artist} - "{title}"</p>', status=200, mimetype='text/html')

    if lyrics:
        html += f'<h2>Lyrics</h2> <p>{"<br>".join(lyrics)}</p>'

        try:
            translated_lyrics: Optional[List[str]] = asyncio.run(get_translated_lyrics(lyrics))
            if translated_lyrics:
                html += f'<h2>Translated lyrics</h2><p>{"<br>".join(translated_lyrics)}</p>'
        except Exception as e:
            print(e)
            html += f'<h2>Translated lyrics</h2><p>Error receiving lyric translations</p>'

        lyric_stats = get_lyric_stats(lyrics)
        html += f'<h2>Most common words</h2><p>{"<br>".join(lyric_stats)}</p>'

        count_words = get_lyric_count(lyrics)
        html += f'<h2>Total different words used</h2><p>{count_words}</p>'

        title_matches = get_title_in_lyrics(lyrics, title)
        html += '<h2>Title presence in lyrics</h2>'

        if title_matches:
            title_length = len(list(filter(lambda x: x.strip() != '', title.split(' '))))
            html += '<p>'
            counter = 0
            span_len = 0
            for line in lyrics:
                for word in line.split(' '):
                    if word.strip() == '':
                        continue

                    if counter in title_matches:
                        html += '<span style="color: red">'
                        span_len = title_length

                    html += word + ' '

                    if span_len == 1:
                        html += '</span>'

                    if span_len > 0:
                        span_len -= 1

                    counter += 1
                html += '<br>'
            html += '</p>'
        else:
            html += '<p>Title not present in the lyrics</p>'

    else:
        html += f'<h2>Lyrics</h2> <p>Lyrics unavailable</p>'

    if info:
        html += f'<h2> Artist Info </h2> <p>{info}</p>'
    else:
        html += f'<h2> Artist Info </h2> <p>No artist info found</p>'
    if recs:
        html += f'<h2> Similar to {artist} </h2> <p>{"<br>".join(recs)}</p>'
    else:
        html += f'<h2>Similar to {artist}</h2> <p>Nobody found :(</p>'

    return Response(html, status=200, mimetype='text/html')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
