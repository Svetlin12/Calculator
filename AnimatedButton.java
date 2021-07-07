import javax.swing.*;
import java.awt.*;

public class AnimatedButton extends JButton {
    private final Color hoverBackgroundColor;
    private final Color pressedBackgroundColor;

    public AnimatedButton(String text, Color background, Color foreground) {
        super(text); // build the button with initial text
        super.setContentAreaFilled(false);
        this.hoverBackgroundColor = background.brighter(); // hover behaviour - make the button a bit brighter
        this.pressedBackgroundColor = background.darker(); // pressed behaviour - make the button a bit darker
        this.setBackground(background);
        this.setForeground(foreground); 
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setHorizontalTextPosition(SwingConstants.CENTER); // center the text
        this.setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        }
        else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        }
        else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
