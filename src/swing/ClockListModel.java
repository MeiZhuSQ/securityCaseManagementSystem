package swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import entity.Clock;
import service.CaseService;

public class ClockListModel<E> extends AbstractListModel<E> {

	private static final long serialVersionUID = 6719655395301687169L;
	public String[] values = null;
	
	public void setValue(){
		List<String> clockList = new ArrayList<>();
		List<Clock> clocks = new CaseService().getClocks();
		for (Clock clock : clocks) {
			clockList.add(clock.getName()+"  "+clock.getTime());
		}
		values = clockList.toArray(new String[clockList.size()]);
	}
	
	@Override
	public E getElementAt(int index) {
		return (E) values[index];
	}

	@Override
	public int getSize() {
		return values.length;
	}

}
