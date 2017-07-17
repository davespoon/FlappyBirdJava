package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

/**
 * Created by grin on 16.07.2017.
 */
public class PlayStates extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private Bird bird;
    private Texture background;
    //private Tube tube;
    private Array<Tube> tubes;

    public PlayStates(GameStatesManager gameStateManager) {
        super(gameStateManager);
        bird = new Bird(50, 300);
        camera.setToOrtho(false, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        background = new Texture("bg.png");
        //tube = new Tube(100);
        tubes = new Array<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        bird.update(deltaTime);
        camera.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);

            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {
                gameStateManager.set(new PlayStates(gameStateManager));
                Gdx.app.debug("PlayStates.update", "player collides with tube");
            }
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, camera.position.x - camera.viewportWidth / 2, 0);
        spriteBatch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            spriteBatch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            spriteBatch.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();

        for (Tube tube : tubes) {
            tube.dispose();
        }
    }
}
