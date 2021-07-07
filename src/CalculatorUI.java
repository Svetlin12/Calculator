import javax.swing.*;
import java.awt.*;

public class CalculatorUI extends JFrame {
    static CalculatorUI instance = null; // in this variable we shall store the only instance this class can create (Singleton)

    private final CalculatorEventListener listener = new CalculatorEventListener(this); // reacts to button presses

    // colors for the buttons
    private final Color digitsBackgroundColor = Color.darkGray;
    private final Color operationsBackgroundColor = new Color(247, 219, 74);
    private final Color digitsForegroundColor = Color.white; // the color of the text inside the digits buttons
    private final Color operationsForegroundColor = Color.black; // the color of the text inside the operations buttons ('+', '-', '/', '*', '=' and so on)

    // row where the numbers will show up
    private final JPanel row1 = new JPanel(); // panel for the UI of the Calculator
    private final JTextField text = new JTextField("0"); // default to displaying 0

    /* create the panels which will hold the buttons of the calculator */
    private final JPanel buttonRow = new JPanel(); // panel for all the buttons
    private final JPanel digitsPanel = new JPanel(); // panel for the digit buttons
    private final JPanel mainFunctions = new JPanel(); // panel for operations such as +, -, /, * and so on
    private final JPanel otherFunctions = new JPanel(); // panel for operations such as AC, DEL and so on

    /* create the buttons of the calculator */
    private final AnimatedButton sum = createButton("+", operationsBackgroundColor, operationsForegroundColor);
    private final AnimatedButton subtract = createButton("-", operationsBackgroundColor, operationsForegroundColor);
    private final AnimatedButton divide = createButton("/", operationsBackgroundColor, operationsForegroundColor);
    private final AnimatedButton multiply = createButton("*", operationsBackgroundColor, operationsForegroundColor);
    private final AnimatedButton equals = createButton("=", operationsBackgroundColor, operationsForegroundColor);
    private final AnimatedButton one = createButton("1", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton two = createButton("2", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton three = createButton("3", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton four = createButton("4", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton five = createButton("5", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton six = createButton("6", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton seven = createButton("7", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton eight = createButton("8", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton nine = createButton("9", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton zero = createButton("0", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton decimalPoint = createButton(".", digitsBackgroundColor, digitsForegroundColor);
    private final AnimatedButton toSecondPower = createButton("x\u00B2", operationsBackgroundColor, operationsForegroundColor);
    private final AnimatedButton toThirdPower = createButton("x\u00B3", operationsBackgroundColor, operationsForegroundColor);
    private final AnimatedButton clear = createButton("AC", operationsBackgroundColor, operationsForegroundColor);
    private final AnimatedButton delete = createButton("DEL", operationsBackgroundColor, operationsForegroundColor);

    private static AnimatedButton createButton(String text, Color backgroundColor, Color foregroundColor) {
        return new AnimatedButton(text, backgroundColor, foregroundColor);
    }

    // Singleton instance
    public static CalculatorUI getInstance() {
        if (instance == null) {
            instance = new CalculatorUI();
        }
        return instance;
    }

    private CalculatorUI() {
        super("Calculator");
        setSize(500, 400);
        GridLayout grid = new GridLayout(2, 1);
        setLayout(grid);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // first row
        BorderLayout border = new BorderLayout();
        row1.setLayout(border);
        text.setEditable(false);
        text.setFont(new Font("Arial", Font.PLAIN, 20).deriveFont((float)25));
        text.setBackground(Color.darkGray);
        text.setForeground(Color.white);
        text.setBorder(BorderFactory.createRaisedBevelBorder());
        row1.add(text, BorderLayout.CENTER);
        add(row1);
        text.addKeyListener(listener); // the buttons down below don't get the initial focus and so the key listener won't react on input (this component gets the initial focus)

        // second row
        BorderLayout buttonsBorder = new BorderLayout();
        buttonRow.setLayout(buttonsBorder);

        // digits
        GridLayout digitsGrid = new GridLayout(4, 3);
        digitsPanel.setLayout(digitsGrid);

        setButton(one, digitsPanel);
        setButton(two, digitsPanel);
        setButton(three, digitsPanel);
        setButton(four, digitsPanel);
        setButton(five, digitsPanel);
        setButton(six, digitsPanel);
        setButton(seven, digitsPanel);
        setButton(eight, digitsPanel);
        setButton(nine, digitsPanel);
        setButton(zero, digitsPanel);
        setButton(decimalPoint, digitsPanel);
        setButton(equals, digitsPanel);
        buttonRow.add(digitsPanel, BorderLayout.CENTER);

        // main operations
        GridLayout mainOperationsGrid = new GridLayout(4, 1);
        mainFunctions.setLayout(mainOperationsGrid);
        setButton(sum, mainFunctions);
        sum.setPreferredSize(new Dimension(60, 30));
        setButton(subtract, mainFunctions);
        setButton(divide, mainFunctions);
        setButton(multiply, mainFunctions);
        buttonRow.add(mainFunctions, BorderLayout.EAST);

        // other functions
        GridLayout otherOperationsGrid = new GridLayout(4, 1);
        otherFunctions.setLayout(otherOperationsGrid);
        setButton(clear, otherFunctions);
        clear.setPreferredSize(new Dimension(60, 30));
        setButton(delete, otherFunctions);
        setButton(toSecondPower, otherFunctions);
        setButton(toThirdPower, otherFunctions);
        buttonRow.add(otherFunctions, BorderLayout.WEST);
        add(buttonRow);

        setVisible(true);
        setLookAndFeel();
    }

    // getter for the text field of the calculator (the place where the numbers will be displayed)
    public JTextField getTextField() {
        return this.text;
    }

    // getters to buttons - they provide read access to the buttons
    public AnimatedButton getSumButton() {
        return this.sum;
    }

    public AnimatedButton getSubtractButton() {
        return this.subtract;
    }

    public AnimatedButton getDivideButton() {
        return this.divide;
    }

    public AnimatedButton getMultiplyButton() {
        return this.multiply;
    }

    public AnimatedButton getEqualsButton() {
        return this.equals;
    }

    public AnimatedButton getOneButton() {
        return this.one;
    }

    public AnimatedButton getTwoButton() {
        return this.two;
    }

    public AnimatedButton getThreeButton() {
        return this.three;
    }

    public AnimatedButton getFourButton() {
        return this.four;
    }

    public AnimatedButton getFiveButton() {
        return this.five;
    }

    public AnimatedButton getSixButton() {
        return this.six;
    }

    public AnimatedButton getSevenButton() {
        return this.seven;
    }

    public AnimatedButton getEightButton() {
        return this.eight;
    }

    public AnimatedButton getNineButton() {
        return this.nine;
    }

    public AnimatedButton getZeroButton() {
        return this.zero;
    }

    public AnimatedButton getDecimalPointButton() {
        return this.decimalPoint;
    }

    public AnimatedButton getToSecondPowerButton() {
        return this.toSecondPower;
    }

    public AnimatedButton getToThirdPowerButton() {
        return this.toThirdPower;
    }

    public AnimatedButton getClearButton() {
        return this.clear;
    }

    public AnimatedButton getDeleteButton() {
        return this.delete;
    }

    public AnimatedButton getOperationButton(Character value) {
        if (value == '*') {
            return multiply;
        }
        else if (value == '/') {
            return divide;
        }
        else if (value == '+') {
            return sum;
        }
        else if (value == '-') {
            return subtract;
        }
        else {
            return null;
        }
    }

    // add listeners for clicks and key presses and attach button to the panel
    private void setButton(AnimatedButton button, JPanel panel) {
        button.addActionListener(listener);
        button.addKeyListener(listener);
        panel.add(button);
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ignored) {
        }
    }
}