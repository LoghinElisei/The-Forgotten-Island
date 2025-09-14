package PaooGame;

import PaooGame.Database.Database;
import PaooGame.Database.DatabaseManager;
import PaooGame.Input.MouseManager;
import PaooGame.Maps.Map;
import PaooGame.Maps.Map1;

import PaooGame.Input.KeyManager;
import PaooGame.Maps.Map2;
import PaooGame.States.PlayState;
import PaooGame.States.State;

import java.awt.*;

/*! \class public class RefLinks
    \brief Clasa ce retine o serie de referinte ale unor elemente pentru a fi usor accesibile.

    Altfel ar trebui ca functiile respective sa aiba o serie intreaga de parametri si ar ingreuna programarea.
 */
public class RefLinks
{
    private Game game;          /*!< Referinta catre obiectul PaooGame.Game.*/
    private Map map;            /*!< Referinta catre harta curenta.*/
    private State state;
    public final Database database = new Database();
    private  String username;
    private  String password;

    /*! \fn public RefLinks(PaooGame.Game game)
            \brief Constructorul de initializare al clasei.

            \param game Referinta catre obiectul game.
         */
    public void setMap()
    {
        this.map = new Map1(this);
    }
    public RefLinks(Game game)
    {
        this.game = game;
        //this.map = new Map1(this);
        this.state = (PlayState) game.playState;

    }

    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza referinta catre managerul evenimentelor de tastatura.
     */
    public KeyManager GetKeyManager()
    {
        return game.GetKeyManager();
    }
    public MouseManager GetMouseManager() { return game.GetMouseManager(); }


    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei jocului.
     */

    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }

    public int GetWidth()
    {
        return game.GetWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei jocului.
     */
    public int GetHeight()
    {
        return game.GetHeight();
    }

    /*! \fn public PaooGame.Game GetGame()
        \brief Intoarce referinta catre obiectul PaooGame.Game.
     */
    public Game GetGame()
    {
        return game;
    }

//    public DatabaseManager GetDatabase(){
//        return database;
//    }
    /*! \fn public void SetGame(PaooGame.Game game)
        \brief Seteaza referinta catre un obiect PaooGame.Game.

        \param game Referinta obiectului PaooGame.Game.
     */
    public void SetGame(Game game)
    {
        this.game = game;
    }

    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map GetMap()
    {
        return map;
    }

    public int getMapNumber()
    {
        if(map instanceof Map1)
        {
            return 1;
        }
        else if(map instanceof Map2)
        {
            return 2;
        }
        else {
            return 3;
        }
    }
    /*! \fn public void SetMap(Map map)
        \brief Seteaza referinta catre harta curenta.

        \param map Referinta catre harta curenta.
     */
    public void SetMap(Map map)
    {
        this.map = map;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}