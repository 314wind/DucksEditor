package ul.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.graphics.Camera;
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

import java.util.ArrayList;

public class FileChooserListener implements InputProcessor {

    private Monde monde;
    private Camera camera;
    private EditorScreen editorScreen;
    private FileChooser fileChooser;

    public FileChooserListener(Monde monde, Camera camera, EditorScreen editorScreen){
        this.monde = monde;
        this.camera = camera;
        this.editorScreen = editorScreen;
        this.fileChooser = editorScreen.getFileChooser();
    }
    @Override
    public boolean keyDown(int keycode) {

        /* maybe un switch meme si c'est pas ouf*/
        //System.out.println(keycode + "\t" + Input.Keys.valueOf("A"));
        if (keycode == Input.Keys.ESCAPE){
            editorScreen.switchInputProcessorWorld();
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
                lesPositions.x +1,
                lesPositions.y +1);



        //lesBody contient tous les objets sous le clique.
        //LesBodies contient tous ce qui est bougeable à la souris
        Button b1 = null;
        /* recuperer le bouton en fonction de la position*/
        ArrayList<Entity> boutons = fileChooser.getLesBoutons();
        for(Body b : lesBody){
            Object o = b.getUserData();
            Entity e = (Entity) o;
            if(boutons.contains(e)){
                Button tmp = (Button) o;
                //System.out.println("EXECUTION "+tmp.executer());
                fileChooser.executer(tmp.executer());
            }


        }
/*
        xContact = lesPositions.x;
        yContact = lesPositions.y;
*/


        return true;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
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
