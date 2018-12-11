package ul.game.model.entities.buttons.ajout;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ul.game.dataFactory.TextureFactory;
import ul.game.model.Monde;

public class ButtonAjoutBeam extends ButtonAjout {

    private Monde monde ;
    private Vector2 pos;
    public ButtonAjoutBeam(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect) {
        super(position, largeur, hauteur, texture, world, isRect);
        this.corps.setUserData(this);
        monde = world;
        pos = position;
    }


    public Object executer(){
        return super.executer(new Vector2(pos.x +1 ,pos.y), 1,4, TextureFactory.getBeam(), true, "Beam");
    }

    @Override
    public String toString() {
        return super.toString()+getClass();
    }
}
