package com.badlogic.droplets.mains.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.uiscene2d.mains.OffBroadway;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("MAKING IT RAIN!");
		config.setWindowSizeLimits(1000, 1000, 1000, 1000);
		new Lwjgl3Application(new OffBroadway(), config);
	}
}
