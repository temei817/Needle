package com.missionbit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animations{

    Array<TextureRegion> frames; //where we store all of our frames
    float maxFrameTime; //this determines how long a frame needs to stay in view before switching to the next one
    float currentFrameTime; //how long the animation has been in the current frame
    int frameCount; //number of frames in our animation
    int frame; //the current frame we're in
    private boolean looping,done;
    float currentRunTime = 0;
    public Animations(String path, int cols, int rows, int framecount, float cycleTime, boolean looping){
        frames = new Array<TextureRegion>();
        Texture Sheet = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(Sheet,
                Sheet.getWidth() / cols,
                Sheet.getHeight() / rows);

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index < framecount) {
                    frames.add(tmp[i][j]);

                }
            }

        }
        this.frameCount = framecount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
        this.looping = looping;
    }
    public Animations(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;

        for(int i = 0; i < frameCount; i++){
            System.out.println("frameWidth:"+ frameWidth + " "+ i + " "+ i*frameWidth);
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
        looping = true;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if(looping) {
            if (frame >= frameCount)
                frame = 0;
        }
        else {
            if (frame >= frameCount) {
                frame = frameCount - 1;
                done = true;
            }
        }
        currentRunTime += dt;

    }

    public boolean getDone(){
        return done;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    public float getCurrentFrameTime(){
        return currentFrameTime;
    }
    public float getcurrentRunTime(){
        return currentRunTime;
    }

    public void dispose() {
        for(TextureRegion r: frames){
            r.getTexture().dispose();
        }
    }
}
