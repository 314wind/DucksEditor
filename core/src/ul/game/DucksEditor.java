package ul.game;


import com.badlogic.gdx.Game;

import ul.game.control.FileChooser;
import ul.game.view.EditorScreen;


public class DucksEditor extends Game {
	EditorScreen editorScreen;

	@Override
	public void create () {
 		editorScreen = new EditorScreen();

		setScreen(editorScreen);

	}


	
	@Override
	public void dispose () {
		editorScreen.dispose();
	}
}
