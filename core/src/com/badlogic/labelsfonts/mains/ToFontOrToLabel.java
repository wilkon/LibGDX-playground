package com.badlogic.labelsfonts.mains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ToFontOrToLabel extends ApplicationAdapter {
    private Stage stage;

    int col_width;
    int row_height;
    int help_guide;

    @Override
    public void create(){
        stage = new Stage(new ScreenViewport());

        help_guide = 12;
        row_height = Gdx.graphics.getWidth() / 12;
        col_width = Gdx.graphics.getWidth() / 12;
        createGrid(help_guide);

        addCustomMenuScreen();
        Gdx.input.setInputProcessor(stage);
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

    public void addCustomMenuScreen(){
        Table layout = new Table();
        layout.setFillParent(true);
        stage.addActor(layout);

        Skin flatEarthSkin = new Skin(Gdx.files.internal("skin-flat/flat-earth-ui.json"));

        TextButton playButton = new TextButton("Play", flatEarthSkin);
        TextButton settingsButton = new TextButton("Settings", flatEarthSkin);
        TextButton exitButton = new TextButton("Exit", flatEarthSkin);

        layout.add(playButton).fillX().uniformX();
        layout.row().pad(10, 0, 10, 0);
        layout.add(settingsButton).fillX().uniform();
        layout.row();
        layout.add(exitButton).fillX().uniform();

        exitButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Gdx.app.exit();
            }
        });
    }

    private void addSkinFont(){
        Skin flatEarthSkin = new Skin(Gdx.files.internal("skins/glassy/glassy-ui.json"));

        Label flatLabel = new Label("A very Glassy day to you, good sir", flatEarthSkin, "big");
        flatLabel.setSize(Gdx.graphics.getWidth()/ help_guide * 6, row_height);
        flatLabel.setColor(Color.BLACK);
        flatLabel.setWrap(true);
        flatLabel.setPosition(col_width * 2, Gdx.graphics.getHeight()-row_height*6);
        stage.addActor(flatLabel);
    }

    private void addBitmapFont(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("hacker-writer.fnt"));
        labelStyle.font = myFont;
        labelStyle.fontColor = Color.BLACK;

        Label titleLabel = new Label("Title (Michael Scarn)", labelStyle);
        titleLabel.setSize(Gdx.graphics.getWidth(), row_height);
        titleLabel.setPosition(0, Gdx.graphics.getHeight() - row_height * 2);
        titleLabel.setAlignment(Align.center);
        stage.addActor(titleLabel);
    }

    private void addTrueType(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Amble-Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderWidth = 1;
        parameter.color = Color.YELLOW;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        BitmapFont ttFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle ambleStyle = new Label.LabelStyle();
        ambleStyle.font = ttFont;

        Label lambel = new Label("True Types", ambleStyle);
        lambel.setSize(Gdx.graphics.getWidth()/ help_guide *6, row_height);
        lambel.setPosition(col_width*2, Gdx.graphics.getHeight()-row_height*4);
        stage.addActor(lambel);
    }

    // doing it again for practice
    private void createGrid(int N){
        Texture texture = new Texture(Gdx.files.internal("background-blue-tiles.jpg"));

        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        TextureRegion tiledRegion = new TextureRegion(texture);
        tiledRegion.setRegion(0, 0, texture.getWidth() * N,
                texture.getHeight() * N);

        Image tiledImage = new Image(tiledRegion);
        tiledImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());

        tiledImage.setPosition(0,
                Gdx.graphics.getHeight() - tiledImage.getHeight());

        stage.addActor(tiledImage);
    }
}
