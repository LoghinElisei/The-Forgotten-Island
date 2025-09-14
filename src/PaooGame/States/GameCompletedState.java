package PaooGame.States;

import PaooGame.Input.MouseManager;
import PaooGame.Maps.Map;
import PaooGame.Music.Music;
import PaooGame.Music.SoundPlayer;
import PaooGame.RefLinks;
import PaooGame.Graphics.Button;
import PaooGame.Timer.Timer;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de meniu pentru joc.
 */
public class GameCompletedState extends State
{
    private final ArrayList<Button> buttons;
    private BufferedImage blurredBackground;

    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public GameCompletedState(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        buttons = new ArrayList<>();

        int width = 200;
        int height = 50;
        int gap = 20;

        int screenWidth = refLink.GetWidth();
        int screenHeight = refLink.GetHeight();

        int totalMenuHeight = 3 * height + 2 * gap;

        int centerX = (screenWidth - width) / 2;
        int startY = (screenHeight - totalMenuHeight) / 2 + 40; // Am lasat un pic de spatiu pentru titlu


        buttons.add(new Button("main menu", new Rectangle(centerX, startY, width, height)));
        Map.timer.stop();

    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        handleMouseHover();
        handleMouseInput();
        handleMouseDrag();
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1408, 1056);
        g.setColor(new Color(255,255,0,200));
        g.setFont(new Font("Merriweather", Font.BOLD, 50));
        String text = "WINNER WINNER, CHICKEN DINNER";
        int x = getXForCenteredText(text, g);
        int y = refLink.GetHeight() / 4;  // Higher than buttons

        g.drawString(text, x, y);

        for (Button b: buttons)
        {
            b.draw(g, Color.decode("#FFFFFF"), Color.decode("#FFFFFF"), Color.decode("#324700"));
        }

    }

    private void handleMouseDrag() {
        MouseManager m = refLink.GetMouseManager();

        // Verificăm dacă mouse-ul este apăsat
        if (m.isMousePressed()) {
            Point mousePos = m.getMousePosition();
        }
    }

    private int getXForCenteredText(String text, Graphics2D g)
    {
        int length = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        int x = refLink.GetWidth() / 2  - length / 2;

        return x;
    }

    private void handleMouseHover() {
        Point mousePos = refLink.GetMouseManager().getMousePosition();

        for (Button b: buttons) {
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
                    handleClick();
                }
            }
        }

        refLink.GetMouseManager().clearMouseClick();
    }
    private void handleClick()
    {
        refLink.setState(refLink.GetGame().menuState);
        State.SetState(refLink.GetGame().menuState);
        Timer.reset();
    }
}

