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
    public Animations(String path, int cols, int rows, int framecount, float cycleTime){
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
