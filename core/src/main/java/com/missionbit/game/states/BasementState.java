package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Needle;
import com.missionbit.game.characters.Female;

public class BasementState extends State{

    private Female female;
    private Texture bkgrd;

    public BasementState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        female = new Female(50,50);
        bkgrd = new Texture("images/basement.png");
    }

    @Override
    protected void handleInput() {

        //move char if player taps
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);
            female.setTargetLoc((int)touchPos.x,(int)touchPos.y);
        }
    }

    @Override
    public void update(float dt) {
        cam.update();
        handleInput();
        female.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bkgrd,0,0,800,480);
        sb.draw(female.getChar(),female.getCharPos().x-female.getBounds().getWidth()/2,female.getCharPos().y-female.getBounds().getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        female.dispose();
        bkgrd.dispose();
    }
}
