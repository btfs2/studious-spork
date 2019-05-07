package uk.ac.cam.bizrain.ui.comp;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

public class ComboBoxFix {

	/**
	 * Used for fixing combobox borders
	 * 
	 * See: https://stackoverflow.com/questions/776893/remove-border-from-jcombobox
	 * 
	 * @param combo
	 */
	public static void fixCbBorder(JComboBox<?> combo) {
		synchronized (combo.getTreeLock()) {
		for (int i = 0; i < combo.getComponentCount(); i++) 
		{
			System.out.println(combo.getComponent(i).getClass().getCanonicalName());
		    if (combo.getComponent(i) instanceof JComponent) {
		        ((JComponent) combo.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
		        System.out.println("\tDID STUFF1");
		    }


		    if (combo.getComponent(i) instanceof AbstractButton) {
		        ((AbstractButton) combo.getComponent(i)).setBorderPainted(false);
		        System.out.println("\tDID STUFF2");
		    }
		}
		}
	}
}
