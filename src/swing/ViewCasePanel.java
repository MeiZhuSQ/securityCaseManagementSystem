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

import entity.AskedPerson;
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
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewCasePanel extends JPanel {

    private static final long serialVersionUID = 7334759086622449699L;
    private JTable noteTable;
    private NoteTableModel noteTableModel;
    private JTextField caseTimeField;
    private JTextField caseRemarkField;
    private JTable procedureTable;
    private ProcedureTableModel procedureTableModel;
    // 记录案子ID
    private int caseId;

    public ViewCasePanel(int caseId) {
        // 更新选中的caseId
        int i = MainFrame.getInstance().caseTable.getSelectedRow();
        this.caseId = Integer.parseInt(MainFrame.getInstance().caseTableModel.getValueAt(i, 0) + "");
        CaseService caseService = new CaseService();
        LegalCase legalCase = caseService.selectCaseById(caseId);

        setLayout(null);

        JPanel casePanel = new JPanel();
        casePanel.setBounds(0, 0, 1000, 77);
        add(casePanel);
        casePanel.setLayout(null);

        Border caseTitleBorder, caseLineBorder;
        caseLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        caseTitleBorder = BorderFactory.createTitledBorder(caseLineBorder, legalCase.getName() + "案件详情",
                TitledBorder.LEFT, TitledBorder.CENTER);
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

        Border noteTitleBorder, noteLineBorder;
        noteLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        noteTitleBorder = BorderFactory.createTitledBorder(noteLineBorder, "关联笔录", TitledBorder.LEFT,
                TitledBorder.CENTER);
        noteScrollPane.setBorder(noteTitleBorder);

        // createNoteTable();
        noteTableModel = new NoteTableModel();
        // 定义笔录Model，动态给Model赋值
        noteTableModel.setList(caseId);
        noteTable = new JTable(noteTableModel);
        noteTable.setRowHeight(30);
        JTableHeader head = noteTable.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
        noteScrollPane.setViewportView(noteTable);

        JPanel panel = new JPanel();
        panel.setBounds(0, 259, 1000, 36);
        add(panel);

        JButton createNoteButton = new JButton("新建");
        createNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                NotePanel notePanel = new NotePanel(caseId);
                MainFrame.tabbedPane.addTab("新建笔录", notePanel, null);
                MainFrame.tabbedPane.setSelectedComponent(notePanel);
            }
        });
        panel.add(createNoteButton);

        JButton editNoteButton = new JButton("修改");
        editNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NotePanel notePanel = new NotePanel(caseId);
                int i = noteTable.getSelectedRow();
                int noteId = Integer.parseInt(noteTableModel.getValueAt(i, 0) + "");
                notePanel.setNoteId(noteId);
                //给note赋值
                Note note = new CaseService().selectNoteById(noteId);
                notePanel.getNoteNameField().setText(note.getName());
                notePanel.getPlaceField().setText(note.getPlace());
                notePanel.getFileNameField().setText(note.getFileName());
                notePanel.getRemarkTextArea().setText(note.getRemark());
                MainFrame.tabbedPane.addTab("修改笔录", notePanel, null);
                MainFrame.tabbedPane.setSelectedComponent(notePanel);
            }
        });
        panel.add(editNoteButton);

        JButton deleteNoteButton = new JButton("删除");
        deleteNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel.add(deleteNoteButton);

        /*JButton viewNoteButton = new JButton("详情");
        viewNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel.add(viewNoteButton);*/

        JScrollPane lawScrollPane = new JScrollPane();
        lawScrollPane.setBounds(0, 298, 1000, 155);
        add(lawScrollPane);
        Border lawTitleBorder, lawLineBorder;
        lawLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        lawTitleBorder = BorderFactory.createTitledBorder(lawLineBorder, "关联法律程序", TitledBorder.LEFT,
                TitledBorder.CENTER);
        lawScrollPane.setBorder(lawTitleBorder);
        // createLawTable();
        procedureTableModel = new ProcedureTableModel();
        // 定义法律程序Model 动态给Model赋值
        procedureTableModel.setList(caseId);
        procedureTable = new JTable(noteTableModel);
        procedureTable.setRowHeight(30);
        head.setPreferredSize(new Dimension(procedureTable.getTableHeader().getWidth(), 30));
        lawScrollPane.setViewportView(procedureTable);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 453, 1000, 40);
        add(panel_1);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton createLawButton = new JButton("新建");
        createLawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_1.add(createLawButton);

        JButton editLawButton = new JButton("修改");
        editLawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_1.add(editLawButton);

        JButton deleteLawButton = new JButton("删除");
        deleteLawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_1.add(deleteLawButton);

        /*JButton viewLawButton = new JButton("详情");
        viewLawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_1.add(viewLawButton);*/

    }

    /*
     * public void createNoteTable () { noteTable = new JTable();
     * noteTable.setRowHeight(30); noteTableModel = new DefaultTableModel( new
     * Object[][] { }, new String[] { "序号", "笔录名称", "开始时间", "结束时间", "地点", "文件名",
     * "警员", "备注" }
     * 
     * ){ private static final long serialVersionUID = -4270056429026018083L;
     * 
     * @Override public boolean isCellEditable(int row, int column) { if (column
     * == 0) { return true; } return false; }
     * 
     * }; CaseService caseService = new CaseService(); List<Note> listNote =
     * caseService.selectNoteByCaseId(caseId); for (Note note : listNote) {
     * String id = String.valueOf(note.getId()); String name = note.getName();
     * String startTime = note.getStartTime(); String endTime =
     * note.getEndTime(); String place = note.getPlace(); String fileName =
     * note.getFileName(); String policeList = note.getPoliceList(); String
     * remark = note.getRemark(); String[] row ={id, name,
     * startTime,endTime,place,fileName,policeList,remark};
     * noteTableModel.addRow(row); } noteTable.setModel(noteTableModel);
     * GUIUtil.hideColumn(noteTable, 0); JTableHeader head =
     * noteTable.getTableHeader(); head.setPreferredSize(new
     * Dimension(head.getWidth(), 30));
     * 
     * }
     */

    /*
     * public void createLawTable () { lawTable.setRowHeight(30); lawTableModel
     * = new DefaultTableModel( new Object[][] { }, new String[] { "序号",
     * "法律程序名称", "时间", "备注" }
     * 
     * ){ private static final long serialVersionUID = -4270056429026018083L;
     * 
     * @Override public boolean isCellEditable(int row, int column) { if (column
     * == 0) { return true; } return false; }
     * 
     * }; CaseService caseService = new CaseService(); List<Procedure>
     * procedureList = caseService.selectProceduresByCaseId(caseId); for
     * (Procedure procedure : procedureList) { String id =
     * String.valueOf(procedure.getId()); String name = procedure.getName();
     * String time = procedure.getTime(); String remark = procedure.getRemark();
     * String[] row ={id, name, time,remark}; lawTableModel.addRow(row); }
     * lawTable.setModel(lawTableModel); GUIUtil.hideColumn(lawTable, 0);
     * JTableHeader head = lawTable.getTableHeader(); head.setPreferredSize(new
     * Dimension(head.getWidth(), 30));
     * 
     * }
     */
    public void updateNoteTable() {
        noteTableModel.setList(caseId);
        noteTable.updateUI();
    }

    public void updateProcedureTable() {
        procedureTableModel.setList(caseId);
        procedureTable.updateUI();
    }

}
