package ul.game.model.entities.buttons.gestion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ul.game.model.Monde;
import ul.game.model.entities.Entity;
import ul.game.model.entities.buttons.Button;

public class BouttonSupprimer extends Button {

    private Monde monde;

    public BouttonSupprimer(Vector2 position, int largeur, int hauteur, Texture texture, Monde world, boolean isRect) {
        super(position, largeur, hauteur, texture, world, isRect,true);
        this.corps.setUserData(this);
        monde = world;
        monde.setBoutonSupr(this);
    }


    public Object executer(Entity... lesE) {
        super.executer();
        for(Entity e : lesE){
            e.delete();
        }
        System.out.println("Suppression");

        return "supr";
    }

    @Override
    public String toString() {
        return super.toString() + getClass();
    }
}
