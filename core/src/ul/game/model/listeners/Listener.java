package ul.game.model.listeners;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import ul.game.model.Monde;
import ul.game.model.entities.Entity;
import ul.game.model.entities.Statique;
import ul.game.model.entities.buttons.Button;
import ul.game.view.EditorScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Listener implements InputProcessor {

    private Monde monde;
    private Camera camera;
    private EditorScreen editorScreen;

    private float xContact;
    private float yContact;

    private float delta_x;
    private float delta_y;

    private Object object;
    private ArrayList<Body> lesBodies;
    private ArrayList<Body> lesBodiesDown;

    public Listener(Monde monde, Camera camera, EditorScreen ed){
        this.monde = monde;
        this.camera = camera;
        this.lesBodies = new ArrayList<Body>();
        this.lesBodiesDown = new ArrayList<Body>();
        this.editorScreen = ed;

    }

    @Override
    public boolean keyDown(int keycode) {

        /* maybe un switch meme si c'est pas ouf*/
        //System.out.println(keycode + "\t" + Input.Keys.valueOf("A"));
        if (keycode == Input.Keys.ESCAPE){
            monde.dispose();
            Gdx.app.exit();
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        /*gestion du clique de la souris*/


        final Vector3 lesPositions = camera.unproject(new Vector3(screenX, screenY, 0));
        World w = monde.getWorld();
        final ArrayList<Body> lesBody = new ArrayList<Body>();


        w.QueryAABB(new QueryCallback() {
                @Override
                public boolean reportFixture(Fixture fixture) {
                    lesBody.add(fixture.getBody());
                    return true;
                }
            }, lesPositions.x,
                lesPositions.y,
                lesPositions.x +0.0001F,
                lesPositions.y +0.0001F);



        //lesBody contient tous les objets sous le clique.
        //LesBodies contient tous ce qui est bougeable à la souris
        Button b1 = null;
        /* recuperer le bouton en fonction de la position*/
        ArrayList<Body> boutons = monde.getLesBoutons();
        for(Body b : lesBody){
            if(boutons.contains(b)){ //si il y a un bouton sous le clique on l'execute
                int i = boutons.indexOf(b);
                object = boutons.get(i).getUserData(); //on recupere la classe boutton

                b1 = (Button) object;
                object = b1.executer(); //on execute son code en recuperant l'objet en sortit
                Object tmp = object;

                if(tmp!=null && !tmp.equals("supr") && !tmp.equals("save") && !tmp.equals("saveCurrent")){
                    Entity tmp2 = (Entity) tmp;
                    lesBodies.add(tmp2.corps);
                } else if (tmp !=null && tmp.equals("save")){
                    this.editorScreen.save("n");
                } else if (tmp !=null && tmp.equals("saveCurrent")){
                    this.editorScreen.save("c");
                }

            } else if (b.getType().toString() != "DynamicBody"){ //si l'objet est statique
                Entity tmp = (Statique) b.getUserData();
                if(tmp!=null && ((Statique) tmp).isMovable()){
                    lesBodies.add(b);
                }
            }

        }

        xContact = lesPositions.x;
        yContact = lesPositions.y;



        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        /*gestion du clique de la souris*/
        Vector3 lesPositions= camera.unproject(new Vector3(screenX,screenY,0));


        lesBodies.clear();
        Vector3 pointeur = new Vector3(screenX,screenY,0);
        World w = editorScreen.getMonde().getWorld();
        w.QueryAABB(new QueryCallback() {
                        @Override
                        public boolean reportFixture(Fixture fixture) {
                            lesBodies.add(fixture.getBody());
                            return true;
                        }
                    }, lesPositions.x,
                lesPositions.y,
                lesPositions.x +1,
                lesPositions.y +1);

        boolean trash = false;
        for(Body e : lesBodies){
            if(e.getUserData()!=null){
                if (e.getUserData().equals(editorScreen.getMonde().getTrash())){
                    trash = true;
                }

            }
        }

        if(trash) {
            for(Body e: lesBodies){
                if (!e.getUserData().equals(editorScreen.getMonde().getTrash())){
                    Entity tmp = (Entity) e.getUserData();
                    tmp.delete();
                }

            }

        }

        lesBodies.clear();

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        final Vector3 lesPositionsNouvelles = camera.unproject(new Vector3(screenX, screenY, 0));
        switch (Gdx.app.getType()){

            case Desktop:
                boolean boutonGauche = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
                boolean boutonDroite = Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
                boolean boutonMilieu = Gdx.input.isButtonPressed(Input.Buttons.MIDDLE);

                if (boutonGauche || boutonMilieu){ //translation clique gauche
                    int i = 0;
                    for(Body b : lesBodies){
                        if(b.getType().toString() != "DynamicBody"){
                            Entity tmp = (Entity) b.getUserData(); //on recupere la classe de l'objet à bouger
                            Body corps = tmp.corps;

                            float delta_x = lesPositionsNouvelles.x - xContact;
                            float delta_y = lesPositionsNouvelles.y - yContact;

                            float x = corps.getWorldCenter().x;
                            float y = corps.getWorldCenter().y;

                            corps.setTransform(x +delta_x, y + delta_y, corps.getAngle());

                        }
                    }
                    xContact = lesPositionsNouvelles.x;
                    yContact = lesPositionsNouvelles.y;

                } else if (boutonDroite){ //rotation
                    for(Body b : lesBodies) {
                        Entity tmp = (Entity) b.getUserData();
                        Sprite sprite = tmp.sprite;
                        sprite.setOrigin(b.getLocalCenter().x + sprite.getWidth() / 2, b.getLocalCenter().y + sprite.getHeight() / 2 );

                        Vector2 A = new Vector2(xContact, yContact);
                        Vector2 B = new Vector2(lesPositionsNouvelles.x, lesPositionsNouvelles.y);

                        float alpha  = MathUtils.atan2(A.x, A.y) - MathUtils.atan2(B.x, B.y);
                        alpha *= MathUtils.radiansToDegrees;

                        xContact = lesPositionsNouvelles.x;
                        yContact = lesPositionsNouvelles.y;

                        Body corps = tmp.corps;

                        corps.setTransform(corps.getPosition(), corps.getAngle() + alpha);
                        sprite.rotate(MathUtils.radiansToDegrees * alpha);

                    }
                }
                break;


            case Android:
                if(getNbAppuie()==1){
                    int i = 0;
                    for(Body b : lesBodies){
                        if(b.getType().toString() != "DynamicBody"){
                            Entity tmp = (Entity) b.getUserData(); //on recupere la classe de l'objet à bouger
                            Body corps = tmp.corps;

                            float delta_x = lesPositionsNouvelles.x - xContact;
                            float delta_y = lesPositionsNouvelles.y - yContact;

                            float x = corps.getWorldCenter().x;
                            float y = corps.getWorldCenter().y;

                            corps.setTransform(x +delta_x, y + delta_y, corps.getAngle());

                        }
                    }
                    xContact = lesPositionsNouvelles.x;
                    yContact = lesPositionsNouvelles.y;

                } else if(getNbAppuie() ==2){
                    if(Gdx.input.isTouched(1)){
                        for(Body b : lesBodies) {
                            Entity tmp = (Entity) b.getUserData();
                            Sprite sprite = tmp.sprite;
                            sprite.setOrigin(b.getLocalCenter().x + sprite.getWidth() / 2, b.getLocalCenter().y + sprite.getHeight() / 2 );



                            Vector2 G = new Vector2(b.getPosition().x, b.getPosition().y);
                            //System.out.println(G);
                            Vector2 A = new Vector2(xContact, yContact);
                            Vector2 B = new Vector2(lesPositionsNouvelles.x, lesPositionsNouvelles.y);

                            Vector2 v1 = G.sub(A);
                            Vector2 v2 = G.sub(B);

                            //float alpha = v1.angleRad(v2);
                            float alpha  = MathUtils.atan2(A.x, A.y) - MathUtils.atan2(B.x, B.y);
                            alpha *= MathUtils.radiansToDegrees;

                            xContact = lesPositionsNouvelles.x;
                            yContact = lesPositionsNouvelles.y;

                            Body corps = tmp.corps;

                            corps.setTransform(corps.getPosition(), corps.getAngle() + alpha);
                            sprite.rotate(MathUtils.radiansToDegrees * alpha);

                        }
                    }
                }



        }



        return true;
    }

    public int getNbAppuie(){
        int res=0;
        int cpt;
        for(cpt = 0; cpt < 20; cpt++){
            if(Gdx.input.isTouched(cpt)){
                res++;
            }
        }
        return res;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
