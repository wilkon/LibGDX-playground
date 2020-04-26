package com.badlogic.droplets.desktop;

import com.badlogic.droplets.Droplets;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.uiscene2d.Broadway;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("MAKING IT RAIN!");
		config.setWindowSizeLimits(800, 480, 999, 999);
		new Lwjgl3Application(new Broadway(), config);
	}
}
