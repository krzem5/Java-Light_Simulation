package com.krzem.light_simulation;



import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class CollisionEngine extends Constants{
	public Main cls;
	public Engine e;
	private List<CollisionObject> ol;



	public CollisionEngine(Main cls,Engine e){
		this.cls=cls;
		this.e=e;
		this.ol=new ArrayList<CollisionObject>();
	}



	public CollisionObject add_object(Polygon p,double[][][] s,double[][] pl){
		this.ol.add(new CollisionObject(this,p,s,pl));
		return this.ol.get(this.ol.size()-1);
	}



	public void collide(Polygon e){
		double[][][] sl=e.next_shapes();
		e.cl.clear();
		for (double[][] s:sl){
			double[] aabb=this._aabb(s);
			double[][] p=this._normals(s);
			this._walls(e,sl,aabb);
			for (CollisionObject co:this.ol){
				if (e.co==co||this._intersect_aabb_aabb(co.t_aabb,aabb)==false){
					continue;
				}
				for (int j=0;j<co.len;j++){
					if (this._separate(e,co,s,sl,aabb,p,j)==true){
						break;
					}
				}
			}
			this._walls(e,sl,aabb);
		}
		// double[][] pl=e.next_polygon();
		// double[] t_aabb=this._aabb(pl);
		// boolean on_g=false;
		// for (CollisionObject co:this.ol){
		// 	if (e.co==co||this._intersect_aabb_aabb(co.t_aabb,t_aabb)==false){
		// 		continue;
		// 	}
		// 	if (this._collision_poly_poly(co.pl,pl)==true){
		// 		Collision c=co.coll_o(t_aabb[0]-co.t_aabb[0],t_aabb[1]-co.t_aabb[1]);
		// 		if (c.is_g()){
		// 			on_g=true;
		// 		}
		// 		e.cl.add(c);
		// 	}
		// }
		// e.on_g=on_g;
	}



	public double[] _aabb(double[][] s){
		double[] o=new double[]{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE};
		for (double[] p:s){
			o[0]=Math.min(o[0],p[0]);
			o[1]=Math.min(o[1],p[1]);
			o[2]=Math.max(o[2],p[0]);
			o[3]=Math.max(o[3],p[1]);
		}
		return o;
	}



	public double[][] _normals(double[][] s){
		double[][] o=new double[s.length][2];
		for (int i=0;i<s.length;i++){
			double x=s[(i+1)%s.length][0]-s[i][0];
			double y=s[(i+1)%s.length][1]-s[i][1];
			double m=Math.sqrt(x*x+y*y);
			o[i][0]=y/m;
			o[i][1]=-x/m;
		}
		return o;
	}



	public boolean _intersect_aabb_aabb(double[] a,double[] b){
		return (a[2]>=b[0]&&a[0]<=b[2]&&a[3]>=b[1]&&a[1]<=b[3]);
	}



	private void _walls(Polygon e,double[][][] sl,double[] aabb){
		double[] off=new double[2];
		if (aabb[0]<0){
			off[0]-=aabb[0];
		}
		if (aabb[1]<0){
			off[1]-=aabb[1];
		}
		if (aabb[2]>=WINDOW_SIZE.width){
			off[0]+=WINDOW_SIZE.width-aabb[2];
		}
		if (aabb[3]>=WINDOW_SIZE.height){
			off[1]+=WINDOW_SIZE.height-aabb[3];
		}
		e.vx+=off[0];
		e.vy+=off[1];
		if (off[0]!=0||off[1]!=0){
			for (double[][] s:sl){
				this._move_shape(s,aabb,off[0],off[1]);
			}
		}
	}



	private boolean _separate(Polygon e,CollisionObject co,double[][] e_s,double[][][] e_sl,double[] e_aabb,double[][] e_p,int idx){
		return  false;
	}



	private boolean _add_support(double[] d){

		return  false;
	}



	private void _move_shape(double[][] s,double[] aabb,double x,double y){
		for (int i=0;i<s.length;i++){
			s[i][0]+=x;
			s[i][1]+=y;
		}
		aabb[0]+=x;
		aabb[1]+=y;
		aabb[2]+=x;
		aabb[3]+=y;
	}



	private boolean _collision_poly_poly(double[][] p1,double[][] p2){
		for (int i=0;i<p1.length;i++){
			for (int j=0;j<p2.length;j++){
				if (this._collision_line_line(p1[i][0],p1[i][1],p1[(i+1)%p1.length][0],p1[(i+1)%p1.length][1],p2[j][0],p2[j][1],p2[(j+1)%p2.length][0],p2[(j+1)%p2.length][1])){
					return true;
				}
			}
		}
		return false;
	}



	private boolean _collision_line_line(double l1ax,double l1ay,double l1bx,double l1by,double l2ax,double l2ay,double l2bx,double l2by){
		double t=((l1ax-l2ax)*(l2ay-l2by)-(l1ay-l2ay)*(l2ax-l2bx))/((l1ax-l1bx)*(l2ay-l2by)-(l1ay-l1by)*(l2ax-l2bx));
		double u=-((l1ax-l1bx)*(l1ay-l2ay)-(l1ay-l1by)*(l1ax-l2ax))/((l1ax-l1bx)*(l2ay-l2by)-(l1ay-l1by)*(l2ax-l2bx));
		return (0<=u&&u<=1&&0<=t&&t<=1);
	}



	private int _sign(double v){
		return (v==0?0:(int)(v/Math.abs(v)));
	}
}
