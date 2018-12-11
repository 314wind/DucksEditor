package ul.game.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.w3c.dom.Entity;
import ul.game.model.Monde;

public class Movable extends ul.game.model.entities.Entity {
/*
    private static final short CATEGORY_DYNA = 1;
    private static final short MASK_DYNA = (short)(0 | 1);*/
    private boolean isRec;
    public Movable(Vector2 position, int largeur, int hauteur, Texture texture, Monde monde, boolean isRect, String type, boolean adding) {
        super(largeur, hauteur, texture, monde, type);

        World world = monde.getWorld();
        this.isRec = isRect;
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        Shape shape = super.buildShape(isRect, false);

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        fixtureDef.shape = shape;
        fixtureDef.density = 2F;
        //fixtureDef.friction = 0.9F;
        fixtureDef.restitution = 0.01F;
/*
        fixtureDef.filter.categoryBits = CATEGORY_DYNA;
        fixtureDef.filter.maskBits = MASK_DYNA;
*/
        bodyDef.position.set(position);

        this.corps = world.createBody(bodyDef); //creation du body via le world
        this.corps.setUserData(this);
        this.corps.createFixture(fixtureDef);

        super.setCorps(corps);

        shape.dispose();
        setAwake(false);
        if(adding)
            monde.addEntity(this);
    }

    public String toString(){
        return super.toString();
    }

    public void draw(SpriteBatch sb){

        super.spriteSetPosition(corps.getWorldCenter().x, corps.getWorldCenter().y);
        super.draw(sb);
    }


}
