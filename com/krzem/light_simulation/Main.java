package com.krzem.light_simulation;



import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.Exception;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;



public class Main extends Constants{
	public static void main(String[] args){
		new Main(args);
	}



	public double FPS=1;
	public Engine e;
	public JFrame frame;
	public Canvas canvas;
	private boolean _break=false;



	public Main(String[] args){
		this.init();
		this.frame_init();
		this.run();
	}



	public void init(){
		this.e=new Engine(this);
	}



	public void frame_init(){
		Main cls=this;
		this.frame=new JFrame("Light Simulation");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setUndecorated(true);
		this.frame.setResizable(false);
		this.frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				cls.quit();
			}
		});
		SCREEN.setFullScreenWindow(this.frame);
		this.canvas=new Canvas(this);
		this.canvas.setSize(WINDOW_SIZE.width,WINDOW_SIZE.height);
		this.canvas.setPreferredSize(new Dimension(WINDOW_SIZE.width,WINDOW_SIZE.height));
		this.frame.setContentPane(this.canvas);
		this.canvas.requestFocus();
		this.canvas.setCursor(this.canvas.getToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),new Point(),null));
	}



	public void run(){
		Main cls=this;
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (cls._break==false){
					long s=System.nanoTime();
					try{
						cls.canvas.repaint();
						double f=(System.nanoTime()-s)*1e-9;
						if (f<1/MAX_FPS){
							Thread.sleep((long)((1/MAX_FPS-f)*1e3));
						}
						cls.FPS=Math.round(1/((System.nanoTime()-s)*1e-9));
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (cls._break==false){
					long s=System.nanoTime();
					try{
						cls.update();
						double f=(System.nanoTime()-s)*1e-9;
						if (f<1/MAX_FPS){
							Thread.sleep((long)((1/MAX_FPS-f)*1e3));
						}
						// cls.FPS=1/((System.nanoTime()-s)*1e-9);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
	}



	public void update(){
		this.e.update();
		if (this.e.c!=null&&this.e.c.get("ps-button")==1){
			this.quit();
		}
	}



	public void draw(Graphics2D g){
		this.e.draw(g);
	}


	private void quit(){
		if (this._break==true){
			return;
		}
		this._break=true;
		this.frame.dispose();
		this.frame.dispatchEvent(new WindowEvent(this.frame,WindowEvent.WINDOW_CLOSING));
	}
}