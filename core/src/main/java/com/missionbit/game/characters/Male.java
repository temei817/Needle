package com.missionbit.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.PolygonButton;

import java.util.ArrayList;

public class Male extends Character{

    public Male(int x, int y){
        charPos = new Vector3(x,y,0);
    }

    @Override
    public void update(float dt, ArrayList<PolygonButton> wall) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public Texture getChar() {
        return character;
    }

    @Override
    public Vector3 getCharPos() {
        return charPos;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }
}
