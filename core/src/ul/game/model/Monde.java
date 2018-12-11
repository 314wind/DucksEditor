package ul.game.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import ul.game.dataFactory.TextureFactory;
import ul.game.model.entities.*;
import ul.game.model.entities.Entity;
import ul.game.model.entities.buttons.*;
import ul.game.model.entities.buttons.ajout.ButtonAjoutEnnemy;
import ul.game.model.entities.buttons.gestion.*;
import ul.game.model.entities.buttons.ajout.ButtonAjoutBeam;
import ul.game.model.entities.buttons.ajout.ButtonAjoutBlock;
import ul.game.model.entities.buttons.gestion.BouttonSupprimer;
import ul.game.model.entities.buttons.gestion.ButtonPause;
import ul.game.model.entities.buttons.gestion.ButtonPlay;
import ul.game.model.entities.buttons.gestion.ButtonSave;
import ul.game.model.entities.buttons.gestion.ButtonSaveCurrent;
import ul.game.model.loader.LevelLoader;
import ul.game.model.parametres.Parametres;
import ul.game.view.EditorScreen;

import java.util.ArrayList;
import java.util.logging.Level;

public class Monde {
    private SpriteBatch sb;
    private World world;
    private EditorScreen editorScreen;
    private LevelLoader levelLoader;

    protected ArrayList<Entity> lesEntites;
    protected ArrayList<Body> lesBoutons;
    protected ArrayList<Entity> staticToDyna;
    protected ArrayList<Entity> lesBlocs;
    protected Button boutonSupr;
    protected Entity trash;

    public Monde(EditorScreen editorScreen) {

        this.levelLoader = new LevelLoader(this);
        this.editorScreen = editorScreen;
        world = new World(new Vector2(0, Parametres.getGravityValue()), true); //nouveau monde avec une gravité
        //la gravité s'applique qu'a l'axe des Y
        //doSleep:true signifie que oui ou non on simule les elements inactifs

        lesBoutons = new ArrayList<Body>();
        lesEntites = new ArrayList<Entity>();
        staticToDyna = new ArrayList<Entity>();
        lesBlocs = new ArrayList<Entity>();

        ///////LES SHAPES////////
        float[] points = {0, 0,
                Parametres.getWorldWidth(), 0,
                0, 6,
                Parametres.getWorldWidth(), 6};

        //les points du polygone pour la collision sur le bas de l'ecran
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(points);
        ///////LES STRUCTURES DE CORPS///////
        //statique
        BodyDef bodyDefStatic = new BodyDef();
        bodyDefStatic.type = BodyDef.BodyType.StaticBody; //corps statique
        bodyDefStatic.position.set(0, 0);
        ///////LES CORPS///////
        Body elementStatique = world.createBody(bodyDefStatic); //ajout un objet statique dans le monde
        //this.elementDynamique = world.createBody(bodyDefDyna); //ajout un objet dynamique dans le monde

        ///////////////////////
        ///////LES PROPRIETES DES CORPS///////
        FixtureDef fixtureDefStatique = new FixtureDef();
        //FixtureDef fixtureDefDynamique = new FixtureDef();
        //definit la forme
        fixtureDefStatique.shape = polygonShape;
        fixtureDefStatique.density = 0.5F;
        fixtureDefStatique.restitution = 0.0F;
        fixtureDefStatique.isSensor = false;
        //////////////////////////////////
        ///////ASSOCIATION DES SHAPES AUX OBJETS///////
        elementStatique.createFixture(fixtureDefStatique);
        polygonShape.dispose();


        Entity mur = new Statique(new Vector2(Parametres.getWorldWidth()/4, 18),
                1, (int) Parametres.getWorldHeight()*2,
                TextureFactory.getBeam(), this, true, "Beam", true, false);
        ((Statique) mur).setMovable(false);


        /*===LES BOUTONS===*/
        Entity boutonStart = new ButtonPlay(new Vector2(5, 28), 2, 2, TextureFactory.getPlay(), this, false);
        Entity boutonPause = new ButtonPause(new Vector2(9, 28), 2, 2, TextureFactory.getStop(), this, false);
        Entity boutonSave = new ButtonSave(new Vector2(3, 34), 2, 2, TextureFactory.getSave(), this, false);
        Entity boutonSaveCurr = new ButtonSaveCurrent(new Vector2(11, 34), 2, 2, TextureFactory.getRewrite(), this, false);
        Entity boutonCharger = new ButtonCharger(new Vector2(7, 30), 2,2,TextureFactory.getLoad(), this, false);
        Entity ajoutBlock = new ButtonAjoutBlock(new Vector2(3, 20), 2, 2, TextureFactory.getBlock(), this, true);
        Entity ajoutBeam = new ButtonAjoutBeam(new Vector2(7, 20), 1, 4, TextureFactory.getBeam(), this, true);
        Entity trash = new BouttonSupprimer(new Vector2(7, 10), 3, 4, TextureFactory.getTrash(), this, true);
        Entity ajoutBeige = new ButtonAjoutEnnemy(new Vector2(7, 15), 2,2, TextureFactory.getTargetBeige(), this, false);
        this.trash = trash;

        boutonCharger.setMovable(false);
        boutonPause.setMovable(false);
        boutonStart.setMovable(false);
        boutonSaveCurr.setMovable(false);
        boutonSave.setMovable(false);
        ajoutBlock.setMovable(false);
        ajoutBeam.setMovable(false);
        mur.setMovable(false);
    }

    public ArrayList<Body> getLesBoutons() {
        return lesBoutons;
    }



    public Monde(SpriteBatch sb) {
        this.sb = sb;
    }

    public void setBoutonSupr(Button b){
        this.boutonSupr=b;
    }

    public World getWorld(){
        assert(world!=null):"Le monde n'est pas instancié";
        return this.world;

    }

    public void addBlock(Entity b){lesBlocs.add(b);}

    public void addEntity(Entity e){
        lesEntites.add(e);
    }

    public void addButton(Entity b){
        lesBoutons.add(b.corps);
    }

    public void addStaticToDyna(Statique b){
        staticToDyna.add(b);
    }


    public void draw(SpriteBatch sb) {
        assert(lesEntites!=null):"pas d'entités";
        for (Entity elem : lesEntites) {

            Vector2 tmp = elem.corps.getLinearVelocity();
            if(Math.abs(tmp.x) < 1.0E-2)
                elem.corps.setLinearVelocity(0, tmp.y);

            if(Math.abs(tmp.y) < 1.0E-2)
                elem.corps.setLinearVelocity(elem.corps.getLinearVelocity().x, 0);

            elem.draw(sb);
        }
    }

    public void reveiller() {
        for (Entity elem : lesBlocs) {
            elem.setAwake(true);
        }
    }

    public void endormir() {
        for (Entity elem : lesBlocs) {
            elem.setAwake(false);
        }
    }

    public void switchStaticToDyna() {
        for (Entity e : staticToDyna){
            e.switchDyna();
            e.setMovable(false);
        }
        staticToDyna = new ArrayList<Entity>();
    }


    public void switchToStatic() {
        for(Entity e : lesBlocs){
            if(e.corps.getType() == BodyDef.BodyType.DynamicBody){
                e.corps.setType(BodyDef.BodyType.StaticBody);
                e.setMovable(true);
            }
            staticToDyna.add(e);
        }
    }

    public void delete(ArrayList<Entity> ls){
        for (Entity e : ls){
            world.destroyBody(e.corps);
            lesEntites.remove(e);
        }



    }
    public void delete(Entity entity) {
        world.destroyBody(entity.corps);
        lesEntites.remove(entity);
        lesBlocs.remove(entity);
    }


    public String getMenuString(){
        StringBuilder res = new StringBuilder();
        ArrayList<Entity> tmp = new ArrayList<Entity>(lesEntites);
        tmp.removeAll(lesBlocs);
        for(Entity e : tmp){
            res.append(e.toString());
            res.append("\n");
        }

        return res.toString();
    }

    public void dispose() {
        for (Entity e : lesEntites){
            e.dispose();
        }
    }

    public String toString(){
        StringBuilder res = new StringBuilder();
        for(Entity e : lesBlocs){
            res.append(e.toString()+"\n");
        }
        return res.toString();

    }

    public Pixmap getScreenShot(){
        int x = Parametres.getScreenWidth()/4;
        int y = 0;

        byte[] frameBufferPixels = ScreenUtils.getFrameBufferPixels(
                x, y,
                Gdx.graphics.getBackBufferWidth(),
                Gdx.graphics.getBackBufferHeight(), true);


        Pixmap screenShot = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(frameBufferPixels, 0, screenShot.getPixels(), frameBufferPixels.length);

        return screenShot;
    }

    public void afficherFileChooser() {
        this.editorScreen.afficherFileChooser();
    }

    public Entity getTrash() {
        return trash;
    }

    public void load(String loaddedString) {
        levelLoader.load(loaddedString);
    }

    public void clean() {
        delete(lesBlocs);
        lesBlocs.clear();
    }
}
