package com.missionbit.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    Array<TextureRegion> frames;
    float maxFrameTime;
    float currentFrameTime;
    int frameCount;
    int frame;
    boolean dead = false;

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for ( int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }
    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame ++;
            currentFrameTime = 0;
        }
        if(frame>=frameCount){
            frame = 0;
        }
    }

    public void setDead(boolean isDead) {
        this.dead = isDead;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    public TextureRegion getLastFrame(){
        if (dead || frame == frameCount-1) {
            dead = true;
            return frames.get(frameCount - 1);
        } else {
            return frames.get(frame);
        }
    }
}

