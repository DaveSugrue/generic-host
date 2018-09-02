package ie.sugrue.domain;

public class Tester1 extends Abster implements AdditFunc {
	private int		x;
	private String	who	= "child";

	public Tester1() {
		this.x = 0;
	}

	public void tryThis() {
		x++;
		System.out.println("x = " + x);
	}

	public void doExtra() {
		x = x + i;
		System.out.println("x = " + x);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void met1() {
		System.out.println("who = " + who);
	}

	public void met2() {
		System.out.println("who = " + who);
	}
}
