package PaooGame.Tiles;
import PaooGame.Graphics.Assets;
public class KeyTile extends  Tile{
    public KeyTile(int id)
    {
        super(Assets.key, id);
    }
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}
