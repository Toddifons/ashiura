# -*- coding: utf-8 -*-
import random
import time

from flask import Flask, request, jsonify
from flask_restx import Api, Resource
import requests
import logging

# this py file is just count number and temporary result for test
portnumber = "http://127.0.0.1:5502"
logging.basicConfig(level=logging.INFO)

def progressRun (user_id, declaration, progress):
    logging.info("progressRun")
    url = portnumber + f"/api/progress/{user_id}/{declaration}"

    res = {
        'progress': progress,
    }

    response = requests.post(url, json=res)

    if response.status_code == 200:
        print("Prediction successfully")
    else:
        print("Failed prediction")

    return url



app = Flask(__name__)
api = Api(app)


@api.route("/api/VoiClaReq/<string:user_id>/<string:declaration>", methods=["POST"])
class runPy(Resource):
    def post(self, user_id, declaration):
        logging.info("startPy")
        for i in range(11):
            logging.info("start_for")
            time.sleep(1)
            url = progressRun(user_id,declaration,str(i*10))
            logging.info(str(i*10)+"%")

        j = random.random()
                k = random.random()
                data = {
                    'voiceResult': f"{int(j * 2)}",
                    'mfccResult': f"{int(k * 2)}"
                }

        requests.post(url, json=data)
        return jsonify(data)

if __name__ == "__main__":
    app.run(debug=False, host="0.0.0.0", port=5503)
