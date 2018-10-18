package swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.Procedure;
import service.CaseService;

public class ProcedureTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -3679080890919115191L;
    private String[] columnNames = new String[] { "序号", "法律程序名称", "时间", "备注"};
    public List<Procedure> list = new ArrayList<>();

    public void setList(int caseId) {
        list = new CaseService().selectProceduresByCaseId(caseId);
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
        Procedure procedure = list.get(rowIndex);
        if (columnIndex == 0)
            return procedure.getId();
        if (columnIndex == 1)
            return procedure.getName();
        if (columnIndex == 2)
            return procedure.getTime();
        if (columnIndex == 3)
            return procedure.getRemark();
        return null;
    }

}
