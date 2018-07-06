package com.missionbit.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Animations;

public class Female extends Character{

    private int charSize;
    private Vector3 direction;
    private Animations charAnimation;
    private TextureRegion t;
    private boolean moving;
    //private float charPos;

    public Female(int x, int y) {
        character = new Texture("images/femaleWalk.png");
        characterStill = new Texture("images/femaleIdle.png");
        t = new TextureRegion(character);
        charSize = 150;
        charPos = new Vector3(x,y,0);
        targetLoc = new Vector3();
        direction = new Vector3();
        bounds = new Rectangle(x,y,charSize,charSize);
        charAnimation = new Animations(t,16,1f);
        charStill = new Animations(new TextureRegion(characterStill),19,1f);
        moving = false;
        velocity = new Vector3(100,0,0);
    }

    @Override
    public void update(float dt) {
        charAnimation.update(dt);
        if( charPos.dst(targetLoc) >= 1) {
            moving = true;
            charPos.x += direction.x * velocity.x * dt;
            charPos.y += direction.y * velocity.x * dt;
        }
        else{
            moving = false;
        }

    }

    @Override
    public void dispose() {
        character.dispose();

    }


    public void setMoving(boolean move){
        moving = move;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Texture getChar(){
        return character;
    }

    public TextureRegion getAni(){
        return charAnimation.getFrame();
    }

    public TextureRegion getAniStill(){
        return charStill.getFrame();
    }

    public Vector3 getCharPos(){
        return charPos;
    }

    public void setTargetLoc(int x, int y){
        targetLoc.set(x, y, 0);
        direction.set(x, y, 0);
        direction.sub(charPos);
        direction.nor();
    }

    public boolean getMoving(){
        return moving;
    }

    public int getCharSize(){
        return charSize;
    }
}
