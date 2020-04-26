package com.badlogic.drophelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetLoader {

    public static BitmapFont regular;
    public static Texture bg;

    public static void load(){
        regular = new BitmapFont(Gdx.files.internal("whitetext.fnt"));
        bg = new Texture(Gdx.files.internal("hill-house-red-room.png"));
    }
}
