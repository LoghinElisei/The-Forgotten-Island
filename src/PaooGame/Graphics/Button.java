package PaooGame.Graphics;

import java.awt.*;

public class Button {
    private Rectangle bounds;
    private String label;
    private boolean hovered = false;

    public Button(String label, Rectangle rect) {
        this.label = label;
        this.bounds = rect;
    }

    public void draw(Graphics2D g2d, Color background, Color text, Color hoverColor) {
        g2d.setColor(hovered ? hoverColor : background);
        if (hovered)
        {
            g2d.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20);
        }
        g2d.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20);

        g2d.setColor(text);
        g2d.setFont(new Font("Merriweather", Font.BOLD, 35));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(label);
        int textHeight = fm.getHeight();

        g2d.drawString(label, bounds.x + (bounds.width - textWidth) / 2,
                            bounds.y + (bounds.height + textHeight / 2) / 2);
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public String getLabel()
    {
        return label;
    }

    public boolean contains(Point p) {
        return bounds.contains(p);
    }
}
