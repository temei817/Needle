package com.missionbit.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.missionbit.game.Animations;
import com.missionbit.game.Needle;

public class IntroState extends State{

    private Animations introAni;
    private Texture intro;
    public IntroState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        intro = new Texture("images/Intro.png");
        introAni = new Animations(new TextureRegion(intro),75,1f);

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        introAni.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(intro,0,0);
        sb.draw(introAni.getFrame(),0,0);
        sb.end();

    }

    @Override
    public void dispose() {
        intro.dispose();
    }
}
