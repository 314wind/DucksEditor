package ul.game.model.entities.buttons.gestion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ul.game.model.Monde;
import ul.game.model.entities.buttons.Button;

public class ButtonCharger extends Button {
    private Monde monde;

    public ButtonCharger(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect) {
        super(position, largeur, hauteur, texture, world, isRect, true);
        this.corps.setUserData(this);
        monde = world;
    }

    @Override
    public Object executer() {
        super.executer();
        monde.afficherFileChooser();
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + getClass();
    }
}

