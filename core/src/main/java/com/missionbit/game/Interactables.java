package com.missionbit.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Interactables {
    private Texture texture;
    private Animations textureAni;
    private Button button;
    private int xLoc,yLoc, widthX, heightY;

    public Interactables(Texture texture,int x,int y,int width,int height){
        this.texture = texture;
        xLoc = x;
        yLoc = y;
        widthX = width;
        heightY = height;
        button = new Button(xLoc,yLoc,width,height, "button");
     }

    public Interactables(Texture texture, int x,int y,int width,int height, int frames, float cycle){
        this.texture = texture;
        textureAni = new Animations(new TextureRegion(texture), frames,cycle);
        xLoc = x;
        yLoc = y;
        widthX = width;
        heightY = height;
        button = new Button(xLoc,yLoc,width,height, "button");
    }

    public Texture getTexture(){
        return texture;
    }

    public int getWidth(){
        return widthX;
    }

    public int getHeight(){
        return heightY;
    }
    public TextureRegion getFrame(){
        return textureAni.getFrame();
    }

    public Button getButton(){
        return button;
    }
    public int getXLoc(){
        return xLoc;
    }

    public int getYLoc(){
        return yLoc;
    }

    public void update(float dt){
        textureAni.update(dt);

    }

    public void setxLoc(int x){
        xLoc = x;
    }

    public void dispose(){
        texture.dispose();
    }
}
