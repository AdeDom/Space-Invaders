package com.java.mygame.spaceinvaders;

public class Alien extends Anime { // Enemy
	public Alien(int x, int y) {
		super(x, y);
		img = SpaceInvaders.picFile.getAtlas("pack").findRegion("alien");

		width = img.getRegionWidth();
		height = img.getRegionHeight();
	}
}