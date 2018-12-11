package ul.game.model.entities.buttons.ajout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ul.game.dataFactory.TextureFactory;
import ul.game.model.Monde;

public class ButtonAjoutEnnemy extends ButtonAjout {
    private Monde monde ;
    private Vector2 pos;
    public ButtonAjoutEnnemy(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect) {
        super(position, largeur, hauteur, texture, world, isRect);
        this.corps.setUserData(this);
        monde = world;
        pos = position;
    }


    public Object executer(){
        return super.executer(new Vector2(pos.x +2,pos.y ), 2,2, TextureFactory.getTargetBeige(), true, "Beige");

    }

    @Override
    public String toString() {
        return super.toString()+getClass();
    }
}
