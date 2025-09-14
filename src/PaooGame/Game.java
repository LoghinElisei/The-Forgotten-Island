package PaooGame;

import PaooGame.CollisionChecker.Collision;
import PaooGame.Music.Music;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Input.KeyManager;
import PaooGame.Input.MouseManager;
import PaooGame.Music.SoundPlayer;
import PaooGame.States.*;
import PaooGame.Timer.Timer;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/*! \class PaooGame.Game
    \brief Clasa principala a intregului proiect. Implementeaza PaooGame.Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa PaooGame.Game.
    Clasa PaooGame.Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public PaooGame.Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable
{
    private static Game     gameInstance = null;
    private GameWindow      wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean         runState;   /*!< Flag ce starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    private Collision collisionChecker;

    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at
    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ //Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private Graphics2D        g;          /*!< Referinta catre un context grafic.*/

        ///Available states
    public State playState;            /*!< Referinta catre joc.*/
    public State menuState;        /*!< Referinta catre setari.*/
    public State pauseState;            /*!< Referinta catre menu.*/
    public State loadingState;
    public State gameOverState;
    public State gameCompletedState;
    public State welcomeState;
    public State aboutState;
    public State infoState;
    public State scoreboardState;

    private KeyManager keyManager;      /*!< Referinta catre obiectul care gestioneaza intrarile din partea utilizatorului.*/
    private MouseManager mouseManager;  // ME
    private RefLinks refLink;            /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte pentru a fi usor accesibile.*/
    /*! \fn public PaooGame.Game(String title, int width, int height)
        \brief Constructor de initializare al clasei PaooGame.Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei PaooGame.Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public static boolean debugState = false;
    private Game(String title, int width, int height)
    {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        wnd = GameWindow.getInstance(title, width, height);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
            ///Construirea obiectului de gestiune a evenimentelor de tastatura
        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        Music bgMusic = Music.getInstance();
        SoundPlayer sound = SoundPlayer.getInstance();
        sound.setVolume(0.6f);
        bgMusic.playMusic("res/sounds/music3.wav");
        bgMusic.setVolume(0.5f);


    }
    public static Game getGame(String title, int width, int height)
    {
        if (gameInstance == null)
        {
            gameInstance = new Game(title, width, height);
        }
        return gameInstance;
    }


    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */

    private void InitGame()
    {
            /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
            ///Sa ataseaza ferestrei managerul de tastatura pentru a primi evenimentele furnizate de fereastra.
        wnd.GetCanvas().addKeyListener(keyManager);
        wnd.GetCanvas().addMouseListener(mouseManager);
        wnd.GetCanvas().addMouseMotionListener(mouseManager);

//        Canvas canvas = wnd.GetCanvas();
//        canvas.setFocusable(true);
//        canvas.requestFocusInWindow();
//        canvas.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                if (State.GetState() instanceof WelcomeState) {
//                    ((WelcomeState) State.GetState()).keyTyped(e);
//                }
//            }
//        });


        ///Se incarca toate elementele grafice (dale)
        Assets.Init();
            ///Se construieste obiectul de tip shortcut ce va retine o serie de referinte catre elementele importante din program.
        refLink = new RefLinks(this);
        collisionChecker = new Collision(refLink);
        ///Definirea starilor programului
        playState       = null;
        pauseState      = null;
        menuState       = new MenuState(refLink);
        aboutState      = new AboutState(refLink,1);
        loadingState = new LoadingState(refLink);
        gameOverState = new GameOver(refLink);
        gameCompletedState = new GameCompletedState(refLink);
            ///Seteaza starea implicita cu care va fi lansat programul in executie
        //infoState = new InfoState(refLink);


        State.SetState(loadingState);
    }
    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */

    public void run()
    {
            /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

            /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
            /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

            /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState == true)
        {
                /// Se obtine timpul curent
            curentTime = System.nanoTime();
                /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime) > timeFrame)
            {
                /// Actualizeaza pozitiile elementelor
                Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }

    }
    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */

    public synchronized void StartGame()
    {
        if(runState == false)
        {
                /// Se actualizeaza flagul de stare a threadului
            runState = true;
                /// Se construieste threadul avand ca parametru obiectul PaooGame.Game. De retinut faptul ca PaooGame.Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa PaooGame.Game.

            gameThread = new Thread(this);
                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
        else
        {
                /// Thread-ul este creat si pornit deja
            return;
        }
    }
    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */

    public synchronized void StopGame()
    {
        if(runState == true)
        {
                /// Actualizare stare thread
            runState = false;
                /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try
            {
                    /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                    /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
            }
            catch(InterruptedException ex)
            {
                    /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else
        {
                /// Thread-ul este oprit deja.
            return;
        }
    }
    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */

    private void Update()
    {
        ///Determina starea tastelor
        keyManager.Update();
        ///Schimbam alternativ starea de playstate cu starea de pauza
        if (keyManager.IsEscJustPressed() )
        {
            if (State.GetState() == playState) {

                BufferedImage screenshot = new BufferedImage(refLink.GetWidth(), refLink.GetHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = screenshot.createGraphics();
                refLink.GetGame().playState.Draw(g2d);
                g2d.dispose();

                refLink.GetGame().pauseState = new PauseState(refLink, screenshot);
                State.SetState(refLink.GetGame().pauseState);

            } else if (State.GetState() == pauseState) {
                Timer.start();
                State.SetState(playState);
            }
            return;
        }

        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if(State.GetState() != null)
        {
        ///Actualizez starea curenta a jocului daca exista.
            State.GetState().Update();
        }
    }
    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */

    private void Draw()
    {
            /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();

            /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
                /// Se executa doar la primul apel al metodei Draw()
            try
            {
                    /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
            /// Se obtine contextul grafic curent in care se poate desena.
        g = (Graphics2D) bs.getDrawGraphics();
            /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        /// operatie de desenare
            ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
            if(State.GetState() != null)
            {
                ///Actualizez starea curenta a jocului daca exista.
                State.GetState().Draw(g);
            }
        /// end operatie de desenare

            /// Se afiseaza pe ecran
        bs.show();

            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }
    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei
     */

    public int GetWidth()
    {
        return wnd.GetWndWidth();
    }
    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei
     */

    public int GetHeight()
    {
        return wnd.GetWndHeight();
    }
    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza obiectul care gestioneaza tastatura.
     */

    public KeyManager GetKeyManager()
    {
        return keyManager;
    }
    public MouseManager GetMouseManager()
    {
        return mouseManager;
    }
    public Collision getCollisionChecker() { return collisionChecker; }
    public Graphics2D getGraphics() {return g;}
}

