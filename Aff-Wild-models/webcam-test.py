from cv2 import *
import numpy as np
from inference_script import inference

import tensorflow as tf

FLAGS = tf.app.flags.FLAGS

tf.app.flags.DEFINE_integer('batch_size', 1, '''The batch size to use.''')

tf.app.flags.DEFINE_integer('sequence_length', 1,
                            'the sequence length: how many consecutive frames to use for the RNN; if the network is only CNN then put here any number you want : total_batch_size = batch_size * seq_length')

# tf.app.flags.DEFINE_integer('size', 96, 'dimensions of input images, e.g. 96x96')

tf.app.flags.DEFINE_string('network', 'affwildnet_vggface',
                           ' which network architecture we want to use,  pick between : vggface_4096, vggface_2000, affwildnet_vggface, affwildnet_resnet ')

tf.logging.set_verbosity(tf.logging.FATAL)
slim = tf.contrib.slim

cam = VideoCapture(1)  # Specifies which camera
namedWindow("Webcam")

if FLAGS.network == "affwildnet_resnet":
    pretrained_model_checkpoint_path = "models/resnet/model.ckpt-0"
elif FLAGS.network == "affwildnet_vggface":
    pretrained_model_checkpoint_path = "models/vggface_rnn/model.ckpt-0"
else:
    pretrained_model_checkpoint_path = ""
    print("No known checkpoint path. Stopping...")
    exit()

while True:
    ret: [bool, np.ndarray] = cam.read()
    s, img = ret

    if s:    # frame captured without any errors
        # namedWindow("cam-test",)  # cv2.CV_WINDOW_AUTOSIZE)
        imshow("Webcam",img)
        waitKey(1)

        inference(np.asarray(img), FLAGS.batch_size, FLAGS.sequence_length, FLAGS.network, pretrained_model_checkpoint_path, 96)
