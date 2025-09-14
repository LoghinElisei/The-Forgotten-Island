package PaooGame.Maps;

import PaooGame.Entity.Character;
import PaooGame.Items.SuperObject;
import PaooGame.Monster_AI.PathFinder;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */

public class Map3 extends Map
{
    private final int ENEMIES_TO_DISPLAY = 22;

    public Map3(RefLinks refLink)
    {
        /// Retine referinta "shortcut".
        super(refLink);
        name = "Map3";

        //atentie latimea si inaltimea trebuiesc corelate cu dimensiunile ferestrei sau
        //se poate implementa notiunea de camera/cadru de vizualizare al hartii
        ///Se stabileste latimea hartii in numar de dale.
        width = 64;
        ///Se stabileste inaltimea hartii in numar de dale
        height = 33;

        pFinder = new PathFinder(refLink, width, height);
        items = new SuperObject[SuperObject.MAX_ELEMENTS_ON_DISPLAY + 7];
        monsters = new Character[ENEMIES_TO_DISPLAY];
        Map.itemPlacer.addObject(3, this);
        Map.itemPlacer.setEnemies(3, this);
        ///Se construieste matricea de coduri de dale
        ThematicMap();
        CollisionMap();


    }
    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    @Override
    public  void Update() {
    }

    /*! \fn private int MiddleEastMap(int x ,int y)
        \brief O harta incarcata static.

        \param x linia pe care se afla codul dalei de interes.
        \param y coloana pe care se afla codul dalei de interes.
     */
    @Override
    public void Draw(Graphics2D g2d) {
        super.Draw(g2d);
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Cascadia Mono", Font.BOLD, 32));
        g2d.drawString("Lvl : 3        SWAMP", 1000, 50);

    }
    @Override
    protected void ThematicMap() {
        int[][] map;
        tiles = new int[width][height];

        //daca sunt conectat la baza de date , incarc mapa din baza de date

        //map = refLink.database.loadThematicMap3(width, height);  // pentru incarcarea in baza de date
        map = refLink.database.downloadThematicMap3();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[j][i] = map[i][j];
            }
        }
    }
    @Override
    protected void CollisionMap()
    {

        int[][] map ;
        collision = new int[width][height];

        //daca sunt conectat la baza de date , incarc mapa din baza de date

        //map = refLink.database.loadCollisionMap3(width, height);  // pentru incarcarea in baza de date
        map = refLink.database.downloadCollisionMap3();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                collision[j][i] = map[i][j];
            }
        }

    }

}