package com.java.mygame.spaceinvaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Anime { // object in game
	protected TextureRegion img;// picture
	private boolean visible;//see object
	
	//Value other
	protected int x;
	protected int y;
	protected int dy;
	protected int width;
	protected int height;
	
	private Rectangle bounded;// keep Rectangle
	
	// constructor start set value 
	public Anime() {}
	public Anime(int x, int y) {
		this.x = x;
		this.y = y;

		img = SpaceInvaders.picFile.getAtlas("pack").findRegion("boss");
		width = img.getRegionWidth();
		height = img.getRegionHeight();

		visible = true;
		bounded = new Rectangle();
	}

	// update action left right
	public void update(int direction) {
		this.x += direction;
	}

	// draw
	public void render(SpriteBatch sb) {
		sb.draw(img, x, y);
	}

	// keep Rectangle picture
	public Rectangle getBounded() {
		return bounded.set(x, y, width, height);
	}
	
	// method not see object
	public void die() {
		visible = false;
	}

	// get visible
	public boolean isVisible() {
		return visible;
	}

	// set visible
	protected void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getX() {return this.x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return this.y;}
	public void setY(int y) {this.y = y;}
}
