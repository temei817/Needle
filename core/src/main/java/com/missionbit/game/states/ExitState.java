package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Button;
import com.missionbit.game.Needle;

public class ExitState extends State {
    private Texture exit;
    private Button back;
    private ShapeRenderer debugRenderer;


    public ExitState(GameStateManager gsm) {
        super(gsm);
        exit = new Texture("images/KeypadExit.png");
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        back = new Button(653,27,85,63,"back");
        debugRenderer = new ShapeRenderer();

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);
            if(back.handleClick(touchPos)){

                gsm.pop();
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(exit,0,0,Needle.WIDTH,Needle.HEIGHT);
        sb.end();

        /*
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(0,1,0,1);
        back.drawDebug(debugRenderer);
        debugRenderer.end();
        */

    }

    @Override
    public void dispose() {
        exit.dispose();

    }
}


