package swing;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.LegalCase;
import service.CaseService;

public class CaseTableModel extends AbstractTableModel{
    
    private static final long serialVersionUID = -9105203202190115194L;
    
    private String[] columnNames = new String[] { "序号", "案件名称", "时间", "备注","操作"};
    
    public List<LegalCase> list = new CaseService().listCase();

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
        //第5列可编辑
        if (columnIndex == 4) {
            return true;
        }
        if (columnIndex == 3) {
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LegalCase legalCase = list.get(rowIndex);
        if (columnIndex == 0)
            return legalCase.getId();
        if (columnIndex == 1)
            return legalCase.getName();
        if (columnIndex == 2)
            return legalCase.getTime();
        if (columnIndex == 3)
            return legalCase.getRemark();
        return null;
    }
}
