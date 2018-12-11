package ul.game.model.entities.buttons.gestion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ul.game.model.Monde;
import ul.game.model.entities.buttons.Button;

public class ButtonSaveCurrent extends Button {
    public ButtonSaveCurrent(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect) {
        super(position, largeur, hauteur, texture, world, isRect, true);
        this.corps.setUserData(this);
    }

    @Override
    public Object executer() {
        super.executer();
        return "saveCurrent";
    }

    @Override
    public String toString() {
        return super.toString() + getClass();
    }
}
