package PaooGame.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/*! \class public class KeyManager implements KeyListener
    \brief Gestioneaza intrarea (input-ul) de tastatura.

    Clasa citeste daca au fost apasata o tasta, stabiliteste ce tasta a fost actionata si seteaza corespunzator un flag.
    In program trebuie sa se tina cont de flagul aferent tastei de interes. Daca flagul respectiv este true inseamna
    ca tasta respectiva a fost apasata si false nu a fost apasata.
 */
public class KeyManager implements KeyListener
{
    private boolean[] keys; /*!< Vector de flaguri pentru toate tastele. Tastele vor fi regasite dupa cod [0 - 255]*/
    public boolean up;      /*!< Flag pentru tasta "sus" apasata.*/
    public boolean down;    /*!< Flag pentru tasta "jos" apasata.*/
    public boolean left;    /*!< Flag pentru tasta "stanga" apasata.*/
    public boolean right;   /*!< Flag pentru tasta "dreapta" apasata.*/
    public boolean lastEscState;
    public boolean esc;
    public boolean debugButtonState, lastDebugButtonState;

    private final List<Character> typedCharacters = new ArrayList<>();
    private boolean backspacePressed = false;
    private boolean lastBackspacePressed = false;
    /*! \fn public KeyManager()
        \brief Constructorul clasei.
     */
    public KeyManager()
    {
            ///Constructie vector de flaguri aferente tastelor.
        keys = new boolean[256];
    }


    public void Update()
    {
        up    = keys[KeyEvent.VK_W];
        down  = keys[KeyEvent.VK_S];
        left  = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        lastEscState = esc;
        esc   = keys[KeyEvent.VK_ESCAPE];
        lastDebugButtonState = debugButtonState;
        debugButtonState = keys[KeyEvent.VK_T];

        lastBackspacePressed = backspacePressed;
        backspacePressed = keys[KeyEvent.VK_BACK_SPACE];
    }

    /*! \fn public void keyPressed(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta apasata este generat.

         \param e obiectul eveniment de tastatura.
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
            ///se retine in vectorul de flaguri ca o tasta a fost apasata.
        keys[e.getKeyCode()] = true;

    }

    /*! \fn public void keyReleased(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta eliberata este generat.

         \param e obiectul eveniment de tastatura.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
            ///se retine in vectorul de flaguri ca o tasta a fost eliberata.
        keys[e.getKeyCode()] = false;

    }

    /*! \fn public void keyTyped(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand o tasta a fost apasata si eliberata.
        Momentan aceasta functie nu este utila in program.
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
        char c = e.getKeyChar();
        if(c>=32 && c<=126){
            typedCharacters.add(c);
        }
    }
    public List<Character> getTypedCharacters()
    {
        return new ArrayList<>(typedCharacters);
    }
    public void clearTypedCharacters()
    {
        typedCharacters.clear();
    }

    public boolean isBackspacePressed()
    {
        return backspacePressed && !lastBackspacePressed ;
    }

    public void setNoBackspacePressed()
    {
        backspacePressed = false;
    }

    public boolean keyHasBeenPressed(){
        if (up || down || left || right){
            return true;
        }
        return false;
    }
    public boolean IsEscJustPressed() {
        return esc && !lastEscState;
    }
    public boolean IsDebugJustPressed() {return debugButtonState && !lastDebugButtonState;}
}