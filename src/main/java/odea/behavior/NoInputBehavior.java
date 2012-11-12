package odea.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;

public class NoInputBehavior extends Behavior {

	@Override
	public void bind(Component component) {
		component.add(new AttributeAppender("onkeypress", "return false;"));
	}
	
	

}
