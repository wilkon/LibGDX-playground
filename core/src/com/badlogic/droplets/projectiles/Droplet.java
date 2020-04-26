package com.badlogic.droplets.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;

public class Droplet implements Pool.Poolable {
    public boolean alive;
    public Rectangle rectangle;

    int min = 200, max = 400;
    int levelOffset=1;
    private int dropSpeed = new Random().nextInt(max-min + 1) + min;

    public Droplet(float height, float width){
        this.alive = false;
        this.rectangle = new Rectangle();
        rectangle.height = height;
        rectangle.width = width;
    }

    public void init(float posX, float posY){
        rectangle.setPosition(posX, posY);
        alive = true;
    }

    public void setLevelOffset(int levelOffset){
        this.levelOffset = levelOffset;
    }

    @Override
    public void reset() {
        rectangle.setPosition(0,0);
        min += levelOffset;
        max += levelOffset;
        dropSpeed = new Random().nextInt(max-min + 1) + min;
        alive = false;
    }

    public void update(){
        rectangle.y -= dropSpeed * Gdx.graphics.getDeltaTime();
        if(rectangle.getY() + rectangle.width < 0) alive = false;
    }

}
