package PaooGame.Entity;

import PaooGame.CollisionChecker.Collision;
import PaooGame.Game;
import PaooGame.Graphics.Assets;
import PaooGame.Monster_AI.PathFinder;
import PaooGame.RefLinks;
import PaooGame.States.GameOver;
import PaooGame.States.PlayState;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.lang.Math.round;

/*! \class public abstract class Character extends Item
    \brief Defineste notiunea abstracta de caracter/individ/fiinta din joc.

    Notiunea este definita doar de viata, viteza de deplasare si distanta cu care trebuie sa se
    miste/deplaseze in urma calculelor.
 */
public abstract class Character extends Entity
{
    public static final int DEFAULT_SPEED         = 10; /*!< Viteza implicita a unu caracter.*/
    public static final int DEFAULT_CREATURE_WIDTH  = 32 * 3;   /*!< Latimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_CREATURE_HEIGHT = 32 * 3;   /*!< Inaltimea implicita a imaginii caracterului.*/
    public static final String DEFAULT_DIRECTION = "right";

    protected int speed;  /*!< Retine viteza de deplasare caracterului.*/
    protected int xMove;  /*!< Retine noua pozitie a caracterului pe axa X.*/
    protected int yMove;  /*!< Retine noua pozitie a caracterului pe axa Y.*/
    protected int startCol, startRow, goalCol, goalRow;
    protected boolean onPath = false;
    protected boolean followPlayer = false;
    /*! \fn public Character(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei Character

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pa axa X a caracterului.
        \param y Pozitia de start pa axa Y a caracterului.
        \param width Latimea imaginii caracterului.
        \param height Inaltimea imaginii caracterului.
     */
    public Character(RefLinks refLink, int x, int y, int width, int height)
    {
            ///Apel constructor la clasei de baza
        super(refLink, x,y, width, height);
            //Seteaza pe valorile implicite pentru viata, viteza si distantele de deplasare
        speed   = DEFAULT_SPEED;
        direction = DEFAULT_DIRECTION;
        xMove   = 0;
        yMove   = 0;

        screenX = refLink.GetGame().GetWidth()/2 - Tile.TILE_WIDTH/2;
        screenY = refLink.GetGame().GetHeight()/2 - Tile.TILE_HEIGHT/2;
    }
    @Override
    public void Update() {
        ///Implicit monstrul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
        Character hero = refLink.GetGame().playState.getHero();
        int xDistance = Math.abs(x - hero.x);
        int yDistance = Math.abs(y - hero.y);
        int tileDistance = (xDistance + yDistance) / Tile.TILE_HEIGHT;
        if (tileDistance < 5 && followPlayer == false) {
            // aggressive enemy only in 50% chance
            int i = new Random().nextInt(100) + 1;
            if (i > 50) followPlayer = true;
        } else if (tileDistance > 12 && followPlayer)  followPlayer = false;
        setAction();
        collisionOn = false;
        refLink.GetGame().getCollisionChecker().checkTile(this);
        boolean contactPlayer = refLink.GetGame().getCollisionChecker().checkFromEnemyToPlayer(this);

        if (contactPlayer && Game.debugState == false) {
            refLink.setState(refLink.GetGame().gameOverState);
            State.SetState(refLink.GetGame().gameOverState);
        }
        if (!collisionOn)
        {
            switch (direction)
            {
                case "up": yMove = -speed; break;
                case "down": yMove = speed; break;
                case "left": xMove = -speed; break;
                case "right": xMove = speed;
            }
        }
        ///Actualizeaza pozitia
        Move();
        ///Actualizeaza imaginea
        spriteCounter++;
        if (spriteCounter > 10)
        {
            changeSpriteNum();
            spriteCounter = 0;
        }
    }

    protected void setAction(){}

    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */
    public void Move()
    {
            ///Modifica pozitia caracterului pe axa X.
            ///Modifica pozitia caracterului pe axa Y.
            MoveX();
            MoveY();
    }

    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */
    public void MoveX()
    {
            ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa X.
        x += xMove;
    }

    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */
    public void MoveY()
    {
            ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa Y.
        y += yMove;
    }

    public void Draw(Graphics2D g, PlayState playState){};

    /*! \fn public int GetSpeed()
        \brief Returneaza viteza caracterului.
     */

    public int GetSpeed()
    {
        return speed;
    }

    /*! \fn public void SetSpeed(float speed)
        \brief
     */
    public void SetSpeed(int speed) {
        this.speed = speed;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (x + bounds.x) / Tile.TILE_HEIGHT;
        int startRow = (y + bounds.y) / Tile.TILE_HEIGHT;
//        if (Game.debugState && this instanceof Chupacabra) {
//            System.out.println("startCol = " + startCol + ", startRow = " + startRow);
//        }
        PathFinder pFinder = refLink.GetMap().getpFinder();
        Collision collchecker = refLink.GetGame().getCollisionChecker();

        pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
        if (pFinder.search()) {

            // Next x and y coordinates
            int nextX = pFinder.pathList.get(0).col * Tile.TILE_HEIGHT;
            int nextY = pFinder.pathList.get(0).row * Tile.TILE_HEIGHT;

            // entity position
            int entLeftX = x + bounds.x;
            int entRightX = x + bounds.x + bounds.width;
            int entTopY = y + bounds.y;
            int entBottomY = y + bounds.y + bounds.height;

            if (entTopY > nextY && entLeftX >= nextX && entRightX < nextX + Tile.TILE_HEIGHT) {
                direction = "up";
            } else
            if (entTopY < nextY && entLeftX >= nextX && entRightX < nextX + Tile.TILE_HEIGHT) {
                direction = "down";
            } else
            if (entTopY >= nextY && entBottomY < nextY + Tile.TILE_HEIGHT) {
                // left or right
                if (entLeftX > nextX) {
                    direction = "left";
                }
                if (entLeftX < nextX) {
                    direction = "right";
                }
            } else if (entTopY > nextY && entLeftX > nextX) {
                // up or left
                direction = "up";
                collchecker.checkTile(this);
                collchecker.checkFromEnemyToPlayer(this);
                if (collisionOn) {
                    direction = "left";
                }
            } else if (entTopY > nextY && entLeftX < nextX) {
                // up or right
                direction = "up";
                collchecker.checkTile(this);
                collchecker.checkFromEnemyToPlayer(this);
                if (collisionOn) {
                    direction = "right";
                }
            } else if (entTopY < nextY && entLeftX > nextX) {
                // down or left
                direction = "down";
                collchecker.checkTile(this);
                collchecker.checkFromEnemyToPlayer(this);
                if (collisionOn) {
                    direction = "left";
                }
            } else if (entTopY < nextY && entLeftX < nextX) {
                // down or right
                direction = "down";
                collchecker.checkTile(this);
                collchecker.checkFromEnemyToPlayer(this);
                if (collisionOn) {
                    direction = "right";
                }
            }

            int nextCol = pFinder.pathList.get(0).col;
            int nextRow = pFinder.pathList.get(0).row;

            if (nextCol == goalCol && nextRow == goalRow) {
                onPath = !onPath;
            }
        }
    }

    public void setPath(int startCol, int startRow, int goalCol, int goalRow){
        this.startCol = startCol;
        this.startRow = startRow;
        this.goalCol = goalCol;
        this.goalRow = goalRow;
    }

    public int getKeys() {return 0;}
    public void setKeys(int keys) {}
    public void setCoins(int coins){;}
    public  int getCoins(){return 1;}
}