# OOP_Assignment3
For this assignment, I created an Optical Character Recognition (OCR) program that worked by means of a Neural Network.

There are multiple projects in this repo. Below is an explanation of what each project does:
* **OCRNeuralNetwork:** This project contains the code for the Neural Network. It also contains programs to "train" the network. These training GUI's were made using JavaFX. Here is a break down of the packages inside this project:
  * **NeuralNetwork:** Contains the code for the network
  * **ImageProcessing:** Contains code to run various functions on an image before it is transferred to the Network
  * **Trainer:** Contains two programs to train the network. AutoTrainer.java will ask for a directory containing files and how many time to run through the directory. It will train the network using these images. If it goes through an entire run of a directory without guessing anything incorrectly, it will stop. Trainer.java is a GUI program that will allow you to select a directory containing image, then see for each image you can see how stimulated each Neuron is.
  * **WordGuesser:** A GUI program that allows the user to select an image and it will try to guess it. You can also enter the correct string to train the network.
* **OCRCamera:** This project contains code for an Android app. This app sends a picture to a web service that runs the NN to guess what characters are in the image.
* **OCRWebService:** This is the web service that the app sends images to. I used Tomcat to run the web service
