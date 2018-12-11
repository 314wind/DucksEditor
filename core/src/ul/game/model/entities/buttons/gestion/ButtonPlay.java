package ul.game.model.entities.buttons.gestion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ul.game.model.Monde;
import ul.game.model.entities.buttons.Button;

public class ButtonPlay extends Button {

    private Monde monde;

    public ButtonPlay(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect) {
        super(position, largeur, hauteur, texture, world, isRect, true);
        this.corps.setUserData(this);
        this.monde = world;
    }

    @Override
    public Object executer() {
        super.executer();
        monde.switchStaticToDyna();
        monde.reveiller();
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + getClass();
    }
}
