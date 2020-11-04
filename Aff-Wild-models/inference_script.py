from __future__ import division
from __future__ import absolute_import
from __future__ import print_function

import tensorflow as tf
tf.logging.set_verbosity(tf.logging.FATAL)

slim = tf.contrib.slim
import numpy as np

# Create FLAGS



###############################################################################################################################################################
####  The sample code and the model weights are for RESEARCH PURPOSES only and cannot be used for commercial use.      ########################################
####                                 Do not redistribute this elsewhere                                                ########################################
################################################################################################################################################################

init_fn = None

def inference(images, batch_size, sequence_length, network, pretrained_model_checkpoint_path, output, size=96, ):
    g = tf.Graph()

    with g.as_default():
        images = np.array(images)
        images = tf.convert_to_tensor(images)

        images_batch = tf.image.resize_images(images, tf.convert_to_tensor([size, size]))

        images_batch = tf.to_float(images_batch)
        images_batch -= 128.0
        images_batch /= 128.0  # scale all pixel values in range: [-1,1]

        images_batch = tf.reshape(images_batch, [-1, size, size, 3])

        if network == 'vggface_4096':
            from vggface import vggface_4096x4096x2 as net
            network = net.VGGFace(batch_size * sequence_length)
            network.setup(images_batch)
            prediction = network.get_output()

        elif network == 'vggface_2000':
            from vggface import vggface_4096x2000x2 as net
            network = net.VGGFace(batch_size * sequence_length)
            network.setup(images_batch)
            prediction = network.get_output()

        elif network == 'affwildnet_resnet':
            from tensorflow.contrib.slim.python.slim.nets import resnet_v1
            with slim.arg_scope(resnet_v1.resnet_arg_scope()):
                net, _ = resnet_v1.resnet_v1_50(inputs=images_batch, is_training=False, num_classes=None)

                with tf.variable_scope('rnn') as scope:
                    cnn = tf.reshape(net, [batch_size, sequence_length, -1])
                    cell = tf.nn.rnn_cell.MultiRNNCell([tf.nn.rnn_cell.GRUCell(128) for _ in range(2)])
                    outputs, _ = tf.nn.dynamic_rnn(cell, cnn, dtype=tf.float32)
                    outputs = tf.reshape(outputs, (batch_size * sequence_length, 128))

                    weights_initializer = tf.truncated_normal_initializer(
                        stddev=0.01)
                    weights = tf.get_variable('weights_output',
                                              shape=[128, 2],
                                              initializer=weights_initializer,
                                              trainable=True)
                    biases = tf.get_variable('biases_output',
                                             shape=[2],
                                             initializer=tf.zeros_initializer, trainable=True)

                    prediction = tf.nn.xw_plus_b(outputs, weights, biases)

        elif network == 'affwildnet_vggface':
            from affwildnet import vggface_gru as net
            network = net.VGGFace(batch_size, sequence_length)
            network.setup(images_batch)
            prediction = network.get_output()

        variables_to_restore = tf.global_variables()

        with tf.Session() as sess:

            init_fn = slim.assign_from_checkpoint_fn(
                pretrained_model_checkpoint_path, variables_to_restore,
                ignore_missing_vars=False)

            init_fn(sess)
            pr = sess.run([prediction])
            print(f"valence: {pr[0][len(pr[0])-1][0]:.3f} arousal: {pr[0][len(pr[0])-1][1]:.3f}")

            # plt.plot(np.reshape(pr, (-1, 2)))
            # plt.show()

            output.append((pr[0][len(pr[0])-1][0], pr[0][len(pr[0])-1][1]))

