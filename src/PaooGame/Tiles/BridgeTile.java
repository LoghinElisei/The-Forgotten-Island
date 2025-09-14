package PaooGame.Tiles;
import PaooGame.Graphics.Assets;
public class BridgeTile extends  Tile{
    public BridgeTile(int id)
    {
        super(Assets.bridge, id);
    }
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}
