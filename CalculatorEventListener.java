import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculatorEventListener implements ActionListener, KeyListener {
    private final CalculatorUI ui;
    private double firstOperand = 0, secondOperand = 0;
    private short operation = -1; // -1 - no operation, 0 - add, 1 - subtract, 2 - multiply, 3 - divide
    private short numCompletedOperands = 0;
    private boolean isOperationActivated = false;
    private String lastInput = null;

    public CalculatorEventListener(CalculatorUI ui) {
        this.ui = ui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AnimatedButton source = (AnimatedButton)e.getSource();
        String caller = e.getActionCommand();
        String currentText = ui.text.getText();

        if (caller.equals(".") && currentText != null && !currentText.contains(".") && !isOperationActivated) {
            ui.text.setText(currentText + caller);
        }
        else if (isDigit(caller)) {
            if (isOperationActivated) {
                ui.text.setText(caller);
                isOperationActivated = false;
                revertRaisedBevelBorder(caller);
                lastInput = caller;
                return;
            }

            if (!caller.equals("0") && currentText != null && currentText.equals("0")) {
                ui.text.setText(caller);
                revertRaisedBevelBorder(caller);
                lastInput = caller;
                return;
            }

            String newNumber = (currentText + caller).replaceAll("^0+", "0");
            ui.text.setText(newNumber);
        }
        else if (caller.equals("Del") && currentText.length() != 0) {
            if (currentText.length() == 1) {
                ui.text.setText(null);
                numCompletedOperands = 0;
                operation = -1;
            }
            else {
                ui.text.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        else if (caller.equals("AC")) {
            ui.text.setText(null);
            numCompletedOperands = 0;
            operation = -1;
        }
        else if (isOperation(caller)) {
            if (currentText == null || currentText.equals("")) {
                return;
            }

            if (lastInput != null && isOperation(lastInput)) {
                changeOperation(caller);
                source.setBorder(BorderFactory.createEmptyBorder());
                revertRaisedBevelBorder(caller);
                lastInput = caller;
                return;
            }

            source.setBorder(BorderFactory.createEmptyBorder());

            setOperands(currentText);

            changeOperation(caller);
        }
        else if (caller.equals("=") && operation != -1) {
            secondOperand = Double.parseDouble(currentText);
            evaluateExpression();
            operation = -1;
            isOperationActivated = false;
            numCompletedOperands = 0;
        }

        revertRaisedBevelBorder(caller);

        lastInput = caller;
    }

    private void revertRaisedBevelBorder(String caller) {
        if (lastInput == null || lastInput.equals(caller)) {
            return;
        }

        switch (lastInput) {
            case "+":
                ui.sum.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case "-":
                ui.subtract.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case "/":
                ui.divide.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case "*":
                ui.multiply.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
        }
    }

    private void revertRaisedBevelBorder(Character caller) {
        if (lastInput == null || lastInput.equals(Character.toString(caller))) {
            return;
        }

        switch (lastInput) {
            case "+":
                ui.sum.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case "-":
                ui.subtract.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case "/":
                ui.divide.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case "*":
                ui.multiply.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
        }
    }

    private void changeOperation(String caller) {
        switch (caller) {
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

    private void changeOperation(char caller) {
        switch (caller) {
            case '*':
                operation = 2;
                break;
            case '/':
                operation = 3;
                break;
            case '+':
                operation = 0;
                break;
            case '-':
                operation = 1;
                break;
        }
    }

    private boolean isOperation(String input) {
        return input.equals("*") || input.equals("/") || input.equals("+") || input.equals("-");
    }

    private boolean isDigit(String buttonValue) {
        return buttonValue.equals("0") || buttonValue.equals("1") || buttonValue.equals("2") || buttonValue.equals("3") ||
                buttonValue.equals("4") || buttonValue.equals("5") || buttonValue.equals("6") || buttonValue.equals("7") ||
                buttonValue.equals("8") || buttonValue.equals("9");
    }

    private boolean isDigit(char keyValue) {
        return keyValue == '0' || keyValue == '1' || keyValue == '2' || keyValue == '3' || keyValue == '4' ||
                keyValue == '5' || keyValue == '6' || keyValue == '7' || keyValue == '8' || keyValue == '9';
    }

    private void setOperands(String currentText) {
        if (numCompletedOperands == 0) {
            firstOperand = Double.parseDouble(currentText);
            numCompletedOperands++;
            isOperationActivated = true;
        } else if (numCompletedOperands == 1) {
            secondOperand = Double.parseDouble(currentText);
            evaluateExpression();
            firstOperand = Double.parseDouble(ui.text.getText());
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
        String resultAsString;
        if (result > 1_000_000_000_000_000_000.0) {
            resultAsString = Double.toString(result);
        }
        else {
            resultAsString = String.format("%.10f", result).replaceAll("\\.0+$", "");
        }
        ui.text.setText(resultAsString);
    }

    private void setSource(Character source) {
        switch (source) {
            case '+':
                ui.sum.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case '-':
                ui.subtract.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case '/':
                ui.divide.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
            case '*':
                ui.multiply.setBorder(BorderFactory.createRaisedBevelBorder());
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // TODO: fix button animation when pressed
    // TODO: fix initial pressing of a key
    @Override
    public void keyPressed(KeyEvent e) {
        AnimatedButton source = (AnimatedButton)e.getSource();
        char keyName = e.getKeyChar();
        String currentText = ui.text.getText();

        if (keyName == '.' && currentText != null && !currentText.contains(".") && !isOperationActivated) {
            ui.text.setText(currentText + keyName);
        }
        else if (isDigit(keyName)) {
            if (isOperationActivated) {
                ui.text.setText(Character.toString(keyName));
                isOperationActivated = false;
                revertRaisedBevelBorder(keyName);
                lastInput = Character.toString(keyName);
                return;
            }

            if (keyName != '0' && currentText != null && currentText.equals("0")) {
                ui.text.setText(Character.toString(keyName));
                revertRaisedBevelBorder(keyName);
                lastInput = Character.toString(keyName);
                return;
            }

            String newNumber = (currentText + keyName).replaceAll("^0+", "0");
            ui.text.setText(newNumber);
        }
        else if (keyName == '\b' && currentText.length() != 0) {
            if (currentText.length() == 1) {
                ui.text.setText(null);
                numCompletedOperands = 0;
                operation = -1;
            }
            else {
                ui.text.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        else if ((e.isShiftDown() && (keyName == '*' || keyName == '+') || keyName == '-' || keyName == '/')) {
            if (currentText == null || currentText.equals("")) {
                return;
            }

            if (lastInput != null && isOperation(lastInput)) {
                changeOperation(keyName);
                setSource(keyName);
                revertRaisedBevelBorder(keyName);
                lastInput = Character.toString(keyName);
                return;
            }

            setSource(keyName);

            setOperands(currentText);

            changeOperation(keyName);
        }
        else if ((keyName == '=' || keyName == '\n') && operation != -1) {
            secondOperand = Double.parseDouble(currentText);
            evaluateExpression();
            operation = -1;
            isOperationActivated = false;
            numCompletedOperands = 0;
        }

        revertRaisedBevelBorder(keyName);
        lastInput = Character.toString(keyName);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
