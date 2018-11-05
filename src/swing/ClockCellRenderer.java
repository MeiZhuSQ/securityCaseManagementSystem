package swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class ClockCellRenderer extends JButton implements ListCellRenderer {
    private static final long serialVersionUID = -1630559713175445689L;
    private Color background;
    private Color foreground;

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        setText(value.toString());
        setHorizontalAlignment(SwingConstants.LEFT);
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.red;

            // check if this cell is selected
        } else if (isSelected) {
            background = Color.green;
            foreground = Color.red;

            // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }
        setBackground(background);
        setForeground(foreground);
        return this;
    }


}