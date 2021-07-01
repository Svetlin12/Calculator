import javax.swing.*;
import java.awt.*;

public class AnimatedButton extends JButton {
    private final Color hoverBackgroundColor;
    private final Color pressedBackgroundColor;

    public AnimatedButton(String text, Color background, Color foreground) {
        super(text);
        super.setContentAreaFilled(false);
        this.hoverBackgroundColor = background.brighter();
        this.pressedBackgroundColor = background;
        this.setBackground(background);
        this.setForeground(foreground);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setHorizontalTextPosition(SwingConstants.CENTER);
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
