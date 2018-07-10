package com.missionbit.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.missionbit.game.Button;
import com.missionbit.game.Needle;

import java.util.ArrayList;

public class BookshelfState extends State {
    private Texture safe;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean showDebug = true;

    public BookshelfState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        safe = new Texture("BOOKSHELF.png");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }
}