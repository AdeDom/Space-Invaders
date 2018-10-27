package com.java.mygame.spaceinvaders;

import com.badlogic.gdx.Game;

public class SpaceInvaders extends Game {

	public static final String TITLE = "Space Invader";// title
	public static final int WIDTH = 800;// width
	public static final int HEIGHT = 600;// height
	public static PictureFile picFile;// picture

	// create game
	public void create() {
		picFile = new PictureFile();
		picFile.loadAtlas("spaceinvaders.pack", "pack");
		setScreen(new GameScreen());
	}
}