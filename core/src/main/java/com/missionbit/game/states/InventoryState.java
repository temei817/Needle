package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Interactables;

import java.lang.reflect.Array;

public class InventoryState extends State {

    private int h = 20;
    private int[] loc;
    private Texture title;

    public InventoryState(GameStateManager gsm) {
        super(gsm);
        title = new Texture("images/inventorys.png");
        loc = new int[gsm.getInventory().getInv().size()];
        for (int i = 1; i < gsm.getInventory().getInv().size(); i++) {
            loc[i] = h;
            h += 155;
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
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

        sb.draw(title, 210, 310, 214, 42);
        sb.draw(gsm.getInventory().getInv().get(0), 20, 10, 135, 135);
        for (int x = 1; x < gsm.getInventory().getInv().size(); x++) {
            sb.draw(gsm.getInventory().getInv().get(x), loc[x], 155, 135, 135);
        }

        sb.end();

    }

    @Override
    public void dispose() {

    }
}
