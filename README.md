# OOP_Assignment3
This is the repository for my third OOP assignment.

I plan on making an [optical character recognition](https://en.wikipedia.org/wiki/Optical_character_recognition) system.
This system will try to identify hand written characters. It will do this using a neural network.

It will work as follows:
The user will take a picture of a character (possibly text, depending on how far I get) with their (Android) phone.
The app will then send the picture to a web service.
This service will be a neural network designed to recognise the characters.
Once the network has guessed what character it is, a text file containing this character will be sent back to the user's phone.

The following features will only be added if I manage to get the basic scenario above working:
Allow the user to take a picture of full words / full pages.
Run guessed words through a spell checker to aid accuracy. Changes this spell checker makes will aid the neural network.
Allow users to create a profile. This profile would be used to help the network guess based on people's individual handwriting.
Integrate with Dropbox / Google Drive and other 3rd party file sharing apps to allow users to send their text file 'to the cloud'.
