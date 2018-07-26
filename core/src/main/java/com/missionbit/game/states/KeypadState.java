package com.missionbit.game.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Button;
import com.missionbit.game.Needle;

import java.util.ArrayList;

public class KeypadState extends State {
    private Texture keypad;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean showDebug = false;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private String Combo = "";
    private String Answer = "4260";
    private Sound dooropensound3, doorlockedsound3;
    public KeypadState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        keypad = new Texture("images/Keypad.png");
        Button b = new Button(345,430,48,48, "1");
        buttons.add(b);
        b = new Button(401,431, 48, 48, "2");
        buttons.add(b);
        b = new Button(454,430,48,48, "3");
        buttons.add(b);
        b =  new Button(345, 375, 48, 48, "4");
        buttons.add(b);
        b = new Button(401, 375, 48, 48, "5");
        buttons.add(b);
        b = new Button(454, 375, 48, 48, "6");
        buttons.add(b);
        b = new Button(345, 320, 48, 48, "7");
        buttons.add(b);
        b = new Button(401, 320, 48, 48, "8");
        buttons.add(b);
        b = new Button(454, 320, 48, 48, "9");
        buttons.add(b);
        b = new Button(345, 266, 48, 48, "X");
        buttons.add(b);
        b = new Button(401, 266, 48, 48, "0");
        buttons.add(b);
        b = new Button(454, 266, 48, 48, "Check");
        buttons.add(b);
        b = new Button(816, 30, 105, 73, "Back");
        buttons.add(b);

        dooropensound3 = Gdx.audio.newSound(Gdx.files.internal("Music/doorlock.mp3"));
        doorlockedsound3 = Gdx.audio.newSound(Gdx.files.internal("Music/not.wav"));








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
                    if(b.getValue().equals("X")){
                        Combo = "";
                    }
                    else if(b.getValue().equals("Check")){
                        if(Combo.equals(Answer)){
                            System.out.println("Unlocked");
                            Combo = "";
                            dooropensound3.play();
                            gsm.set(new ThirdFloorState(gsm));
                        }
                        else{
                            doorlockedsound3.play();
                            Combo = "";
                            System.out.println("Wrong! Try Again");
                        }
                    }
                    else if(b.getValue().equals("Back")){
                        gsm.pop();
                    }
                    else{
                        Combo += b.getValue();
                    }
                    System.out.println(b.getValue());
                    //gsm.set(new BasementState(gsm));
                }

                System.out.println(touchPos.x + " " + touchPos.y + " " + Combo);
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
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(keypad,0,0);
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


