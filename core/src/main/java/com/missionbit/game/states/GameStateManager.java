package com.missionbit.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> states;
    private Inventory inventory;

    public GameStateManager() {

        states = new Stack<State>();
        inventory = new Inventory();
    }

    public void push(State state) {
        states.push(state);
    }

    public State pop() {
        return states.pop();
    }

    public void set(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

    public Inventory getInventory() {
        return inventory;
    }

}

