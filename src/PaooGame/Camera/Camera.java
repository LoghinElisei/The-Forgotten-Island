package PaooGame.Camera;
import PaooGame.Entity.Character;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Camera {
    private Rectangle bounds; // limitele vizibile ale camerei
    private int x; //coordonata x a camerei
    private int y; //coordonata y a camerei

    // starea camerei; poziție și dimensiune
    public Camera(int x, int y, int width, int height) {
        this.bounds = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
    }
    // metoda prin care se setează poziția camerei
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // metoda prin care se setează factorul de scalare, daca este nevoie de el
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    // metoda de aplicare a matricei de vizualizare a camerei
    public void apply(Graphics2D g2d) {
        AffineTransform transform = new AffineTransform();
        transform.translate(-x, -y); // translarea poziției camerei
        g2d.setTransform(transform);
    }
    // metoda returnează limitele vizibile ale camerei
    public Rectangle getBounds() {
        return bounds;
    }
    public void updateCamera(Character target)
    {
        this.x = (int)(target.GetX() - bounds.width / 2.0 + target.GetWidth() / 2.0);
        this.y = (int)(target.GetY() - bounds.height / 2.0 + target.GetHeight() / 2.0);
        bounds.setLocation(x ,y);
    }
}
