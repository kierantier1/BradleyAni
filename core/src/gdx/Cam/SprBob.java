/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.Cam;

/**
 *
 * @author schmk3258
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SprBob extends gdx.ani1.GdxAni1 {

    private float fX, fY, fVx, fVy; // coordinates and velocities
    String sFile;
    Texture txImg;
    private Sprite sprImg;

    public SprBob(String _sFile, float _fX, float _fY) {
        sFile = _sFile;
        fX = _fX;
        fY = _fY;
        txImg = new Texture(sFile);
        sprImg = new Sprite(txImg); // I should not have to create a new Sprite, since this class extends the Sprite class. 
        //this = new Sprite(txImg);
    }

    // mutators and accessors
    void update(float _fVx, float _fVy) {
        fVx = _fVx;
        fVy = _fVy;
        fX += fVx;
        fY += fVy;
    }

    public Sprite getSprite() {
        return sprImg;
    }

    public float getX() {
        return fX;
    }

    public float getY() {
        return fY;
    }

}
