package com.java.mygame.spaceinvaders;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {// about screen @android
	private GameWorld world;// process x,y and check collision and press keyboard
	private GameRenderer renderer;// loop draw graphic on screen set x,y from press keyboard
	private SpriteBatch sb;// draw graphic 2d

	// call all constructor
	public GameScreen() {
		world = new GameWorld();// call process working
		renderer = new GameRenderer(world);// @loop draw process class GameWorld
		sb = new SpriteBatch();// create object draw graphic
	}

	// loop game main
	public void render(float delta) {
		world.update(delta);// update NEW process class GameWorld
		renderer.render(sb);// update NEW draw graphic 2d
	}

	/* //////////////////////////////////////////// */
	public void resize(int width, int hieght) {}
	public void show() {}
	public void hide() {}
	public void pause() {}
	public void resume() {}
	public void dispose() {}
	/* //////////////////////////////////////////// */
}
