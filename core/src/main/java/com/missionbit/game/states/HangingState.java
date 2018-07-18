package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.missionbit.game.Animations;

public class HangingState extends State{

    private Texture hanging;
    private Animations hangingAni;

    public HangingState(GameStateManager gsm) {
        super(gsm);
        hanging = new Texture("images/hung.png");
        hangingAni = new Animations(new TextureRegion(hanging),8,1f);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.pop();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        hangingAni.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(hangingAni.getFrame(),90,-130);
        sb.end();

    }

    @Override
    public void dispose() {
        hanging.dispose();

    }
}
