package PaooGame;

public class Main
{
    public static void main(String[] args)
    {
        Game paooGame = Game.getGame("TheForgottenIsland", 1408, 1056);
        paooGame.StartGame();
    }
}