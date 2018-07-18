package com.missionbit.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Button {
    private Rectangle rect;
    protected String value;

    public Button(float x, float y, float w, float h, String v){
        rect = new Rectangle(x,y,w,h);
        value = v;
    }
    public void drawDebug(ShapeRenderer r){
        r.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public boolean handleClick(Vector3 touchPos){
        boolean hit = rect.contains(touchPos.x, touchPos.y);
        return hit;

    }

    public Rectangle getRect(){
        return rect;
    }
    public String getValue(){
        return value;
    }

}
