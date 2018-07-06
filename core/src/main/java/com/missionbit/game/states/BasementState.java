package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Needle;
import com.missionbit.game.characters.Female;

public class BasementState extends State{

    private Female female;
    private Texture bkgrd;
    private float camOffset;

    //private float stateTime;

    public BasementState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH/1.5f, Needle.HEIGHT/1.5f);
        female = new Female(50,50);
        bkgrd = new Texture("images/basement.png");
        camOffset = -300;
    }

    @Override
    protected void handleInput() {

        //move char if player taps
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);
            female.setTargetLoc((int)touchPos.x,(int)touchPos.y);
            //female.setMoving(true);
        }
    }

    @Override
    public void update(float dt) {
        cam.position.x = female.getCharPos().x;
        handleInput();
        female.update(dt);
        cam.update();

    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(bkgrd,camOffset,0,Needle.WIDTH,Needle.HEIGHT);
        if(female.getMoving()) {
            sb.draw(female.getAni(), female.getCharPos().x-300, female.getCharPos().y, female.getCharSize(), female.getCharSize());
        }
        else{
            sb.draw(female.getAniStill(), female.getCharPos().x-300, female.getCharPos().y, female.getCharSize(), female.getCharSize());
        }

        sb.end();
    }

    @Override
    public void dispose() {
        female.dispose();
        bkgrd.dispose();
    }
}
