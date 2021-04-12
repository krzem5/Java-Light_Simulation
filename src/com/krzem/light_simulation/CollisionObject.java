package com.krzem.light_simulation;



public class CollisionObject extends Constants{
	public CollisionEngine ce;
	public double[][][] s;
	public double[][] aabb;
	public double[] t_aabb;
	public double[][][] p;
	public double[][] pl;
	public int len;
	public Polygon o;



	public CollisionObject(CollisionEngine ce,Polygon o,double[][][] s,double[][] pl){
		this.ce=ce;
		this.o=o;
		this.update(s,pl);
	}



	public void update(double[][][] s,double[][] pl){
		this.pl=pl;
		this.s=s;
		this.len=this.s.length;
		this.aabb=new double[this.len][4];
		this.t_aabb=this.ce._aabb(this.pl);
		this.p=new double[this.len][][];
		for (int i=0;i<this.len;i++){
			this.aabb[i]=this.ce._aabb(this.s[i]);
			this.p[i]=this.ce._normals(this.s[i]);
		}
	}



	public Collision coll_o(double x,double y){
		return new Collision(this,x,y);
	}
}