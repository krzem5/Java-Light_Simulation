package com.krzem.light_simulation;



import java.lang.Math;



public class MousePolygon extends Polygon{
	public int mx;
	public int my;



	public MousePolygon(Engine e){
		super(e);
		this.mx=WINDOW_SIZE.width/2;
		this.my=WINDOW_SIZE.height/2;
	}



	public void update(){
		if (this.e.c.get("left-joystick-x")<-30){
			this.mx-=10;
		}
		if (this.e.c.get("left-joystick-x")>30){
			this.mx+=10;
		}
		if (this.e.c.get("left-joystick-y")<-30){
			this.my-=10;
		}
		if (this.e.c.get("left-joystick-y")>30){
			this.my+=10;
		}
		this.mx=Math.min(Math.max(this.mx,0),WINDOW_SIZE.width);
		this.my=Math.min(Math.max(this.my,0),WINDOW_SIZE.height);
		if (this.e.c.get("cross")==1){
			double x=this.mx-this.cx;
			double y=this.my-this.cy;
			this.vx=Math.min(Math.abs(x),MAX_MOUSE_FORCE)*(x<0?-1:1);
			this.vy=Math.min(Math.abs(y),MAX_MOUSE_FORCE)*(y<0?-1:1);
		}
		super.update();
	}
}