import javax.swing.*;
import java.awt.*;

public class CalculatorUI extends JFrame {
    CalculatorEventListener listener = new CalculatorEventListener(this);

    // colors for the buttons
    Color digitsBackgroundColor = Color.darkGray;
    Color operationsBackgroundColor = new Color(247, 219, 74);
    Color digitsForegroundColor = Color.white;
    Color operationsForegroundColor = Color.black;

    // row where the numbers will show up
    JPanel row1 = new JPanel();
    JTextField text = new JTextField(null);

    // buttons for the calculator are going to be located
    JPanel buttonRow = new JPanel();
    JPanel digitsPanel = new JPanel();
    JPanel mainFunctions = new JPanel();
    JPanel otherFunctions = new JPanel();
    AnimatedButton sum = new AnimatedButton("+", operationsBackgroundColor, operationsForegroundColor);
    AnimatedButton subtract = new AnimatedButton("-", operationsBackgroundColor, operationsForegroundColor);
    AnimatedButton divide = new AnimatedButton("/", operationsBackgroundColor, operationsForegroundColor);
    AnimatedButton multiply = new AnimatedButton("*", operationsBackgroundColor, operationsForegroundColor);
    AnimatedButton equals = new AnimatedButton("=", operationsBackgroundColor, operationsForegroundColor);
    AnimatedButton one = new AnimatedButton("1", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton two = new AnimatedButton("2", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton three = new AnimatedButton("3", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton four = new AnimatedButton("4", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton five = new AnimatedButton("5", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton six = new AnimatedButton("6", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton seven = new AnimatedButton("7", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton eight = new AnimatedButton("8", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton nine = new AnimatedButton("9", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton zero = new AnimatedButton("0", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton decimalPoint = new AnimatedButton(".", digitsBackgroundColor, digitsForegroundColor);
    AnimatedButton toSecondPower = new AnimatedButton("x\u00B2", operationsBackgroundColor, operationsForegroundColor);
    AnimatedButton toThirdPower = new AnimatedButton("x\u00B3", operationsBackgroundColor, operationsForegroundColor);
    AnimatedButton clear = new AnimatedButton("AC", operationsBackgroundColor, operationsForegroundColor);
    AnimatedButton delete = new AnimatedButton("Del", operationsBackgroundColor, operationsForegroundColor);

    public CalculatorUI() {
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
    }

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

    public static void main(String[] args) {
        CalculatorUI.setLookAndFeel();
        new CalculatorUI();
    }
}
