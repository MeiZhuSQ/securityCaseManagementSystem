package swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.Police;
import service.CaseService;

public class PoliceTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -1425685789812673396L;
    private String[] columnNames = new String[] { "序号", "姓名", "性别", "警号" };
    public List<Police> list = new CaseService().listPolice();

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
            return police.getName();
        if (columnIndex == 2)
            return police.getSex();
        if (columnIndex == 3)
            return police.getPoliceNumber();
        return null;
    }

}
