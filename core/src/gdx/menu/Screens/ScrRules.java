package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import Game.GamMenu;
import gdx.menus.TbMenu;
import gdx.menus.TbsMenu;

public class ScrRules implements Screen, InputProcessor {

    GamMenu gamMenu;
    TbsMenu tbsMenu;
    TbMenu tbPlay, tbMenu;
    Stage stage;
    SpriteBatch batch;
    BitmapFont screenName;
    Texture BackGround;

    public ScrRules(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    public void show() {
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        screenName = new BitmapFont();
        tbMenu = new TbMenu("MENU", tbsMenu);
        BackGround = new Texture(Gdx.files.internal("rules.jpg"));
        tbMenu.setY(0);
        tbMenu.setX(0);
        stage.addActor(tbMenu);
        Gdx.input.setInputProcessor(stage);
        btnMenuListener();
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(BackGround, 0, 0, 800, 500);
        screenName.draw(batch, "Simple stuff really", 230, 400);
        screenName.draw(batch, "1) WASD to move", 230, 375);
        screenName.draw(batch, "2) Shift key to run faster", 230, 355);
        screenName.draw(batch, "3) Mouse to click things (but you already knew that ;) )", 230, 335);
        screenName.draw(batch, "OBJECTIVE", 230, 300);
        screenName.draw(batch, "FIND THE POKEBALL", 230, 270);
        screenName.draw(batch, "DON'T LET PIKACHU CATCH YOU, OR ELSE!!!", 230, 250);
        batch.end();
        stage.act();
        stage.draw();
    }

    public void btnMenuListener() {
        tbMenu.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                gamMenu.updateState(0);
            }
        });
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
