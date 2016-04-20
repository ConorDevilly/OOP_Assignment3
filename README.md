# OOP_Assignment3
For this assignment, I created an Optical Character Recognition (OCR) program that worked by means of a Neural Network.

There are multiple projects in this repo. Below is an explanation of what each project does:
* **OCRNeuralNetwork:** This project contains the code for the Neural Network. It also contains programs to "train" the network. These training GUI's were made using JavaFX.
* **OCRCamera:** This project contains code for an Android app. This app sends a picture to a web service that runs the NN to guess what characters are in the image.
* **OCRWebService:** This is the web service that the app sends images to. I used Tomcat to run the web service
