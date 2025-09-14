package PaooGame.Input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
    private boolean mouseClicked;
    private boolean mousePressed;
    private int mouseX, mouseY;

    public MouseManager() {
        mouseX = 0;
        mouseY = 0;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public Point getMousePosition() {
        return new Point(mouseX, mouseY);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = true;
        mousePressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseMoved(e);
    }

    public void clearMouseClick() {
        mouseClicked = false;
    }

    public void mouseClicked(MouseEvent e) {}      // Not usually used
    public void mouseEntered(MouseEvent e) {}      // Optional
    public void mouseExited(MouseEvent e) {}       // Optional
}
