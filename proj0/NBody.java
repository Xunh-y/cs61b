public class NBody{

	public static double readRadius(String path){
		In in = new In(path);
		int num = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String path){
		In in = new In(path);
		int num = in.readInt();
		in.readDouble();
		Planet[] ps = new Planet[num];
		for (int i = 0; i < num; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			ps[i] = new Planet(xP, yP, xV, yV, m, img);
		}
		return ps;
	}

	public static int readNum(String path){
		In in = new In(path);
		int num = in.readInt();
		return num;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = NBody.readRadius(filename);
		Planet[] ps = NBody.readPlanets(filename);
		int n = NBody.readNum(filename);

		StdDraw.enableDoubleBuffering();
		
		StdDraw.setScale(-radius,radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for(Planet p:ps){
			p.draw();
		}
		StdDraw.show();
		double t = 0.0;
		while(t < T){
			double[] fx = new double[n];
			double[] fy = new double[n];
			for(int i = 0; i < n; i++){
				fx[i] = ps[i].calcNetForceExertedByX(ps);
				fy[i] = ps[i].calcNetForceExertedByY(ps);
			}
			for(int i = 0; i < n; i++){
				ps[i].update(dt,fx[i],fy[i]);
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for(Planet p:ps){
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			t += dt;
		}
		StdOut.printf("%d\n", ps.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < ps.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            ps[i].xxPos, ps[i].yyPos, ps[i].xxVel,
            ps[i].yyVel, ps[i].mass, ps[i].imgFileName);   
		}
	}
}