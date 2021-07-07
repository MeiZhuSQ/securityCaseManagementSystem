package swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import constant.CommonConstant;
import entity.OtherPerson;
import service.CaseService;

public class OtherPersonTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1378854762463879797L;
    private String[] columnNames = new String[] { "ID", "序号", "姓名", "性别", "身份证号", "其他人类型"};
    public List<OtherPerson> list = new ArrayList<>();

    public void setList(int noteId) {
        list = new CaseService().selectOtherPersonByNoteId(noteId);
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
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OtherPerson otherPerson = list.get(rowIndex);
        if (columnIndex == 0)
            return otherPerson.getId();
        if (columnIndex == 1)
            return rowIndex + 1;
        if (columnIndex == 2)
            return otherPerson.getName();
        if (columnIndex == 3)
            return otherPerson.getSex().equals("0") ? "男" : "女";
        if (columnIndex == 4)
            return otherPerson.getIdCard();
        if (columnIndex == 5) {
            if (CommonConstant.OTHER_PERSON_TYPE_1.equals(otherPerson.getType())) {
                return "监护人";
            } else if (CommonConstant.OTHER_PERSON_TYPE_2.equals(otherPerson.getType())) {
                return "翻译";
            } else if (CommonConstant.OTHER_PERSON_TYPE_3.equals(otherPerson.getType())) {
                return "其他人员";
            } else {
                return "-";
            }
        }
        return null;
    }

}
