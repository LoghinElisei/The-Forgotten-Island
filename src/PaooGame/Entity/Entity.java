package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;


/*! \class Item
    \brief. Implementeaza notiunea abstracta de entitate activa din joc, "element cu care se poate interactiona: monstru, turn etc.".
 */
public abstract class Entity
{
        ///asa cum s-a mai precizat, coordonatele x si y sunt de tip float pentru a se elimina erorile de rotunjire
        ///ce pot sa apara in urma calculelor, urmand a se converti la intreg doar in momentul desenarii.
    protected int x;                  /*!< Pozitia pe axa X a "tablei" de joc a imaginii entitatii.*/
    protected int y;                  /*!< Pozitia pe axa Y a "tablei" de joc a imaginii entitatii.*/
    protected int width;                /*!< Latimea imaginii entitatii.*/
    protected int height;               /*!< Inaltimea imaginii entitatii.*/
    public Rectangle bounds;         /*!< Dreptunghiul curent de coliziune.*/
    protected Rectangle normalBounds;   /*!< Dreptunghiul de coliziune aferent starii obisnuite(spatiul ocupat de entitate in mod normal), poate fi mai mic sau mai mare decat dimensiunea imaginii sale.*/
    protected int defaultBoundsX;
    protected int defaultBoundsY;
    protected int spriteNum = 1, spriteCounter = 0;
    public int screenX; // x coordinates relative to the camera
    public int screenY;
    protected boolean collisionOn = false;
    protected String direction;

    protected RefLinks refLink;         /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/

    /*! \fn public Item(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei

        \param  reflink Referinte "shortcut" catre alte referinte
        \param  x   Pozitia pe axa X a "tablei" de joc a imaginii entitatii.
        \param  y   Pozitia pe axa Y a "tablei" de joc a imaginii entitatii.
        \param  width   Latimea imaginii entitatii.
        \param  height  Inaltimea imaginii entitatii.
     */
    public Entity(RefLinks refLink, int x, int y, int width, int height)
    {
        this.x = x;             /*!< Retine coordonata pe axa X.*/
        this.y = y;             /*!< Retine coordonata pe axa X.*/

        this.width = width;     /*!< Retine latimea imaginii.*/
        this.height = height;   /*!< Retine inaltimea imaginii.*/
        this.refLink = refLink; /*!< Retine the "shortcut".*/

            ///Creeaza dreptunghi de coliziune pentru modul normal
        normalBounds = new Rectangle(8, 16, 32, 32);
            ///Creeaza dreptunghi de coliziune pentru modul de attack, aici a fost stabilit la dimensiunea imaginii dar poate fi orice alta dimensiune

        normalBounds.x = 50;
        normalBounds.y = 80;
        normalBounds.width = 32;
        normalBounds.height = 32;
            ///Dreptunghiul de coliziune implicit este setat ca fiind cel normal
        bounds = normalBounds;
    }

    ///Metoda abstracta destinata actualizarii starii curente
    public abstract void Update();
    ///Metoda abstracta destinata desenarii starii curente
    public abstract void Draw(Graphics2D g2d);

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
    /*! \fn public float GetX()
        \brief Returneaza coordonata pe axa X.
     */

    public int GetX()
    {
        return x;
    }
    /*! \fn public float GetY()
        \brief Returneaza coordonata pe axa Y.
     */

    public int GetY()
    {
        return y;
    }
    /*! \fn public float GetWidth()
        \brief Returneaza latimea entitatii.
     */

    public int GetWidth()
    {
        return width;
    }

    /*! \fn public float GetHeight()
        \brief Returneaza inaltimea entitatii.
     */
    public int GetHeight()
    {
        return height;
    }


    public String getDirection() {
        return direction;
    }

    /*! \fn public float SetX()
        \brief Seteaza coordonata pe axa X.
     */
    public void SetX(int x)
    {
        this.x = x;
    }

    /*! \fn public float SetY()
        \brief Seteaza coordonata pe axa Y.
     */
    public void SetY(int y)
    {
        this.y = y;
    }

    /*! \fn public void SetNormalMode()
        \brief Seteaza modul normal de interactiune
     */
    public void SetNormalMode()
    {
        bounds = normalBounds;
    }

    /*! \fn public void SetAttackMode()
        \brief Seteaza modul de atac de interactiune
     */
    public void SetDefaultMode()
    {
        bounds.x = defaultBoundsX;
        bounds.y = defaultBoundsY;
    }

    public void changeSpriteNum(){
        if (spriteNum == 3) {
            spriteNum = 1;
        } else {
            spriteNum++;
        }
    }
}