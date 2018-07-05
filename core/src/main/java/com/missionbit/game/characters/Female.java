package com.missionbit.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Female extends Character{

    private int charSize;
    private Vector3 direction;
    //private float charPos;

    public Female(int x, int y) {
        character = new Texture("images/dog.png");
        charSize = 50;
        charPos = new Vector3(x,y,0);
        targetLoc = new Vector3();
        direction = new Vector3();
        bounds = new Rectangle(x,y,character.getWidth(),character.getWidth());
    }

    @Override
    public void update(float dt) {
        if(charPos.dst(targetLoc) >= 1) {
            charPos.x += direction.x * 100 * dt;
            charPos.y += direction.y * 100 * dt;
        }

    }

    @Override
    public void dispose() {
        character.dispose();

    }

    public void moveForward(){
        charPos.add(50,0,0);
    }

    public void moveBackward(){
        charPos.sub(50,0,0);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Texture getChar(){
        return character;
    }

    public Vector3 getCharPos(){
        return charPos;
    }

    public void setTargetLoc(int x, int y){
        targetLoc.set(x,y,0);
        direction.set(x,y,0);
        direction.sub(charPos);
        direction.nor();
    }

    public int getCharSize(){
        return charSize;
    }
}
