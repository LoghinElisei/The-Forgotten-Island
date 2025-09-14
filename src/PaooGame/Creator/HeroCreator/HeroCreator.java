package PaooGame.Creator.HeroCreator;

import PaooGame.Creator.EntityCreator;
import PaooGame.Creator.EntityType;
import PaooGame.Entity.Character;
import PaooGame.Entity.Hero;
import PaooGame.RefLinks;

public class HeroCreator extends EntityCreator {
    @Override
    protected Character createItem(EntityType type, RefLinks refLink, int x, int y) {
        return new Hero(refLink, x, y);
    }
}
