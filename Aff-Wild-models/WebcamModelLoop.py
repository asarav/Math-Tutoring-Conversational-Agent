import math
from threading import Thread
from typing import Optional

import cv2
import numpy as np
from inference_script import inference
from matplotlib import pyplot as plt


import tensorflow as tf

FLAGS = tf.app.flags.FLAGS

tf.app.flags.DEFINE_integer('batch_size', 1, '''The batch size to use.''')

tf.app.flags.DEFINE_integer('sequence_length', 1,
                            'the sequence length: how many consecutive frames to use for the RNN; if the network is only CNN then put here any number you want : total_batch_size = batch_size * seq_length')

# tf.app.flags.DEFINE_integer('size', 96, 'dimensions of input images, e.g. 96x96')

tf.app.flags.DEFINE_string('network', 'affwildnet_vggface',
                           ' which network architecture we want to use,  pick between : vggface_4096, vggface_2000, affwildnet_vggface, affwildnet_resnet ')

tf.logging.set_verbosity(tf.logging.FATAL)


class WebcamModelLoop:

    def __init__(self):
        slim = tf.contrib.slim

        self.cam = cv2.VideoCapture(0)  # Specifies which camera
        cv2.namedWindow("Webcam")

        if FLAGS.network == "affwildnet_resnet":
            self.pretrained_model_checkpoint_path = "models/resnet/model.ckpt-0"
        elif FLAGS.network == "affwildnet_vggface":
            self.pretrained_model_checkpoint_path = "models/vggface_rnn/model.ckpt-0"
        else:
            self.pretrained_model_checkpoint_path = ""
            print("No known checkpoint path. Stopping...")
            exit()

        self.outputs = []

    def get_and_empty_outputs(self):
        to_return = self.outputs
        self.outputs = []
        return to_return

    def start_loop(self):
        left = 0
        right = 200
        top = 0
        bottom = 200

        x = 200
        y = 200
        w = 200
        h = 200

        images = []
        t: Optional[Thread] = None
        while True:
            ret: [bool, np.ndarray] = self.cam.read()
            s, img = ret

            if s:
                gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

                faceCascade = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")
                faces = faceCascade.detectMultiScale(
                    img,
                    scaleFactor=1.3,
                    minNeighbors=2,
                    minSize=(100, 100)
                )

                if len(faces) > 0:
                    new_x, new_y, new_w, new_h = faces[0]
                    smoothing = 0
                    x = round(smoothing * x + (1 - smoothing) * new_x)
                    y = round(smoothing * y + (1 - smoothing) * new_y)
                    w = round(smoothing * w + (1 - smoothing) * new_w)
                    h = round(smoothing * h + (1 - smoothing) * new_h)

                    side_border = int(120 - w)
                    top_border = int(120 - h)

                    left = x - side_border // 2
                    right = x + w + math.ceil(side_border / 2)
                    top = y - top_border // 2
                    bottom = y + h + math.ceil(top_border / 2)

                    if left < 0:
                        right -= left
                        left = 0
                    elif right >= img.shape[1]:
                        left -= right - img.shape[1] - 1
                        right = img.shape[1] - 1
                        print(left,  right - img.shape[1])

                    if top < 0:
                        bottom -= top
                        top = 0
                    elif bottom >= img.shape[0]:
                        top += bottom - img.shape[1] - 1
                        bottom = img.shape[0] - 1

                cropped_img = img[top:bottom, left:right]
                cv2.imshow("Webcam", cropped_img)
                cv2.waitKey(1)

                if len(faces) > 0:
                    images.append(cropped_img)

                    while len(images) > FLAGS.sequence_length:
                        images.pop(0)

                    if len(images) == FLAGS.sequence_length and (t is None or not t.is_alive()):
                        t = Thread(target=inference, args=(images, FLAGS.batch_size, FLAGS.sequence_length, FLAGS.network,
                                           self.pretrained_model_checkpoint_path, self.outputs, 96))
                        t.start()
                        images = []
