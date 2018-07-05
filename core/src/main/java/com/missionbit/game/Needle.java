package com.missionbit.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Needle extends ApplicationAdapter {
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
    }

    @Override
    public void render() {

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set up our camera
        camera.update();
        myBatch.setProjectionMatrix(camera.combined);

        //TODO: Draw our image!
        myBatch.begin();
        myBatch.draw(background,0,0);
        myBatch.end();
    }

    @Override
    public void dispose() {
        myBatch.dispose();
    }
}