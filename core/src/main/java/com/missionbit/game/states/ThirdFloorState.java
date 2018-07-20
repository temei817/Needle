package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
    private Animations issac,bunny;
    private Texture props, carKeyInv, bunInv;
    private Interactables bun, carKey,bunKey;

    private boolean showDebug = true;
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



    public ThirdFloorState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH/1.5f, Needle.HEIGHT/1.5f);
        bkgd = new Texture("images/Thirdroom.png");
        female = new Female(50, 50);
        issac = new Animations("images/Isaac.png",4,3,11,3f,true);
        props = new Texture("images/thirdprop.png");
        bunny = new Animations("images/bunnysad.png",7,6,42,5f,true);
        carKeyInv = new Texture("images/carkeyin.png");

        //walls
        walls = new ArrayList<PolygonButton>();
        for(int i=0;i<wall.length;i++){
            walls.add(new PolygonButton(wall[i]));
        }

        //doors
        labDoor = new PolygonButton(labDoorVertices);
        exitDoor = new PolygonButton(exitDoorVertices);

        bun = new Interactables("images/bunnysad.png",750,145,127,163,7,6,42,5f);
        carKey = new Interactables(new Texture("images/carkeys.png"),280,210,24,24);
        //invButton = new Interactables(new Texture("images/Inventory.png"),10,20,40,40);
        bunKey = new Interactables(new Texture("images/bunnykeyclose.png"),50,50,92,20);
        bunInv = new Texture("images/bunin.png");



    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            if(gsm.getInventory().handleInput()){
                gsm.push(new InventoryState(gsm));
            }
            else if(labDoor.handleClick(touchPos)){
                gsm.pop();
            }
            else if(bun.getButton().handleClick(touchPos)){
                if(gsm.getInventory().getKey()){

                }
                else if(gsm.getInventory().getKey() && gsm.getInventory().getBunKey()){
                    gsm.getInventory().setBunKey(true);
                    gsm.getInventory().getInv().add(bunInv);
                }
            }
            else if(carKey.getButton().handleClick(touchPos)){
                gsm.getInventory().setInv(carKeyInv);
                gsm.getInventory().setCarKey(true);
            }
            else {
                female.setTargetLoc((int) touchPos.x, (int) touchPos.y);
            }

        }
    }
    @Override
    public void update(float dt) {
        handleInput();
        female.update(dt,walls);
        issac.update(dt);
        bunny.update(dt);

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

        gsm.getInventory().update();

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(bkgd,0,0, Needle.WIDTH,Needle.HEIGHT);
        sb.draw(issac.getFrame(),340,145);
        sb.draw(bun.getFrame(),bun.getXLoc(),bun.getYLoc(),bun.getWidth(),bun.getHeight());
        if(!gsm.getInventory().getCarKey()) {
            sb.draw(carKey.getTexture(), carKey.getXLoc(), carKey.getYLoc(), carKey.getWidth(), carKey.getHeight());
        }

        //draw the character
        female.draw(sb);

        sb.draw(props,50,0);

        sb.end();

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
            bunKey.getButton().drawDebug(debugRenderer);
        }
        debugRenderer.end();

        gsm.getInventory().draw(sb);

    }

    @Override
    public void dispose() {
        bkgd.dispose();
        female.dispose();
        props.dispose();

    }
}
