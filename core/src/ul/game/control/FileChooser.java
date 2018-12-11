package ul.game.control;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ul.game.dataFactory.TextureFactory;
import ul.game.model.Monde;

import ul.game.model.entities.Entity;
import ul.game.model.entities.buttons.selection.ButtonLeft;
import ul.game.model.entities.buttons.selection.ButtonReturn;
import ul.game.model.entities.buttons.selection.ButtonRight;
import ul.game.model.entities.buttons.selection.ButtonSelection;
import ul.game.model.parametres.Parametres;
import ul.game.view.EditorScreen;

import java.io.File;
import java.util.ArrayList;


public class FileChooser {
    private EditorScreen editorScreen;
    private String path;
    private ArrayList<String> lesNomsFichiers;
    private String nomFichierSauvegarde;
    private int indice;

    private Batch sb;
    private Camera camera;

    private Stage stage;
    private ArrayList<Entity> lesBoutons;

    private Texture previewLevel;
    private boolean isLevelSelected = false;
    private InputProcessor inputProcessor;
    private World world;
    private int nbFichiers;

    public FileChooser(EditorScreen ed){


        this.stage = new Stage();
        //init var sauvegarde
        this.editorScreen = ed;
        this.nomFichierSauvegarde = "save";
        this.lesNomsFichiers = new ArrayList<String>();
        this.indice = getNbFiles();

        this.lesBoutons = new ArrayList<Entity>();
        this.sb = new SpriteBatch();
        this.camera = this.editorScreen.getCamera();
        //init var selection
        this.path = "MadDuckFiles/";
        this.nbFichiers = getNbFiles();

        //**** partie selection niveau****///

        Entity leftArrow = new ButtonLeft(new Vector2(5,20),
                4,2,
                TextureFactory.getLeftArrow(), this.editorScreen.getMonde(), true);

        Entity rightArrow = new ButtonRight(new Vector2(Parametres.getWorldWidth()-5, 20),
                4, 2,
                TextureFactory.getRightArrow(), this.editorScreen.getMonde(), true);

        Entity select = new ButtonSelection(new Vector2(Parametres.getWorldWidth()/2 + Parametres.getWorldWidth()/16, 20),
                30, 20,
                TextureFactory.getSmiley() , this.editorScreen.getMonde(), true);

        Entity exit = new ButtonReturn(new Vector2(Parametres.getWorldWidth()/2, 5),
                4, 4,
                TextureFactory.getCancel(), this.editorScreen.getMonde(), true);


        leftArrow.setMovable(false);
        rightArrow.setMovable(false);
        select.setMovable(false);
        exit.setMovable(false);

        this.lesBoutons.add(leftArrow);
        this.lesBoutons.add(rightArrow);
        this.lesBoutons.add(select);
        this.lesBoutons.add(exit);

        /***** partie sauvegarde etc etc ******/

        boolean isLocAvailable = Gdx.files.isLocalStorageAvailable(); //stockage local availabe ?
        boolean isExtAvailable = Gdx.files.isExternalStorageAvailable(); //stockage extern available ?

        if(isExtAvailable){ //si le stockage externe est OK
            String extRoot = Gdx.files.getExternalStoragePath(); //path of local stockage
            //System.out.println(localRoot);
            //path = MadDuckFiles/
            File dir = new File(extRoot+path);
            if(!dir.exists()){
                try{
                    System.out.println("Creation dossier a " + extRoot + path);
                    boolean tmp = new File(extRoot+path).mkdir();

                    System.out.print("Directory created ");
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
            majFichiers();
        }

    }

    public void majFichiers(){
        //this.indice = getNbFiles()-1;
        this.nbFichiers = getNbFiles();
    }

    public int getNbFiles(){
        FileHandle dirHandle;
        int res = 0;
        String extRoot = Gdx.files.getExternalStoragePath();
        dirHandle = Gdx.files.external("/"+path);
        for (FileHandle entry: dirHandle.list()) {
            res++;
        }
        return res;
    }

    public void save(String c) {
        majFichiers();
        if(c=="n") {
            indice++;
        }
        int indicetmp  = Math.abs(this.indice);
        //FileHandle filePNG = new FileHandle(path);

        boolean isExtAvailable = Gdx.files.isExternalStorageAvailable(); //stockage externe availabe ?
        String extRoot = Gdx.files.getExternalStoragePath();
        if(isExtAvailable){
            File dir = new File(extRoot+path+"save"+nbFichiers);
            if(!dir.exists()){
                try{
                    dir.mkdir();
                    System.out.print("Directory created ");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            //System.out.println("===>" + dir.toString());
            FileHandle fileMDL = new FileHandle(dir.toString()+"/"+"save"+nbFichiers+".mdl");
            fileMDL.writeString(editorScreen.getMonde().toString(),false);
            System.out.println("Fichier enregistré");

            Pixmap px = this.editorScreen.getMonde().getScreenShot();
            assert(px!=null);
            FileHandle filePNG = new FileHandle(dir.toString() + "/"+"save"+nbFichiers+".png");
            PixmapIO.writePNG(filePNG, px);
            px.dispose();
            System.out.println("ScreenShot enregistré");

        }
    }



    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        Monde monde = editorScreen.getMonde();
        World world = monde.getWorld();
        assert(world!=null):"le monde n'existe pas";
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
            sb.draw(TextureFactory.getEditPanel(),0,0,Parametres.getWorldWidth(), Parametres.getWorldHeight());
            for(Entity e : lesBoutons){
                e.draw((SpriteBatch) sb);
            }
        sb.end();
        stage.draw();


        world.step(1f/60f, 6, 2);
        //debugRenderer.render(world, camera.combined);
        world.step(Gdx.graphics.getDeltaTime(), 0, -10);

        //System.out.println("debugRenderer");
    }

    public ArrayList<Entity> getLesBoutons() {
        return lesBoutons;
    }

    public void executer(Object e) {




        int t = (Integer) e;
        if(-1 == t){ //fleche left
            this.indice--;
        } else if (1 == t){ //fleche right
            this.indice++;
        } else if (0 == t){ //exit
            editorScreen.switchInputProcessorWorld();
        } else if (2 == t) { //selection
            int indicetmp  = Math.abs(this.indice);
            String localRoot = Gdx.files.getExternalStoragePath();
            FileHandle dirHandle = Gdx.files.external(path+"save"+indicetmp+"/save"+indicetmp+".mdl");
            String loaddedString = dirHandle.readString();

            this.editorScreen.loadLevel(loaddedString);
        }
        if(nbFichiers!=0){
            this.indice%=nbFichiers;

        }

        if(this.nbFichiers>0){
            int indicetmp  = Math.abs(this.indice);
            boolean isExtAvailable = Gdx.files.isExternalStorageAvailable(); //stockage local availabe ?
            if(isExtAvailable) {
                File dir = new File(Gdx.files.getExternalStoragePath()+path + "save" + indicetmp);
                if (dir.exists()){

                    FileHandle fichierPNG = new FileHandle(Gdx.files.getExternalStoragePath()+path + "save" + indicetmp + "/save" + indicetmp + ".png");
                    Pixmap pix = new Pixmap(fichierPNG);
                    this.previewLevel = new Texture(pix);
                    lesBoutons.get(2).setTexture(previewLevel);
                }

            }
        }

        System.out.println(indice);
    }
}
