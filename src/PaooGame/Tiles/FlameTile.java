package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

public class FlameTile extends Tile
{
    public FlameTile(int id)
    {
        super(Assets.flame, id);
    }
    @Override
    public boolean IsSolid()
    {
        return true;
    }

}
