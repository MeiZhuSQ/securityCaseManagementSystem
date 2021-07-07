package swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.Police;
import service.CaseService;

public class PoliceTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -1425685789812673396L;
    private String[] columnNames = new String[] {"ID", "序号", "姓名", "性别"};
    public List<Police> list = new ArrayList<>();

    public void setList(int noteId) {
        list = new CaseService().selectPoliceForNote(noteId);
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
        Police police = list.get(rowIndex);
        if (columnIndex == 0)
            return police.getId();
        if (columnIndex == 1)
            return rowIndex + 1;
        if (columnIndex == 2)
            return police.getName();
        if (columnIndex == 3)
            return police.getSex().equals("0") ? "男" : "女";
        /*if (columnIndex == 3)
            return police.getPoliceNumber();*/
        return null;
    }

}
