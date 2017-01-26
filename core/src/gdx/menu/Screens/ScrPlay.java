package gdx.menu.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import Game.GamMenu;
import gdx.menus.TbMenu;
import gdx.menus.TbsMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
    Sprite sprBob, sprPika, sprJoy, sprBall;
    Sprite sprFront, sprBack, sprLeft, sprRight;
    Texture txSheet, txSheet2, txSheet3, txSheet4, txTemp, txOne, txtBall;
    Animation aranBob[], aranPic[], aranJoy[];
    TextureRegion trTemp, trTemp2, trTemp3, trTemp4; // a single temporary texture region
    int fW, fH, fSx, fSy; // height and width of SpriteSheet image - and the starting upper coordinates on the Sprite Sheet
    int nFrame, nPos;
    float fBobX = 200;
    float fBobY = 200;
    float fPikaX = 100;
    float fPikaY = 100;
    float fJoyX = 600;
    float fJoyY = 700;
    float fBallX = 300;
    float fBallY = 300;
    Vector2 vBobPos, vPikaPos;
    float fBobH = 60, fBobW = 40, fPikaSize = 40, fBallSize = 20;
    int nDx, nDy, nDir;
    int nPixRenderTimer = 0;
    int nDirPixX, nDirPixY;       // Direction Pix wants to walk in.
    double nDeltaPixX, nDeltaPixY;
    double dPixAngle;
    int nPixXi, nPixYi;   // Pika X and Y increment
    int nJoyRenderTimer = 0;
    int nRoomNum = 0;   // Current room number we are in 
    int nDirJoyX, nDirJoyY;       // Direction Joy wants to walk in.
    double nDeltaJoyX, nDeltaJoyY;
    double dJoyAngle;
    int nJoyXi, nJoyYi;
    boolean bMove;
    //Texture BackGround;
    Texture arrBackGround[];
    ShapeRenderer shape;

    public ScrPlay(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
        Gdx.input.setInputProcessor((this));
        nFrame = 0;
        nPos = 0; // the position in the SpriteSheet - 0 to 7
        aranBob = new Animation[4];
        aranPic = new Animation[4];
        aranJoy = new Animation[4];
        batch = new SpriteBatch();
        txtBall = new Texture("Pokeball_(Paper).png");
        txSheet = new Texture("bob3.png");
        txSheet2 = new Texture("pic5.png");
        txSheet3 = new Texture("joy3.png");
        arrBackGround = new Texture[9];
        arrBackGround[0] = new Texture("town.png");
        arrBackGround[1] = new Texture("ff.png");
        arrBackGround[2] = new Texture("thing.jpg");
        sprBall = new Sprite(txtBall);
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
                if (i == 0 && j == 0) {
                    sprFront = new Sprite(sprBob);
                } else if (i == 1 && j == 0) {
                    sprBack = new Sprite(sprBob);
                } else if (i == 2 && j == 0) {
                    sprLeft = new Sprite(sprBob);
                } else if (i == 3 && j == 1) {
                    sprRight = new Sprite(sprBob);
                }
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
                sprPika = new Sprite(txSheet2, fSx, fSy, fW, fH);
                arSprPic[j] = new Sprite(sprPika);
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
        shape = new ShapeRenderer();
        //Thanks, Abdullah.
        vBobPos = new Vector2(fBobX, fBobY);
        vPikaPos = new Vector2(fPikaX, fPikaY);
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
        sprBob.setRegion(trTemp);
        trTemp2 = aranPic[nPos].getKeyFrame(nFrame, true);
        sprPika.setRegion(trTemp2);
        trTemp3 = aranJoy[nPos].getKeyFrame(nFrame, true);
        sprJoy.setRegion(trTemp3);

        batch.begin();
        batch.draw(arrBackGround[nRoomNum], 0, 0, 800, 500);
        nDir = 0;
        if (nRoomNum == 2) {
            batch.draw(sprBall, fBallX, fBallY, fBallSize, fBallSize);
        }
        if (bMove == true) {
            batch.draw(sprBob, fBobX, fBobY, fBobW, fBobH);
        }
        batch.draw(sprPika, fPikaX, fPikaY, fPikaSize, fPikaSize);
        batch.draw(sprJoy, fJoyX, fJoyX, 60, 70);
        batch.end();
        Rooms();
        PikaMove();
        JoyMove();
        movement();
        hits();
    }

    public void movement() {
        bMove = true;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            fBobY -= 3;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                fBobY -= 3;
            }
            nPos = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            fBobY += 3;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                fBobY += 3;
            }
            nPos = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            fBobX -= 3;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                fBobX -= 3;
            }
            nPos = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            fBobX += 3;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                fBobX += 3;
            }
            nPos = 3;
        } else {
            bMove = false;
            batch.begin();
            if (nPos == 0) {
                batch.draw(sprFront, fBobX, fBobY, fBobW, fBobH);
            } else if (nPos == 1) {
                batch.draw(sprBack, fBobX, fBobY, fBobW, fBobH);
            } else if (nPos == 2) {
                batch.draw(sprLeft, fBobX, fBobY, fBobW, fBobH);
            } else if (nPos == 3) {
                batch.draw(sprRight, fBobX, fBobY, fBobW, fBobH);
            }

            batch.end();
        }
    }

    public void Rooms() {
        //This is changing rooms based on his coordinates
        if (fBobX > 600 && nRoomNum == 0) {
            nRoomNum = 1;
            fBobX = 0;
            fPikaX = -100;
            fJoyX = -100;
        }
        if (fBobX < 0 && nRoomNum == 1) {
            nRoomNum = 0;
            fBobX = 600;
            fPikaX = 700;
            fJoyX = 700;
        }
        if (fBobX > 600 && nRoomNum == 1) {
            nRoomNum = 2;
            fBobX = 0;
            fPikaX = -100;
            fJoyX = -100;
        }
        if (fBobX < 0 && nRoomNum == 2) {
            nRoomNum = 1;
            fBobX = 600;
            fPikaX = 700;
            fJoyX = 700;
        }
        if (fBobX > 600 && nRoomNum == 2) {
            nRoomNum = 0;
            fBobX = 0;
            fPikaX = -100;
            fJoyX = -100;
        }
        //hit testing agaist borders of rooms
        if (fBobY < 20 && (nRoomNum == 0 || nRoomNum == 1 || nRoomNum == 2)) {
            fBobY += 3;
        }
        if (fBobX < 60 && nRoomNum == 0) {
            fBobX += 3;
        }
        if (fBobY > 400 && nRoomNum == 0) {
            fBobY -= 3;
        }
        if (fBobY > 200 && nRoomNum == 1) {
            fBobY -= 3;
        }
        if (fBobY > 300 && nRoomNum == 2) {
            fBobY -= 3;
        }
    }

    public void PikaMove() {
        nPixRenderTimer++;
        if (nPixRenderTimer == 9) {
            nPixRenderTimer = 0;

            nDirPixX = 1;
            if (fBobX < fPikaX) {
                nDirPixX = -1;
            }
            nDirPixY = 1;
            if (fBobY < fPikaY) {
                nDirPixY = -1;
            }

            nDeltaPixX = abs(fPikaX - fBobX);
            nDeltaPixY = abs(fPikaY - fBobY);

            dPixAngle = atan(nDeltaPixX / nDeltaPixY);

            nPixXi = (int) (12 * sin(dPixAngle) * nDirPixX);
            nPixYi = (int) (12 * cos(dPixAngle) * nDirPixY);

            fPikaX += nPixXi;
            fPikaY += nPixYi;

        }
    }

    public void JoyMove() {
        nJoyRenderTimer++;
        if (nJoyRenderTimer == 9) {
            nJoyRenderTimer = 0;

            nDirJoyX = 1;
            if (fPikaX < fJoyX) {
                nDirJoyX = -1;
            }
            nDirJoyY = 1;
            if (fPikaY < fJoyY) {
                nDirJoyY = -1;
            }

            nDeltaJoyX = abs(fJoyX - fPikaX);
            nDeltaJoyY = abs(fJoyY - fPikaY);

            dJoyAngle = atan(nDeltaJoyX / nDeltaJoyY);

            nJoyXi = (int) (10 * sin(dJoyAngle) * nDirJoyX);
            nJoyYi = (int) (10 * cos(dJoyAngle) * nDirJoyY);

            fJoyX += nJoyXi;
            fJoyY += nJoyYi;

        }
    }

    public void hits() {
        //BENNY IS THE BOSS WHEN IT COMES TO HIT DETECTION!
        if (fBobX + fBobW - 25 > fPikaX && fBobX < fPikaX + fPikaSize - 15
                && fBobY + fBobH - 25 > fPikaY && fBobY < fPikaY + fPikaSize - 15) {
            gamMenu.updateState(3);
            fPikaX = vPikaPos.x;
            fPikaY = vPikaPos.y;
            fBobX = vBobPos.x;
            fBobY = vBobPos.y;
            nRoomNum = 0;
        }
        if (nRoomNum == 2) {
            if (fBobX + fBobW > fBallX && fBobX < fBallX + fBallSize
                    && fBobY + fBobH > fBallY && fBobY < fBallY + fBallSize) {
                gamMenu.updateState(4);
                fPikaX = vPikaPos.x;
                fPikaY = vPikaPos.y;
                fBobX = vBobPos.x;
                fBobY = vBobPos.y;
                nRoomNum = 0;
                //Create a new state for 
            }
        }
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
