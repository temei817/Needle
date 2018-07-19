package com.missionbit.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Interactables;

import java.util.ArrayList;

public class Inventory{
    private boolean key;
    private ArrayList<Interactables> inv;

    public Inventory(){
        inv = new ArrayList<Interactables>();
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

}
