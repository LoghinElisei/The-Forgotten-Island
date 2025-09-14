package PaooGame.Maps;

import PaooGame.Camera.Camera;
import PaooGame.Entity.Character;
import PaooGame.Entity.Entity;
import PaooGame.Items.Coin;
import PaooGame.Items.ItemPlacer;
import PaooGame.Items.SuperObject;
import PaooGame.Monster_AI.PathFinder;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;
import PaooGame.Timer.Timer;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Map {
    protected RefLinks refLink;   /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/
    protected int width;          /*!< Latimea hartii in numar de dale.*/
    protected int height;         /*!< Inaltimea hartii in numar de dale.*/
    protected int [][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    protected int [][] collision;
    protected String name;
    protected PathFinder pFinder;
    public static Camera camera = new Camera(300,300,1408,1056);
    public static Timer timer = Timer.getInstance();
    public static ItemPlacer itemPlacer;
    public SuperObject[] items;
    public Character[] monsters;

    public Map(RefLinks refLink)
    {
        /// Retine referinta "shortcut".
        this.refLink = refLink;
        itemPlacer = new ItemPlacer(refLink);
        ///incarca harta de start. Functia poate primi ca argument id-ul hartii ce poate fi incarcat.


    }

    public abstract void Update();
    public void Draw(Graphics2D g2d)
    {
        camera.apply(g2d);
        //Determină limitele vizibile pe baza poziției camerei și dimensiunii dalelor
        int startX = 0;
        int startY = 0;
        int endX = Math.min(width, (int) (camera.getBounds().x + camera.getBounds().width) / Tile.TILE_WIDTH + 1);
        int endY = Math.min(height, (int) (camera.getBounds().y + camera.getBounds().height) / Tile.TILE_HEIGHT + 1);

        // Parcurge doar dalele vizibile
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                // Desenează dalele de bază
                GetTile(x, y).Draw(g2d, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);

                // Dacă există o dală de coliziune, o desenăm de asemenea
                if (collision[x][y] != 0) {
                    GetCollisionTile(x, y).Draw(g2d, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
                }

            }
        }
//        g2d.setColor(Color.WHITE);
//        g2d.setFont(new Font("Cascadia Mono",Font.BOLD,55));
//        String timeTxt = String.format("%.2f", timer.getElapsedTime());
//        //System.out.println(timeTxt);
//        g2d.drawString(timeTxt, camera.getX()+1300, camera.getY()+50);

        //resetare transformari
        g2d.setTransform(new AffineTransform());
        DrawUI(g2d);
    }

    private void DrawUI(Graphics2D g2d)
    {
        g2d.setTransform(new AffineTransform());

        g2d.setColor(new Color(0, 0, 0, 130));
        g2d.fillRect(0, 0, refLink.GetWidth(), 80);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Cascadia Mono", Font.BOLD, 32));

        g2d.drawString(String.format("Time: %.2f s", timer.getElapsedTime()), 20, 50);
//        g2d.drawString("Coins: " + coinCount, 250, 50);
//        g2d.drawString("Keys: " + keyCount, 450, 50);


    }
    public Tile GetTile(int x, int y)
    {
        if(x < 0 || y < 0 || x >= width || y >= height)
        {
            return Tile.grassTile;
        }
        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null)
        {
            return Tile.grassTile;
        }
        return t;
    }
    public Tile GetCollisionTile(int x,int y)
    {
        if(x < 0 || y < 0 || x >= width || y >= height)
        {
            return Tile.waterTile;
        }
        Tile t = Tile.tiles[collision[x][y]];

        if(t == null)
        {
            return Tile.grassTile;
        }
        return t;
    }

    protected abstract void CollisionMap();
    protected abstract void  ThematicMap();

    public Map nextMap(int lvl){
        if (lvl == 1)
        {
            return new Map2(refLink);
        }
        return new Map3(refLink);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public String getName() { return name; }
    public PathFinder getpFinder() {
        return pFinder;
    }
}
