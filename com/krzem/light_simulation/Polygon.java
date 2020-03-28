package com.krzem.light_simulation;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;



public class Polygon extends Constants{
	public Engine e;
	public double cx;
	public double cy;
	public double vx;
	public double vy;
	public CollisionObject co;
	public double[][][] s;
	public double[][] p;
	public List<Collision> cl;
	public boolean on_g;



	public Polygon(Engine e){
		this.e=e;
		this.cx=WINDOW_SIZE.width/2;
		this.cy=100;
		this.vx=0;
		this.vy=0;
		this.s=new double[][][]{{{this.cx-50,this.cy-50},{this.cx+50,this.cy-50},{this.cx+50,this.cy+50},{this.cx-50,this.cy+50}}};
		this.p=new double[][]{{this.cx-50,this.cy-50},{this.cx+50,this.cy-50},{this.cx+50,this.cy+50},{this.cx-50,this.cy+50}};
		this.co=this.e.ce.add_object(this,this.s,this.p);
		this.cl=new ArrayList<Collision>();
		this.on_g=false;
	}



	public void update(){
		this.e.ce.collide(this);
		this.cx+=this.vx;
		this.cy+=this.vy;
		for (double[][] ss:this.s){
			for (double[] pt:ss){
				pt[0]+=this.vx;
				pt[1]+=this.vy;
			}
		}
		for (double[] pt:this.p){
			pt[0]+=this.vx;
			pt[1]+=this.vy;
		}
		this.vx*=FRICTION;
		this.vy+=GRAVITY;
		this.co.update(this.s,this.p);
	}



	public void draw(Graphics2D g){
		g.setColor(Color.white);
		g.setStroke(new BasicStroke(10));
		for (int i=0;i<this.p.length;i++){
			g.drawLine((int)this.p[i][0],(int)this.p[i][1],(int)this.p[(i+1)%this.p.length][0],(int)this.p[(i+1)%this.p.length][1]);
		}
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(3));
		for (double[][] ss:this.s){
			for (int i=0;i<ss.length;i++){
				g.drawLine((int)ss[i][0],(int)ss[i][1],(int)ss[(i+1)%ss.length][0],(int)ss[(i+1)%ss.length][1]);
			}
		}
	}



	public double[][][] next_shapes(){
		double[][][] o=new double[this.s.length][][];
		int i=0;
		for (double[][] ss:this.s){
			o[i]=new double[ss.length][2];
			int j=0;
			for (double[] p:ss){
				o[i][j]=new double[]{p[0]+this.vx,p[1]+this.vy};
				j++;
			}
			i++;
		}
		return o;
	}



	public double[][] next_polygon(){
		double[][] o=new double[this.p.length][];
		int i=0;
		for (double[] pt:this.p){
			o[i]=new double[]{pt[0]+this.vx,pt[1]+this.vy};
			i++;
		}
		return o;
	}



	public double[][][] shapes(){
		return this.s;
	}



	public double[][] polygon(){
		return this.p;
	}
}