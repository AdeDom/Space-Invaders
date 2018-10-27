package com.java.mygame.spaceinvaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld implements Commons {
	public Player player;// player
	public final int START_PLAYER_X = 179;
	public final int START_PLAYER_Y = 15;

	// alien , location , direction , alien death
	public ArrayList<Alien> aliens;
	public final int ALIEN_INIT_X = 150;
	public final int ALIEN_INIT_Y = 400;
	public int direction;
	public int deaths;

	public ArrayList<Bomb> bombs;// bomb

	// boss alien,direction Boss,death boss
	public Boss boss;
	public final int LOCATION_BOSS_X = 100;
	public final int LOCATION_BOSS_Y = 550;
	public int directionBoss;
	public int deathBoss;

	public String message;// message show
	public boolean inGame;// game running
	public int live;// live

	// explosion
	private TextureRegion explImg = SpaceInvaders.picFile.getAtlas("pack")
			.findRegion("explosion");
	int explX;
	int explY;
	public boolean expl;

	// game time
	public int hiTime = 0;
	public int gameTime;
	int interval;
	Timer timer;

	// music,sound
	public Music mainSound;
	public Sound soundExplo;
	public Sound soundBomb;

	private static Preferences prefs;// keep high time

	// constructor
	public GameWorld() {
		gameInit();
	}

	// set default start game
	public void gameInit() {
		inGame = true;// Set inGame.
		message = "Game Over";// start message
		live = 3;// live

		deaths = 0;// start destroy alien
		deathBoss = 0;// start destroy alien

		direction = -1;// set direction alien
		directionBoss = 1;// set direction boss alien

		// time
		gameTime = 60;
		time();

		player = new Player(START_PLAYER_X,START_PLAYER_Y);// player

		// alien
		aliens = new ArrayList<Alien>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				Alien alien = new Alien(ALIEN_INIT_X + 50 * j, ALIEN_INIT_Y
						+ 35 * i);
				aliens.add(alien);
			}
		}

		bombs = new ArrayList<Bomb>();//bomb

		boss = new Boss(LOCATION_BOSS_X, LOCATION_BOSS_Y);// boss

		expl = false;// explosion

		// music,sound
		mainSound = Gdx.audio.newMusic(Gdx.files
				.internal("sounds/mainSound.wav"));
		mainSound.setLooping(true);
		mainSound.play();
		soundExplo = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion.wav"));
		soundBomb = Gdx.audio.newSound(Gdx.files.internal("sounds/bomb.wav"));

		// Create/Retrieve existing preferences file.
		prefs = Gdx.app.getPreferences("SpaceInvaders");
		if (!prefs.contains("hiTime")) {
			prefs.putInteger("hiTime", 0);
		}
	}

	// game update
	public void update(float delta) {
		if (inGame) {
			player.update();// player

			// Shot
			for (int i = 0; i < player.shots.size(); i++) {
				boolean removeShot = player.shots.get(i).update();
				if (removeShot) {
					player.shots.remove(i);
					player.shot.setVisible(true);
				}
			}

			updateAlien();// alien
			updateBoss();// boss

			//bomb
			createBomb();
			for (int i = 0; i < bombs.size(); i++) {
				boolean removeEnemy = bombs.get(i).update();
				if (removeEnemy)
					bombs.remove(i);
			}

			// checkCollision
			checkShotAlienCollision();
			checkShotBossCollision();
			checkBombPlayerCollision();
		}
	}

	// update alien
	private void updateAlien() {
		// direction alien left,right but down end --> End Game
		Iterator it = aliens.iterator();
		while (it.hasNext()) {
			Alien alien = (Alien) it.next();
			if (alien.isVisible()) {
				int y = alien.getY();
				if (y < GROUND + 40) {
					timer.cancel();
					gameTime = 0;
					inGame = false;
					message = "Invasion.!!!";
				}

				alien.update(direction);
			}
		}

		// check X alien left,right
		for (Alien alien : aliens) {
			int x = alien.getX();// get X alien
			// left
			if (x <= BORDER_LEFT && direction != 1) {
				direction = 1;
				Iterator i2 = aliens.iterator();
				while (i2.hasNext()) {
					Alien a = (Alien) i2.next();
					a.setY(a.getY() - GO_DOWN);
				}
			}

			// right
			if (x >= SpaceInvaders.WIDTH - BORDER_RIGHT && direction != -1) {
				direction = -1;
				Iterator i1 = aliens.iterator();
				while (i1.hasNext()) {
					Alien a2 = (Alien) i1.next();
					a2.setY(a2.getY() - GO_DOWN);
				}
			}
		}
	}

	// update boss alien
	private void updateBoss() {
		boss.update(directionBoss);
		int x = boss.getX();
		if (x >= SpaceInvaders.WIDTH - BORDER_RIGHT && directionBoss != -1)
			directionBoss = -1;
		if (x <= BORDER_LEFT && directionBoss != 1)
			directionBoss = 1;
	}

	//create bomb and random == 5 from 150
	private void createBomb() {
		Random generator = new Random();
		for (Alien alien : aliens) {
			int x = alien.getX();
			int y = alien.getY();
			int shot = generator.nextInt(200);

			if (shot == CHANCE) {
				bombs.add(new Bomb(x, y));
				soundBomb.play();
			}
		}
	}

	//check bomb collision
	private void checkBombPlayerCollision() {
		Rectangle playerRect = player.getBounded();
		for (int i = 0; i < bombs.size(); i++) {
			Rectangle bombRect = bombs.get(i).getBounded();
			if (bombRect.overlaps(playerRect)) {
				bombs.remove(i);
				live--;

				expl = true;
				explX = player.getX();
				explY = player.getY() - 5;
				soundExplo.play();

				if (live <= 0) {
					timer.cancel();
					gameTime = 0;
					inGame = false;
				}
			}
		}
	}

	// check collision shot --> alien
	private void checkShotAlienCollision() {
		// game win
		if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
			timer.cancel();
			inGame = false;
			message = "Game won!";
		}

		// get rectangle shot
		Rectangle shotRect = new Rectangle();
		for (Shot shot : player.shots)
			shotRect = shot.getBounded();

		// get rectangle alien and check collision
		for (int i = 0; i < aliens.size(); i++) {
			Rectangle alienRect = aliens.get(i).getBounded();
			if (shotRect.overlaps(alienRect)) {
				for (int j = 0; j < player.shots.size(); j++) {
					player.shots.remove(j);
					player.shot.setVisible(true);
				}

				expl = true;
				explX = aliens.get(i).getX();
				explY = aliens.get(i).getY();
				soundExplo.play();

				aliens.remove(i);
				deaths++;
			}
		}
	}

	// check collision shot --> alien
	private void checkShotBossCollision() {
		// game win
		if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
			timer.cancel();
			inGame = false;
			message = "Game won!";
		}

		// get rectangle boss
		Rectangle bossRect = boss.getBounded();

		// get rectangle shot and check collision
		Rectangle shotRect = new Rectangle();
		for (int i = 0; i < player.shots.size(); i++) {
			shotRect = player.shots.get(i).getBounded();
			if (shotRect.overlaps(bossRect) && boss.isVisible()) {
				for (int j = 0; j < player.shots.size(); j++) {
					player.shots.remove(j);
					player.shot.setVisible(true);
				}

				expl = true;
				explX = boss.getX();
				explY = boss.getY();
				soundExplo.play();

				deaths++;
				deathBoss++;
				if (deathBoss == 3)
					boss.die();
			}
		}
	}

	// game time 60 second
	public void time() {
		timer = new Timer();
		interval = Integer.parseInt("60");
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				gameTime = interval;
				if (interval == 0) {
					timer.cancel();
					inGame = false;
					message = "Time out!";
				} else {
					interval--;
				}
			}
		}, 0, 1000);// delay,deploy
	}

	// draw explosion
	public void renderShotAlien(SpriteBatch sb) {
		sb.draw(explImg, explX, explY);
	}

	// set time
	public static void setHiTime(int time) {
		prefs.putInteger("hiTime", time);
		prefs.flush();
	}

	//get high time
	public static int getHiTime() {
		return prefs.getInteger("hiTime");
	}
}
