You can download the models from here: https://drive.google.com/drive/folders/1xkVK92XLZOgYlpaRpG_-WP0Elzg4ewpw

Put them in their corresponding folders like this:

![Image](2020-11-02%2020_16_00-Aff-Wild-models.png)

You can either choose the resnet or rnn version. I think the rnn performs slightly better but is slightly slower.

Then I would recommend using a virtual environment, since we have to install a very old tensorflow version.
Use `pip install -r requirements.txt` This should install all the needed requirements.

If you need to specify a specif webcam this can be done on line 33 in `WebcamModelLoop.py` by changing the number.

If the model runs too slow you can try to run with `--sequence_length=60 ` to change the sequence length to 60 (its 80 by default)