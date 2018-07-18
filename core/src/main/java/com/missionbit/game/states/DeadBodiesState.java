package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.missionbit.game.Animations;

public class DeadBodiesState extends State{

    private Texture dead;
    private Animations deadAni;

    public DeadBodiesState(GameStateManager gsm) {
        super(gsm);
        dead = new Texture("images/dead.png");
        deadAni = new Animations(new TextureRegion(dead),8,1f);
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
        deadAni.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(deadAni.getFrame(),100,0);
        sb.end();

    }

    @Override
    public void dispose() {
        dead.dispose();

    }
}
