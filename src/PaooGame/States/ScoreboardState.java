package PaooGame.States;

import PaooGame.Graphics.Button;
import PaooGame.Input.MouseManager;
import PaooGame.Music.SoundPlayer;
import PaooGame.RefLinks;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScoreboardState extends State{

    private ArrayList<Button> buttons;
    private final Image backgroundImage;
    private ArrayList<String> score ;

    public ScoreboardState(RefLinks refLink) {
        super(refLink);
        buttons = new ArrayList<>();

        int width = 200;
        int height = 50;
        int gap = 20;

        int screenWidth = refLink.GetWidth();
        int screenHeight = refLink.GetHeight();
        int totalMenuHeight = 4 * height + 2 * gap;

        int centerX = (screenWidth - width) / 2;
        int startY = (screenHeight - totalMenuHeight) / 2 + 20; // Am lasat un pic de spatiu pentru titlu
        backgroundImage = new ImageIcon("res/images/scoreboard.jpeg").getImage();

        buttons.add(new Button("Menu", new Rectangle(centerX, 800, width, height)));

        score = refLink.database.getScoreboard();
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

        g2d.setFont(new Font("Cascadia Mono", Font.BOLD, 70));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Scoreboard",500,150);
        int gap = 0;
        g2d.setFont(new Font("Cascadia Mono", Font.BOLD, 50));
        g2d.setColor(Color.BLACK);
        for ( String line: score)
        {
            g2d.drawString(line, 350, 230+gap);
            gap += 60;
        }

        for (Button b: buttons)
        {
            b.draw(g2d, Color.decode("#FFFFFF"), Color.BLACK, Color.decode("#324700"));
        }
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
        if (label.equals("Menu") ) {
            SoundPlayer.playSound();
            //refLink.GetGame().menuState = new MenuState(refLink);
            State.SetState(refLink.GetGame().menuState);

        }
    }
}
