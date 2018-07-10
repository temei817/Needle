package com.missionbit.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Animations;

public class Female extends Character{

    private int charSize;
    private Vector3 direction;
    private Animations charAnimation;
    private boolean movingR, movingL;

    public Female(int x, int y) {
        character = new Texture("images/femaleWalk.png");
        characterStill = new Texture("images/femaleIdle.png");
        charStillLeft = new Texture("images/femaleIdleL.png");
        charWalkLeft = new Texture("images/femaleWalkL.png");
        charSize = 150;
        charPos = new Vector3(x,y,0);
        targetLoc = new Vector3();
        direction = new Vector3();
        bounds = new Rectangle(x,y,charSize,charSize);
        charAnimation = new Animations(new TextureRegion(character),16,1f);
        charStill = new Animations(new TextureRegion(characterStill),21,1f);
        charStillL = new Animations(new TextureRegion(charStillLeft), 21,1f);
        charWalkL = new Animations(new TextureRegion(charWalkLeft), 16,1f);
        movingR = false;
        movingL = false;
        velocity = new Vector3(100,0,0);
    }

    @Override
    public void update(float dt) {
        charAnimation.update(dt);
        if( charPos.dst(targetLoc) >= 1) {
            movingR = true;
            charPos.x += direction.x * velocity.x * dt;
            charPos.y += direction.y * velocity.x * dt;
        }
        else{
            movingR = false;
        }

    }

    @Override
    public void dispose() {
        character.dispose();
        charWalkLeft.dispose();
        charStillLeft.dispose();
        characterStill.dispose();

    }


    public Vector3 getTargetLoc(){
        return targetLoc;
    }

    public void setMovingL(boolean moveL){
        movingL = moveL;
    }

    public void setMovingR(boolean move){
        movingR = move;
    }

    public Boolean getMovingL(){
        return movingL;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Texture getChar(){
        return character;
    }

    public TextureRegion getAniWalkLeft() {
        return charWalkL.getFrame();
    }

    public TextureRegion getStillLeft(){
        return charStillL.getFrame();
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

    public void drawDebug(ShapeRenderer shapeRenderer){
        //Vector3 temp = new Vector3(charPos);
        //temp.add(direction);
        //temp.scl(10f);
        Vector3 temp = new Vector3(direction);
        temp.scl(10f);
        shapeRenderer.circle(targetLoc.x,targetLoc.y,10);
        shapeRenderer.circle(charPos.x,charPos.y,10);
        shapeRenderer.rect(charPos.x,charPos.y,49,98);


    }

    public boolean getMovingR(){
        return movingR;
    }

    public int getCharSize(){
        return charSize;
    }
}
