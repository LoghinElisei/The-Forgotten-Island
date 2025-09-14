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
public class PauseState extends State
{
    private final ArrayList<Button> buttons;
    private BufferedImage blurredBackground;
    private final Rectangle volumeSlider;
    private int volumeLevel = 40;

    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PauseState(RefLinks refLink, BufferedImage screenshot)
    {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        buttons = new ArrayList<>();
        blurredBackground = applyBlur(screenshot);

        int width = 200;
        int height = 50;
        int gap = 20;

        int screenWidth = refLink.GetWidth();
        int screenHeight = refLink.GetHeight();

        int totalMenuHeight = 3 * height + 2 * gap;

        int centerX = (screenWidth - width) / 2;
        int startY = (screenHeight - totalMenuHeight) / 2 + 40; // Am lasat un pic de spatiu pentru titlu


        buttons.add(new Button("Continue", new Rectangle(centerX, startY, width, height)));
        buttons.add(new Button("Save", new Rectangle(centerX, startY + height + gap, width, height)));
        buttons.add(new Button("Exit", new Rectangle(centerX, startY + 2 * (height + gap), width, height)));

        //slider volum
        int sliderWidth = 300;
        int sliderHeight = 50;
        int sliderX = (screenWidth - sliderWidth) / 2;
        int sliderY = startY + 3 * (height + gap) + 40; // Sub butoane
        volumeSlider = new Rectangle(sliderX, sliderY, sliderWidth, sliderHeight);

        Timer.stop();

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
        g.drawImage(blurredBackground, 0, 0, null);

        g.setColor(new Color(255,255,255,200));
        g.setFont(new Font("Merriweather", Font.BOLD, 50));
        String text = "PAUSE";
        int x = getXForCenteredText(text, g);
        int y = refLink.GetHeight() / 4;  // Higher than buttons

        g.drawString(text, x, y);

        for (Button b: buttons)
        {
            b.draw(g, Color.decode("#FFFFFF"), Color.decode("#FFFFFF"), Color.decode("#324700"));
        }

        drawVolumeSlider(g);
    }

    private void handleMouseDrag() {
        MouseManager m = refLink.GetMouseManager();

        // Verificăm dacă mouse-ul este apăsat
        if (m.isMousePressed()) {
            Point mousePos = m.getMousePosition();

            // Dacă mouse-ul este deasupra slider-ului
            if (volumeSlider.contains(mousePos)) {
                int sliderX = mousePos.x - volumeSlider.x;
                volumeLevel = Math.max(0, Math.min(100, (int) (sliderX / (float) volumeSlider.width * 100)));
                Music.getInstance().setVolume(volumeLevel / 100.0f); // Actualizează volumul în timp real
            }
        }
    }

    private void drawVolumeSlider(Graphics2D g) {
        // Fundalul slider-ului
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(volumeSlider.x, volumeSlider.y, volumeSlider.width, volumeSlider.height);

        // Nivelul volumului (bară umplută)
        g.setColor(Color.GREEN);
        int filledWidth = (int) (volumeSlider.width * (volumeLevel / 100.0));
        g.fillRect(volumeSlider.x, volumeSlider.y, filledWidth, volumeSlider.height);

        // Textul volumului în interiorul slider-ului
        String volumeText = volumeLevel + "%";
        g.setFont(new Font("Arial", Font.BOLD, 27));
        g.setColor(Color.BLACK); // Culoare text (asigură contrast cu fundalul verde)

        // Calculăm poziția textului pentru a fi centrat
        int textWidth = g.getFontMetrics().stringWidth(volumeText);
        int textX = volumeSlider.x + (volumeSlider.width - textWidth) / 2;
        int textY = volumeSlider.y + ((volumeSlider.height - g.getFontMetrics().getHeight()) / 2)
                + g.getFontMetrics().getAscent();

        g.drawString(volumeText, textX, textY);
    }

    private int getXForCenteredText(String text, Graphics2D g)
    {
        int length = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        int x = refLink.GetWidth() / 2  - length / 2;

        return x;
    }

    private BufferedImage applyBlur(BufferedImage src)
    {
        float[] kernel = {
                1f/9f, 1f/9f, 1f/9f,
                1f/9f, 1f/9f, 1f/9f,
                1f/9f, 1f/9f, 1f/9f
        };

        ConvolveOp op = new ConvolveOp(
                new Kernel(3, 3, kernel),
                ConvolveOp.EDGE_NO_OP,
                null
        );
        for (int i = 0; i < 7; ++i)
        {
            src = op.filter(src, null);
        }
        return src;
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

            // Verifică dacă slider-ul este clicat
            if (volumeSlider.contains(mousePos)) {
                int sliderX = mousePos.x - volumeSlider.x;
                volumeLevel = Math.max(0, Math.min(100, (int) (sliderX / (float) volumeSlider.width * 100)));
                Music.getInstance().setVolume(volumeLevel / 100.0f); // Actualizează volumul
            }

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
        switch (label) {
            case "Continue":
                SoundPlayer.playSound();
                State.SetState(refLink.GetGame().playState);
                break;

            case "Save":
                SoundPlayer.playSound();
                int heroX = refLink.GetGame().playState.hero.GetX();
                int heroY = refLink.GetGame().playState.hero.GetY();
                //System.out.println(heroX + " " + heroY);

                refLink.database.updatePlayerPosition(refLink.getUsername(),refLink.getPassword(), heroX, heroY);
                refLink.database.updateTimer(refLink.getUsername(), refLink.getPassword(),refLink.getMapNumber(),(int)Timer.getElapsedTime());
               // refLink.database.insertEmptyCoinsForPlayerAndLevel(refLink.getUsername(), refLink.getPassword(),refLink.GetMap(),refLink.getMapNumber());
                //refLink.database.insertCoinsPlayerTables(refLink.getUsername(), refLink.getPassword(),refLink.GetMap(),refLink.getMapNumber());
                Timer.start();
                refLink.database.updateCoinsForPlayerAndLevel(refLink.getUsername(), refLink.getPassword(), refLink.GetMap(), refLink.getMapNumber());

                System.out.println("COINS :"  + refLink.GetGame().playState.getHero().getCoins() + " KEYS : " + refLink.GetGame().playState.getHero().getKeys());
                System.out.println("Game Saved!");
                break;

            case "Exit":
                SoundPlayer.playSound();
                Timer.reset();
                State.SetState(refLink.GetGame().menuState);
                break;
        }
    }

}
