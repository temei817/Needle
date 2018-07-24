package com.missionbit.game.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Animations;
import com.missionbit.game.Button;
import com.missionbit.game.Interactables;
import com.missionbit.game.Needle;

import java.util.ArrayList;


public class SafeState extends State {
    private Texture safe;
    private Texture safeOpen;
    private Texture keyInv;
    private Animations box;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean showDebug = false;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private String Combo = "";
    private String Answer = "6197";
    private Boolean open = false;
    private Boolean gotKey = false;
    private Interactables key;
    private Button back;
    private Sound keysound;
    private boolean keyplayed;
    public SafeState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        safe = new Texture("images/Safe Box.png");
        safeOpen = new Texture("images/OpenSafe.png");
        box = new Animations(new TextureRegion(new Texture("images/box.png")),21,1f);
        key = new Interactables(new Texture("images/key.png"),450,80,183,309,21,1f);
        keyInv = new Texture("images/keybut.png");
        back = new Button(856, 4, 105,73, "Back");
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

        keysound = Gdx.audio.newSound(Gdx.files.internal("Music/keyy.wav"));







    }

    @Override
    protected void handleInput() {

        //move char if player taps
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);
            if(!open) {
                for (Button b : buttons) {
                    boolean hit = b.handleClick(touchPos);
                    if (hit) {
                        if (b.getValue().equals("X")) {
                            Combo = "";
                        } else if (b.getValue().equals("Check")) {
                            if (Combo.equals(Answer)) {
                                System.out.println("Unlocked");
                                Combo = "";
                                open = true;
                            } else {
                                Combo = "";
                                System.out.println("Wrong! Try Again");
                            }
                        } else if (b.getValue().equals("Back")) {
                            gsm.pop();
                        } else {
                            Combo += b.getValue();
                        }
                        System.out.println(b.getValue());
                        //gsm.set(new BasementState(gsm));
                    }


                    System.out.println(touchPos.x + " " + touchPos.y + " " + Combo);
                }
                if (back.handleClick(touchPos)) {
                    gsm.pop();
                }
            }
            if(open) {
                if (back.handleClick(touchPos)) {
                    gsm.pop();
                }
                else if(key.getButton().handleClick(touchPos)) {
                    keysound.play();
                    if (!gsm.getInventory().getKey()){
                        gsm.getInventory().setKey(true);
                        gsm.getInventory().setInv(keyInv);
                }
                }

            }

        }
    }

    @Override
    public void update(float dt) {
        cam.update();
        handleInput();
        key.update(dt);
        box.update(dt);


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        if(!open) {
            sb.draw(safe, 0, 0);
        }
        else{
            sb.draw(safeOpen,0,0);
            if(!gsm.getInventory().getKey()) {
                sb.draw(key.getFrame(), key.getXLoc(), key.getYLoc());
                sb.draw(box.getFrame(),260,75);
            }
        }
        sb.end();
        if(showDebug){
            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            //debugRenderer.setColor(0,1,0,1);
            if(!open) {
                for (Button b : buttons) {
                    b.drawDebug(debugRenderer);
                    back.drawDebug(debugRenderer);
                }
            }
            else{
                key.getButton().drawDebug(debugRenderer);
                back.drawDebug(debugRenderer);
            }
            debugRenderer.end();
        }
    }


    @Override
    public void dispose(){
        safeOpen.dispose();
        safe.dispose();
        key.dispose();

    }

}
