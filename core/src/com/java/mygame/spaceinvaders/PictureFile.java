package com.java.mygame.spaceinvaders;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PictureFile {
	private HashMap<String, TextureAtlas> atlases;// keep key,value

	// constructor
	public PictureFile() {
		atlases = new HashMap<String, TextureAtlas>();
	}

	// load picture
	public void loadAtlas(String path, String key) {
		atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
	}

	// get picture
	public TextureAtlas getAtlas(String key) {
		return atlases.get(key);
	}

}
