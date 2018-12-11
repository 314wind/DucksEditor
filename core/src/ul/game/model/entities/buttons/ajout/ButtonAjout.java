package ul.game.model.entities.buttons.ajout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ul.game.dataFactory.TextureFactory;
import ul.game.model.Monde;
import ul.game.model.entities.Entity;
import ul.game.model.entities.Statique;
import ul.game.model.entities.buttons.Button;

public abstract class ButtonAjout extends Button {
    private Monde monde;

    public ButtonAjout(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect) {
        super(position, largeur, hauteur, texture, world, isRect, true);
        monde = world;
    }

    public Object executer(Vector2 pos, int l, int h, Texture t, boolean isRec, String type) {

        Object res = new Statique(pos, l, h, t, monde, isRec, type, true, false);
        monde.addStaticToDyna((Statique)res);
        monde.addBlock((Entity) res);

        return res;
    }
}
