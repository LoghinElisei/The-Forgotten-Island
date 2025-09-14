package PaooGame.States;

import PaooGame.Entity.Character;
import PaooGame.Graphics.Button;
import PaooGame.Input.MouseManager;
import PaooGame.Maps.Map;
import PaooGame.Maps.Map2;
import PaooGame.Maps.Map3;
import PaooGame.Music.Music;
import PaooGame.Music.SoundPlayer;
import PaooGame.RefLinks;
import PaooGame.Timer.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.List;

public class WelcomeState extends State{
    private final ArrayList<Button> buttons;
    private final Image backgroundImage;
    private String usernameInput = "";
    private String passwordInput = "";
    private boolean usernameSelected = false;
    private boolean passwordSelected = false;
    private FontMetrics fontMetrics;
    private boolean usernameExist = false;
    private int option = 1;
    private boolean errorLogin;
    private String cryptedPassword;
    private boolean hoverUsername = false;
    private boolean hoverPassword = false;
    public WelcomeState(RefLinks refLink,int option)
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

        if(option == 1)
        {
            buttons.add(new Button("Log in", new Rectangle(centerX, startY+350, width, height)));
        }
        else {
            buttons.add(new Button("Sign up", new Rectangle(centerX, startY+350, width, height)));
        }
        buttons.add(new Button("Back", new Rectangle(centerX, startY+250, width, height)));

        backgroundImage = new ImageIcon("res/images/welcome1.png").getImage();

        this.option = option;

    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    private boolean canAddChar(String currentText, char c, Font font, int maxWidth) {
        BufferedImage tempImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tempImg.createGraphics();
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int currentWidth = fm.stringWidth(currentText);
        int charWidth = fm.charWidth(c);
        g2d.dispose();

        return currentWidth + charWidth < maxWidth - 20; // -20 pentru padding intern
    }
    @Override
    public void Update()
    {
        Font font = new Font("Cascadia Mono", Font.PLAIN, 35);


        List<java.lang.Character> chars = refLink.GetKeyManager().getTypedCharacters();
        for (char c : chars) {
            if (usernameSelected) {

                if(canAddChar(usernameInput, c, font, 470))
                {
                    usernameInput += c;
                }
            } else if (passwordSelected) {
                if(canAddChar(passwordInput, c, font, 436)){
                    passwordInput += c;
                }
            }
        }
        refLink.GetKeyManager().clearTypedCharacters();

        //Backspace
        if (refLink.GetKeyManager().isBackspacePressed()) {
            if (usernameSelected && usernameInput.length() > 0) {
                usernameInput = usernameInput.substring(0, usernameInput.length() - 1);
            }
            if (passwordSelected && passwordInput.length() > 0) {
                passwordInput = passwordInput.substring(0, passwordInput.length() - 1);
            }
        }
       // System.out.println(usernameInput );
        //System.out.println(passwordInput);
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

        Rectangle usernameField = new Rectangle(500, 420, 470, 50);
        Rectangle passwordField = new Rectangle(500, 590, 470, 50);
        if(hoverUsername)
        {
            g2d.setColor(Color.decode("#f0f77c"));
            g2d.fillRect(usernameField.x, usernameField.y, usernameField.width, usernameField.height);
        }
        else if(hoverPassword)
        {
            g2d.setColor(Color.decode("#f0f77c"));
            g2d.fillRect(passwordField.x, passwordField.y, passwordField.width, passwordField.height);
        }
        g2d.setFont(new Font("Cascadia Mono", Font.BOLD, 40));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Username:", 400, 400);
        g2d.drawString("Password:", 400, 570);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(usernameField.x, usernameField.y, usernameField.width, usernameField.height);
        g2d.drawRect(passwordField.x, passwordField.y, passwordField.width, passwordField.height);


        g2d.setFont(new Font("Cascadia Mono", Font.PLAIN, 35));
        g2d.drawString(usernameInput, usernameField.x + 10, usernameField.y + 35);
        g2d.drawString(maskPassword(passwordInput), passwordField.x + 10, passwordField.y + 35);

        Font font = new Font("Cascadia Mono", Font.PLAIN, 35);
        fontMetrics = g2d.getFontMetrics(font);

        if(usernameExist)
        {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Cascadia Mono", Font.BOLD, 35));
            g2d.drawString("Username already exist", 650, 400);
        }
        if(errorLogin)
        {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Cascadia Mono", Font.BOLD, 35));
            g2d.drawString("Incorrect username/password", 650, 400);
        }

        if(usernameSelected)
        {
            int cursorX = usernameField.x + 10 + fontMetrics.stringWidth(usernameInput);
            int cursorY = usernameField.y + 10;
            g2d.drawLine(cursorX, cursorY, cursorX, cursorY + 30);
        }
        else if(passwordSelected)
        {
            int cursorX = passwordField.x + 10 + fontMetrics.stringWidth(maskPassword(passwordInput));
            int cursorY = passwordField.y + 10;
            g2d.drawLine(cursorX, cursorY, cursorX, cursorY + 30);
        }


    }

    private String maskPassword(String password)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            sb.append("*");
        }
        return sb.toString();
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

        Rectangle usernameField = new Rectangle(500, 420, 470, 50);
        Rectangle passwordField = new Rectangle(500, 590, 470, 50);
        hoverUsername = usernameField.contains(mousePos);
        hoverPassword = passwordField.contains(mousePos);

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
                usernameSelected = usernameField.contains(mousePos);
                passwordSelected = passwordField.contains(mousePos);
            }
        }
        refLink.GetMouseManager().clearMouseClick();
    }

    private String cryptPassword(String password)
    {
        String cryptedPassword = "";
        for (int i = 0; i < password.length(); i++) {
            cryptedPassword += ((char)(password.charAt(i) + 1) * 2);
        }
        return cryptedPassword;
    }

    private void handleClick(String label)
    {
        if (label.equals("Sign up")  || label.equals("Log in")) {
            SoundPlayer.playSound();
            // 1 - log in
            // 2 - sing up
            cryptedPassword = cryptPassword(passwordInput);
            switch (option) {
                case 1: {
                   // refLink.database.createLevelsTable();
                    if(refLink.database.verifyCredentials(usernameInput, cryptedPassword)) {

                        refLink.setUsername(usernameInput);
                        refLink.setPassword(cryptedPassword);
                        int mapNumber = refLink.database.getNextMapNumber(refLink.getUsername(), refLink.getPassword());

                        if (mapNumber != 4) {
                            refLink.GetGame().playState = new PlayState(refLink);
                            refLink.GetGame().infoState = new InfoState(refLink, mapNumber);
                            // refLink.database.insertEmptyCoinsForPlayerAndLevel(refLink.getUsername(), refLink.getPassword(),refLink.GetMap(),refLink.getMapNumber());
                            State.SetState(refLink.GetGame().infoState);
                        }
                        else {
                            refLink.setState(refLink.GetGame().gameCompletedState);
                            State.SetState(refLink.GetGame().gameCompletedState);
                        }
                    }
                    else {
                        errorLogin = true;
                    }
                    break;
                }
                case 2: //sign up
                {

                    //refLink.database.createDefaultCoinsTable();

//                    refLink.database.createPlayersTable();
                   //refLink.database.createLevelsTable();
                    //refLink.database.insertPlayer(usernameInput, passwordInput);


                    if(!refLink.database.insertPlayer(usernameInput, cryptedPassword))
                    {

                        //desenez
                        usernameExist = true;

                    }
                    else {
                        refLink.database.insertDefaultLevelsForPlayer(usernameInput,cryptedPassword);
                        refLink.setUsername(usernameInput);
                        refLink.setPassword(cryptedPassword);
                        int mapNumber = 1; // un nou player , incepe de la 1

                        //refLink.database.createCoinsTable();

                        refLink.GetGame().playState = new PlayState(refLink);
                        refLink.GetGame().infoState = new InfoState(refLink, mapNumber);

                        // doar aici creem o noua tabela cu coin-uri , la log il actualizam
                        //refLink.database.insertEmptyCoinsForPlayerAndLevel(refLink.getUsername(), refLink.getPassword(),refLink.GetMap(),refLink.getMapNumber());
                        refLink.database.insertCoinsPlayerTables(refLink.getUsername(), refLink.getPassword(),refLink.GetMap(),mapNumber);
//                        Map map = new Map2(refLink);
//                        refLink.database.downloadDefaultCoinsFromDatabase(refLink.getUsername(), refLink.getPassword(), map ,2);
//                        refLink.database.insertCoinsPlayerTables(refLink.getUsername(), refLink.getPassword(),map,mapNumber);
//                        map = new Map3(refLink);
//                        refLink.database.downloadDefaultCoinsFromDatabase(refLink.getUsername(), refLink.getPassword(), map ,2);
//                        refLink.database.insertCoinsPlayerTables(refLink.getUsername(), refLink.getPassword(),map,mapNumber);
//
                        refLink.database.updateCoinsForPlayerAndLevel(refLink.getUsername(), refLink.getPassword(), refLink.GetMap(), refLink.getMapNumber());


                        State.SetState(refLink.GetGame().infoState);
                    }
                    break;
                }
            }

        }
        else if(label.equals("Back"))
        {   SoundPlayer.playSound();
            State.SetState(refLink.GetGame().menuState);
        }
    }

}
