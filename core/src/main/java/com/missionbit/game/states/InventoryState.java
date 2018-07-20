package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Interactables;

import java.lang.reflect.Array;

public class InventoryState extends State{

    private int h = 0;
    private int [] loc;
    public InventoryState(GameStateManager gsm) {
        super(gsm);
        loc=new int[gsm.getInventory().getInv().size()];
        for(int i=0;i<gsm.getInventory().getInv().size();i++){
            loc[i]= h;
            h+=100;

        }
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
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();


        for(int x = 0; x<gsm.getInventory().getInv().size();x++) {
           sb.draw(gsm.getInventory().getInv().get(x),loc[x],50);
        }

        sb.end();

    }

    @Override
    public void dispose() {

    }
}
