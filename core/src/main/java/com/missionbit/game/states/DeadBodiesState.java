package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Animations;
import com.missionbit.game.Interactables;
import com.missionbit.game.Needle;

public class DeadBodiesState extends State{

    private Texture dead;
    private Animations deadAni;
    private Interactables bunKey;
    private Texture bunKeyInv;
    private boolean showDebug = false;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private Sound keysound;
    boolean Keyplayed;

    public DeadBodiesState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH/1.5f, Needle.HEIGHT/1.5f);
        dead = new Texture("images/dead.png");
        deadAni = new Animations(new TextureRegion(dead),8,1f);
        bunKey = new Interactables(new Texture("images/bunnykeyclose.png"),350,25,46,10);
        bunKeyInv = new Texture("images/bunkeyin.png");
        keysound = Gdx.audio.newSound(Gdx.files.internal("Music/keyy.wav"));
    }

    @Override
    protected void handleInput() {

        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);

            //put bunny key in inv if touched
            if(bunKey.getButton().handleClick(touchPos)){
                if(!gsm.getInventory().getBunKey() && !Keyplayed) {
                    keysound.play();
                    Keyplayed = true;
                    gsm.getInventory().setInv(bunKeyInv);
                    gsm.getInventory().setBunKey(true);
                    System.out.println("clicked");
                }

            }

            else {
               gsm.pop();
            }
        }


    }

    @Override
    public void update(float dt) {
        handleInput();
        deadAni.update(dt);
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(deadAni.getFrame(),100,0);

        //draw bunny key if key isnt in inventory
        if(!gsm.getInventory().getBunKey()) {
            sb.draw(bunKey.getTexture(), bunKey.getXLoc(), bunKey.getYLoc(), bunKey.getWidth(), bunKey.getHeight());
        }

        sb.end();

        if (showDebug) {
            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(1, 1, 1, 1);
            bunKey.getButton().drawDebug(debugRenderer);
        }
        debugRenderer.end();

    }

    @Override
    public void dispose() {
        dead.dispose();
        deadAni.dispose();

    }
}
