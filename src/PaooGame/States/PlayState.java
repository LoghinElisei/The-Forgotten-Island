package PaooGame.States;

import PaooGame.Creator.HeroCreator.HeroCreator;
import PaooGame.Creator.EntityCreator;
import PaooGame.Creator.EntityType;
import PaooGame.Entity.Character;
import PaooGame.Game;
import PaooGame.Maps.Map;
import PaooGame.Maps.Map1;
import PaooGame.Maps.Map2;
import PaooGame.Maps.Map3;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;
import PaooGame.Timer.Timer;

import java.awt.*;
import java.awt.geom.AffineTransform;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    public static Map map;    /*!< Referinta catre harta curenta.*/
    int coins = 0;
    int keys = 0;
    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink) {
            ///Apel al constructorului clasei de baza
        super(refLink);


        if(map == null)
        {
            map = new Map1(refLink);
        }
            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
            ///Construieste eroul
        EntityCreator heroCreator = new HeroCreator();

        Point playerPos = refLink.database.getPlayerPosition(refLink.getUsername(),refLink.getPassword());

        hero = heroCreator.getItem(EntityType.HERO, refLink, playerPos.x, playerPos.y);
        Game.debugState = false;

        int mapNumber = 1;
        if(map instanceof Map2)
        {
            mapNumber = 2;
        }
        else if(map instanceof Map3)
        {
            mapNumber = 3;
        }

        int timeOnThisLevel = refLink.database.getTimer(refLink.getUsername(), refLink.getPassword(),mapNumber);
        Timer.setElapsedTime(timeOnThisLevel);
        Timer.start();

        Point p = refLink.database.getNrOfCoinsPicked(refLink,mapNumber);
        hero.setCoins(p.x);
        hero.setKeys(p.y);

//        if(refLink.getMapNumber() != 1 && refLink.getMapNumber() !=3)
//            refLink.database.downloadCoinsFromDatabase(refLink.getUsername(),refLink.getPassword(),refLink.GetMap(),refLink.getMapNumber());


    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {
        map.Update();
        hero.Update();

        // ENEMIES
        for (int i = 0; i < map.monsters.length; ++i)
        {
            if (map.monsters[i] != null)
            {
                map.monsters[i].Update();
            }
        }



        // DEBUG
        if (refLink.GetKeyManager().IsDebugJustPressed()) {
            Game.debugState = !Game.debugState;
            if (Game.debugState) {
                hero.SetSpeed(30);
            } else {
                hero.SetSpeed(Character.DEFAULT_SPEED);
            }

        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Dese neaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics2D g)
    {
        g.setColor(Color.BLACK); // sau orice culoare de fundal a hărții
        g.fillRect(0, 0, refLink.GetWidth(), refLink.GetHeight());

        map.Draw(g);
        // OBJECTS
        for (int i = 0; i < map.items.length; ++i)
        {
            if (map.items[i] != null)
            {
                map.items[i].draw(g, hero);
            }
        }

        // ENEMIES
        for (int i = 0; i < map.monsters.length; ++i)
        {
            if (map.monsters[i] != null)
            {
                map.monsters[i].Draw(g);
            }
        }
        hero.Draw(g);

        // DEBUG
        if (Game.debugState) {
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.setColor(Color.WHITE);
            int x = 10, y = 400, lineHeight = 20;

            g.drawString("x: " + hero.GetX(), x, y); y += lineHeight;
            g.drawString("y: " + hero.GetY(), x, y); y += lineHeight;
            g.drawString("Col: " + (hero.GetX() + hero.bounds.x) / Tile.TILE_WIDTH , x, y); y += lineHeight;
            g.drawString("Row: " + (hero.GetY() + hero.bounds.y) / Tile.TILE_WIDTH , x, y);
        }

        DrawUI(g);

    }
    private void DrawUI(Graphics2D g2d)
    {
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.WHITE);

        int nrOfCoins = refLink.GetMap().items.length - 2 - 3;
        int nrOfKeys = 2;
        int mapNumber = refLink.getMapNumber();
        Point p = refLink.database.getNrOfCoinsPicked(refLink,mapNumber);
        int pickedCoins = p.x;
        int pickedKeys = p.y;
        if(mapNumber == 2)
            nrOfCoins ++;
        if(mapNumber == 3)
            nrOfCoins+=3;

        coins = pickedCoins;
        keys = pickedKeys;

        g2d.setFont(new Font("Cascadia Mono", Font.BOLD, 32));
//        g2d.drawString("Coins: " + getCoins()+" / "+nrOfCoins, 400, 50);
//        g2d.drawString("Keys: " + getKeys() + " / " + nrOfKeys, 670, 50);
        g2d.drawString("Coins: " + coins+" / "+nrOfCoins, 400, 50);
        g2d.drawString("Keys: " +keys + " / " + nrOfKeys, 670, 50);
    }
    public static void setMap(Map map){
        PlayState.map = map;
    }
}