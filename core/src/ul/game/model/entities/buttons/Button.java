package ul.game.model.entities.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ul.game.model.Monde;
import ul.game.model.entities.Statique;

public abstract class Button extends Statique {
    public Button(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect, boolean adding) {
        super(position, largeur, hauteur, texture, world, isRect, "", adding, true);
        if(adding){
            world.addButton(this);
        }
        setMovable(false);

    }

    public Object executer(){
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
