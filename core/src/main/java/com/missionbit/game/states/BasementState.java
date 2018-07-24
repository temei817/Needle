package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.missionbit.game.Animations;
import com.missionbit.game.Button;
import com.missionbit.game.Interactables;
import com.missionbit.game.Needle;
import com.missionbit.game.PolygonButton;
import com.missionbit.game.characters.Female;

import java.nio.IntBuffer;
import java.sql.Time;
import java.util.ArrayList;

public class BasementState extends State {

    private Female female;
    private Texture bkgrd;
    private Button doorButton;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean showDebug = false;
    private PolygonButton safeButton;
    private float[] safevertices = {776, 100, 777, 186, 796, 142, 796, 58};
    private Interactables hangingBody, bleedingBody, bookshelf, invButton;
    private Sound sound;
    private float[][] wall = new float[][]{
            {110.0f, 315.0f, 111.0f, 121.0f, 769.0f, 121.0f, 771.0f, 318.0f, 109.0f, 317.0f, },
            {769.0f, 315.0f, 825.0f, 348.0f, 826.0f, 188.0f, 859.0f, 85.0f, 812.0f, 27.0f, 769.0f, 122.0f, },
            {108.0f, 319.0f, 0.0f, 382.0f, 6.0f, 11.0f, 109.0f, 123.0f, },
    };

    private ArrayList<PolygonButton> walls;
    BitmapFont font;
    private GameStateManager gameStateManager;

    private boolean locked;
    private Animations lockedAni;


    public BasementState(GameStateManager gsm) {
        super(gsm ,"Music/The_Binding_of_Isaac_Rebirth_Soundtrack_The_Calm_HQ_.mp3");
        cam.setToOrtho(false, Needle.WIDTH / 1.5f, Needle.HEIGHT / 1.5f);

        female = new Female(50, 50);
        bkgrd = new Texture("images/basement.png");
        lockedAni = new Animations("images/LOCKED.png",3,4,11,3f,false);

        //interactables
        hangingBody = new Interactables(new Texture("images/Hanging.png"), 150, 175, 70, 130, 8, 1f);
        bleedingBody = new Interactables(new Texture("images/Bleeding.png"), 220, 85, 70, 110, 8, 1f);
        bookshelf = new Interactables("images/BOOKSHELF.png", 589, 115, 163, 169, 5,6,28, 2f);
        invButton = new Interactables(new Texture("images/Inventory.png"),10,20,40,40);
        doorButton = new Button(830,300,70,100,"door");
        safeButton = new PolygonButton(safevertices);

        //wall bounds
        walls = new ArrayList<PolygonButton>();
        for(int i=0;i<wall.length;i++){
            walls.add(new PolygonButton(wall[i]));
        }

        //timer stuff
        gameStateManager = gsm;
        gameStateManager.setStartTime();
        font = new BitmapFont();

        System.out.println("texture");
        IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
        Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, intBuffer);
        System.out.println(intBuffer.get());

    }

    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            System.out.println("Click" + touchPos.x + " " + touchPos.y);
            System.out.println(touchPos.x + ", " + touchPos.y);

            //switch to inventory
            if(gsm.getInventory().handleInput()){
                gsm.push(new InventoryState(gsm));
            }
            //switch to first person bookshelf if touched
            else if (bookshelf.getButton().handleClick(touchPos)) {
                gsm.push(new BookshelfState(gsm));
                //sound.play();
            }
            //switch to safe
            else if (safeButton.handleClick(touchPos)) {
                gsm.push(new SafeState(gsm));

            }
            else if(doorButton.handleClick(touchPos)) {
                //switch to second floor
                if (gsm.getInventory().getKey()){
                    gsm.push(new SecondFloorState(gsm));
                 }
                 else{
                    locked = true;
                }
            }
            //switch to close up
            else if(bleedingBody.getButton().handleClick(touchPos)){
                gsm.push(new DeadBodiesState(gsm));
            }
            //switch to close up
            else if(hangingBody.getButton().handleClick(touchPos)){
                gsm.push(new HangingState(gsm));
            }
            //move char
            else {
                female.setTargetLoc((int) touchPos.x, (int) touchPos.y);
            }


        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        //update the animations
        female.update(dt, walls);
        hangingBody.update(dt);
        bleedingBody.update(dt);
        bookshelf.update(dt);

        //camera bounds
        float minX = cam.viewportWidth / 2, maxX = bkgrd.getWidth() - cam.viewportWidth / 2;
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
            }
        }

        //update lock ani if door is touched
        if(locked){
            lockedAni.update(dt);
        }
        else if(lockedAni.getDone()){
            locked = false;
        }

        //update the inventory
        gsm.getInventory().update();

        //update camera
        cam.update();

    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        //draw the background
        sb.draw(bkgrd, 0, 0, Needle.WIDTH, Needle.HEIGHT);

        //draw the interactables
        sb.draw(bookshelf.getFrame(), bookshelf.getXLoc(), bookshelf.getYLoc(), bookshelf.getWidth(), bookshelf.getHeight());
        sb.draw(hangingBody.getFrame(), hangingBody.getXLoc(), hangingBody.getYLoc(), hangingBody.getWidth(), hangingBody.getHeight());
        sb.draw(bleedingBody.getFrame(), bleedingBody.getXLoc(), bleedingBody.getYLoc(), 362, 110);

        //draw the character
        if(!female.getGetUp().getDone()){
            sb.draw(female.getGetUp().getFrame(),-28,-18,200,200);
        }
        else {
            female.draw(sb);
        }

        //locked animation
        if(locked && !lockedAni.getDone()){
            sb.draw(lockedAni.getFrame(),815,290);
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
            female.drawDebug(debugRenderer);
            safeButton.drawDebug(debugRenderer);
            hangingBody.getButton().drawDebug(debugRenderer);
            bookshelf.getButton().drawDebug(debugRenderer);
            bleedingBody.getButton().drawDebug(debugRenderer);
            doorButton.drawDebug(debugRenderer);
            invButton.getButton().drawDebug(debugRenderer);
            for(PolygonButton wall:walls){
                wall.drawDebug(debugRenderer);
            }
        }
        debugRenderer.end();

        //draw the inventory
        gsm.getInventory().draw(sb);



    }


    @Override
    public void dispose() {
        female.dispose();
        bkgrd.dispose();
        hangingBody.dispose();
        bleedingBody.dispose();
        bookshelf.dispose();
        music.stop();
        music.dispose();
        System.out.println("dispose");
    }
}


