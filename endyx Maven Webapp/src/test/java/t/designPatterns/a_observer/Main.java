package t.designPatterns.a_observer;

public class Main {
	public static void main(String[] args) {
		Button b= new Button();
		b.addLinster(new BtnPressActionListenerImpl());
		b.btnPress();
	}
}
