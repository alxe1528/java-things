package t.designPatterns.a_observer;

import java.util.ArrayList;
import java.util.List;

public class Button {

	private List<ActionListener> linsteners=new ArrayList<ActionListener>();
	
	public void addLinster(ActionListener l){
		this.linsteners.add(l);
	}
	
	public void btnPress(){
		for(ActionListener l:linsteners){
			l.performed(new ActionEvent(System.currentTimeMillis(), this));
		}
	}
	
}
