package PaooGame.States;

import PaooGame.Graphics.Button;
import PaooGame.Input.MouseManager;
import PaooGame.Items.SuperObject;
import PaooGame.Maps.Map;
import PaooGame.Maps.Map2;
import PaooGame.Maps.Map3;
import PaooGame.Music.SoundPlayer;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;
import PaooGame.Timer.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class InfoState extends State{
    private final ArrayList<Button> buttons;
    private final Image backgroundImage;
    private final int mapNumber;
    public InfoState(RefLinks refLink, int mapNumber)
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

        buttons.add(new Button("Continue", new Rectangle(centerX, startY+250, width, height)));

        switch (mapNumber)
        {
            case 1:
                backgroundImage = new ImageIcon("res/images/info1.png").getImage();
                break;
            case 2:
                backgroundImage = new ImageIcon("res/images/info2.png").getImage();
                break;
            default:
                backgroundImage = new ImageIcon("res/images/info3.png").getImage();
                break;
        }


        this.mapNumber = mapNumber;
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

        for (Button b: buttons)
        {
            b.draw(g2d, Color.decode("#FFFFFF"), Color.BLACK, Color.decode("#324700"));
        }


    }


    private void handleMouseHover() {
        Point mousePos = refLink.GetMouseManager().getMousePosition();

        for (Button b: buttons) {
            b.setHovered(b.getBounds().contains(mousePos));
        }
    }

    private void handleMouseInput() {
        MouseManager m = refLink.GetMouseManager();

        Rectangle usernameField = new Rectangle(500, 420, 470, 50);
        Rectangle passwordField = new Rectangle(500, 590, 470, 50);


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
        Map map;
        if (label.equals("Continue") ) {
                 SoundPlayer.playSound();
                //refLink.GetGame().playState = new PlayState(refLink);
                refLink.setState(refLink.GetGame().playState);
                State.SetState(refLink.GetGame().playState);
                int timeOnThisLevel = refLink.database.getTimer(refLink.getUsername(), refLink.getPassword(),mapNumber);
                Timer.stop();
                Timer.setElapsedTime(timeOnThisLevel);
                Timer.start();
//            System.out.println(" ********* MAP " + mapNumber + " ********* ");
//                if(mapNumber == 1)
//                    refLink.database.downloadCoinsFromDatabase(refLink.getUsername(),refLink.getPassword(),refLink.GetMap(),mapNumber);

                switch (mapNumber) {
                    case 2: {
                        map = new Map2(refLink);
                         //Timer.reset();
                        PlayState.setMap(map);
                        refLink.database.downloadCoinsFromDatabase(refLink.getUsername(),refLink.getPassword(), refLink.GetMap(), mapNumber);
                        refLink.SetMap(map);

                        break;
                    }
                    case 3:{
                        map = new Map3(refLink);
                        //Timer.reset();
                        PlayState.setMap(map);
                        //refLink.database.insertCoinsPlayerTables(refLink.getUsername(), refLink.getPassword(),refLink.GetMap(),refLink.getMapNumber());
                        refLink.database.downloadCoinsFromDatabase(refLink.getUsername(),refLink.getPassword(),refLink.GetMap(),mapNumber);
                        refLink.SetMap(map);

                        break;
                    }


                }

        }
    }



}
