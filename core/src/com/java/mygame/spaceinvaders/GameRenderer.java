package com.java.mygame.spaceinvaders;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameRenderer {
	private GameWorld myWorld;// game board
	private OrthographicCamera cam;// camera
	private BitmapFont font;// draw character

	Texture imgSkyNight;

	// constructor , set default
	public GameRenderer(GameWorld world) {
		myWorld = world;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, SpaceInvaders.WIDTH, SpaceInvaders.HEIGHT);
		font = new BitmapFont();

		imgSkyNight = new Texture("nightSky.jpg");
	}

	// draw all game
	public void render(SpriteBatch sb) {
		Gdx.gl.glClearColor(0, 0, 0, 0);// background RGB and alpha
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// clear screen
		if (myWorld.inGame) {
			sb.begin();// start batch
			sb.draw(imgSkyNight, 0, 0);// background

			font.draw(sb, "Time: " + myWorld.gameTime, 10, SpaceInvaders.HEIGHT - 15);// time
			font.draw(sb, "Live: " + myWorld.live, 10, 15);// live

			myWorld.player.render(sb);// player

			// shot
			for (Shot shot : myWorld.player.shots)
				shot.render(sb);

			// alien
			for (Alien alien : myWorld.aliens)
				alien.render(sb);

			// bomb
			for (Bomb bomb : myWorld.bombs)
				bomb.render(sb);

			// boss
			if (myWorld.boss.isVisible())
				myWorld.boss.render(sb);

			// explosion
			if (myWorld.expl) {
				myWorld.renderShotAlien(sb);
				myWorld.expl = false;
			}
			sb.end();// stop batch
		} else {
			sb.begin();

			// Game Name
			font.getData().setScale(3);
			font.draw(sb, "Space Invaders", SpaceInvaders.WIDTH / 2 - 130, 500);

			// message show
			font.getData().setScale(2);
			font.draw(sb, myWorld.message, SpaceInvaders.WIDTH / 2 - 50,
					SpaceInvaders.HEIGHT / 2 + 100);

			// use time
			font.draw(sb, "Time: " + myWorld.gameTime,
					SpaceInvaders.WIDTH / 2 - 35, SpaceInvaders.HEIGHT / 2);

			// high time
			if (myWorld.gameTime > myWorld.getHiTime())
				myWorld.setHiTime(myWorld.gameTime);
			font.draw(sb, "High Time: " + myWorld.getHiTime(),
					SpaceInvaders.WIDTH / 2 - 70, SpaceInvaders.HEIGHT / 2 - 120);

			// message new game
			font.getData().setScale(1);
			font.draw(sb, "Press shift to replay",
					SpaceInvaders.WIDTH / 2 - 40, 25);

			sb.end();
			myWorld.mainSound.pause();// pause music
			inputHandlerGameOver();// new game
		}
	}

	// new game again
	private void inputHandlerGameOver() {
		if ((Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input
				.isKeyPressed(Keys.SHIFT_RIGHT))) {
			myWorld.inGame = true;// set inGame.
			myWorld.message = "Game Over";// new start message
			myWorld.live = 3;// live

			myWorld.deaths = 0;// start destroy alien
			myWorld.deathBoss = 0;// start destroy boss alien

			myWorld.direction = -1;// set direction alien

			// new Time , start time
			myWorld.gameTime = 60;
			myWorld.time();

			myWorld.player = new Player(myWorld.START_PLAYER_X,myWorld.START_PLAYER_Y);// Create Player

			// create alien
			myWorld.aliens = new ArrayList<Alien>();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 6; j++) {
					Alien alien = new Alien(myWorld.ALIEN_INIT_X + 50 * j, myWorld.ALIEN_INIT_Y
							+ 35 * i);
					myWorld.aliens.add(alien);
				}
			}

			myWorld.bombs = new ArrayList<Bomb>();// new bomb

			myWorld.boss = new Boss(myWorld.LOCATION_BOSS_X,
					myWorld.LOCATION_BOSS_Y);// create boss alien

			myWorld.expl = false;// explosion

			myWorld.mainSound.play();// play music
		}
	}
}
