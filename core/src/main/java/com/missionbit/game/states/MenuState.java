package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Needle;

public class MenuState extends State{

    private Texture bkgd;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        bkgd = new Texture("images/titleImg.png");

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            //BasementState basementstate = new BasementState(gsm);
            //gsm.set(basementstate);
            System.out.println(gsm);
            gsm.set(new BasementState(gsm));
            //gsm.set(new KeypadState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bkgd,100,-50);
        sb.end();
    }

    @Override
    public void dispose() {
       bkgd.dispose();

    }
}
