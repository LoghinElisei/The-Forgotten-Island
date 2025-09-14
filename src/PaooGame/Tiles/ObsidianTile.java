package PaooGame.Tiles;
import PaooGame.Graphics.Assets;

public class ObsidianTile extends Tile
{
    public ObsidianTile(int id)
    {
        super(Assets.obsidian, id);
    }

    /*! \fn public boolean IsSolid()
        \brief Suprascrie metoda IsSolid() din clasa de baza in sensul ca va fi luat in calcul in caz de coliziune.
     */
    @Override
    public boolean IsSolid()
    {
        return false;
    }
}
