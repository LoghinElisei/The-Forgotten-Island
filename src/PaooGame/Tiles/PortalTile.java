package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

public class PortalTile extends Tile
{
    public PortalTile(int id)
    {
        super(Assets.portal, id);
    }

    @Override
    public boolean IsSolid()
    {
        return true;
    }
}
