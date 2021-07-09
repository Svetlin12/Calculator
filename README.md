# Calculator ![type badge](https://img.shields.io/badge/type-own%20project-brightgreen) ![version badge](https://img.shields.io/badge/version-v1.0.0-blue) ![build badge](https://img.shields.io/badge/build-passing-success) ![language badge](https://img.shields.io/badge/language-Java-yellow)

Calculator written in Java which supports only some basic operations ***(+, -, /, \*, x^2, x^3)***

## Table of contents

* [General Information](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Usage](#usage)
* [Author](#author)

## General information

This project was created mainly for practice in Java. It realises a calculator which supports only the above mentioned operations. The UI was made as modern as possible with hover effects, onclick button borders change and etc. More information on how the calculator can be used, you can find in the section [Usage](#usage) I should note that the calculator supports only numbers which integer part have no more than 25 digits and fraction part with no more than 10 digits. This was made purely because the number would overflow the text field of the calculator.

## Technologies Used

* Java
* Visual Studio Code
* Intellij IDEA Community Edition
* Swing

## Features

* Add two numbers
* Subtract two numbers
* Multiply two numbers
* Divide two numbers
* Raise a number to the power of two
* Raise a number to the power of three
* Support both integer and fraction numbers
* Support for keyboard input
* Clear everything
* Delete one digit

## Usage

* The calculator's UI looks like this:

![calculator look snip](https://github.com/Svetlin12/Calculator/blob/main/screenshots/calculator-snip-1.PNG)

* Let's input the number 12. There are two ways to do it:
  * Press the UI buttons labeled ***"1"*** and ***"2"*** consecutively
  * Press the keyboard buttons ***"1"*** and ***"2"*** consecutively

Then, let's press the "+" button. Again, you have two ways of doing so:
  * Press the UI ***"+"*** button on the calculator
  * Press ***(Shift + "+")*** on the keyboard
You should have this by now:

![calculator first input snip](https://github.com/Svetlin12/Calculator/blob/main/screenshots/calculator-snip-2.PNG)

Notice how the ***"+"*** button's border is different from the rest. This indicates that you have pressed an operation and the calculator is awaiting you to start typing the next number. If you press on one of the other operations ***("-", "/", "\*")***, the ***"+"*** button will restore its normal border and the operation that you just pressed will change its border. You can change the operation only whenever you haven't started inputting the second number for the operation. You can always raise the first operand to the power of two or three even after clicking on one of the operations as long as you haven't started inputting the second number.

* Let's input the second number. Notice how the border of the ***"+"*** operation got back to normal:

![calculator second input snip](https://github.com/Svetlin12/Calculator/blob/main/screenshots/calculator-snip-3.PNG)

* Let's evaluate the expression we just entered. You can do so in two ways:
  * Press the UI button ***"="*** in the calculator
  * Press ***"="*** keyboard button or ***Enter***
 
As a result, we get:

![calculator result snip](https://github.com/Svetlin12/Calculator/blob/main/screenshots/calculator-snip-4.PNG)

## Author

Svetlin Popivanov ([GitHub](https://github.com/Svetlin12)) 
