package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Button;
import com.missionbit.game.Needle;
import com.missionbit.game.characters.Female;

public class BasementState extends State{

    private Female female;
    private Texture bkgrd;
    private float camOffset;
    private Button bkgdButton, bkgdButtonTwo, bkgdButtonThree;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean showDebug = true;



    public BasementState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH/1.5f, Needle.HEIGHT/1.5f);
        female = new Female(50,50);
        bkgrd = new Texture("images/basement.png");
        camOffset = -300;
        bkgdButton = new Button(camOffset,0,770,130,"1");
        bkgdButtonTwo = new Button(530,0,160,400,"2");
        bkgdButtonThree = new Button(470,0,60,50, "3");
    }

    @Override
    protected void handleInput() {

        //move char if player taps
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);
            female.setTargetLoc((int)touchPos.x,(int)touchPos.y);
            //bounds for char movement
            //bkgdbutton bounds
            if(female.getTargetLoc().y > 130 && female.getCharPos().x<500){
                female.setTargetLoc((int)touchPos.x,80);
            }
            //bkgdbutton2 bounds
            else if(female.getTargetLoc().y>400 && female.getCharPos().x>700){
                female.setTargetLoc((int)touchPos.x,400);
            }
            //bkgdbutton3 bounds
            else if(female.getTargetLoc().x == 500 && female.getTargetLoc().y > 130){
                female.setTargetLoc((int)touchPos.x,110);
            }

        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        female.update(dt);
        cam.position.x = female.getCharPos().x;
        cam.update();

    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(bkgrd,camOffset,0,Needle.WIDTH,Needle.HEIGHT);
        if(female.getMovingR()) {
            //sb.draw(female.getAni(), female.getCharPos().x, female.getCharPos().y, female.getCharSize(), female.getCharSize());
            sb.draw(female.getAni(), female.getCharPos().x, female.getCharPos().y, female.getCharSize(),female.getCharSize());

        }
        else if(female.getMovingL()){
            sb.draw(female.getAniWalkLeft(), female.getCharPos().x, female.getCharPos().y, female.getCharSize(), female.getCharSize());
        }
        else{
            sb.draw(female.getAniStill(), female.getCharPos().x, female.getCharPos().y, female.getCharSize(), female.getCharSize());
        }
        sb.end();
        if(showDebug) {
            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(0, 1, 0, 1);
            bkgdButton.drawDebug(debugRenderer);
            bkgdButtonTwo.drawDebug(debugRenderer);
            bkgdButtonThree.drawDebug(debugRenderer);
            female.drawDebug(debugRenderer);
        }
        debugRenderer.end();

    }

    @Override
    public void dispose() {
        female.dispose();
        bkgrd.dispose();
    }
}
