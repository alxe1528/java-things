package t.designPatterns.a_observer;

public class ActionEvent {
	long time;
	Object source;

	public ActionEvent(long time, Object source) {
		super();
		this.time = time;
		this.source = source;
	}
}
