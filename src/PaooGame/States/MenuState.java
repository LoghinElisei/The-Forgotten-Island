package PaooGame.States;

import PaooGame.Input.MouseManager;
import PaooGame.Music.SoundPlayer;
import PaooGame.RefLinks;
import PaooGame.Graphics.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de meniu pentru joc.
 */
public class MenuState extends State
{
    private ArrayList<Button> buttons;
    private Image backgroundImage;

    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public MenuState(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        buttons = new ArrayList<>();

        int width = 230;
        int height = 60;
        int gap = 20;

        int screenWidth = refLink.GetWidth();
        int screenHeight = refLink.GetHeight();

        int totalMenuHeight = 4 * height + 2 * gap;

        int centerX = (screenWidth - width) / 2;
        int startY = (screenHeight - totalMenuHeight) / 2 + 20; // Am lasat un pic de spatiu pentru titlu

        buttons.add(new Button("Load Game", new Rectangle(centerX, startY + buttons.size() * (height + gap), width, height)));
        buttons.add(new Button("New Game", new Rectangle(centerX, startY + buttons.size() * (height + gap), width, height)));
        buttons.add(new Button("ScoreBoard", new Rectangle(centerX, startY + buttons.size() * (height + gap), width, height)));
        buttons.add(new Button("Leave", new Rectangle(centerX, startY + buttons.size() * (height + gap), width, height)));

        backgroundImage = new ImageIcon("res/images/island_animated.gif").getImage();




    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
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
//            int imgW = backgroundImage.getWidth();
//            int imgH = backgroundImage.getHeight();
//
//            float scaleW = (float) refLink.GetWidth() / imgW;
//            float scaleH = (float) refLink.GetHeight() / imgH;
//            float scale = Math.max(scaleW, scaleH); // Or use Math.min to avoid cropping
//
//            int drawW = (int)(imgW * scale);
//            int drawH = (int)(imgH * scale);
//
//            int x = (refLink.GetWidth() - drawW) / 2;
//            int y = (refLink.GetHeight() - drawH) / 2;

            g2d.drawImage(backgroundImage, 0, 0, refLink.GetWidth(), refLink.GetHeight(), null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, refLink.GetWidth(), refLink.GetHeight());
        }

        g2d.setColor(Color.decode("#00697d"));
        g2d.setFont(new Font("Roboto", Font.BOLD, 48));
        String text = "MAIN MENU";

        int x = getXForCenteredText(text, g2d);
        int y = refLink.GetHeight() / 4;  // Higher than buttons

        g2d.drawString(text, x, y+40);
        for (Button b: buttons)
        {
            b.draw(g2d, Color.decode("#0E161B"), Color.decode("#0E161B"), Color.decode("#C0C49C"));
        }


    }

    private int getXForCenteredText(String text, Graphics2D g2d)
    {
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = refLink.GetWidth() / 2  - length / 2;

        return x;
    }

    private void handleMouseInput() {
        MouseManager m;
        m = refLink.GetMouseManager();

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

    private void handleMouseHover() {
        Point mousePos = refLink.GetMouseManager().getMousePosition();
        for (Button b: buttons) {
            b.setHovered(b.getBounds().contains(mousePos));
        }
    }
    private void handleClick(String label)
    {

        switch (label) {
            case "Load Game":
                SoundPlayer.playSound();
                //refLink.database.createLevelsTable();
                refLink.GetGame().aboutState = new AboutState(refLink,1);
                refLink.setState(refLink.GetGame().aboutState);
                State.SetState(refLink.GetGame().aboutState);
                break;
            case "New Game":
                SoundPlayer.playSound();
                System.out.println("New Game");
                refLink.GetGame().aboutState = new AboutState(refLink,2);
                refLink.setState(refLink.GetGame().aboutState);
                State.SetState(refLink.GetGame().aboutState);
                break;
            case "ScoreBoard":
                SoundPlayer.playSound();
                refLink.GetGame().scoreboardState = new ScoreboardState(refLink);
                refLink.setState(refLink.GetGame().scoreboardState);
                State.SetState(refLink.GetGame().scoreboardState);
                break;
            case "Leave":
                System.exit(0);
        }
    }


}