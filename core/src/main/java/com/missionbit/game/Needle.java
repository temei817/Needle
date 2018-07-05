package com.missionbit.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.states.BasementState;
import com.missionbit.game.states.GameStateManager;



public class Needle extends ApplicationAdapter {
<<<<<<< HEAD
    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;

    private OrthographicCamera camera;
    private Random randomSource;
    private Sprite myImage;
    private SpriteBatch myBatch;
    private Vector2 velocity;
    private Texture background;


    @Override
    public void create() {
        randomSource = new Random();

        // Set up camera for 2d view of 800x480 pixels
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);

        // Create a sprite batch for rendering our image
        myBatch = new SpriteBatch();

        //TODO: Load our image
        background = new Texture("BASEMENT.png");
=======
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final String TITLE = "Needle";
    private GameStateManager gsm;
    private SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        gsm.push(new BasementState(gsm));
        Gdx.gl.glClearColor(0, 0, 0, 1);
>>>>>>> 628d91927f9689c4188179c68e9a53488f0f287d
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

}