package ul.game.model.parametres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import ul.game.dataFactory.TextureFactory;

public class Parametres {


    private static Parametres ourInstance = new Parametres();

    public static Parametres getInstance() {
        return ourInstance;
    }

    private static int screenWidth = Gdx.graphics.getWidth();
    private static int screenHeight= Gdx.graphics.getHeight();
    private static int worldWidth = 64;
    private static int worldHeight = 36;
    private static float gravityValue = -9.8F;


    private Parametres() {

    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }


    public static float getWorldWidth() {
        return worldWidth;
    }

    public static float getWorldHeight(){
        return worldHeight;
    }

    public static float getGravityValue() {
        return gravityValue;
    }


}
