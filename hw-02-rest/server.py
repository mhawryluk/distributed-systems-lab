from flask import Flask, request, render_template
from flask_cors import CORS
import requests

app = Flask(__name__)
CORS(app)


@app.route('/service', methods=['GET'])
def service():
    argument = request.args.get('arg', '')
    return f'<html><body><h1>{argument}</h1></body></html>'


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
