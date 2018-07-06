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
        Button b = new Button(518,247,65,43, "1");
        buttons.add(b);
        b = new Button(593,247, 60, 43, "2");
        buttons.add(b);
        b = new Button(662,247,65,43, "3");
        buttons.add(b);
        b =  new Button(517, 190, 65, 43, "4");
        buttons.add(b);
        b = new Button(593, 191, 60, 43, "5");
        buttons.add(b);
        b = new Button(662, 191, 65, 43, "6");
        buttons.add(b);
        b = new Button(517, 135, 65, 43, "7");
        buttons.add(b);
        b = new Button(593, 135, 60, 43, "8");
        buttons.add(b);
        b = new Button(663, 135, 65, 43, "9");
        buttons.add(b);
        b = new Button(516, 81, 67, 43, "X");
        buttons.add(b);
        b = new Button(592, 81, 60, 43, "0");
        buttons.add(b);
        b = new Button(662, 81, 65, 43, "Check");
        buttons.add(b);
        b = new Button(820, 27, 100,75, "Back");
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
                    //gsm.set(new BasementState(gsm));
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
