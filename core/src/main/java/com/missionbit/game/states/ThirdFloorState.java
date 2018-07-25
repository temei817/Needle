package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Animations;
import com.missionbit.game.Interactables;
import com.missionbit.game.Needle;
import com.missionbit.game.PolygonButton;
import com.missionbit.game.characters.Female;

import java.util.ArrayList;

import javax.xml.soap.Text;

public class ThirdFloorState extends State{

    private Texture bkgd;
    private Female female;
    private Animations issac,oneLock;
    private Texture props, carKeyInv, bunInv, bunOpen;
    private Interactables bun, carKey,bunKey;
    private boolean playUnlock, playFullUnlock, carkeysoundplayed;
    private Sound carkeypickupsound;

    //debug stuff
    private boolean showDebug = false;
    private ShapeRenderer debugRenderer = new ShapeRenderer();

    //walls
    private float[][] wall = new float[][]{
            {33.0f, 381.0f, 32.0f, 163.0f, 3.0f, 134.0f, 0.0f, 380.0f, 29.0f, 381.0f, 904.0f, 376.0f, 906.0f, 168.0f, 341.0f, 166.0f, 324.0f, 147.0f, 171.0f, 147.0f, 165.0f, 166.0f, 166.0f, 318.0f, 77.0f, 319.0f, 79.0f, 165.0f, 32.0f, 164.0f, },
            {905.0f, 377.0f, 956.0f, 378.0f, 956.0f, 255.0f, 932.0f, 283.0f, 932.0f, 134.0f, 905.0f, 171.0f, },
    };
    private ArrayList<PolygonButton> walls;

    //doors
    private float[] labDoorVertices = {79.0f, 317.0f, 79.0f, 165.0f, 165.0f, 165.99998f, 164.0f, 319.0f, 78.0f, 318.0f, };
    private float[] exitDoorVertices = {933.0f, 278.0f, 933.0f, 130.0f, 956.0f, 105.0f, 956.0f, 254.0f, 933.0f, 279.0f, };
    private PolygonButton labDoor,exitDoor;

    //timer stuff
    private GameStateManager gameStateManager;
    BitmapFont font;



    public ThirdFloorState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH/1.5f, Needle.HEIGHT/1.5f);


        //timer stuff
        gameStateManager = gsm;
        font = new BitmapFont();

        //textures
        bkgd = new Texture("images/Thirdroom.png");
        female = new Female(50, 50);
        issac = new Animations("images/Isaac.png",4,3,11,3f,true);
        props = new Texture("images/thirdprop.png");
        carKeyInv = new Texture("images/carkeyin.png");
        carKey = new Interactables(new Texture("images/carkeys.png"),280,210,24,24);
        bunInv = new Texture("images/bunnyin.png");

        //bunny lock animations
        oneLock = new Animations("images/buncut.png",7,6,12,3f,true);
        bunOpen = new Texture("images/bunopen.png");
        bun = new Interactables("images/bunnysad.png",750,145,127,163,7,6,42,5f);


        //walls
        walls = new ArrayList<PolygonButton>();
        for(int i=0;i<wall.length;i++){
            walls.add(new PolygonButton(wall[i]));
        }

        //doors
        labDoor = new PolygonButton(labDoorVertices);
        exitDoor = new PolygonButton(exitDoorVertices);

        carkeypickupsound = Gdx.audio.newSound(Gdx.files.internal("Music/keyy.wav"));


    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            //switch to inv
            if(gsm.getInventory().handleInput()){
                gsm.push(new InventoryState(gsm));
            }
            //back to second floor
            else if(labDoor.handleClick(touchPos)){
                gsm.pop();
            }
            //switch to bun unlock animation
            else if(bun.getButton().handleClick(touchPos)){
                if(gsm.getInventory().getKey() && gsm.getInventory().getBunKey()){
                    playFullUnlock = true;
                    gsm.push(new BunUnlockState(gsm));
                    if(!gsm.getInventory().getBun()) {
                        gsm.getInventory().getInv().add(bunInv);
                        gsm.getInventory().setBun(true);
                    }
                }
                else{
                    playUnlock = true;
                    gsm.push(new BunUnlockState(gsm));

                }
            }
            //add car key to inv
            else if(carKey.getButton().handleClick(touchPos)){
                if(!gsm.getInventory().getCarKey() && !carkeysoundplayed) {
                    carkeypickupsound.play();
                    carkeysoundplayed = true;
                    gsm.getInventory().setInv(carKeyInv);
                    gsm.getInventory().setCarKey(true);
                }
            }
            //switch to end animations
            else if(exitDoor.handleClick(touchPos)){
                if(gsm.getInventory().getCarKey()){
                    gsm.clear();
                    gsm.push(new GameOverState(gsm));
                }
            }
            //move the char
            else {
                female.setTargetLoc((int) touchPos.x, (int) touchPos.y);
            }

        }
    }
    @Override
    public void update(float dt) {
        handleInput();

        //update animations
        female.update(dt,walls);
        issac.update(dt);
        bun.update(dt);
        oneLock.update(dt);

        //camera bounds
        float minX = cam.viewportWidth / 2, maxX = bkgd.getWidth() - cam.viewportWidth / 2;
        cam.position.x = female.getCharPos().x;
        if (cam.position.x <= minX) {
            cam.position.x = minX;
        } else if (cam.position.x > maxX) {
            cam.position.x = maxX;
        } else {
            cam.position.x = female.getCharPos().x;
        }

        //timer
        if (!gameStateManager.getStopTimer()){
            if (System.currentTimeMillis() - gameStateManager.getStartTime() > 180000) {
                female.setDead(true);
                if(female.getDeath().getDone()){
                    gsm.push(new GameOverState(gsm));
                }
            }
        }

        //update inv
        gsm.getInventory().update();

        //update camera
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        //draw background stuff
        sb.draw(bkgd,0,0, Needle.WIDTH,Needle.HEIGHT);
        sb.draw(issac.getFrame(),340,145);

        //draw bunny stuff
        sb.draw(bun.getFrame(),bun.getXLoc(),bun.getYLoc(),bun.getWidth(),bun.getHeight());
        if(playUnlock){
            sb.draw(oneLock.getFrame(),bun.getXLoc(),bun.getYLoc(),bun.getWidth(),bun.getHeight());
        }
        else if(playFullUnlock){
            sb.draw(bunOpen,bun.getXLoc(),bun.getYLoc(),bun.getWidth(),bun.getHeight());
        }

        if(!gsm.getInventory().getCarKey()) {
            sb.draw(carKey.getTexture(), carKey.getXLoc(), carKey.getYLoc(), carKey.getWidth(), carKey.getHeight());
        }

        //draw the character
        female.draw(sb);

        //more background stuff
        sb.draw(props,50,0);

        //timer
        if(!gameStateManager.getStopTimer()) {
            font.setColor(Color.WHITE);
            font.getData().setScale(2, 2);
            font.draw(sb, ((181000 - (System.currentTimeMillis() - gameStateManager.getStartTime())) / 1000) + " ", cam.position.x, Needle.HEIGHT / 1.5f);
        }

        sb.end();

        //debug lines
        if(showDebug){
            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(1, 1, 1, 1);
            for(PolygonButton wall:walls){
                wall.drawDebug(debugRenderer);
            }
            labDoor.drawDebug(debugRenderer);
            exitDoor.drawDebug(debugRenderer);
            bun.getButton().drawDebug(debugRenderer);
            carKey.getButton().drawDebug(debugRenderer);
        }
        debugRenderer.end();

        //draw inventory
        gsm.getInventory().draw(sb);

    }

    @Override
    public void dispose() {
        bkgd.dispose();
        female.dispose();
        props.dispose();

    }
}
