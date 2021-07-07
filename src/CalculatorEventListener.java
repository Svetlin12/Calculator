import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.text.ParseException;

public class CalculatorEventListener implements ActionListener, KeyListener {
    private final CalculatorUI ui;
    private double firstOperand = 0, secondOperand = 0;
    private boolean isSecondOperandEntered = false;
    private short operation = -1; // -1 - no operation, 0 - add, 1 - subtract, 2 - multiply, 3 - divide
    private short numCompletedOperands = 0;
    private boolean isOperationActivated = false, shouldClearTextField = false;
    private String lastInput = null;
    private AnimatedButton activeOperationButton = null; // will store the operation button that the user has pressed
    private DecimalFormat numberFormat = new DecimalFormat();

    public CalculatorEventListener(CalculatorUI ui) {
        this.ui = ui;
        numberFormat.setMaximumFractionDigits(10); // max digits after the decimal point
        numberFormat.setMaximumIntegerDigits(25); // max digits before the decimal point
    }

    // format the double to the defined DecimalFormat from numberFormat and replace ',' with '.'
    private String formatDouble(double number) {
        return numberFormat.format(number).replace(',', '.');
    }

    // format the number from the text field of the calculator to a double in the format defined in numberFormat
    private double getDouble(String input) throws ParseException {
        try {
            return numberFormat.parse(input).doubleValue();
        }
        catch (ParseException e) {
            throw e;
        }
    }

    // check if char or String is a digit
    private boolean isDigit(String value) {
        return value.equals("0") || value.equals("1") || value.equals("2") || value.equals("3") ||
                value.equals("4") || value.equals("5") || value.equals("6") || value.equals("7") ||
                value.equals("8") || value.equals("9");
    }

    // check if a String is a main operation
    private boolean isOperation(String input) {
        return input.equals("*") || input.equals("/") || input.equals("+") || input.equals("-");
    }

    // raise x to the power of 2
    private double secondPower(double x) {
        return x * x;
    }

    // raise x to the power of 3
    private double thirdPower(double x) {
        return secondPower(x) * x;
    }

    // switch the border of the active operation back to the default one (raised bevel border)
    private void revertToRaisedBevelBorder() {
        if (this.activeOperationButton != null) {
            this.activeOperationButton.setBorder(BorderFactory.createRaisedBevelBorder());
        }
    }

    private <E> void changeOperation(E caller) {
        switch(caller.toString()) {
            case "*":
                operation = 2;
                break;
            case "/":
                operation = 3;
                break;
            case "+":
                operation = 0;
                break;
            case "-":
                operation = 1;
                break;
        }
    }

    // set operands (first or second) depending on which one was entered
    private void setOperands(String currentText) {
        if (numCompletedOperands == 0) {
            try {
                firstOperand = getDouble(currentText);
            }
            catch (ParseException e) {
                System.out.println(e.getMessage()); // ideally, this should be printing to a log file
                System.exit(1);
            }

            numCompletedOperands++;
            isOperationActivated = true;
        } else if (numCompletedOperands == 1) {
            try {
                secondOperand = getDouble(currentText);
            }
            catch (ParseException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            evaluateExpression();

            try {
                firstOperand = getDouble(ui.getTextField().getText());
            }
            catch (ParseException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            isOperationActivated = true;
        }
    }

    private void evaluateExpression() {
        double result = 0;
        switch (operation) {
            case 0:
                result = firstOperand + secondOperand;
                break;
            case 1:
                result = firstOperand - secondOperand;
                break;
            case 2:
                result = firstOperand * secondOperand;
                break;
            case 3:
                result = firstOperand / secondOperand;
                break;
        }
        setUIText(formatDouble(result));
        shouldClearTextField = true;
    }

    // update text in the text field of the calculator
    private void setUIText(String text) {
        ui.getTextField().setText(text);
    } 

    // when the user presses on one of the buttons in the UI
    @Override
    public void actionPerformed(ActionEvent e) {
        AnimatedButton source = (AnimatedButton)e.getSource(); // get the pressed button
        String caller = e.getActionCommand(); // get the value of the pressed button
        String currentText = ui.getTextField().getText(); // get the text in the text field of the calculator

        // if the user has pressed the "." button and there aren't any other "." characters in the text field of the calculator, and the user hasn't pressed an operation beforehand
        if (caller.equals(".") && !currentText.contains(".") && !isOperationActivated) {
            setUIText(currentText + caller); // append the character "." to the end of the text field
        }
        // if the user has pressed on one of the digit buttons
        else if (isDigit(caller)) {
            if (isOperationActivated) { // if the user has pressed an operation beforehand then the user inputs the other number for the operation
                setUIText(caller); // clear the text field and write the digit
                isOperationActivated = false; 
                revertToRaisedBevelBorder(); // switch back the operation button over to the raised bevel border (the default border)
                lastInput = caller; // update lastInput since we're returning down below
                isSecondOperandEntered = true; // the user has started entering the second number
                activeOperationButton = null; // reset the active operation button (since the user started inputting the second operand)
                return;
            }

            if (currentText.equals("0") && !caller.equals("0") || shouldClearTextField) { // if the input digit is not 0 and the text on the calculator shows 0 (or alternatively, there is a result from a previous operation - clear the text field), then replace the 0
                setUIText(caller);
                revertToRaisedBevelBorder(); // just in case - it won't do anything if activeOperationButton is null
                lastInput = caller;
                shouldClearTextField = false;
                return;
            }

            setUIText((currentText + caller).replaceAll("^0+", "0")); // otherwise, append the character to the current string that is on the text field of the calculator and replace multiple starting zeros with one
        }
        else if (caller.equals("DEL") && !currentText.equals("0")) { // when the user presses "DEL" button and the text field does not contain the default 0
            if (isOperationActivated == true) { // if operation was clicked on the last step, return to the state where there isn't a first operand inputted yet
                revertToRaisedBevelBorder(); // make the border of the active operation back to the default one
                activeOperationButton = null; // no operation is set for now, so clear this variable
                isOperationActivated = false;
                operation = -1; // no operation is set anymore
                firstOperand = 0; // first operand is no longer set, since one digit was deleted
                numCompletedOperands = 0; // first operand is not yet completed
                isSecondOperandEntered = false;
            }

            if (currentText.length() == 1) { // if the text field has one character only, then switch to default 0
                setUIText("0");
            }
            else {
                setUIText(currentText.substring(0, currentText.length() - 1)); // otherwise, remove one character from the end of the string (meaning - remove one digit)
            }
        }
        else if (caller.equals("AC")) { // if the user has pressed "AC" button
            if (isOperationActivated == true) { // if operation was clicked on the last step, return to the state where there isn't a first operand inputted yet
                revertToRaisedBevelBorder(); // make the border of the active operation back to normal if there is an active operation
                activeOperationButton = null; // clear the variable, since everything was cleared
                isOperationActivated = false;
                firstOperand = 0;
            }

            setUIText("0");
            numCompletedOperands = 0;
            operation = -1;
            isSecondOperandEntered = false;
        }
        else if (isOperation(caller)) { // if the user has pressed any of the operations: "+", "-", "/", "*"
            if (lastInput != null && isOperation(lastInput)) { // if the last input was an operation, switch the active operation
                changeOperation(caller); // switch the operation number
                revertToRaisedBevelBorder(); // make the border of the last operation back to the default one
                activeOperationButton = source; // update the current active operation border
                source.setBorder(BorderFactory.createLoweredBevelBorder()); // switch the border of the active operation to a border that represents a pressed button
                lastInput = caller;
                return;
            }

            source.setBorder(BorderFactory.createLoweredBevelBorder());
            this.activeOperationButton = source;

            setOperands(currentText); // update the firstOperand variable, since operation was pressed
            changeOperation(caller); // update operation variable
            shouldClearTextField = false; // don't clear the previous result just yet
        }
        else if (caller.equals("=") && operation != -1) { // if the user has pressed "=" and there was not a previously entered operation
            try {
                secondOperand = getDouble(currentText); // update secondOperand variable from the text field of the calculator
            }
            catch (ParseException exc) {
                System.out.println(exc.getMessage());
                System.exit(1);
            }

            evaluateExpression();
            operation = -1;
            isOperationActivated = false;
            numCompletedOperands = 0;

            if (isOperation(lastInput) || activeOperationButton != null) { // if the user has pressed for example '*' and then '=', revert to the default border of the button
                revertToRaisedBevelBorder();
            }
        }
        else if (caller.equals("x\u00B2")) {
            if (numCompletedOperands == 0 && !ui.getTextField().getText().isEmpty()) { // if the user hasn't yet inputted the first operand, then the current number must be the first operand for now - raise it to the power of 2
                double result = 0;
                try {
                    result = secondPower(getDouble(ui.getTextField().getText())); // get the current number on the text field of the calculator and evaluate x*x
                }
                catch (ParseException exc) {
                    System.out.println(exc.getMessage());
                    System.exit(1);
                }
                setUIText(formatDouble(result));
            }
            else if (numCompletedOperands == 1 && !isSecondOperandEntered) { // if the user has already inputted the first operand and he has pressed an operation but hasn't started inputting digits for the second operand, then raise the first operand to the power of 2
                firstOperand = secondPower(firstOperand);
                setUIText(formatDouble(firstOperand));
            }
            else if (numCompletedOperands == 1 && isSecondOperandEntered) { // if the user has already started inputting the second operand, raise it to the power of 2
                double result = 0;
                try {
                    result = secondPower(getDouble(ui.getTextField().getText()));
                }
                catch (ParseException exc) {
                    System.out.println(exc.getMessage());
                    System.exit(1);
                }
                
                setUIText(formatDouble(result));
            }
        }
        else if (caller.equals("x\u00B3")) { // logic behind raising to the power of 3 is the same as above
            if (numCompletedOperands == 0 && !ui.getTextField().getText().isEmpty()) {
                double result = 0;
                try {
                    result = thirdPower(getDouble(ui.getTextField().getText()));
                }
                catch (ParseException exc) {
                    System.out.println(exc.getMessage());
                    System.exit(1);
                }

                setUIText(formatDouble(result));
            }
            else if (numCompletedOperands == 1 && !isSecondOperandEntered) {
                firstOperand = thirdPower(firstOperand);
                setUIText(formatDouble(firstOperand));
            }
            else if (numCompletedOperands == 1 && isSecondOperandEntered) {
                double result = 0;
                try {
                    result = thirdPower(getDouble(ui.getTextField().getText()));
                }
                catch (ParseException exc) {
                    System.out.println(exc.getMessage());
                    System.exit(1);
                }
                setUIText(formatDouble(result));
            }
        }

        lastInput = caller; // update last input after the end of each input using the UI buttons
    }

    // when the user presses on one of the keys we are listening to
    @Override
    public void keyTyped(KeyEvent e) {
        char keyName = e.getKeyChar();
        String currentText = ui.getTextField().getText();

         // if the user has pressed the "." key and there aren't any other "." characters in the text field of the calculator, and the user hasn't pressed an operation beforehand
        if (keyName == '.' && !currentText.contains(".") && !isOperationActivated) {
            setUIText(currentText + keyName); // append the character "." to the end of the text field
        }
        // if the user has pressed on one of the digit keys
        else if (isDigit(Character.toString(keyName))) {
            if (isOperationActivated) { // if the user has pressed an operation beforehand then the user inputs the other number for the operation
                setUIText(Character.toString(keyName));
                isOperationActivated = false;
                revertToRaisedBevelBorder(); // switch back the operation button over to the raised bevel border (the default border)
                lastInput = Character.toString(keyName); // update lastInput since we're returning down below
                isSecondOperandEntered = true; // the user has started entering the second number
                activeOperationButton = null; // reset the active operation button (since the user started inputting the second operand)
                return;
            }

            if (keyName != '0' && currentText.equals("0") || shouldClearTextField) { // if the input digit is not 0 and the text on the calculator shows 0 (or alternatively, there is a result from a previous operation - clear the text field), then replace the 0
                setUIText(Character.toString(keyName));
                revertToRaisedBevelBorder();
                lastInput = Character.toString(keyName);  // just in case - it won't do anything if activeOperationButton is null
                shouldClearTextField = false;
                return;
            }

            setUIText((currentText + keyName).replaceAll("^0+", "0")); // otherwise, append the character to the current string that is on the text field of the calculator and replace multiple starting zeros with one
        }
        else if (keyName == '\b' && !currentText.equals("0")) {
            if (isOperationActivated == true) {
                revertToRaisedBevelBorder(); // make the border of the active operation back to the default one
                activeOperationButton = null; // no operation is set for now, so clear this variable
                isOperationActivated = false;
                operation = -1; // no operation is set anymore
                firstOperand = 0; // first operand is no longer set, since one digit was deleted
                numCompletedOperands = 0; // first operand is not yet completed
                isSecondOperandEntered = false;
            }

            if (currentText.length() == 1) { // if the text field has one character only, then switch to default 0
                setUIText("0");
            }
            else {
                setUIText(currentText.substring(0, currentText.length() - 1)); // otherwise, remove one character from the end of the string (meaning - remove one digit)
            }
        }
        else if (e.isShiftDown() && isOperation(Character.toString(keyName)) || keyName == '-' || keyName == '/') {  // if the user has pressed any of the operations: "+", "-", "/", "*"
            AnimatedButton source = ui.getOperationButton(keyName); // get the operation associated with the pressed key
            if (lastInput != null && isOperation(lastInput)) { // if the last input was an operation, switch the active operation
                changeOperation(keyName); // switch the operation number
                revertToRaisedBevelBorder(); // make the border of the last operation back to the default one
                activeOperationButton = source; // update the current active operation border
                source.setBorder(BorderFactory.createLoweredBevelBorder()); // switch the border of the active operation to a border that represents a pressed button
                lastInput = Character.toString(keyName);
                return;
            }

            source.setBorder(BorderFactory.createLoweredBevelBorder());
            this.activeOperationButton = source;

            setOperands(currentText); // update the firstOperand variable, since operation was pressed
            changeOperation(keyName); // update operation variable
            shouldClearTextField = false; // don't clear the previous result just yet
        }
        else if ((keyName == '=' || keyName == '\n') && operation != -1) { // if the user has pressed "=" or Enter and there was not a previously entered operation
            try {
                secondOperand = getDouble(currentText);
            }
            catch (ParseException exc) {
                System.out.println(exc.getMessage());
                System.exit(1);
            }

            evaluateExpression();
            operation = -1;
            isOperationActivated = false;
            numCompletedOperands = 0;

            if (isOperation(lastInput) || activeOperationButton != null) { // if the user has pressed for example '*' and then '=', revert to the default border of the button
                revertToRaisedBevelBorder();
            }
        }

        lastInput = Character.toString(keyName);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}