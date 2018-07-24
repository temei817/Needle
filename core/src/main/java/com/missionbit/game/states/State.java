package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected Music music;
    //protected Sound sound;

    public State(GameStateManager gsm,String musicpath){
        cam = new OrthographicCamera();
        mouse = new Vector3();
        this.gsm = gsm;
        music = Gdx.audio.newMusic(Gdx.files.internal(musicpath));
        music.play();
        //sound = Gdx.audio.newSound(Gdx.files.internal(soundpath));



    }
    public State(GameStateManager gsm){
        cam = new OrthographicCamera();
        mouse = new Vector3();
        this.gsm = gsm;

    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
