package com.missionbit.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State{

    private Texture button;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        button = new Texture("images/femaleButton");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(button,200,200);
        sb.end();

    }

    @Override
    public void dispose() {
        button.dispose();

    }
}
