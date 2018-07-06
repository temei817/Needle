package com.missionbit.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Animations;

public abstract class Character {
    protected Texture character;
    protected Vector3 charPos;
    protected Vector3 velocity;
    protected Rectangle bounds;
    protected Vector3 targetLoc;
    protected  Texture characterStill;
    protected Animations charStill;
    protected  Texture characterL;
    protected Animations characterLeft;

    protected abstract void update(float dt);
    protected abstract void dispose();
    protected abstract Texture getChar();
    protected abstract Vector3 getCharPos();
    protected abstract Rectangle getBounds();
}
