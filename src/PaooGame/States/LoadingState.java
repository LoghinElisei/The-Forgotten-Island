package PaooGame.States;

import PaooGame.Graphics.Button;
import PaooGame.Input.MouseManager;
import PaooGame.Music.SoundPlayer;
import PaooGame.RefLinks;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class LoadingState extends State{
    private ArrayList<Button> buttons;
    private final Image backgroundImage;
    private final Image loadingImage;
    private boolean isLoading = true;
    private boolean errorLoading = false;

    public LoadingState(RefLinks refLink) {
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
        backgroundImage = new ImageIcon("res/images/loading_background.png").getImage();
        loadingImage = new ImageIcon("res/images/loading.gif").getImage();
        buttons.add(new Button("Continue", new Rectangle(centerX, startY + buttons.size() * (height + gap), width, height)));
        buttons.add(new Button("Continue Offline", new Rectangle(centerX-50, startY + 50 + buttons.size() * (height + gap), width+100, height)));
        buttons.add(new Button("Try reconnect", new Rectangle(centerX-50, startY + 60 + buttons.size() * (height + gap), width+100, height)));


        connectToDatabase();

    }
    public void connectToDatabase(){
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground(){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<Boolean> future = executor.submit(() -> refLink.database.connect());

                try {
                    // așteptăm max. 5 secunde pentru conectare
                    boolean connected = future.get(5, TimeUnit.SECONDS);

                    if (!connected || !refLink.database.isOracleDatabase()) {
                        errorLoading = true;
                    } else {
                        errorLoading = false;
                    }
                } catch (TimeoutException e) {
                    future.cancel(true); // oprește task-ul
                    errorLoading = true;
                    System.err.println("Conectarea la DB a expirat după 5 secunde!");
                } catch (Exception e) {
                    errorLoading = true;
                    e.printStackTrace();
                } finally {
                    executor.shutdownNow();
                }
                return null;
            }
            @Override
            protected void done() {
                super.done();
                isLoading = false;
            }
        }.execute();
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
        String text = "The Forgotten Island";

        int x = getXForCenteredText(text, g2d);
        int y = refLink.GetHeight() / 4;  // Higher than buttons

        if (isLoading ) {
            g2d.drawImage(loadingImage, x + 200, y + 80, refLink.GetWidth() / 17, refLink.GetHeight() / 15, null);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Cal Sans", Font.BOLD, 50));
            String ld = "Connecting to the internet";
            g2d.drawString(ld, x-80, y+220);
            g2d.setColor(Color.decode("#00697d"));

        }
        else if(errorLoading){
            g2d.drawImage(loadingImage, x + 200, y + 80, refLink.GetWidth() / 17, refLink.GetHeight() / 15, null);
            g2d.setColor(Color.decode("#000473"));
            g2d.setFont(new Font("Cal Sans", Font.BOLD, 50));
            String ld = "Connecting to the internet";
            g2d.drawString(ld, x-80, y+220);

            g2d.setColor(Color.decode("#00697d"));
            g2d.drawString(text, x, y);
            buttons.get(1).draw(g2d, Color.WHITE, Color.BLUE, Color.RED);
            buttons.get(2).draw(g2d, Color.WHITE, Color.BLUE, Color.GREEN);
        }
        else{
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Cal Sans", Font.BOLD, 50));
            String ld = "Connected to the internet";
            g2d.drawString(ld, x-80, y+100);


            g2d.setColor(Color.decode("#00697d"));
            g2d.drawString(text, x, y);
            buttons.get(0).draw(g2d, Color.decode("#0E161B"), Color.decode("#0E161B"), Color.GREEN);

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
            case "Continue":
                SoundPlayer.playSound();
                refLink.GetGame().menuState = new MenuState(refLink);
                State.SetState(refLink.GetGame().menuState);
                break;
            case "Continue Offline":
                SoundPlayer.playSound();
                refLink.GetGame().menuState = new MenuState(refLink);
                State.SetState(refLink.GetGame().menuState);
                break;
            case "Try reconnect":
                SoundPlayer.playSound();
                connectToDatabase();
                System.out.println("Try reconnecting to the internet ...");
        }
    }

}

