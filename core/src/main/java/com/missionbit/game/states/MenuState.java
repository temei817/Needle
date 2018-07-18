package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.missionbit.game.Animations;
import com.missionbit.game.Needle;

public class MenuState extends State{

    private Texture bkgd;
    private Animations bkgdAni;
    private Texture tsp;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        bkgd = new Texture("images/titleImg.png");
        tsp = new Texture("images/TSP.png");
        //bkgdAni = new Animations(new TextureRegion(bkgd),10,1f);
        bkgdAni = new Animations("images/titleImg.png",10, 1, 10,1f);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            //BasementState basementstate = new BasementState(gsm);
            //gsm.set(basementstate);
            System.out.println(gsm);
            //gsm.set(new BasementState(gsm));
            gsm.set(new IntroState(gsm));
            //gsm.set(new BasementState(gsm));
            //gsm.set(new KeypadState(gsm));
            //gsm.set(new SecondFloorState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bkgdAni.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bkgdAni.getFrame(),295,70, 400, 400);
        sb.draw(tsp,110,-70);
        sb.end();
    }

    @Override
    public void dispose() {
       bkgd.dispose();

    }
}
