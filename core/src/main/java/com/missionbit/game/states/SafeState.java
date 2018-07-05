package com.missionbit.game.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Button;
import com.missionbit.game.Needle;

import java.util.ArrayList;


public class SafeState extends State {
    private Texture safe;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean showDebug = true;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    public SafeState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        safe = new Texture("images/Safe Box.png");
        Button b = new Button(207,98,26,16, "1");
        buttons.add(b);
        b = new Button(237,98, 25, 16, "2");
        buttons.add(b);
        b = new Button(266,98,25,16, "3");
        buttons.add(b);
        b =  new Button(207, 76, 26, 16, "4");
        buttons.add(b);
        b = new Button(237, 76, 24, 16, "5");
        buttons.add(b);
        b = new Button(266, 76, 26, 16, "6");
        buttons.add(b);
        b = new Button(207, 54, 26, 16, "7");
        buttons.add(b);
        b = new Button(237, 54, 24, 16, "8");
        buttons.add(b);
        b = new Button(266, 54, 26, 16, "9");
        buttons.add(b);
        b = new Button(207, 32, 26, 16, "X");
        buttons.add(b);
        b = new Button(237, 32, 25, 16, "0");
        buttons.add(b);
        b = new Button(266, 32, 26, 16, "Check");
        buttons.add(b);







    }

    @Override
    protected void handleInput() {

        //move char if player taps
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);
            for(Button b: buttons){
                boolean hit = b.handleClick(touchPos);
                if (hit){
                    System.out.println(b.getValue());
                }
                System.out.println(touchPos.x + " " + touchPos.y);
            }

        }
    }

    @Override
    public void update(float dt) {
        cam.update();
        handleInput();


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(safe,0,0);
        sb.end();
        if(showDebug){
            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(0,1,0,1);
            for(Button b: buttons){
                b.drawDebug(debugRenderer);
            }
            debugRenderer.end();
        }
    }

    @Override
    public void dispose(){

    }

}
