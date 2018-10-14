package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import entity.LegalCase;
import entity.Note;
import entity.Procedure;
import service.CaseService;
import util.GUIUtil;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JButton;

public class ViewCasePanel extends JPanel {
    
    private static final long serialVersionUID = 7334759086622449699L;
    private JTable noteTable;
    private DefaultTableModel noteTableModel;
    private int caseId;
    private JTextField caseTimeField;
    private JTextField caseRemarkField;
    private JTable lawTable;
    private DefaultTableModel lawTableModel;
    
    public ViewCasePanel(int caseId) {
        this.caseId = caseId;
        CaseService caseService = new CaseService();
        LegalCase legalCase = caseService.selectCaseById(caseId);
        setLayout(null);
        
        JPanel casePanel = new JPanel();
        casePanel.setBounds(0, 0, 1000, 77);
        add(casePanel);
        casePanel.setLayout(null);
        
        Border caseTitleBorder,caseLineBorder;
        caseLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        caseTitleBorder = BorderFactory
                .createTitledBorder(caseLineBorder 
                       , legalCase.getName()+"案件详情" , TitledBorder.LEFT 
                       , TitledBorder.CENTER );
        casePanel.setBorder(caseTitleBorder);
                
        JLabel caseTimeLabel = new JLabel("案件时间：");
        caseTimeLabel.setBounds(19, 20, 84, 18);
        casePanel.add(caseTimeLabel);
        
        JLabel caseRemarkLabel = new JLabel("案件备注：");
        caseRemarkLabel.setBounds(227, 20, 84, 18);
        casePanel.add(caseRemarkLabel);
        
        caseTimeField = new JTextField(legalCase.getTime());
        caseTimeField.setEditable(false);
        caseTimeField.setBounds(90, 17, 120, 24);
        casePanel.add(caseTimeField);
        caseTimeField.setColumns(10);
        
        caseRemarkField = new JTextField(legalCase.getRemark());
        caseRemarkField.setEditable(false);
        caseRemarkField.setBounds(299, 17, 120, 24);
        casePanel.add(caseRemarkField);
        caseRemarkField.setColumns(10);
        
        JScrollPane noteScrollPane = new JScrollPane();
        noteScrollPane.setBounds(0, 90, 1000, 168);
        add(noteScrollPane);
        
        Border noteTitleBorder,noteLineBorder;
        noteLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        noteTitleBorder = BorderFactory
                .createTitledBorder(noteLineBorder 
                        , "关联笔录" , TitledBorder.LEFT 
                        , TitledBorder.CENTER );
        noteScrollPane.setBorder(noteTitleBorder);
        
        createNoteTable();
        noteScrollPane.setViewportView(noteTable);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 259, 771, 36);
        add(panel);
        
        JButton btnNewButton_3 = new JButton("New button");
        panel.add(btnNewButton_3);
        
        JButton btnNewButton_2 = new JButton("New button");
        panel.add(btnNewButton_2);
        
        JButton btnNewButton_1 = new JButton("New button");
        panel.add(btnNewButton_1);
        
        JButton btnNewButton = new JButton("New button");
        panel.add(btnNewButton);
        
        JScrollPane lawScrollPane = new JScrollPane();
        lawScrollPane.setBounds(0, 298, 771, 155);
        add(lawScrollPane);
        Border lawTitleBorder,lawLineBorder;
        lawLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        lawTitleBorder = BorderFactory
                .createTitledBorder(lawLineBorder 
                        , "关联法律程序" , TitledBorder.LEFT 
                        , TitledBorder.CENTER );
        lawScrollPane.setBorder(lawTitleBorder);
        lawTable = new JTable();
        createLawTable();
        lawScrollPane.setViewportView(lawTable);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 453, 1000, 40);
        add(panel_1);
        
        JButton btnNewButton_7 = new JButton("New button");
        panel_1.add(btnNewButton_7);
        
        JButton btnNewButton_6 = new JButton("New button");
        panel_1.add(btnNewButton_6);
        
        JButton btnNewButton_5 = new JButton("New button");
        panel_1.add(btnNewButton_5);
        
        JButton btnNewButton_4 = new JButton("New button");
        panel_1.add(btnNewButton_4);
        
    }
    
    public void createNoteTable () {
        noteTable = new JTable();
        noteTable.setRowHeight(30);
        noteTableModel = new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "序号", "笔录名称", "开始时间", "结束时间", "地点", "文件名", "警员",  "备注"
                }
                
            ){
                private static final long serialVersionUID = -4270056429026018083L;
                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) {
                        return true;
                    }
                    return false;
                }
            
        };
        CaseService caseService = new CaseService();
        List<Note> listNote = caseService.selectNoteByCaseId(caseId);
        for (Note note : listNote) {
            String id = String.valueOf(note.getId());
            String name = note.getName();
            String startTime = note.getStartTime();
            String endTime = note.getEndTime();
            String place = note.getPlace();
            String fileName = note.getFileName();
            String policeList = note.getPoliceList();
            String remark = note.getRemark();
            String[] row ={id, name, startTime,endTime,place,fileName,policeList,remark};
            noteTableModel.addRow(row);
        }
        noteTable.setModel(noteTableModel);
        GUIUtil.hideColumn(noteTable, 0);
        JTableHeader head = noteTable.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 30));// 设置表头大小
        
    }
    
    public void createLawTable () {
        lawTable.setRowHeight(30);
        lawTableModel = new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "序号", "法律程序名称", "时间", "备注"
                }
                
            ){
                private static final long serialVersionUID = -4270056429026018083L;
                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) {
                        return true;
                    }
                    return false;
                }
            
        };
        CaseService caseService = new CaseService();
        List<Procedure> procedureList = caseService.selectProcedureByCaseId(caseId);
        for (Procedure procedure : procedureList) {
            String id = String.valueOf(procedure.getId());
            String name = procedure.getName();
            String time = procedure.getTime();
            String remark = procedure.getRemark();
            String[] row ={id, name, time,remark};
            lawTableModel.addRow(row);
        }
        lawTable.setModel(lawTableModel);
        GUIUtil.hideColumn(lawTable, 0);
        JTableHeader head = lawTable.getTableHeader(); 
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
        
    }
}
