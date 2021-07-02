public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public static double G = 6.67e-11; 
	
	public Planet(double xP,double yP,double xV,double yV,double m,String img){
		xxPos = xP;
		xxVel = xV;
		yyPos = yP;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

    public Planet(Planet p){
    	xxPos = p.xxPos;
		xxVel = p.xxVel;
		yyPos = p.yyPos;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p){
    	double dx = p.xxPos-this.xxPos;
    	double dy = p.yyPos-this.yyPos;
    	double r = Math.sqrt(dx*dx+dy*dy);
    	return r;
    }

    public double calcForceExertedBy(Planet p){
    	double r = this.calcDistance(p);
    	double f = (G*p.mass*this.mass)/(r*r);
    	return f;
    }

    public double calcForceExertedByX(Planet p){
    	double r = this.calcDistance(p);
    	double f = this.calcForceExertedBy(p);
    	double fx = (f*(p.xxPos-this.xxPos))/r;
    	return fx;
    }

    public double calcForceExertedByY(Planet p){
    	double r = this.calcDistance(p);
    	double f = this.calcForceExertedBy(p);
    	double fy = (f*(p.yyPos-this.yyPos))/r;
    	return fy;
    }

    public double calcNetForceExertedByX (Planet[] ps){
    	double fx = 0.0;
    	for(Planet p:ps){
    		if(p.equals(this)){
    			continue;
    		}
    		double tmpfx = this.calcForceExertedByX(p);
    		fx += tmpfx;
    	}
    	return fx;
    }

    public double calcNetForceExertedByY  (Planet[] ps){
    	double fy = 0.0;
    	for(Planet p:ps){
    		if(p.equals(this)){
    			continue;
    		}
    		double tmpfy = this.calcForceExertedByY(p);
    		fy += tmpfy;
    	}
    	return fy;
    }

    public void update(double dt,double fx,double fy){
    	double ax = fx/this.mass;
    	double ay = fy/this.mass;
    	this.xxVel = this.xxVel + ax*dt;
    	this.yyVel = this.yyVel + ay*dt;
    	this.xxPos = this.xxPos + this.xxVel*dt;
    	this.yyPos = this.yyPos + this.yyVel*dt;
    }

    public void draw(){
    	StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}