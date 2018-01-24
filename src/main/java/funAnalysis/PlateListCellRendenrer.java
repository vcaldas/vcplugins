package funAnalysis;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
//https://stackoverflow.com/questions/20155122/is-there-any-way-to-add-objects-to-a-jcombobox-and-assign-a-string-to-be-shown
public class PlateListCellRendenrer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if(value instanceof Plate) {
			value = ((Plate)value).getName();
		}
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		return this;
	}


}
