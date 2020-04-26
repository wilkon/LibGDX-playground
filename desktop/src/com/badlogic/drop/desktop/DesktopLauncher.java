package com.badlogic.drop.desktop;

import com.badlogic.drop.Droplets;
import com.badlogic.drop.MainMenuScreen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("MAKING IT RAIN!");
		config.setWindowSizeLimits(800, 480, 999, 999);
		new Lwjgl3Application(new Droplets(), config);
	}
}
