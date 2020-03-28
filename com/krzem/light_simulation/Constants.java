package com.krzem.light_simulation;



import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.util.Arrays;
import java.util.List;



public class Constants{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice SCREEN=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=SCREEN.getDefaultConfiguration().getBounds();
	public static final double MAX_FPS=60;

	public static final Color BG_COLOR=new Color(0,0,0);

	public static final double LIGHT_RAY_ANGLE_OFFSET=1e-4;
	public static final double LIGHT_RAY_INTERSECTION_BUFFOR=1e-7;

	public static final double FRICTION=0.995;
	public static final double GRAVITY=0.25;
	public static final double MAX_MOUSE_FORCE=10;
}