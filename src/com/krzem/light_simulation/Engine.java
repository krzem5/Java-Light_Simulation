package com.krzem.light_simulation;



import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.Exception;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class Engine extends Constants{
	public Main cls;
	public Controller c;
	public CollisionEngine ce;
	public List<Polygon> pl;
	public MousePolygon mp;



	public Engine(Main cls){
		this.cls=cls;
		this.ce=new CollisionEngine(this.cls,this);
		this.ce.add_object(null,new double[][][]{{{0,WINDOW_SIZE.height/2},{WINDOW_SIZE.width/2,WINDOW_SIZE.height},{0,WINDOW_SIZE.height}}},new double[][]{{0,WINDOW_SIZE.height/2},{WINDOW_SIZE.width/2,WINDOW_SIZE.height},{0,WINDOW_SIZE.height}});
		this.ce.add_object(null,new double[][][]{{{WINDOW_SIZE.width,WINDOW_SIZE.height/2},{WINDOW_SIZE.width/2,WINDOW_SIZE.height},{WINDOW_SIZE.width,WINDOW_SIZE.height}}},new double[][]{{WINDOW_SIZE.width,WINDOW_SIZE.height/2},{WINDOW_SIZE.width/2,WINDOW_SIZE.height},{WINDOW_SIZE.width,WINDOW_SIZE.height}});
		this.pl=new ArrayList<Polygon>();
		this.pl.add(new Polygon(this));
		this.mp=new MousePolygon(this);
		this._find_controller();
	}



	public void update(){
		if (this.c==null){
			return;
		}
		for (Polygon p:this.pl){
			p.update();
		}
		this.mp.update();
	}



	public void draw(Graphics2D g){
		g.setColor(BG_COLOR);
		g.fillRect(0,0,WINDOW_SIZE.width,WINDOW_SIZE.height);
		g.setColor(new Color(50,50,50));
		g.fillPolygon(new int[]{0,WINDOW_SIZE.width/2,WINDOW_SIZE.width,WINDOW_SIZE.width,0},new int[]{WINDOW_SIZE.height/2,WINDOW_SIZE.height,WINDOW_SIZE.height/2,WINDOW_SIZE.height,WINDOW_SIZE.height},5);
		for (Polygon p:this.pl){
			p.draw(g);
		}
		this.mp.draw(g);
		g.setColor(new Color(255,255,255,100));
		g.fillRect(this.mp.mx-20,this.mp.my-20,40,40);
	}



	private void _find_controller(){
		Engine cls=this;
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (true){
					ArrayList<Controller> cl=Controller.list();
					if (cl.size()>0){
						cls.c=cl.get(0);
						return;
					}
					try{
						Thread.sleep(50);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}