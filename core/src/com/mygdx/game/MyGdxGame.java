package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.GameStatesManager;
import com.mygdx.game.states.MenuState;
import com.mygdx.game.states.PlayStates;

public class MyGdxGame extends ApplicationAdapter {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Flappy Bird";


    private GameStatesManager gameStatesManager;

    private SpriteBatch batch;

    @Override
    public void create() {
        gameStatesManager = new GameStatesManager();
        Gdx.app.debug("MyGdxGame.create", "gameStateManager is created");
        batch = new SpriteBatch();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gameStatesManager.push(new MenuState(gameStatesManager));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();
        gameStatesManager.update(Gdx.graphics.getDeltaTime());
        gameStatesManager.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
