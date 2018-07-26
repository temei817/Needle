package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Animations;
import com.missionbit.game.Interactables;
import com.missionbit.game.Needle;
import com.missionbit.game.PolygonButton;
import com.missionbit.game.characters.Female;

import java.util.ArrayList;

public class SecondFloorState extends State{

    private Texture bkgd;
    private Texture table;
    private Texture cureGone;
    private Interactables cure;
    private Female female;
    private Animations labStuffAni;
    private Animations cureAni;
    private float[][] wall = new float[][]{
            {33.0f, 98.0f, 2.0f, 64.0f, 3.0f, 232.0f, 28.0f, 261.0f, 33.0f, 94.999985f, },
            {32.0f, 265.0f, 148.0f, 264.0f, 149.0f, 98.0f, 164.0f, 98.0f, 232.0f, 175.0f, 825.0f, 176.0f, 825.0f, 339.0f, 914.0f, 338.0f, 912.0f, 176.0f, 958.0f, 176.0f, 958.0f, 376.0f, 3.0f, 381.0f, 2.0f, 235.0f, 29.0f, 264.0f, },
            {162.0f, 3.0f, 208.0f, 51.0f, 941.0f, 48.0f, 962.0f, 27.0f, 961.0f, 4.0f, 160.0f, 3.0f, },
    };

    private ArrayList<PolygonButton> walls;
    private boolean showDebug = false;
    private PolygonButton basementDoor, labDoor;
    private float[] basementDoorVertices ={33.0f, 260.0f, 34.0f, 96.0f, 148.0f, 97.0f, 148.0f, 264.0f, 33.0f, 263.0f};
    private float[] labDoorVertices = {826.0f, 313.0f, 826.0f, 177.0f, 913.0f, 177.0f, 912.0f, 312.0f, 825.0f, 311.0f, };


    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean gotCure = false;

    private BitmapFont font;
    private GameStateManager gameStateManager;

    private Sound dooropensound2, doorlockedsound2;
    private boolean dooropensound2played;




    public SecondFloorState(GameStateManager gsm) {
        super(gsm/*,"Music/Pulso_Profundum_The_Binding_of_Isaac_Afterbirth_OST_.mp3"*/);
        cam.setToOrtho(false, Needle.WIDTH/1.5f, Needle.HEIGHT/1.5f);

        bkgd = new Texture("images/Secondfloor.png");
        table = new Texture("images/table.png");
        cureGone = new Texture("images/gone.png");
        cure = new Interactables(new Texture("images/cureA.png"),810,40,30,51);
        cureAni = new Animations("images/cure.png",5,11,55,5f,false);
        female = new Female(50, 50);
        labStuffAni = new Animations("images/lab.png", 6,6,34,5f,true);

        //walls
        walls = new ArrayList<PolygonButton>();
        for(int i=0;i<wall.length;i++){
            walls.add(new PolygonButton(wall[i]));
        }

        //doors
        basementDoor = new PolygonButton(basementDoorVertices);
        labDoor = new PolygonButton(labDoorVertices);

        //timer
        gameStateManager = gsm;
        font = new BitmapFont();

        dooropensound2 = Gdx.audio.newSound(Gdx.files.internal("Music/doorlock.mp3"));
        doorlockedsound2 = Gdx.audio.newSound(Gdx.files.internal("Music/not.wav"));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            //back to basement
            if(basementDoor.handleClick(touchPos)){
                dooropensound2.play();
                gsm.pop();
            }
            //switch to keypad
            else if(labDoor.handleClick(touchPos)){
                gsm.push(new KeypadState(gsm));


            }
            //switch to inventory
            else if(gsm.getInventory().handleInput()){
                gsm.push(new InventoryState(gsm));
            }
            //stop timer if got cure
            else if(cure.getButton().handleClick(touchPos)){
                gotCure = true;
                gameStateManager.setStopTimer(true);
            }
            //move char
            else{
                female.setTargetLoc((int) touchPos.x, (int) touchPos.y);
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

        //update animations
        female.update(dt,walls);
        labStuffAni.update(dt);

        //update cure animation if cure is touched
        if(gotCure && !cureAni.getDone()) {
            cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
            cureAni.update(dt);
        }else if( cureAni.getDone()){
            cam.setToOrtho(false, Needle.WIDTH/1.5f, Needle.HEIGHT/1.5f);

        }

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

        //check the timer
        if(!gameStateManager.getStopTimer()) {
            if (System.currentTimeMillis() - gameStateManager.getStartTime() > 180000) {
                female.setDead(true);
                if(female.getDeath().getDone()){
                    gsm.push(new GameOverState(gsm));
                }
            }
        }

        //update camera
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        //draw the background
        sb.draw(bkgd,0,0,Needle.WIDTH,Needle.HEIGHT);

        //draw the character
        female.draw(sb);

        //draw background stuff
        sb.draw(table,160,0,800,76);
        sb.draw(labStuffAni.getFrame(),200,0,labStuffAni.getFrame().getRegionWidth() * 1.75f,labStuffAni.getFrame().getRegionHeight()* 2f);

        //draw the cure
        if(!gotCure) {
            sb.draw(cure.getTexture(), cure.getXLoc(), cure.getYLoc(), cure.getWidth(), cure.getHeight());
        }
        //draw cure injection animation if cure is touched
        else if(gotCure && !cureAni.getDone()){
            sb.draw(cureAni.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        //draw empty cure if cure animation is done
        else if(cureAni.getDone()) {
            sb.draw(cureGone, cure.getXLoc(), cure.getYLoc(), 40, 50);
        }

        //display timer
        if(!gameStateManager.getStopTimer()) {
            font.setColor(Color.WHITE);
            font.getData().setScale(2, 2);
            font.draw(sb, ((181000 - (System.currentTimeMillis() - gameStateManager.getStartTime())) / 1000) + " ", cam.position.x, Needle.HEIGHT / 1.5f);
        }


        sb.end();

        //draw debug lines
        if (showDebug) {
            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(1, 1, 1, 1);
            for(PolygonButton wall:walls){
                wall.drawDebug(debugRenderer);
            }
            basementDoor.drawDebug(debugRenderer);
            labDoor.drawDebug(debugRenderer);
            cure.getButton().drawDebug(debugRenderer);
        }
        debugRenderer.end();

        //draw inventory
        gsm.getInventory().draw(sb);


    }

    @Override
    public void dispose() {
        bkgd.dispose();
        female.dispose();
        cure.dispose();
        /*music.stop();
        music.dispose();*/
    }
}
