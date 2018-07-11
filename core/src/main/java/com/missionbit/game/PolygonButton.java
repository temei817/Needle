package com.missionbit.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

public class PolygonButton{
    private Polygon shape;
    public PolygonButton(float[] vertices){
        shape = new Polygon(vertices);
    }

    public void drawDebug(ShapeRenderer r){
        r.polygon(shape.getVertices());
    }

    public boolean handleClick(Vector3 touchPos){
        boolean hit = shape.contains(touchPos.x, touchPos.y);
        return hit;

    }
}
