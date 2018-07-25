package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Animations;
import com.missionbit.game.Needle;
import com.badlogic.gdx.audio.Music;

public class GameOverState extends State {
    private boolean goodEnd, badEnd, deadEnd;
    private Animations goodEnding, badEnding, badEnding2, goodEnding2, badEnding3, goodEnding3;
    private Animations escape0, escape1, escape2, escape3;
    private Animations deadLine;
    private Music goodendmusic;
    private Music badendmusic;
    private Music bunnymusic, flatline;

    private Sound explosion, explosion2,carsound2, carsound1, carsound3, dying, carkeysound, carsound2good, carsound3good, carkeysound2;
    private boolean explosionplayed, explosion2played, carsound2played, carsound1played, carsound3played, dyingplayed, carkeysoundplayed, flatlineplayed, carsound2goodplayed, carsound3goodplayed, carkeysound2played;




    public GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        goodEnding = new Animations("images/goodending.png",3,6,18,3f,false);
        goodEnding2 = new Animations("images/buncure.png",5,10,47,3f,false);
        goodEnding3 = new Animations("images/Goodlast.png",2,6,12,1f,false);
        badEnding = new Animations("images/badending1.png",4,7,28,1f,false);
        badEnding2 = new Animations("images/badending2.png",2,6,11,2f,false);
        badEnding3 = new Animations("images/Badendng.png",3,7,20,3f,false);
        escape0 = new Animations("images/escape0.png",3,6,17,3f,false);
        escape1 = new Animations("images/escape1.png",3,5,15,0.6f,false);
        escape2 = new Animations("images/escape2.png",3,8,24,0.6f,false);
        escape3 = new Animations("images/escape3.png",4,8,30,1f,false);
        deadLine = new Animations("images/DEADLINE.png",3,6,16,1f,false);
        goodendmusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Hollow_Knight_OST_White_Palace.mp3"));
        badendmusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Rain_World_Threat_Chimney_Canopy_Soundtrack_OST_.mp3"));
        //gsm.getInventory().setBun(true);
        //gsm.getInventory().setCarKey(true);
        bunnymusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Layers_Of_Fear_Soundtrack_The_End_feat_Penelopa_Willmann_Szynalik_.mp3"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("Music/explosion.wav"));
        flatline = Gdx.audio.newMusic(Gdx.files.internal("Music/flatline.wav"));
        explosion2 = Gdx.audio.newSound(Gdx.files.internal("Music/explosion2.ogg"));
        carsound2 = Gdx.audio.newSound(Gdx.files.internal("Music/carsound2.ogg"));
        carsound1 = Gdx.audio.newSound(Gdx.files.internal("Music/burnout.ogg"));
        dying = Gdx.audio.newSound(Gdx.files.internal("Music/infected.ogg"));
        carsound3 = Gdx.audio.newSound(Gdx.files.internal("Music/carsound2.ogg"));
        carkeysound = Gdx.audio.newSound(Gdx.files.internal("Music/carkeys.mp3"));
        carsound2good = Gdx.audio.newSound(Gdx.files.internal("Music/carsound2.ogg"));
        carsound3good = Gdx.audio.newSound(Gdx.files.internal("Music/carsound2.ogg"));
        carkeysound2 = Gdx.audio.newSound(Gdx.files.internal("Music/carkeys.mp3"));

    }

    @Override
    protected void handleInput() {
        if (gsm.getInventory().getBun()) {
            goodEnd = true;
            goodendmusic.play();
        } else if(gsm.getInventory().getCarKey() && !gsm.getInventory().getBunKey()){
            badEnd = true;
            if(badEnding2.getDone()){
                badendmusic.stop();
                badendmusic.dispose();
            }
            /*else{
                badendmusic.play();
            }*/
        }
        else{
            deadEnd = true;
        }

        if(Gdx.input.justTouched()){
            if(deadEnd && deadLine.getDone()){
                gsm.set(new BasementState(gsm));
                flatline.stop();
                flatline.dispose();
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

        if (goodEnd) {

            if (!escape0.getDone()) {
                escape0.update(dt);
            }
            else if (!escape1.getDone()) {
                escape1.update(dt);
            }
            else if (!escape2.getDone()) {
                escape2.update(dt);
            }
            else if (!goodEnding.getDone()) {
                goodEnding.update(dt);
            }
            //cure
            else if (!goodEnding2.getDone()) {
                goodEnding2.update(dt);
            }
            else if(!goodEnding3.getDone()){
                goodEnding3.update(dt);
            }

        }

        if (badEnd) {
            if (!escape0.getDone()) {
                escape0.update(dt);
            } else if (!escape1.getDone()) {
                escape1.update(dt);
            } else if (!escape2.getDone()) {
                escape2.update(dt);
            }
            //infection
            else if (!badEnding3.getDone()) {
                badEnding3.update(dt);
            } else if (!escape3.getDone()) {
                escape3.update(dt);
            }
            //crash
            else if (!badEnding.getDone()) {
                badEnding.update(dt);
            }
            //bunny
            else if (!badEnding2.getDone()) {
                badEnding2.update(dt);
            }
        }

        if(deadEnd){
            deadLine.update(dt);
        }

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        if (goodEnd) {
            if (!escape0.getDone()) {
                sb.draw(escape0.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                if(escape0.getcurrentRunTime() > 2.5 && !carkeysoundplayed){
                    carkeysound.play(1f);
                    carkeysoundplayed = true;
                }
            } else if (!escape1.getDone()) {
                sb.draw(escape1.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
//                if(/*escape1.getcurrentRunTime() < 1 &&*/ !carkeysoundplayed){
//                    carkeysound.play(1f);
 //                   carkeysoundplayed = true;
 //               }
            } else if (!escape2.getDone()) {
                sb.draw(escape2.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                if(!carsound2goodplayed){
                    carsound2good.play();
                    carsound2goodplayed = true;
                }
            }
            //GOOD END
            else if (!goodEnding.getDone()) {
                sb.draw(goodEnding.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                carsound2good.stop();
                carsound2good.dispose();
            }
            //cure
            else if (!goodEnding2.getDone()) {
                sb.draw(goodEnding2.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);


            }
            else if(goodEnding2.getDone()){
                sb.draw(goodEnding3.getFrame(),0,20,Needle.WIDTH,500);
                if(!carsound3goodplayed){
                   carsound3good.play();
                   carsound3goodplayed = true;
                }
            }
        }

        if (badEnd) {
            if (!escape0.getDone()) {
                sb.draw(escape0.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                if(escape0.getcurrentRunTime() > 2.5 && !carkeysound2played){
                    carkeysound2.play(1f);
                    carkeysound2played = true;
                }

            } else if (!escape1.getDone()) {
                sb.draw(escape1.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                if(!carsound1played){
                    carsound1.play();
                    carsound1played = true;
                }
            } else if (!escape2.getDone()) {
                sb.draw(escape2.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                carsound1.stop();
                if(!carsound2played){
                    carsound2.play();
                    carsound2played = true;
                }
            }
            //infection
            else if (!badEnding3.getDone()) {
                sb.draw(badEnding3.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                carsound2.stop();
                if(!dyingplayed){
                    dying.play(0.5f,0.25f,0);
                    dyingplayed = true;
                }
            } else if (!escape3.getDone()) {
                sb.draw(escape3.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                if(!carsound3played){
                    carsound3.play();
                    carsound3played = true;
                }
            }
            //crash
            else if (!badEnding.getDone()) {
                sb.draw(badEnding.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                if(badEnding.getcurrentRunTime() > 1  && !explosionplayed){
                    explosion.play(1f);
                    explosionplayed = true;
                    carsound3.stop();
                }
                if(badEnding.getcurrentRunTime() > 1 && !explosion2played){
                    explosion2played = true;
                    explosion2.play(1f);
                    carsound3.stop();
                }

            }
            //bunny
            else if (badEnding.getDone()) {
                sb.draw(badEnding2.getFrame(), 0, 0, Needle.WIDTH, Needle.HEIGHT);
                badendmusic.stop();
                badendmusic.dispose();
                bunnymusic.play();
            }
        }

        if(deadEnd){
            sb.draw(deadLine.getFrame(),0,0,Needle.WIDTH,Needle.HEIGHT);
            if(/*deadLine.getcurrentRunTime() < 1 && */!flatlineplayed){
                flatline.play();
                flatline.setPosition(4f);
            flatlineplayed = true;
            }
        }

        sb.end();

    }

    @Override
    public void dispose() {
        goodendmusic.dispose();
        badendmusic.dispose();
        bunnymusic.dispose();
    }
}
