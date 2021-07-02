
public class TestPlanet{
	public static void main(String[] args) {
        Planet p1 = new Planet(1.0,1.0,2.0,2.0,10.0,"p1");
        Planet p2 = new Planet(1422.0,111.0,212.0,223.0,10444.0,"p2");
        Planet[] ps = {p1,p2};
        double dx = p1.calcNetForceExertedByX(ps);
        System.out.println(dx);
    }
}