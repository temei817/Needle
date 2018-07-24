package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Animations;
import com.missionbit.game.Needle;
import com.badlogic.gdx.audio.Music;

public class GameOverState extends State{
    private boolean goodEnd,badEnd;
    private Animations goodEnding,badEnding,badEnding2,goodEnding2,badEnding3;
    private Animations escape0, escape1,escape2,escape3;
    private Music goodendmusic;
    private Music badendmusic;
    public GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        goodEnding = new Animations("images/goodending.png",3,6,18,3f,false);
        goodEnding2 = new Animations("images/buncure.png",5,10,47,3f,false);
        badEnding = new Animations("images/badending1.png",4,7,28,1f,false);
        badEnding2 = new Animations("images/badending2.png",2,6,11,1f,false);
        badEnding3 = new Animations("images/Badendng.png",3,7,20,1f,false);
        escape0 = new Animations("images/escape0.png",3,6,17,1f,false);
        escape1 = new Animations("images/escape1.png",3,5,15,0.3f,false);
        escape2 = new Animations("images/escape2.png",3,6,24,0.6f,false);
        escape3 = new Animations("images/escape3.png",4,8,30,1f,false);
        goodendmusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Hollow_Knight_OST_White_Palace.mp3"));
        badendmusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Rain_World_Threat_Chimney_Canopy_Soundtrack_OST_.mp3"));
    }

    @Override
    protected void handleInput() {
        if(gsm.getInventory().getBun()){
            goodEnd = true;
            goodendmusic.play();
        }
        else{
            badEnd = true;
            badendmusic.play();
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
        //infection
        else if (badEnd && !badEnding3.getDone()) {
            badEnding3.update(dt);
        }
        else if (goodEnd && !goodEnding.getDone()) {
            goodEnding.update(dt);


        }
        else if(!escape3.getDone()){
            escape3.update(dt);
        }
        //crash
        else if (badEnd && !badEnding.getDone()) {
            badEnding.update(dt);
        }
        //bunny
        else if (badEnding.getDone() && badEnd) {
            badEnding2.update(dt);
        }
        //cure
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
        //infection
        else if (badEnd && !badEnding3.getDone()) {
            sb.draw(badEnding3.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }
        else if (goodEnd && !goodEnding.getDone()) {
            sb.draw(goodEnding.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }
        else if(!escape3.getDone()){
            sb.draw(escape3.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
        }
        //crash
        else if (badEnd && !badEnding.getDone()) {
            sb.draw(badEnding.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }
        //bunny
        else if (badEnding.getDone()) {
            sb.draw(badEnding2.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }
        //cure
        else if (goodEnd && goodEnding.getDone()) {
            sb.draw(goodEnding2.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
        }


        sb.end();

    }

    @Override
    public void dispose() {
        goodendmusic.dispose();
        badendmusic.dispose();
    }
}
