package PaooGame.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    PaooGame.Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
    /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage heroUp1;
    public static BufferedImage heroUp2;
    public static BufferedImage heroUp3;

    public static BufferedImage heroDown1;
    public static BufferedImage heroDown2;
    public static BufferedImage heroDown3;

    public static BufferedImage heroLeft1;
    public static BufferedImage heroLeft2;
    public static BufferedImage heroLeft3;

    public static BufferedImage heroRight1;
    public static BufferedImage heroRight2;
    public static BufferedImage heroRight3;


    public static BufferedImage orcUp1;
    public static BufferedImage orcUp2;
    public static BufferedImage orcUp3;

    public static BufferedImage orcDown1;
    public static BufferedImage orcDown2;
    public static BufferedImage orcDown3;

    public static BufferedImage orcLeft1;
    public static BufferedImage orcLeft2;
    public static BufferedImage orcLeft3;

    public static BufferedImage orcRight1;
    public static BufferedImage orcRight2;
    public static BufferedImage orcRight3;


    public static BufferedImage blazeUp1;
    public static BufferedImage blazeUp2;
    public static BufferedImage blazeUp3;

    public static BufferedImage blazeDown1;
    public static BufferedImage blazeDown2;
    public static BufferedImage blazeDown3;

    public static BufferedImage blazeLeft1;
    public static BufferedImage blazeLeft2;
    public static BufferedImage blazeLeft3;

    public static BufferedImage blazeRight1;
    public static BufferedImage blazeRight2;
    public static BufferedImage blazeRight3;

    public static BufferedImage chupaUp1;
    public static BufferedImage chupaUp2;
    public static BufferedImage chupaUp3;

    public static BufferedImage chupaDown1;
    public static BufferedImage chupaDown2;
    public static BufferedImage chupaDown3;

    public static BufferedImage chupaLeft1;
    public static BufferedImage chupaLeft2;
    public static BufferedImage chupaLeft3;

    public static BufferedImage chupaRight1;
    public static BufferedImage chupaRight2;
    public static BufferedImage chupaRight3;


    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage water;
    public static BufferedImage tree1;
    public static BufferedImage tree2;
    public static BufferedImage stone;
    public static BufferedImage bridge;
    public static BufferedImage coin ;
    public static BufferedImage key;
    public static BufferedImage guardian;
    public static BufferedImage portal;
    public static BufferedImage obsidian;
    public static BufferedImage lava;
    public static BufferedImage flame;
    public static BufferedImage boat;
    public static BufferedImage weed;
    public static BufferedImage swampWater;
    public static BufferedImage grass2;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/SpriteSheet.png"));
        SpriteSheet sheet2 = new SpriteSheet(ImageLoader.LoadImage("/textures/SpriteSheet2.png"));
        SpriteSheet sheet3 = new SpriteSheet(ImageLoader.LoadImage("/textures/SpriteSheet3.png"));

        SpriteSheet grassSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/grass1.png"));
        SpriteSheet boatSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/boat1.png"));
        //SpriteSheet grass2Sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/Grass3.png"));
        SpriteSheet grass3 = new SpriteSheet(ImageLoader.LoadImage("/textures/grass5.png"));

        SpriteSheet mcSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/mainCharacter.png"));
        SpriteSheet orcSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/orcSheet.png"));
        SpriteSheet blazeSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/blazeSheet.png"));
        SpriteSheet chupaSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/chupacabraSheet.png"));
        /// Se obtin subimaginile corespunzatoare elementelor necesare.
//        grass = sheet.crop(50, 95);
////        soil = sheet.crop(4, 2);
////        water = sheet3.crop(10, 95);
////        greenTree = sheet.crop(25, 13);
////        yellowTree = sheet.crop(21,13);
////        stone = sheet.crop(61,11);
////        bridge = sheet.crop(11,18);
////        coin = sheet.crop(42,40);
////        key = sheet.crop(56,40);

        grass=grassSheet.crop(0,0);
        water = sheet3.crop(1, 1);
        tree1 = sheet.crop(14, 13);
        tree2 = sheet.crop(20,13);
        soil = sheet.crop(2, 4);
        obsidian = sheet.crop(40, 2);
        guardian=sheet.crop(6,62);
        bridge = sheet.crop(11,18);
        coin = sheet.crop(41,40);
        key = sheet.crop(55,40);
        stone = sheet.crop(60,11);
        portal = sheet.crop(50,68);
        lava = sheet.crop(0,6);
        boat = boatSheet.crop(0,0);
        flame = sheet.crop(47,0);
//        swampWater = sheet.crop(33,22);
        swampWater = sheet.crop(53,22);
//        grass2 = grass2Sheet.crop(4,0);
        grass2 = sheet.crop(13,9);
        //weed = grass2Sheet.crop(21,11);
        weed = sheet.crop(22,94);

        heroUp1 = mcSheet.crop(0,3);
        heroUp2 = mcSheet.crop(1,3);
        heroUp3 = mcSheet.crop(2,3);

        heroDown1 = mcSheet.crop(0,0);
        heroDown2 = mcSheet.crop(1,0);
        heroDown3 = mcSheet.crop(2,0);

        heroLeft1 = mcSheet.crop(0, 1);
        heroLeft2 = mcSheet.crop(1, 1);
        heroLeft3 = mcSheet.crop(2, 1);

        heroRight1 = mcSheet.crop(0, 2);
        heroRight2 = mcSheet.crop(1, 2);
        heroRight3 = mcSheet.crop(2, 2);

        orcUp1 = orcSheet.crop(0, 3);
        orcUp2 = orcSheet.crop(1, 3);
        orcUp3 = orcSheet.crop(2, 3);

        orcDown1 = orcSheet.crop(0, 0);
        orcDown2 = orcSheet.crop(1,0);
        orcDown3 = orcSheet.crop(2,0);

        orcLeft1 = orcSheet.crop(0, 1);
        orcLeft2 = orcSheet.crop(1,1);
        orcLeft3 = orcSheet.crop(2,1);

        orcRight1 = orcSheet.crop(0, 2);
        orcRight2 = orcSheet.crop(1,2);
        orcRight3 = orcSheet.crop(2,2);


        blazeUp1 = blazeSheet.crop(0, 3, 16, 17);
        blazeUp2 = blazeSheet.crop(1, 3, 16, 17);
        blazeUp3 = blazeSheet.crop(2, 3, 16, 17);

        blazeDown1 = blazeSheet.crop(0, 0, 16, 17);
        blazeDown2 = blazeSheet.crop(1,0, 16, 17);
        blazeDown3 = blazeSheet.crop(2,0, 16, 17);

        blazeLeft1 = blazeSheet.crop(0, 1, 16, 17);
        blazeLeft2 = blazeSheet.crop(1,1, 16, 17);
        blazeLeft3 = blazeSheet.crop(2,1, 16, 17);

        blazeRight1 = blazeSheet.crop(0, 2, 16, 17);
        blazeRight2 = blazeSheet.crop(1,2, 16, 17);
        blazeRight3 = blazeSheet.crop(2,2, 16, 17);

        chupaUp1 = chupaSheet.crop(0, 3, 32, 38);
        chupaUp2 = chupaSheet.crop(1, 3, 32, 38);
        chupaUp3 = chupaSheet.crop(2, 3, 32, 38);

        chupaDown1 = chupaSheet.crop(0, 0, 32, 38);
        chupaDown2 = chupaSheet.crop(1,0, 32, 38);
        chupaDown3 = chupaSheet.crop(2,0, 32, 38);

        chupaLeft1 = chupaSheet.crop(0, 1, 32, 38);
        chupaLeft2 = chupaSheet.crop(1,1, 32, 38);
        chupaLeft3 = chupaSheet.crop(2,1, 32, 38);

        chupaRight1 = chupaSheet.crop(0, 2, 32, 38);
        chupaRight2 = chupaSheet.crop(1,2, 32, 38);
        chupaRight3 = chupaSheet.crop(2,2, 32, 38);

    }
}