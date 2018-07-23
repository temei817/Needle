package com.missionbit.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Animations;
import com.missionbit.game.Needle;

public class GameOverState extends State{
    private boolean goodEnd,badEnd;
    private Animations goodEnding,badEnding,badEnding2,goodEnding2;
    public GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        goodEnding = new Animations("images/goodending.png",3,6,18,3f,false);
        goodEnding2 = new Animations("images/buncure.png",5,10,47,3f,false);
        badEnding = new Animations("images/badending1.png",4,7,28,2f,false);
        badEnding2 = new Animations("images/badending2.png",2,6,11,2f,false);
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
        if(goodEnd && !goodEnding.getDone()){
            goodEnding.update(dt);
        }
        else if(goodEnding.getDone() && goodEnd){
            goodEnding2.update(dt);
        }
        else if(badEnd && !badEnding.getDone()){
            badEnding.update(dt);
        }
        else if(badEnding.getDone() && badEnd){
            badEnding2.update(dt);
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
/*
        if(goodEnd){
            sb.draw(goodEnding.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if(goodEnding.getDone()){
            sb.draw(goodEnding2.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if(badEnd){
            sb.draw(badEnding.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if(badEnding.getDone()){
            sb.draw(badEnding2.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        */



        if(badEnd && !badEnding.getDone()){
            sb.draw(badEnding.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if(badEnding.getDone()){
            sb.draw(badEnding2.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        if(goodEnd && !goodEnding.getDone()){
            sb.draw(goodEnding.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        else if(goodEnd && goodEnding.getDone()){
            sb.draw(goodEnding2.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }

        sb.end();

    }

    @Override
    public void dispose() {

    }
}
