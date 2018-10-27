package com.java.mygame.spaceinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.java.mygame.spaceinvaders.SpaceInvaders;

public class DesktopLauncher {

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// set screen
		config.title = SpaceInvaders.TITLE;
		config.width = SpaceInvaders.WIDTH;
		config.height = SpaceInvaders.HEIGHT;
		
		new LwjglApplication(new SpaceInvaders(), config);
	}

}
