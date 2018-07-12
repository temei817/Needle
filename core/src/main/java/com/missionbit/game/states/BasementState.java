package com.missionbit.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.missionbit.game.Animations;
import com.missionbit.game.Button;
import com.missionbit.game.Interactables;
import com.missionbit.game.Needle;
import com.missionbit.game.PolygonButton;
import com.missionbit.game.characters.Female;

public class BasementState extends State {

    private Female female;
    private Texture bkgrd;
    private Button bkgdButton, bkgdButtonTwo;
    private Button bookshelfButton;
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private boolean showDebug = true;
    private Animations BookShelfAnimation;
    private PolygonButton safeButton;
    private float[] safevertices = {776, 100, 777, 186, 796, 142, 796, 58};
    private Interactables hangingBody, bleedingBody, bookshelf;


    public BasementState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Needle.WIDTH / 1.5f, Needle.HEIGHT / 1.5f);
        female = new Female(50, 50);
        bkgrd = new Texture("images/basement.png");
        //Texture book = new Texture("images/BOOKSHELF.png");
        //BookShelfAnimation = new Animations(new TextureRegion(book),28,2f);
        bkgdButton = new Button(0, 0, 800, 130, "1");
        bkgdButtonTwo = new Button(800, 0, 160, 400, "2");
        safeButton = new PolygonButton(safevertices);
        //bookshelfButton = new Button(589, 115, 115, 164, "bookshelf");
        hangingBody = new Interactables(new Texture("images/Hanging.png"), 150, 175, 70, 130, 8, 1f);
        bleedingBody = new Interactables(new Texture("images/Bleeding.png"), 400, 100, 58, 82, 8, 1f);
        bookshelf = new Interactables(new Texture("images/BOOKSHELF.png"), 589, 115, 163, 169, 28, 2f);

    }

    @Override
    protected void handleInput() {

        //move char if player taps
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            System.out.println("Click" + touchPos.x + " " + touchPos.y);
            System.out.println(touchPos.x + ", " + touchPos.y);

            //switch to first person bookshelf if touched
            if (bookshelf.getButton().handleClick(touchPos)) {
                gsm.push(new BookshelfState(gsm));
            }
            //switch to safe
            else if (safeButton.handleClick(touchPos)) {
                gsm.push(new SafeState(gsm));
            }
            else {
                female.setTargetLoc((int) touchPos.x, (int) touchPos.y);
            }
            mapBounds(touchPos);

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        female.update(dt);
        hangingBody.update(dt);
        bleedingBody.update(dt);
        bookshelf.update(dt);

        //camera bounds
        float minX = cam.viewportWidth / 2, maxX = bkgrd.getWidth() - cam.viewportWidth / 2;
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
        sb.setProjectionMatrix(cam.combined);

        //draw the background
        sb.draw(bkgrd, 0, 0, Needle.WIDTH, Needle.HEIGHT);

        //draw the interactables
        sb.draw(bookshelf.getFrame(), bookshelf.getXLoc(), bookshelf.getYLoc(), bookshelf.getWidth(), bookshelf.getHeight());
        sb.draw(hangingBody.getFrame(), hangingBody.getXLoc(), hangingBody.getYLoc(), hangingBody.getWidth(), hangingBody.getHeight());
        sb.draw(bleedingBody.getFrame(), bleedingBody.getXLoc(), bleedingBody.getYLoc(), bleedingBody.getWidth(), bleedingBody.getHeight());

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

        if (showDebug) {
            debugRenderer.setProjectionMatrix(cam.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(1, 1, 1, 1);
            bkgdButton.drawDebug(debugRenderer);
            bkgdButtonTwo.drawDebug(debugRenderer);
            female.drawDebug(debugRenderer);
            //bookshelfButton.drawDebug(debugRenderer);
            safeButton.drawDebug(debugRenderer);
            hangingBody.getButton().drawDebug(debugRenderer);
            bookshelf.getButton().drawDebug(debugRenderer);
            bleedingBody.getButton().drawDebug(debugRenderer);
        }
        debugRenderer.end();

    }


    public void mapBounds(Vector3 touchPos) {
        //bounds for char movement
        if ((!bkgdButton.handleClick(touchPos) && (!bkgdButtonTwo.handleClick(touchPos))) && female.getTargetLoc().y > 100) {
            female.setTargetLoc((int) touchPos.x, 90);
        }
        else if ((!bkgdButtonTwo.handleClick(touchPos)) && female.getTargetLoc().y > 100) {
            female.setTargetLoc((int) touchPos.x, 90);

        }
    }

    @Override
    public void dispose() {
        female.dispose();
        bkgrd.dispose();
        hangingBody.dispose();
        bleedingBody.dispose();
        bookshelf.dispose();
    }
}


