package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.missionbit.game.Animations;
import com.missionbit.game.Needle;

public class MenuState extends State {

    private Texture bkgd;
    private Animations bkgdAni;
    private Texture tsp;

    public MenuState(GameStateManager gsm) {
        super(gsm, "Music/Layers_Of_Fear_Soundtrack_The_End_feat_Penelopa_Willmann_Szynalik_.mp3");
        cam.setToOrtho(false, Needle.WIDTH, Needle.HEIGHT);
        bkgd = new Texture("images/titleImg.png");
        tsp = new Texture("images/TSP.png");
        bkgdAni = new Animations("images/titleImg.png", 3, 4, 10, 1f, true);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            //BasementState basementstate = new BasementState(gsm);
            //gsm.set(basementstate);
            System.out.println(gsm);
            //gsm.set(new BasementState(gsm));
            //gsm.push(new IntroState(gsm));
            gsm.push(new IntroState(gsm));
            //gsm.set(new KeypadState(gsm));
            //gsm.set(new SecondFloorState(gsm));
            //gsm.set(new SafeState(gsm));
            //gsm.set(new ThirdFloorState(gsm));
            //gsm.set(new GameOverState(gsm));
            //gsm.set(new BunUnlockState(gsm));

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bkgdAni.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bkgdAni.getFrame(), 295, 70, 400, 400);
        sb.draw(tsp, 110, -70);
        sb.end();
    }

    @Override
    public void dispose() {
        bkgd.dispose();
        music.stop();
        music.dispose();
        System.out.println("Disposing of MenuState");

    }

    public void stopmusic() {
        music.stop();
        music.dispose();
    }
}
