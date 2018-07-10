package com.missionbit.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.missionbit.game.Animations;
import com.missionbit.game.Button;
import com.missionbit.game.Needle;

import java.util.ArrayList;

public class BookshelfState extends State {
    private Animations BookShelfAnimation;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean showDebug = true;

    public BookshelfState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        Texture book = new Texture("images/BOOKSHELF.png");
        BookShelfAnimation = new Animations(new TextureRegion(book),28,2f);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        BookShelfAnimation.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        TextureRegion Frame = BookShelfAnimation.getFrame();
        sb.draw(Frame,0,0,Frame.getRegionWidth(),Frame.getRegionHeight());
        sb.end();
    }

    @Override
    public void dispose() {
//        BookShelfAnimation.dispose;

    }
}