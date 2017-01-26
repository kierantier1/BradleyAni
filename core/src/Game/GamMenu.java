package Game;

import com.badlogic.gdx.Game;
import gdx.menu.Screens.ScrMenu;
import gdx.menu.Screens.ScrPlay;
import gdx.menu.Screens.ScrGameover;
import gdx.menu.Screens.ScrRules;
import gdx.menu.Screens.ScrWin;

public class GamMenu extends Game {

    ScrMenu scrMenu;
    ScrPlay scrPlay;
    ScrRules scrRules;
    ScrGameover scrGameover;
    ScrWin scrWin;
    int nScreen; // 0 for menu, 1 for play, and 2 for rules, 3 for Game over

    public void updateState(int _nScreen) {
        System.out.println(nScreen);
        nScreen = _nScreen;
        if (nScreen == 0) {
            setScreen(scrMenu);
        } else if (nScreen == 1) {
            setScreen(scrPlay);
        } else if (nScreen == 2) {
            setScreen(scrRules);
        } else if (nScreen == 3) {
            setScreen(scrGameover);
        } else if (nScreen == 4) {
            setScreen(scrWin);
        }
    }

    @Override
    public void create() {
        nScreen = 0;
        // notice that "this" is passed to each screen. Each screen now has access to methods within the "game" master program
        scrMenu = new ScrMenu(this);
        scrPlay = new ScrPlay(this);
        scrRules = new ScrRules(this);
        scrGameover = new ScrGameover(this);
        scrWin = new ScrWin(this);
        updateState(0);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}