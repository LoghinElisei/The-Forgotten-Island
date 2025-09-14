package PaooGame.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class public class Tile
    \brief Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un id.
 */
public class Tile
{
    private static final int NO_TILES   = 10000;
    public static Tile[] tiles          = new Tile[NO_TILES];       /*!< Vector de referinte de tipuri de dale.*/

    /// De remarcat ca urmatoarele dale sunt statice si publice. Acest lucru imi permite sa le am incarcate
    /// o singura data in memorie
    public static Tile grassTile        = new GrassTile(600);     /*!< Dala de tip iarba*/
    public static Tile waterTile        = new WaterTile(6090);     /*!< Dala de tip apa*/
    public static Tile tree1Tile         = new Tree1Tile(847);      /*!< Dala de tip copac*/
    public static Tile tree2Tile = new Tree2Tile(853);
    public static Tile soilTile         = new SoilTile(258);      /*!< Dala de tip sol/pamant*/
    public static Tile bridgeTile = new BridgeTile(1163);
    public static Tile coinTile = new CoinTile(2602);
    public static Tile keyTile = new KeyTile(2616);
    public static Tile stoneTile = new StoneTile(765);
    public static Tile guardian = new GuardianTile(3);
    public static Tile portal = new PortalTile(1);
    public static Tile obsidian = new ObsidianTile(193);
    public static Tile lava = new LavaTile(409);
    public static Tile flame = new FlameTile(72);
    public static Tile boat = new BoatTile(6107);
    public static Tile swampWater = new SwampWaterTile(1442);
    public static Tile weed = new WeedTile(6405);
    public static Tile grass2 = new Grass2Tile(592);

    public static final int TILE_WIDTH  = 32 * 4;                       /*!< Latimea unei dale.*/
    public static final int TILE_HEIGHT = 32 * 4;                       /*!< Inaltimea unei dale.*/

    protected BufferedImage img;                                    /*!< Imaginea aferenta tipului de dala.*/
    protected final int id;                                         /*!< Id-ul unic aferent tipului de dala.*/
    /*! \fn public Tile(BufferedImage texture, int id)
        \brief Constructorul aferent clasei.

        \param image Imaginea corespunzatoare dalei.
        \param id Id-ul dalei.
     */
    public Tile(BufferedImage image, int idd)
    {
        img = image;
        id = idd;

        tiles[id] = this;
    }

    /*! \fn public void Update()
        \brief Actualizeaza proprietatile dalei.
     */
    public void Update()
    {

    }

    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra dala.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */
    public void Draw(Graphics2D g, int x, int y)
    {
        /// Desenare dala
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    /*! \fn public boolean IsSolid()
        \brief Returneaza proprietatea de dala solida (supusa coliziunilor) sau nu.
     */
    public boolean IsSolid()
    {
        return false;
    }

    /*! \fn public int GetId()
        \brief Returneaza id-ul dalei.
     */
    public int GetId()
    {
        return id;
    }
}
