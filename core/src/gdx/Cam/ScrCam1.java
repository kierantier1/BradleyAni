package gdx.Cam;

// this basic program works!!!
// Now, I want to click the image and change the background colour
//trying many things - imgage buttons, just images. Can't get it to work without lots of layers.
// wonder if I need scene2d - nope - not now.
//I am going back to "drop".
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import gdx.ani1.GdxAni1;


public class ScrCam1 implements Screen, InputProcessor {

    Game game;
    SpriteBatch batch;
    OrthographicCamera ocCam;
    Viewport viewport;
    //Texture txBad;
    Sprite sprBob;
    Texture txBg;
    boolean isClick = false;
    int nX, nY, fVx, fVy; // coordinates for the dude.
    //float fGameworldWidth = 1920, fGameworldHeight = 1080;
    int nW, nH;
     float scrollTimer = 0.0f;
    
    

    public ScrCam1(Game _game) {
        nX = 0;
        nY = 0;
        game = _game;
        batch = new SpriteBatch();
        //txBad = new Texture("badlogic.jpg");
        Gdx.input.setInputProcessor((this));
        nW = Gdx.graphics.getWidth();
        nH = Gdx.graphics.getHeight();
       // sprBob = new Sprite("bob.png", nW / 2, nH / 2);
    }

    //@Override
    public void show() {
        txBg = new Texture("bg.jpg");

        ocCam = new OrthographicCamera();
        viewport = new FillViewport(nW, nH, ocCam);
        // We tried FitViewport and StretchViewport and they didn't seem to work, but Fill did !
        viewport.apply();
        //ocCam.position.set(nW / 2, nH / 2, 0);
        ocCam.translate(nW / 2, nH / 2, 0);
    }

    //@Override
    public void render(float delta) {
        ocCam.translate(fVx, fVy, 0);
        ocCam.update();
        

        //sprBad.update(fVx, fVy);

        if (isClick) {
            Gdx.gl.glClearColor(1, 0, 1, 1); // purple???

        } else {
            Gdx.gl.glClearColor(1, 0, 0, 1);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(ocCam.combined);
        batch.draw(txBg, 0, 0, nW, nH);
     //   batch.draw(sprBob, gdxAni1.getX(), gdxAni1.getY());
      //  System.out.println(gdxAni1.getX() + "  " + gdxAni1.getY());
        //imgBad.draw(batch, delta);
        //batch.draw(txBad, nX, nY);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        ocCam.viewportWidth = width;
        ocCam.viewportHeight = height;
        ocCam.position.set(width / 2f, height / 2f, 0); //by default camera position on (0,0,0)

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    //@Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    //@Override
    public boolean keyDown(int keycode) {
        fVx = 0;
        fVy = 0;
        if (keycode == Input.Keys.UP) {
            nY++;
            fVy = 2;
            System.out.println("UP");
        } else if (keycode == Input.Keys.DOWN) {
            nY--;
            fVy = -2;
        } else if (keycode == Input.Keys.LEFT) {
            nX--;
            fVx = -2;
        } else if (keycode == Input.Keys.RIGHT) {
            nX++;
            fVx = 2;
        } else {
            System.out.println("Zappa for President");
        }
        // trying to re-set the camera, and therefore scroll the background.
        System.out.println(nX + " " + nY);
        //ocCam.position.set((int)sprBad.getX()+ nX, (int)sprBad.getY()+ nY, 0);
        


        return true;
    }

    //@Override
    public boolean keyUp(int keycode) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        fVx = 0;
        fVy = 0;
        return true;
    }

    //@Override
    public boolean keyTyped(char character) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    //@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    //@Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    //@Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    //@Override
    public boolean mouseMoved(int screenX, int screenY) {
        ///throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    //@Override
    public boolean scrolled(int amount) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

}
