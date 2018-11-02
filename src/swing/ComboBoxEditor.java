package swing;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 * @author LiuPF
 * @date 2018-11-02
 */
class ComboBoxEditor extends DefaultCellEditor {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ComboBoxEditor(String[] items) {
        super(new JComboBox(items));
    }
}
