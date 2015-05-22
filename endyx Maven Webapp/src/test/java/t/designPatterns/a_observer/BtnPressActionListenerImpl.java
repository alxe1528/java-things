package t.designPatterns.a_observer;

public class BtnPressActionListenerImpl implements ActionListener{

	public void performed(ActionEvent event) {
		System.out.println("btnPress...");
	}

}
