package dev.game.spacechaos.engine.input;

import com.badlogic.gdx.Gdx;

/**
 * Created by Jo on 13.04.2017.
 */
public class InputStates {

    public static boolean isLeftMouseButtonPressed() {
        return Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT);
    }

    public static boolean isLeftMouseButtonJustPressed() {
       return isLeftMouseButtonPressed() && Gdx.input.justTouched();
    }

    public static boolean isRightMouseButtonPressed() {
        return Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.RIGHT);
    }

    public static boolean isRightMouseButtonJustPressed() {
        return isRightMouseButtonPressed() && Gdx.input.justTouched();
    }

    public static boolean isSpacePressed() {
        return Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.SPACE);
    }
}
