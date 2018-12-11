package ul.game.model.entities.buttons.selection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ul.game.model.Monde;
import ul.game.model.entities.buttons.Button;

public class ButtonSelection extends Button {
    public ButtonSelection(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect) {
        super(position, largeur, hauteur, texture, world, isRect, false);

    }


    @Override
    public Object executer() {
        return 2;
    }
}

