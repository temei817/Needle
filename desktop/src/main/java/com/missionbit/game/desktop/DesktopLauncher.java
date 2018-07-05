package com.missionbit.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.missionbit.game.Needle;

/** Launches the desktop (LWJGL) application. */
public class DesktopLauncher {
    public static void main(String[] args) {
        createApplication();
    }

    private static LwjglApplication createApplication() {
        return new LwjglApplication(new Needle(), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
<<<<<<< HEAD
        configuration.title = "Needle";
        configuration.width = 960;
        configuration.height = 540;
=======
        configuration.title = Needle.TITLE;
        configuration.width = Needle.WIDTH;
        configuration.height = Needle.HEIGHT;

>>>>>>> 628d91927f9689c4188179c68e9a53488f0f287d
        for (int size : new int[] { 128, 64, 32, 16 }) {
            configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
        }
        return configuration;
    }
}