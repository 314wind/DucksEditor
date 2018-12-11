package ul.game.dataFactory;

import com.badlogic.gdx.graphics.Texture;
import ul.game.model.parametres.Parametres;


public class TextureFactory {


    private static TextureFactory ourInstance = new TextureFactory();

    public static TextureFactory getInstance() {
        return ourInstance;
    }

    public static Texture smiley = new Texture("badlogic.jpg");
    public static Texture background =  new Texture("DonnéesEditeur/images/background.png");
    public static Texture beam = new Texture("DonnéesEditeur/images/beam.png");
    public static Texture block = new Texture("DonnéesEditeur/images/block.png");
    public static Texture cancel = new Texture("DonnéesEditeur/images/cancel.png");
    public static Texture duck = new Texture("DonnéesEditeur/images/cancel.png");
    public static Texture editPanel = new Texture("DonnéesEditeur/images/editPanel.png");
    public static Texture leftArrow = new Texture("DonnéesEditeur/images/leftarrow.png");
    public static Texture load = new Texture("DonnéesEditeur/images/Load.png");
    public static Texture play = new Texture("DonnéesEditeur/images/Play.png");
    public static Texture rewrite = new Texture("DonnéesEditeur/images/Rewrite.png");
    public static Texture rightArrow = new Texture("DonnéesEditeur/images/rightarrow.png");
    public static Texture save = new Texture("DonnéesEditeur/images/Save.png");
    public static Texture stop = new Texture("DonnéesEditeur/images/Stop.png");
    public static Texture targetBeige = new Texture("DonnéesEditeur/images/targetbeige.png");
    public static Texture targetBlue = new Texture("DonnéesEditeur/images/targetblue.png");
    public static Texture trash = new Texture("DonnéesEditeur/images/Trash.png");



    public static Texture getSmiley(){ return smiley; }

    public static Texture getBackground() {
        return background;
    }

    public static Texture getBeam() {
        return beam;
    }

    public static Texture getBlock() {
        return block;
    }

    public static Texture getCancel() {
        return cancel;
    }

    public static Texture getDuck() {
        return duck;
    }

    public static Texture getEditPanel() {
        return editPanel;
    }

    public static Texture getLeftArrow() {
        return leftArrow;
    }

    public static Texture getLoad() {
        return load;
    }

    public static Texture getPlay() {
        return play;
    }

    public static Texture getRewrite() {
        return rewrite;
    }

    public static Texture getRightArrow() {
        return rightArrow;
    }

    public static Texture getSave() {
        return save;
    }

    public static Texture getStop() {
        return stop;
    }

    public static Texture getTargetBeige() {
        return targetBeige;
    }

    public static Texture getTargetBlue() {
        return targetBlue;
    }

    public static Texture getTrash() {
        return trash;
    }

}
