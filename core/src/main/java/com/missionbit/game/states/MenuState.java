package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State{

    private Texture bkgd;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        bkgd = new Texture("images/basement.png");

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            //BasementState basementstate = new BasementState(gsm);
            //gsm.set(basementstate);
            System.out.println(gsm);
            gsm.set(new BasementState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {
       bkgd.dispose();

    }
}
