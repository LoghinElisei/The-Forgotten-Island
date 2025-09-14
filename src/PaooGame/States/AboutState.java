package PaooGame.States;

import PaooGame.Graphics.Button;
import PaooGame.Input.MouseManager;
import PaooGame.Maps.Map;
import PaooGame.Maps.Map2;
import PaooGame.Maps.Map3;
import PaooGame.Music.SoundPlayer;
import PaooGame.RefLinks;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*! \class public class AboutState extends State
    \brief Implementeaza notiunea de credentiale (about)
 */
public class AboutState extends State
{
    private final ArrayList<PaooGame.Graphics.Button> buttons;
    private final Image backgroundImage;
    private int option = 1;

    public AboutState(RefLinks refLink,int option)
    {

        ///Apel al constructorului clasei de baza.
        super(refLink);
        buttons = new ArrayList<>();

        int width = 300;
        int height = 70;
        int gap = 20;
        int screenWidth = refLink.GetWidth();
        int screenHeight = refLink.GetHeight();

        int totalMenuHeight = 3 * height + 2 * gap;

        int centerX = (screenWidth - width) / 2;
        int startY = (screenHeight - totalMenuHeight) / 2 + 40; // Am lasat un pic de spatiu pentru titlu

        buttons.add(new PaooGame.Graphics.Button("Continue", new Rectangle(centerX, startY+400, width, height)));

        backgroundImage = new ImageIcon("res/images/about.png").getImage();

        this.option = option;
    }

    @Override
    public void Update()
    {
        handleMouseHover();
        handleMouseInput();
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics2D g2d)
    {
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, refLink.GetWidth(), refLink.GetHeight(), null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, refLink.GetWidth(), refLink.GetHeight());
        }

        g2d.setColor(new Color(255,255,255,200));
        g2d.setFont(new Font("Merriweather", Font.BOLD, 50));

        int y = refLink.GetHeight() / 4;  // Higher than buttons

        for (PaooGame.Graphics.Button b: buttons)
        {
            b.draw(g2d, Color.decode("#FFFFFF"), Color.BLACK, Color.decode("#324700"));
        }


    }


    private void handleMouseHover() {
        Point mousePos = refLink.GetMouseManager().getMousePosition();

        for (PaooGame.Graphics.Button b: buttons) {
            b.setHovered(b.getBounds().contains(mousePos));
        }
    }

    private void handleMouseInput() {
        MouseManager m = refLink.GetMouseManager();

        if (m.isMouseClicked())
        {
            Point mousePos = m.getMousePosition();
            for (Button b: buttons)
            {
                if (b.getBounds().contains(mousePos)) {
                    handleClick(b.getLabel());
                }
            }
        }
        refLink.GetMouseManager().clearMouseClick();
    }

    private void handleClick(String label)
    {

        if (label.equals("Continue") ) {
            SoundPlayer.playSound();
            refLink.GetGame().welcomeState = new WelcomeState(refLink,option);
            refLink.setState(refLink.GetGame().welcomeState);
            State.SetState(refLink.GetGame().welcomeState);

        }
    }
}