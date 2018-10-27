package com.java.mygame.spaceinvaders;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;

public class Player extends Anime {// spaceship player
	// shot
	public Shot shot = new Shot();
	public ArrayList<Shot> shots = new ArrayList<Shot>();
	public Sound soundShot = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.wav"));//sound

	// constructor
	public Player(int x, int y) {
		super(x, y);
		img = SpaceInvaders.picFile.getAtlas("pack").findRegion("player");

		width = img.getRegionWidth();
		height = img.getRegionHeight();
		
		shot.setVisible(true);
	}

	// update player left right
	public void update() {
		inputLeftRight();
		if (x < 0) 
			x = 0;
		if (x > SpaceInvaders.WIDTH - width) 
			x = SpaceInvaders.WIDTH - width;
	}

	// action player left and right
	private void inputLeftRight() {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			x -= 200 * Gdx.graphics.getDeltaTime();
			setX(x);
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			x += 200 * Gdx.graphics.getDeltaTime();
			setX(x);
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE)) 
			createShot(x, y);
	}

	// create Shot
	private void createShot(int x, int y) {
		if (shot.isVisible()) {
			shots.add(new Shot(x, y));
			shot.die();
			soundShot.play();
		}
	}
}
