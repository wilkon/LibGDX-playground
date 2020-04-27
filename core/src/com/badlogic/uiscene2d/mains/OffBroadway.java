package com.badlogic.uiscene2d.mains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class OffBroadway extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create(){
        stage = new Stage(new ScreenViewport());

        //creating a tiled grid N x N
        int N = 8;
        createGrid(N);
    }
    private void createGrid(int N){
        Texture texture = new Texture(Gdx.files.internal("image.jpg"));

        //type of repetition - MirroedRepeat will mirror each to the next
        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        TextureRegion tiledRegion = new TextureRegion(texture);
        // how we split our region - in this case, N defines row/col count
        tiledRegion.setRegion(0, 0, texture.getWidth() * N,
                texture.getHeight() * N);

        //assigning our tiled region to tiledImage
        Image tiledImage = new Image(tiledRegion);

        //filling the screen
        tiledImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());

        //drawing our new grid filled actor!
        tiledImage.setPosition(0, Gdx.graphics.getHeight() - tiledImage.getHeight());
        stage.addActor(tiledImage);
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose(){

    }
}
