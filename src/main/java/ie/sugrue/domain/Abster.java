package ie.sugrue.domain;

public abstract class Abster {
	private String who = "parent";

	public abstract void met1();

	public void met2() {
		System.out.println("who = " + who);
	}

	public void met3() {
		System.out.println("who = " + who);
	}

	public void met4() {
		System.out.println("who = " + who);
	}
}
