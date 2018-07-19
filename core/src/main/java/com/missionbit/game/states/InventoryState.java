package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Interactables;

public class InventoryState extends State{
    public InventoryState(GameStateManager gsm) {
        super(gsm);
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
        for(Interactables items: gsm.getInventory().getInv()){
            items.update(dt);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        for(Interactables items: gsm.getInventory().getInv()){
            sb.draw(items.getFrame(),50,50);
        }
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
