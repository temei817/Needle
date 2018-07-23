package com.missionbit.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.missionbit.game.Animations;
import com.missionbit.game.Needle;

public class BunUnlockState extends State{
    private Animations unlock, fullUnlock, bunOneLock;
    private boolean playUnlock, playFullUnlock;

    public BunUnlockState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        unlock = new Animations("images/unlock.png",4,5,19,3f,false);
        fullUnlock = new Animations("images/fullunlock.png",4,4,16,3f,false);
        bunOneLock = new Animations("images/Bunnykeycut.png",3,4,12,1f,true);
    }

    @Override
    protected void handleInput() {
        if(gsm.getInventory().getKey() && gsm.getInventory().getBunKey()){
            playFullUnlock = true;
        }
        else{
            playUnlock = true;
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        bunOneLock.update(dt);
        if(playUnlock) {
            cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
            unlock.update(dt);
        }
        else if(playFullUnlock) {
            cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
            unlock.update(dt);
        }
        if(unlock.getDone()) {
            cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
            fullUnlock.update(dt);
        }

        if(playFullUnlock && fullUnlock.getDone()){
            gsm.pop();
        }
        else if(playUnlock && unlock.getDone()){
            gsm.pop();
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        if(playFullUnlock && !unlock.getDone()){
            sb.draw(bunOneLock.getFrame(),0,0);
            sb.draw(unlock.getFrame(),128,250);

        }
        else if(playFullUnlock && unlock.getDone()){
            sb.draw(bunOneLock.getFrame(),0,0);
            sb.draw(fullUnlock.getFrame(), 128, 250);

        }
        else{
            sb.draw(bunOneLock.getFrame(),0,0);
            sb.draw(unlock.getFrame(),128,250);
        }
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
