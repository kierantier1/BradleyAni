package gdx.menu.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import Game.GamMenu;
import gdx.menus.TbMenu;
import gdx.menus.TbsMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ScrPlay implements Screen, InputProcessor {

    GamMenu gamMenu;
    TbsMenu tbsMenu;
    TbMenu tbMenu, tbGameover;
    SpriteBatch batch;
    BitmapFont screenName;
    Sprite sprBob, sprPic, sprJoy;
    Texture txSheet, txSheet2, txSheet3, txTemp, txOne;
    Texture txtFront, txtBack, txtRight, txtLeft;
    Animation aranBob[], aranPic[], aranJoy[];
    TextureRegion trTemp, trTemp2, trTemp3; // a single temporary texture region
    int fW, fH, fSx, fSy; // height and width of SpriteSheet image - and the starting upper coordinates on the Sprite Sheet
    int nFrame, nPos;
    float spriteSpeed = 10.0f; // 10 pixels per second.
    float fSpriteX = 400;
    float fSpriteY = 200;
    float fSpriteX2 = 100;
    float fSpriteY2 = 100;
    float fSpriteX3 = 500;
    float fSpriteY3 = 400;
    int nDx, nDy, nDir;
    int nPixRenderTimer = 0;
    int nDirPixX, nDirPixY;       // Direction Pix wants to walk in.
    double nDeltaPixX, nDeltaPixY;
    double dPixAngle;
    int nPixXi, nPixYi;   // Pix X and Y increment
    int nJoyRenderTimer = 0;
    int nRoomNum = 0;   // Current room number we are in 
    int nDirJoyX, nDirJoyY;       // Direction Pix wants to walk in.
    double nDeltaJoyX, nDeltaJoyY;
    double dJoyAngle;
    int nJoyXi, nJoyYi;
    //Texture BackGround;
    Texture arrBackGround[];

    public ScrPlay(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
        Gdx.input.setInputProcessor((this));
        nFrame = 0;
        nPos = 0; // the position in the SpriteSheet - 0 to 7
        aranBob = new Animation[4];
        aranPic = new Animation[4];
        aranJoy = new Animation[4];
        batch = new SpriteBatch();
        txSheet = new Texture("bob3.png");
        txtFront = new Texture("front2.png");
        txtBack = new Texture("back2.png");
        txtRight = new Texture("right2.png");
        txtLeft = new Texture("left2.png");
        txSheet2 = new Texture("pic5.png");
        txSheet3 = new Texture("joy3.png");
        arrBackGround = new Texture[9];

        arrBackGround[0] = new Texture("town.png");
        arrBackGround[1] = new Texture("ff.png");
        arrBackGround[2] = new Texture("thing.jpg");

        //BackGround = new Texture(Gdx.files.internal("town.png"));
        fW = txSheet.getWidth() / 4;
        fH = txSheet.getHeight() / 4;
        System.out.println(fW + " " + fH);
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprBob = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprBob = new Sprite(txSheet, fSx, fSy, fW, fH);
                arSprBob[j] = new Sprite(sprBob);
            }
            aranBob[i] = new Animation(7f, arSprBob);

        }
        fW = txSheet2.getWidth() / 4;
        fH = txSheet2.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprPic = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprPic = new Sprite(txSheet2, fSx, fSy, fW, fH);
                arSprPic[j] = new Sprite(sprPic);
            }
            aranPic[i] = new Animation(7f, arSprPic);

        }
        fW = txSheet3.getWidth() / 4;
        fH = txSheet3.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprJoy = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprJoy = new Sprite(txSheet3, fSx, fSy, fW, fH);
                arSprJoy[j] = new Sprite(sprJoy);
            }
            aranJoy[i] = new Animation(7f, arSprJoy);

        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        nFrame++;
        if (nFrame > 28) {
            nFrame = 0;
        }
        trTemp = aranBob[nPos].getKeyFrame(nFrame, true);
        trTemp2 = aranPic[nPos].getKeyFrame(nFrame, true);
        trTemp3 = aranJoy[nPos].getKeyFrame(nFrame, true);
        batch.begin();
        nDx = 0;
        nDy = 0;
        nDir = 0;

        if (fSpriteX > 600 && nRoomNum == 0) {
            nRoomNum = 1;
            fSpriteX = 0;
            fSpriteX2 = 0;
            fSpriteX3 = 0;
        }
        if (fSpriteX < 0 && nRoomNum == 1) {
            nRoomNum = 0;
            fSpriteX = 600;
            fSpriteX2 = 700;
            fSpriteX3 = 700;
        }
        if (fSpriteX > 600 && nRoomNum == 1) {
            nRoomNum = 2;
            fSpriteX = 0;
            fSpriteX2 = 0;
            fSpriteX3 = 0;
        }
        if (fSpriteX < 0 && nRoomNum == 2) {
            nRoomNum = 1;
            fSpriteX = 600;
            fSpriteX2 = 700;
            fSpriteX3 = 700;
        }



        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            batch.draw(arrBackGround[nRoomNum], 0, 0, 800, 500);
            nDy = -3;
            nPos = 0;
            batch.draw(trTemp, fSpriteX, fSpriteY, 40, 70);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            batch.draw(arrBackGround[nRoomNum], 0, 0, 800, 500);
            nDy = 3;
            nPos = 1;
            batch.draw(trTemp, fSpriteX, fSpriteY, 40, 70);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            batch.draw(arrBackGround[nRoomNum], 0, 0, 800, 500);
            nDx = -3;
            nPos = 2;
            batch.draw(trTemp, fSpriteX, fSpriteY, 40, 70);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            batch.draw(arrBackGround[nRoomNum], 0, 0, 800, 500);
            nDx = 3;
            nPos = 3;
            batch.draw(trTemp, fSpriteX, fSpriteY, 40, 70);
        } else {
            batch.draw(arrBackGround[nRoomNum], 0, 0, 800, 500);
            if (nPos == 0) {
                batch.draw(txtFront, fSpriteX, fSpriteY, 40, 60);
            }
            if (nPos == 1) {
                batch.draw(txtBack, fSpriteX, fSpriteY, 40, 60);
            }
            if (nPos == 2) {
                batch.draw(txtLeft, fSpriteX, fSpriteY, 40, 55);
            }
            if (nPos == 3) {
                batch.draw(txtRight, fSpriteX, fSpriteY, 40, 55);
            }

        }

        nPixRenderTimer++;
        if (nPixRenderTimer == 9) {
            nPixRenderTimer = 0;

            nDirPixX = 1;
            if (fSpriteX < fSpriteX2) {
                nDirPixX = -1;
            }
            nDirPixY = 1;
            if (fSpriteY < fSpriteY2) {
                nDirPixY = -1;
            }

            nDeltaPixX = abs(fSpriteX2 - fSpriteX);
            nDeltaPixY = abs(fSpriteY2 - fSpriteY);

            dPixAngle = atan(nDeltaPixX / nDeltaPixY);

            nPixXi = (int) (12 * sin(dPixAngle) * nDirPixX);
            nPixYi = (int) (12 * cos(dPixAngle) * nDirPixY);

            fSpriteX2 += nPixXi;
            fSpriteY2 += nPixYi;

        }
        nJoyRenderTimer++;
        if (nJoyRenderTimer == 9) {
            nJoyRenderTimer = 0;

            nDirJoyX = 1;
            if (fSpriteX2 < fSpriteX3) {
                nDirJoyX = -1;
            }
            nDirJoyY = 1;
            if (fSpriteY2 < fSpriteY3) {
                nDirJoyY = -1;
            }

            nDeltaJoyX = abs(fSpriteX3 - fSpriteX2);
            nDeltaJoyY = abs(fSpriteY3 - fSpriteY2);

            dJoyAngle = atan(nDeltaJoyX / nDeltaJoyY);

            nJoyXi = (int) (10 * sin(dJoyAngle) * nDirJoyX);
            nJoyYi = (int) (10 * cos(dJoyAngle) * nDirJoyY);

            fSpriteX3 += nJoyXi;
            fSpriteY3 += nJoyYi;

        }

        batch.draw(trTemp2, fSpriteX2, fSpriteY2, 40, 40);
        batch.draw(trTemp3, fSpriteX3, fSpriteY3, 60, 70);

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            batch.draw(arrBackGround[nRoomNum], 0, 0, 800, 500);
            batch.draw(trTemp, fSpriteX, fSpriteY, 40, 70);
            batch.draw(trTemp2, fSpriteX2, fSpriteY2, 40, 40);
            batch.draw(trTemp3, fSpriteX3, fSpriteY3, 60, 70);
            nDx = (nDx * 2);
            nDy = (nDy * 2);
        }
        fSpriteX += nDx;
        fSpriteY += nDy;
        batch.end();

    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
