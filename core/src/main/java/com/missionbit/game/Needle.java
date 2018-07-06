package com.missionbit.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.states.BasementState;
import com.missionbit.game.states.GameStateManager;

import com.missionbit.game.states.SafeState;

import com.missionbit.game.states.MenuState;



public class Needle extends ApplicationAdapter {
    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;
    public static final String TITLE = "Needle";
    private GameStateManager gsm;
    private SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        //gsm.push(new BasementState(gsm));
        gsm.push(new SafeState(gsm));
        Gdx.gl.glClearColor(0, 0, 0, 1);
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


