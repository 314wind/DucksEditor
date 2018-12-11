package ul.game.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ul.game.model.Monde;


public abstract class Entity {

    public Body corps;
    public Monde world;
    public Sprite sprite;
    public boolean dynamique;
    public boolean isPlay = false;
    public boolean isMovable = true;
    public String type;


    public Entity(int largeur, int hauteur, Texture texture, Monde world, String type){

        this.dynamique = dynamique;
        this.sprite = new Sprite(texture);
        sprite.setSize(largeur, hauteur);
        sprite.setOrigin(largeur/2, hauteur/2);
        this.world = world;
        this.type = type;

    }

    public void setCorps(Body body){
        this.corps = body;
    }



    public void spriteSetPosition(float x, float y){
        sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);

    }
    public void draw(SpriteBatch sb){
        sprite.setRotation(corps.getAngle()*MathUtils.radiansToDegrees);
        sprite.draw(sb);
    }

    public Shape buildShape(boolean isRect, boolean statique){
        if(isRect){
            PolygonShape shape = new PolygonShape();
            shape.set(new float[]{
                    0, 0,
                    sprite.getWidth(), 0,
                    0, sprite.getHeight(),
                    sprite.getWidth(), sprite.getHeight()
            });

            shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2, new Vector2(0, 0), 0);
            return shape;
        } else {
            CircleShape shape = new CircleShape();
            shape.setRadius(sprite.getWidth()/2);

            return shape;
        }

    }

    public void setAwake(boolean flag){
        corps.setAwake(flag);
    }


    public void setMovable(boolean b) {
        this.isMovable = b;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void delete() {
        world.delete(this);
    }

    public void dispose(){
        this.sprite.getTexture().dispose();
    }

    public String toString(){
        return  this.corps.getPosition().x + "_" +
                this.corps.getPosition().y + "_" +
                this.corps.getAngle() +"_" +
                this.sprite.getWidth()+ "_" +
                this.sprite.getHeight() + "_" +
                this.type;

    }

    public void switchDyna(){

    }
    public void setTexture(Texture texture) {
        this.sprite.setTexture(texture);
    }
}
