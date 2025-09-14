package PaooGame.Maps;

import PaooGame.Database.DatabaseManager;
import PaooGame.Entity.Character;
import PaooGame.Entity.Entity;
import PaooGame.Items.SuperObject;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */

public class Map1 extends Map {
    private final int ENEMIES_TO_DISPLAY = 10;

    public Map1(RefLinks refLink) {
        /// Retine referinta "shortcut".
        super(refLink);
        //atentie latimeagit si inaltimea trebuiesc corelate cu dimensiunile ferestrei sau
        //se poate implementa notiunea de camera/cadru de vizualizare al hartii
        ///Se stabileste latimea hartii in numar de dale.
        width = 44;
        ///Se stabileste inaltimea hartii in numar de dale
        height = 33;
        items = new SuperObject[SuperObject.MAX_ELEMENTS_ON_DISPLAY];
        monsters = new Character[ENEMIES_TO_DISPLAY];
        Map.itemPlacer.addObject(1, this);
        Map.itemPlacer.setEnemies(1, this);
        ///Se construieste matricea de coduri de dale
        name = "Map1";

        ThematicMap();
        CollisionMap();

    }

    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    @Override
    public void Update() {
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
        g2d.drawString("Lvl : 1        FOREST", 1000, 50);

    }

    @Override
    protected void ThematicMap() {


        int[][] map;
        tiles = new int[width][height];

        //daca sunt conectat la baza de date , incarc mapa din baza de date

           //map = refLink.database.loadThematicMap1(width, height);  // pentru incarcarea in baza de date
       map = refLink.database.downloadThematicMap1();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[j][i] = map[i][j];
            }
        }
        System.out.println("Succes downloading ThematicMap1 !");
//        else {
//            //altfel , incarc din fisier
//
//            String path = "res/maps/thematic/map1.txt";
//            String line;
//            int row = 0;
//            try {
////            System.out.println("Trying to open file with FileReader...");
//                FileReader fr = new FileReader(path);
////            System.out.println("FileReader opened successfully.");
//                BufferedReader reader = new BufferedReader(fr);
//
//                while ((line = reader.readLine()) != null && row < height) {
//                    line = line.trim(); //elimina spatiile de la inceput si sfarsit
//                    if (line.isEmpty()) continue;
//
//                    String[] element = line.split("\\s+");
//                    for (int i = 0; i < width; i++) {
//                        map[row][i] = Integer.parseInt(element[i]);
//                    }
//
//                    row++;
//                }
//                for (int i = 0; i < height; i++) {
//                    for (int j = 0; j < width; j++) {
//                        tiles[j][i] = map[i][j];
//                    }
//                }
//
//                reader.close();
//            } catch (IOException e) {
//                System.out.println("Text file ThematicMap couldn't be opened");
//            }
//        }
    }

    @Override
    protected void CollisionMap() {


        int[][] map = new int[height][width];
        collision = new int[width][height];

        //daca sunt conectat la baza de date , incarc mapa din baza de date

        //map = refLink.database.loadCollisionMap1(width, height);  // pentru incarcarea in baza de date
        map = refLink.database.downloadCollisionMap1();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                collision[j][i] = map[i][j];
            }
        }

//        String path = "res/maps/collision/map1.txt";
//
//        map = new int[height][width];
//        collision=new int[width][height];
//        String line;
//        int row = 0;
//        try {
////            System.out.println("Trying to open file with FileReader...");
//            FileReader fr = new FileReader(path);
////            System.out.println("FileReader opened successfully.");
//            BufferedReader reader = new BufferedReader(fr);
//
//
//            while((line = reader.readLine()) != null && row < height){
//                line = line.trim(); //elimina spatiile de la inceput si sfarsit
//                if(line.isEmpty()) continue;
//
//                String [] element = line.split("\\s+");
//                for(int i=0;i<width;i++)
//                {
//                    map[row][i] = Integer.parseInt(element[i]);
//                }
//
//                row++;
//            }
//            //facem transpusa matricii
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    collision[j][i] = map[i][j];
//                }}
//
//            reader.close();
//        }
//        catch (IOException e)
//        {
//            System.out.println("Text file ThematicMap couldn't be opened");
//        }
//
    }
}