package PaooGame.Creator.EnemyCreator;

import PaooGame.Creator.EntityCreator;
import PaooGame.Creator.EntityType;
import PaooGame.Entity.Blaze;
import PaooGame.Entity.Character;
import PaooGame.Entity.Chupacabra;
import PaooGame.Entity.Orc;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

public class EnemyCreator extends EntityCreator {
    @Override
    protected Character createItem(EntityType type, RefLinks refLink, int x, int y)  {
        switch (type)
        {
            case ORC:
                return new Orc(refLink, x * Tile.TILE_WIDTH, y * Tile.TILE_WIDTH);
            case BLAZE:
                return new Blaze(refLink, x * Tile.TILE_WIDTH, y * Tile.TILE_WIDTH);
            case CHUPACABRA:
                return new Chupacabra(refLink, x * Tile.TILE_WIDTH, y * Tile.TILE_WIDTH);
        }
        return null;
    }
}
