package ul.game.model.loader;

import com.badlogic.gdx.math.Vector2;
import ul.game.dataFactory.TextureFactory;
import ul.game.model.Monde;
import ul.game.model.entities.Entity;
import ul.game.model.entities.Movable;
import ul.game.model.entities.Statique;

import java.util.*;
import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelLoader {
    public static Logger LOGGER     = Logger.getLogger("TestGetConstrutor");
    private Class classe;
    private String nomClasse;
    private Monde monde;

    public LevelLoader(Monde monde){
        this.monde = monde;
    }


    public void load (String lesParam) {

        String[] params = lesParam.split("\n");
        String[] tmp = new String[6*params.length];

        String leMenu = monde.getMenuString();

        this.monde.clean();
        for (String s : params) {
            tmp = s.split("_");
            int offset = 5;

            //System.out.println(s);
            //System.out.println("___");
            float x = Float.valueOf(tmp[0]);
            float y = Float.valueOf(tmp[1]);
            float a = Float.valueOf(tmp[2]);
            float w = Float.valueOf(tmp[3]);
            float h = Float.valueOf(tmp[4]);
            String t = tmp[5];

            switch (t){

                case "Block":
                    //System.out.println("\n*generation block*\n");
                    Entity A = new Movable(new Vector2(x,y), (int)w, (int)h, TextureFactory.getBlock(), monde, true, "Block", true);
                    A.corps.setTransform(x,y,a);
                    A.setMovable(true);
                    monde.addBlock(A);
                    break;


                case "Beam":
                    //System.out.println("\n*generation beam*\n");
                    Entity B = new Movable(new Vector2(x,y), (int)w, (int)h, TextureFactory.getBeam(), monde, true, "Beam", true);
                    B.corps.setTransform(x,y,a);
                    B.sprite.setCenter(B.sprite.getWidth()/2, B.sprite.getHeight()/2);
                    B.setMovable(true);
                    monde.addBlock(B);
                    break;

                case "Beige":
                    Entity C = new Movable(new Vector2(x,y), (int)w, (int)h, TextureFactory.getTargetBeige(), monde,false, "Beige", true);
                    C.corps.setTransform(x,y,a);
                    C.setMovable(true);
                    monde.addBlock(C);
                    break;
            }

        }


        //classe = Class.forName(nomClasse);
    }
}
