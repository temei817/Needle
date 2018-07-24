package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Interactables;
import com.missionbit.game.Needle;

import java.util.ArrayList;

public class Inventory {
    private boolean key, bunKey, carKey, bun;
    private ArrayList<Texture> inv;
    private Interactables invButton;
    private OrthographicCamera cam;
    private Texture knife;

    public Inventory(){
        inv = new ArrayList<Texture>();
        knife = new Texture("images/knifee.png");
        inv.add(knife);
        invButton = new Interactables(new Texture("images/Inventory.png"),10,20,40,40);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Needle.WIDTH / 1.5f, Needle.HEIGHT / 1.5f);

    }

    public void setInv(Texture a){
        inv.add(a);
    }

    public ArrayList<Texture> getInv() {
        return inv;
    }

    public void setKey(boolean key){
        this.key = key;
    }

    public boolean getKey(){
        return key;
    }

    public boolean handleInput(){
        boolean clicked = false;
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            System.out.println(touchPos);
            //System.out.println(gsm.getInventory().getInvButton().getButton().getRect());
            if (invButton.getButton().handleClick(touchPos)) {
                clicked = true;
                System.out.println("clicked");
               // gsm.push(new InventoryState(gsm));
            }
        }
        return clicked;

    }

    public void update(){
        cam.position.x = cam.viewportWidth/2;
        cam.update();
    }
    public void draw(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(invButton.getTexture(),invButton.getXLoc(),invButton.getYLoc(),invButton.getWidth(),invButton.getHeight());
        sb.end();
    }

    public Interactables getInvButton() {
        return invButton;
    }

    public void setBunKey(boolean bunKey) {
        this.bunKey = bunKey;
    }

    public boolean getBunKey(){
        return bunKey;
    }

    public void setCarKey(boolean carKey){this.carKey = carKey;}

    public boolean getCarKey(){
        return carKey;
    }

    public void setBun(boolean bun){
        this.bun = bun;
    }

    public boolean getBun(){
        return bun;
    }
}
