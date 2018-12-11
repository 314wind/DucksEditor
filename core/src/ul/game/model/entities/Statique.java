package ul.game.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.w3c.dom.Entity;
import ul.game.model.Monde;

public class Statique extends ul.game.model.entities.Entity{
    private boolean dyna;
    private boolean movable = true;
    /*
        private static final short CATEGORY_STATIC = 0;
        private static final short MASK_STATIC = -1;*/
    public boolean isRec;
    private Monde monde;

    public Statique(Vector2 position, int largeur, int hauteur, Texture texture, Monde monde, boolean isRect, String type, boolean adding, boolean notCollide) {
        super(largeur, hauteur, texture, monde, type);
        this.monde = monde;
        World world = monde.getWorld();
        this.isRec = isRect;
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        Shape shape = super.buildShape(isRect, true);

        bodyDef.type = BodyDef.BodyType.StaticBody;
        fixtureDef.shape = shape;
        fixtureDef.density = 5F;
        //fixtureDef.friction = 0.9F;
        fixtureDef.isSensor = notCollide;
/*
        fixtureDef.filter.categoryBits = CATEGORY_STATIC;
        fixtureDef.filter.maskBits = MASK_STATIC;*/
        bodyDef.position.set(position);

        this.corps = world.createBody(bodyDef); //creation du body via le world
        this.corps.setUserData(this);
        this.corps.createFixture(fixtureDef);

        super.setCorps(corps);
        if(adding)
            monde.addEntity(this);
        this.dyna = false;

        shape.dispose();
    }

    public void setMovable(boolean b){
        super.setMovable(b);
    }
    public boolean isMovable(){
        return super.isMovable();
    }

    public void draw(SpriteBatch sb){
        super.spriteSetPosition(corps.getWorldCenter().x, corps.getWorldCenter().y);
        super.draw(sb);
    }

    public String toString(){
        return super.toString();
    }

    @Override
    public void switchDyna() {
        corps.setType(BodyDef.BodyType.DynamicBody);
        this.dyna = true;
        spriteSetPosition(corps.getWorldCenter().x, corps.getWorldCenter().y);

    }
}
