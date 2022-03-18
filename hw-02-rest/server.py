from flask import Flask, request, Response
from flask_cors import CORS
from requests import get, Response as response_t


app = Flask(__name__)
CORS(app)


def get_lyrics(artist: str, title: str):
    response: response_t = get(f'https://api.lyrics.ovh/v1/{artist}/{title}')
    if response.status_code == 200:
        return response.json()['lyrics'].replace('\n', '<br>').replace('\r', '<br>')


@app.route('/service', methods=['GET'])
def service():

    artist: str = request.args.get('artist', '')
    title: str = request.args.get('title', '')

    if not (artist and title):
        return Response(f'<p>wrong arguments (artist: {artist}, title: {title})</p>', status=400, mimetype='text/html')

    html = f'<h1>{artist} - {title}</h1>'

    lyrics = get_lyrics(artist, title)
    if lyrics:
        html += f'<h2>Lyrics</h2> <p>{lyrics}</p>'

    return Response(html, status=200, mimetype='text/html')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
