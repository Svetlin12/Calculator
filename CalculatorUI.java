import javax.swing.*;
import java.awt.*;

public class CalculatorUI extends JFrame {
    CalculatorEventListener listener = new CalculatorEventListener(this);

    // row where the numbers will show up
    JPanel row1 = new JPanel();
    JTextField text = new JTextField(null);

    // buttons for the calculator are going to be located
    JPanel buttonRow = new JPanel();
    JPanel digitsPanel = new JPanel();
    JPanel mainFunctions = new JPanel();
    JPanel otherFunctions = new JPanel();
    JButton sum = new JButton("+");
    JButton subtract = new JButton("-");
    JButton divide = new JButton("/");
    JButton multiply = new JButton("*");
    JButton equals = new JButton("=");
    JButton one = new JButton("1");
    JButton two = new JButton("2");
    JButton three = new JButton("3");
    JButton four = new JButton("4");
    JButton five = new JButton("5");
    JButton six = new JButton("6");
    JButton seven = new JButton("7");
    JButton eight = new JButton("8");
    JButton nine = new JButton("9");
    JButton zero = new JButton("0");
    JButton decimalPoint = new JButton(".");
    JButton toSecondPower = new JButton("x2");
    JButton toThirdPower = new JButton("x3");
    JButton clear = new JButton("AC");
    JButton delete = new JButton("Del");

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
        row1.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        text.setEnabled(false);
        Font arial = new Font("Arial", Font.BOLD, 20);
        text.setFont(arial);
        text.setBackground(Color.darkGray);
        text.setForeground(Color.white);
        text.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        row1.add(text, BorderLayout.CENTER);
        add(row1);

        // second row
        BorderLayout buttonsBorder = new BorderLayout();
        buttonRow.setLayout(buttonsBorder);

        Color digitsBackgroundColor = Color.darkGray;
        Color digitsTextColor = Color.white;
        Color operationsBackgroundColor = new Color(247, 219, 74);

        // digits
        GridLayout digitsGrid = new GridLayout(4, 3);
        digitsPanel.setLayout(digitsGrid);

        setButton(one, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(two, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(three, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(four, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(five, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(six, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(seven, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(eight, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(nine, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(zero, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(decimalPoint, digitsBackgroundColor, digitsTextColor, digitsPanel);
        setButton(equals, operationsBackgroundColor, null, digitsPanel);
        buttonRow.add(digitsPanel, BorderLayout.CENTER);

        // main operations
        GridLayout mainOperationsGrid = new GridLayout(4, 1);
        mainFunctions.setLayout(mainOperationsGrid);
        setButton(sum, operationsBackgroundColor, null, mainFunctions);
        sum.setPreferredSize(new Dimension(60, 30));
        setButton(subtract, operationsBackgroundColor, null, mainFunctions);
        setButton(divide, operationsBackgroundColor, null, mainFunctions);
        setButton(multiply, operationsBackgroundColor, null, mainFunctions);
        buttonRow.add(mainFunctions, BorderLayout.EAST);

        // other functions
        GridLayout otherOperationsGrid = new GridLayout(4, 1);
        otherFunctions.setLayout(otherOperationsGrid);
        setButton(clear, operationsBackgroundColor, null, otherFunctions);
        clear.setPreferredSize(new Dimension(60, 30));
        setButton(delete, operationsBackgroundColor, null, otherFunctions);
        setButton(toSecondPower, operationsBackgroundColor, null, otherFunctions);
        setButton(toThirdPower, operationsBackgroundColor, null, otherFunctions);
        buttonRow.add(otherFunctions, BorderLayout.WEST);
        add(buttonRow);

        setVisible(true);
    }

    private void setButton(JButton button, Color background, Color foreground, JPanel panel) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
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
