package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Needle;
import com.missionbit.game.characters.Female;

public class SecondFloorState extends State{

    private Texture bkgd;
    private Female female;


    public SecondFloorState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH/1.5f , Needle.HEIGHT/1.5f);
        bkgd = new Texture("images/Secondfloor.png");
        female = new Female(50, 50);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            female.setTargetLoc((int) touchPos.x, (int) touchPos.y);
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        female.update(dt);

        //camera bounds
        float minX = cam.viewportWidth / 2, maxX = bkgd.getWidth() - cam.viewportWidth / 2;
        cam.position.x = female.getCharPos().x;
        if (cam.position.x <= minX) {
            cam.position.x = minX;
        } else if (cam.position.x > maxX) {
            cam.position.x = maxX;
        } else {
            cam.position.x = female.getCharPos().x;
        }

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bkgd,0,0,Needle.WIDTH,Needle.HEIGHT);

        //draw the character
        if (female.getMovingR()) {
            sb.draw(female.getAni(), female.getCharPos().x, female.getCharPos().y, 49, 98);
            System.out.println("moving right");
        } else if (female.getMovingL()) {
            sb.draw(female.getAniWalkLeft(), female.getCharPos().x, female.getCharPos().y, 49, 98);
            System.out.println("moving left");
        } else if (!female.getMovingR() && !female.getMovingL()) {
            sb.draw(female.getAniStill(), female.getCharPos().x, female.getCharPos().y, 50, 98);
            System.out.println("still");
        }

        sb.end();

    }

    @Override
    public void dispose() {
        bkgd.dispose();
        female.dispose();
    }
}
