package com.missionbit.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Interactables;

import java.util.ArrayList;

public class Inventory {
    private boolean key;
    private ArrayList<Interactables> inv;
    private Interactables invButton;

    public Inventory(){
        inv = new ArrayList<Interactables>();
        invButton = new Interactables(new Texture("images/Inventory.png"),10,20,40,40);
    }

    public void setInv(Interactables a){
        inv.add(a);
    }

    public ArrayList<Interactables> getInv() {
        return inv;
    }

    public void setKey(boolean key){
        this.key = key;
    }

    public boolean getKey(){
        return key;
    }

    public void update(OrthographicCamera cam){
        invButton.setxLoc((int)(cam.position.x-cam.viewportWidth/2));
        invButton.getButton().getRect().setX(cam.position.x-cam.viewportWidth/2);
        System.out.println(cam.position.x-cam.viewportWidth/2);
    }
    public void draw(SpriteBatch sb){
        sb.draw(invButton.getTexture(),invButton.getXLoc(),invButton.getYLoc(),invButton.getWidth(),invButton.getHeight());
    }

    public Interactables getInvButton() {
        return invButton;
    }
}
