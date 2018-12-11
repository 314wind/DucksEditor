package ul.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ul.game.control.FileChooser;
import ul.game.control.FileChooserListener;
import ul.game.dataFactory.TextureFactory;
import ul.game.model.Monde;
import ul.game.model.listeners.Listener;
import ul.game.model.loader.LevelLoader;
import ul.game.model.parametres.Parametres;


public class EditorScreen extends ScreenAdapter {
    private Monde monde;
    private SpriteBatch sb;
    private Camera camera;
    private Texture background;
    private FileChooser fileChooser;
    private boolean renderWorld;
    private InputProcessor ecouteurMonde;
    private InputProcessor ecouteurFile;

    private static final boolean DEBUG = false;
    private Listener inputManagement;


    private Box2DDebugRenderer debugRenderer;

    public EditorScreen(){

        sb = new SpriteBatch();
        monde = new Monde(this);
        TextureFactory factory = TextureFactory.getInstance();
        background = TextureFactory.getBackground();
        this.renderWorld = true;
        assert(background!=null):"bug factoruy";


        camera = new OrthographicCamera(Parametres.getWorldWidth(), Parametres.getWorldHeight()); //creation de la camera

        //vp = new FitViewport(Parametres.getWorldWidth(), Parametres.getWorldHeight(), camera); //pour faire le lien entre unité du monde et camera
        //vp.apply();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0); //place la camera au centre du monde
        camera.update();

        if (DEBUG)
            debugRenderer = new Box2DDebugRenderer(true, true ,true ,true, true, true);

        this.fileChooser = new FileChooser(this);
        this.ecouteurMonde = new Listener(monde, camera, this);
        this.ecouteurFile = new FileChooserListener(monde, camera, this);

        Gdx.input.setInputProcessor(ecouteurMonde);
    }

    public void switchInputProcessorWorld(){
        Gdx.input.setInputProcessor(ecouteurMonde);
        renderWorld = true;
    }

    public void switchInputProcessorFile(){
        Gdx.input.setInputProcessor(ecouteurFile);
        renderWorld = false;

    }
    @Override
    public void resize (int width, int height){
        //vp.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();
    }

    public SpriteBatch getSb() {
        return sb;
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        World world = monde.getWorld();
        assert(world!=null):"le monde n'existe pas";
        sb.setProjectionMatrix(camera.combined);
        if(renderWorld) {
            //System.out.println("here\n____\n");
            sb.begin();
                sb.draw(background, 0, 0, Parametres.getWorldWidth(), Parametres.getWorldHeight());
                sb.draw(TextureFactory.getEditPanel(), 0, 0, Parametres.getWorldWidth() / 4, Parametres.getWorldHeight());
                monde.draw(sb);
            sb.end();
        } else {

            fileChooser.render(delta);

        }

        world.step(1f/60f, 6, 2);
        if (DEBUG)
            debugRenderer.render(world, camera.combined);
        world.step(Gdx.graphics.getDeltaTime(), 0, -10);

        //System.out.println("debugRenderer");
    }



    public void dispose () {
        sb.dispose();
        background.dispose();

    }

    public void draw(){

    }

    public void save(String c) {
        this.fileChooser.save(c);
    }

    public Monde getMonde() {
        return monde;
    }

    public Camera getCamera() {
        return camera;
    }

    public void afficherMonde(){
        switchInputProcessorWorld();
        renderWorld = true;
    }
    public void afficherFileChooser() {
        switchInputProcessorFile();
        renderWorld = false;



    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public void loadLevel(String loaddedString) {
        this.monde.load(loaddedString);
        switchInputProcessorWorld();
    }
}
