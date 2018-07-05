package com.missionbit.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animations{

    Array<TextureRegion> frames; //where we store all of our frames
    float maxFrameTime; //this determines how long a frame needs to stay in view before switching to the next one
    float currentFrameTime; //how long the animation has been in the current frame
    int frameCount; //number of frames in our animation
    int frame; //the current frame we're in

    public Animations(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount)
            frame = 0;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

}
