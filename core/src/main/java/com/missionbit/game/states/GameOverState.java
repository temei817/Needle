package com.missionbit.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Animations;
import com.missionbit.game.Needle;

public class GameOverState extends State{
    private boolean goodEnd,badEnd;
    private Animations goodEnding,badEnding,badEnding2,goodEnding2;
    private Animations escape0, escape1,escape2,escape3;
    public GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        goodEnding = new Animations("images/goodending.png",3,6,18,3f,false);
        goodEnding2 = new Animations("images/buncure.png",5,10,47,3f,false);
        badEnding = new Animations("images/badending1.png",4,7,28,2f,false);
        badEnding2 = new Animations("images/badending2.png",2,6,11,2f,false);
        escape0 = new Animations("images/escape0.png",3,6,17,3f,false);
        escape1 = new Animations("images/escape1.png",3,5,15,3f,false);
        escape2 = new Animations("images/escape2.png",2,4,8,5f,false);
        escape3 = new Animations("images/escape3.png",4,8,30,5f,false);
    }

    @Override
    protected void handleInput() {
        if(gsm.getInventory().getBun()){
            goodEnd = true;
        }
        else{
            badEnd = true;
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

        if(!escape0.getDone()){
            escape0.update(dt);
        }
        else if(!escape1.getDone()){
            escape1.update(dt);
        }
        else if(!escape2.getDone()){
            escape2.update(dt);
        }
        else if (badEnd && !badEnding.getDone()) {
            badEnding.update(dt);
        }
        else if(!escape3.getDone()){
            escape3.update(dt);
        }
        else if (badEnding.getDone() && badEnd) {
            badEnding2.update(dt);
        }
        else if (goodEnd && !goodEnding.getDone()) {
            goodEnding.update(dt);
        }
        else if (goodEnding.getDone() && goodEnd) {
            goodEnding2.update(dt);
        }

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        if (!escape0.getDone()){
            sb.draw(escape0.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if(!escape1.getDone()){
            sb.draw(escape1.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if(!escape2.getDone()){
            sb.draw(escape2.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if (badEnd && !badEnding.getDone()) {
            sb.draw(badEnding.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }
        else if (goodEnd && !goodEnding.getDone()) {
            sb.draw(goodEnding.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }
        else if(!escape3.getDone()){
            sb.draw(escape3.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if (badEnding.getDone()) {
            sb.draw(badEnding2.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }
        else if (goodEnd && goodEnding.getDone()) {
            sb.draw(goodEnding2.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }


        sb.end();

    }

    @Override
    public void dispose() {

    }
}
