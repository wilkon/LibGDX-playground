package com.badlogic.uiscene2d.mains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Broadway extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create(){
        stage = new Stage(new ScreenViewport()); //showcase finals - Bellas up!
        Texture texture = new Texture(Gdx.files.internal("image.jpg"));

        //creating an image at location
        Image image = new Image(texture);

        // setting position to 1/3 of screenwidth minus the size of
        // our image to find the left most point
                                            //v Width
        image.setPosition(Gdx.graphics.getWidth()/3 - image.getWidth()/2,
                Gdx.graphics.getHeight() * 2/3 - image.getHeight()/2);
                                // ^Height -
        // 2/3 of full height minus the height of our image.

        stage.addActor(image); // the image is our digitized Anna Kendrick

        //rotating image
        Image rotatedImage = new Image(texture);
        rotatedImage.setPosition(Gdx.graphics.getWidth() * 2/3 - rotatedImage.getWidth()/2,
                Gdx.graphics.getHeight() * 2 / 3 - rotatedImage.getHeight()/2);
        //origin by default is bottom left (position.x & y)
        rotatedImage.setOrigin(rotatedImage.getWidth()/2, rotatedImage.getHeight()/2);
        rotatedImage.rotateBy(45);
        stage.addActor(rotatedImage);

        //changing image size
        Image biggerQualityImage = new Image(texture);
        biggerQualityImage.setPosition(Gdx.graphics.getWidth()/3 - biggerQualityImage.getWidth()/2,
                Gdx.graphics.getHeight()/3-biggerQualityImage.getHeight()/2);
        biggerQualityImage.setSize(biggerQualityImage.getWidth()/2,
                biggerQualityImage.getHeight()/2);
        stage.addActor(biggerQualityImage);

        //image with repeating texture
        //setwrap will mirror edges
        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        TextureRegion textureRegion = new TextureRegion(texture);

        // creating texture region - canvas to draw our 8 * 4 tiles.
        textureRegion.setRegion(0, 0, texture.getWidth() * 8, texture.getHeight() * 4);

        Image mirrorTile = new Image(textureRegion);
        mirrorTile.setSize(200, 100);
        mirrorTile.setPosition(Gdx.graphics.getWidth()*2/3-mirrorTile.getWidth()/2,
                Gdx.graphics.getHeight()/3 - mirrorTile.getHeight());
        stage.addActor(mirrorTile);

    }
    @Override
    public void render(){
        Gdx.gl.glClearColor(1,1 , 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
    @Override
    public void dispose(){

    }
}
