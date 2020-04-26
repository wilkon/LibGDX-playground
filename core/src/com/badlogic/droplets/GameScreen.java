package com.badlogic.droplets;

import com.badlogic.droplets.projectiles.Droplet;
import com.badlogic.drophelpers.AssetLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	private static final int CHAR_LENGTH = 58;

	final Droplets game;

	private Texture bg;

	private Texture dropletImage;
	private Texture bucketImage;

	private Sound dropSound;
	private Music rainMusic;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Rectangle bucket;

	private Vector3 touchPos;

	private long lastDropTime; //time is in nanoseconds

	int score = 0;
	int lives = 3;

	private Rectangle ground;

	private Texture bucketLife1Image;
	private Texture bucketLife2Image;
	private Texture bucketLife3Image;
	private Rectangle bucketLife1;
	private Rectangle bucketLife2;
	private Rectangle bucketLife3;
	private final static float BUCKET_LIFE_WIDTH = 32;

	float dropletScale = .5f;
	float dropletHeight = 64 * dropletScale;
	float dropletWidth = 64 * dropletScale;

	static final int MAX_LEVEL = 10;
	private int curLevel = 1;
	private int lastLevelIncrease= 5;

	private Array<Droplet> activeDroplets = new Array<Droplet>();
	private final Pool<Droplet> dropletPool = new Pool<Droplet>(){
		@Override
		protected Droplet newObject(){
			return new Droplet(dropletHeight, dropletWidth);
		}
	};

	private static int screenHeight;
	private static int screenWidth;

	int raindropDelay = 1000000000;

	public GameScreen(final Droplets game) {
		AssetLoader.load();

		bg = AssetLoader.bg;
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		this.game = game;
		//loading droplet and bucket images
		dropletImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		bucketLife1Image = new Texture(Gdx.files.internal("bucket.png"));
		bucketLife2Image = new Texture(Gdx.files.internal("bucket.png"));
		bucketLife3Image = new Texture(Gdx.files.internal("bucket.png"));

		//importing my sound files
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		//setting music to continuously loop
		rainMusic.setLooping(true);

		//creating camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		//making the body that our bucket will take shape in
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2; //calculating the center - bottom left point of the bucket
		bucket.y = 5;
		bucket.width = 64;
		bucket.height = 64;


		bucketLife3 = new Rectangle();
		bucketLife3.setX(screenWidth);
		bucketLife3.setY(screenHeight-BUCKET_LIFE_WIDTH);
		bucketLife3.setWidth(BUCKET_LIFE_WIDTH);
		bucketLife3.setHeight(BUCKET_LIFE_WIDTH);

		bucketLife2 = new Rectangle();
		bucketLife2.setX(screenWidth);
		bucketLife2.setY(screenHeight-BUCKET_LIFE_WIDTH);
		bucketLife2.setWidth(BUCKET_LIFE_WIDTH);
		bucketLife2.setHeight(BUCKET_LIFE_WIDTH);

		bucketLife1 = new Rectangle();
		bucketLife1.setX(screenWidth);
		bucketLife1.setY(screenHeight-BUCKET_LIFE_WIDTH);
		bucketLife1.setWidth(BUCKET_LIFE_WIDTH);
		bucketLife1.setHeight(BUCKET_LIFE_WIDTH);

		// to prevent the constant initiation of a new vector3 per frame
		touchPos = new Vector3();

		ground = new Rectangle(0, 0, screenWidth, 12);

		spawnRaindrop();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//good practice to update the camera once per frame
		camera.update();

		if(score > 0 && score-lastLevelIncrease != 0 && score % 5 == 0 && curLevel < MAX_LEVEL){
			lastLevelIncrease = score;
			curLevel++;
		}

		//drawing our bucket at the rectangle location
		batch.setProjectionMatrix(camera.combined);
		batch.begin(); //this is telling Open GL to batch create what ever we want
		batch.draw(bg, 0, 0, screenWidth+200, screenHeight);
		batch.draw(bucketImage, bucket.x, bucket.y);

		if(lives >= 1)batch.draw(bucketLife1Image, bucketLife1.x + 100, bucketLife1.y - 30, bucketLife1.width, bucketLife1.height);
		if(lives >= 2)batch.draw(bucketLife2Image, bucketLife2.x + 68-10, bucketLife2.y - 30, bucketLife2.width, bucketLife2.height);
		if(lives >= 3)batch.draw(bucketLife3Image, bucketLife3.x + 26-10, bucketLife3.y - 30, bucketLife3.width, bucketLife3.height);

		for(Droplet droplet : activeDroplets){
			droplet.setLevelOffset(curLevel * 15);
			batch.draw(dropletImage, droplet.rectangle.x, droplet.rectangle.y, dropletWidth, dropletHeight);
		}

		String scoreText = "" + score;
		AssetLoader.regular.draw(batch, scoreText, 10, screenHeight - 10);

		if(lives > 0){
			String levelText = "Level: " + curLevel;
			AssetLoader.regular.draw(batch, levelText, (screenWidth/2) - (levelText.length()) - CHAR_LENGTH, screenHeight - 10);
		}


		batch.end();

		//making the bucket move with touch or mouse click
		if(Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		//making the bucket move with keyboard
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

		//making sure our bucket stays within the screen limits
		if(bucket.x < 0) bucket.x = 0; //left
		if(bucket.x > 800 - 64) bucket.x = 800 - 64; //right

		//making it rain
		if(TimeUtils.nanoTime() - lastDropTime > raindropDelay) spawnRaindrop();

		//raindrop logic
		if(lives > 0){
			Droplet droplet;
			int len = activeDroplets.size;
			for(int i=len; --i >= 0;){
				droplet = activeDroplets.get(i);
				droplet.update();
				if(droplet.rectangle.overlaps(bucket)) {
					score++;
					dropSound.play();
					activeDroplets.removeIndex(i);
					dropletPool.free(droplet);
				} else if (droplet.rectangle.overlaps(ground)) {
					lives--;
					activeDroplets.removeIndex(i);
					dropletPool.free(droplet);
				}
			}
		}else{
			batch.begin();

			String levelText = "YOU LOSE. LVL: " + curLevel;
			AssetLoader.regular.draw(batch, levelText, (screenWidth/2) - (levelText.length() * CHAR_LENGTH)/2 , screenHeight - 10);

			batch.end();
		}
	}

	@Override
	public void show() {
		rainMusic.play();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		dropletImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	private void spawnRaindrop(){
		Droplet droplet = dropletPool.obtain();
		droplet.init(MathUtils.random(0, 800-64), 480);
		activeDroplets.add(droplet);
		lastDropTime = TimeUtils.nanoTime();
	}
}
