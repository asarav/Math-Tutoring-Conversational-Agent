import threading

from flask import Flask

import WebcamModelLoop

app = Flask(__name__)
model = WebcamModelLoop.WebcamModelLoop()


@app.route('/predictions')
def hello_world():
    return {"outputs": model.get_and_empty_outputs()}


if __name__ == "__main__":
    threading.Thread(target=app.run).start()
    model.start_loop()
