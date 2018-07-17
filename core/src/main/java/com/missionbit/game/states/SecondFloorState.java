package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Needle;
import com.missionbit.game.PolygonButton;
import com.missionbit.game.characters.Female;

import java.util.ArrayList;

public class SecondFloorState extends State{

    private Texture bkgd;
    private Female female;
    private float[][] wall = new float[][]{
            {33.0f, 98.0f, 2.0f, 64.0f, 3.0f, 232.0f, 28.0f, 261.0f, 33.0f, 94.999985f, },
            {32.0f, 265.0f, 148.0f, 264.0f, 149.0f, 98.0f, 164.0f, 98.0f, 232.0f, 175.0f, 825.0f, 176.0f, 825.0f, 339.0f, 914.0f, 338.0f, 912.0f, 176.0f, 958.0f, 176.0f, 958.0f, 376.0f, 3.0f, 381.0f, 2.0f, 235.0f, 29.0f, 264.0f, },
    };
    private ArrayList<PolygonButton> walls;
    private boolean showDebug = true;
    private PolygonButton basementDoor;
    private float[] basementDoorVertices ={33.0f, 260.0f, 34.0f, 96.0f, 148.0f, 97.0f, 148.0f, 264.0f, 33.0f, 263.0f};


    private ShapeRenderer debugRenderer = new ShapeRenderer();




    public SecondFloorState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH/1.5f, Needle.HEIGHT/1.5f);
        bkgd = new Texture("images/Secondfloor.png");
        female = new Female(50, 50);

        //walls
        walls = new ArrayList<PolygonButton>();
        for(int i=0;i<wall.length;i++){
            walls.add(new PolygonButton(wall[i]));
        }

        //doors
        basementDoor = new PolygonButton(basementDoorVertices);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            if(basementDoor.handleClick(touchPos)){
                gsm.pop();
            }
            else{
                female.setTargetLoc((int) touchPos.x, (int) touchPos.y);
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        female.update(dt,walls);

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

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(bkgd,0,0,Needle.WIDTH,Needle.HEIGHT);

        //draw the character
        if (female.getMovingR()) {
            sb.draw(female.getAni(), female.getCharPos().x, female.getCharPos().y, 49, 98);
            System.out.println("moving right");
        } else if (female.getMovingL()) {
            sb.draw(female.getAniWalkLeft(), female.getCharPos().x, female.getCharPos().y, 49, 98);
            System.out.println("moving left");
        } else if (!female.getMovingR() && !female.getMovingL()) {
            sb.draw(female.getAniStill(), female.getCharPos().x, female.getCharPos().y, 50, 98);
            System.out.println("still");
        }

        sb.end();

        if (showDebug) {
            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(1, 1, 1, 1);
            for(PolygonButton wall:walls){
                wall.drawDebug(debugRenderer);
            }
            basementDoor.drawDebug(debugRenderer);
        }
        debugRenderer.end();


    }

    @Override
    public void dispose() {
        bkgd.dispose();
        female.dispose();
    }
}
