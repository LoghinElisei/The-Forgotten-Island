package PaooGame.Tiles;
import PaooGame.Graphics.Assets;
public class WeedTile extends  Tile{
    public WeedTile(int id)
    {
        super(Assets.weed, id);
    }
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}
