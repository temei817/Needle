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
    private String Combo = "";
    private String Answer = "6197";
    public SafeState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        safe = new Texture("images/Safe Box.png");
        Button b = new Button(513,256,67,44, "1");
        buttons.add(b);
        b = new Button(588,256, 64, 44, "2");
        buttons.add(b);
        b = new Button(657,256,67,44, "3");
        buttons.add(b);
        b =  new Button(513, 201, 67, 44, "4");
        buttons.add(b);
        b = new Button(587, 201, 64, 44, "5");
        buttons.add(b);
        b = new Button(657, 201, 67, 44, "6");
        buttons.add(b);
        b = new Button(513, 147, 67, 44, "7");
        buttons.add(b);
        b = new Button(588, 147, 64, 44, "8");
        buttons.add(b);
        b = new Button(658, 147, 67, 44, "9");
        buttons.add(b);
        b = new Button(513, 91, 67, 44, "X");
        buttons.add(b);
        b = new Button(587, 91, 64, 44, "0");
        buttons.add(b);
        b = new Button(657, 91, 67, 44, "Check");
        buttons.add(b);
        b = new Button(856, 4, 105,73, "Back");
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
                    if(b.getValue().equals("X")){
                        Combo = "";
                    }
                    else if(b.getValue().equals("Check")){
                        if(Combo.equals(Answer)){
                            System.out.println("Unlocked");
                            Combo = "";
                        }
                        else{
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
