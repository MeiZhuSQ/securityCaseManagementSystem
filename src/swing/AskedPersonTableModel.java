package swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.AskedPerson;
import service.CaseService;
/**
 * 弃用2019-01-11
 * @author lpf
 *
 */
public class AskedPersonTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5172529856464610326L;
    private String[] columnNames = new String[] { "序号", "姓名", "性别", "身份证号", "被询问人类型", "是否成人", "是否健全"};
    public List<AskedPerson> list = new ArrayList<>();

    public void setList(int noteId) {
        //list = new CaseService().selectAskedPersonByNoteId(noteId);
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AskedPerson askedPerson = list.get(rowIndex);
        if (columnIndex == 0)
            return askedPerson.getId();
        if (columnIndex == 1)
            return askedPerson.getName();
        if (columnIndex == 2)
            return askedPerson.getSex().equals("0") ? "男" : "女";
        if (columnIndex == 3)
            return askedPerson.getIdCard();
        if (columnIndex == 4)
            return askedPerson.getType();
        if (columnIndex == 5)
            return askedPerson.getAdultFlag();
        if (columnIndex == 6)
            return askedPerson.getDisabledFlag();
        return null;
    }

}
