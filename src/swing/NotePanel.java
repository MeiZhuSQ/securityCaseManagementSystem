package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.eltima.components.ui.DatePicker;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.Note;
import entity.Police;
import service.CaseService;
import util.DateUtil;
import util.GUIUtil;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class NotePanel extends JPanel {

    private static final long serialVersionUID = 5820832059732422488L;
    private JTextField noteNameField;
    private DatePicker startTime;
    private DatePicker endTime;
    private JTextField placeField;
    private JTextArea remarkTextArea;
    private int caseId;
    //public final MulitCombobox mulit;
    public static int noteId;
    private int procedureId;
    private JTextField fileNameField;
    public NoteAskedTypeListener askedTypeListener;
    private JTable askedPersonTable;
    private JTable otherPersonTable;
    public AskedPersonTableModel askedPersonTableModel;
    public OtherPersonTableModel otherPersonTableModel;
    private static NotePanel instance;
    
    String[] values = new String[] { "1", "2", "3" };
    private JTextField textField;
    private JTextField textField_1;
    
    public static NotePanel getInstance() {
        if (instance == null) {
            instance = new NotePanel();
        }
        return instance;
    }
    public NotePanel() {
        setLayout(null);
        JPanel notePanel = new JPanel();
        notePanel.setBounds(14, 13, 972, 194);
        add(notePanel);
        Border noteTitleBorder, noteLineBorder;
        noteLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        noteTitleBorder = BorderFactory.createTitledBorder(noteLineBorder, "笔录基本信息", TitledBorder.LEFT,
                TitledBorder.CENTER);
        notePanel.setBorder(noteTitleBorder);
        notePanel.setLayout(null);

        JLabel noteNameLabel = new JLabel("笔录名称");
        noteNameLabel.setBounds(24, 32, 72, 18);
        notePanel.add(noteNameLabel);

        noteNameField = new JTextField();
        noteNameField.setBounds(102, 29, 137, 24);
        notePanel.add(noteNameField);
        noteNameField.setColumns(10);

        JLabel startTimeLabel = new JLabel("开始时间");
        startTimeLabel.setBounds(24, 77, 72, 18);
        notePanel.add(startTimeLabel);

        startTime = DateUtil.getDatePicker();
        JPanel startTimePanel = new JPanel();
        startTimePanel.setBounds(99, 64, 177, 30);
        startTimePanel.add(startTime);
        notePanel.add(startTimePanel);

        JLabel endTimelabel = new JLabel("结束时间");
        endTimelabel.setBounds(303, 74, 72, 18);
        notePanel.add(endTimelabel);

        JPanel endTimePanel = new JPanel();
        endTimePanel.setBounds(374, 64, 177, 34);
        endTime = DateUtil.getDatePicker();
        endTimePanel.add(endTime);
        notePanel.add(endTimePanel);

        JLabel placeLabel = new JLabel("地点");
        placeLabel.setBounds(303, 32, 72, 18);
        notePanel.add(placeLabel);

        placeField = new JTextField();
        placeField.setBounds(381, 29, 137, 24);
        notePanel.add(placeField);
        placeField.setColumns(10);

        JLabel remarkLabel = new JLabel("备注");
        remarkLabel.setBounds(24, 110, 61, 18);
        notePanel.add(remarkLabel);

        remarkTextArea = new JTextArea();
        remarkTextArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        remarkTextArea.setBounds(99, 108, 675, 63);
        notePanel.add(remarkTextArea);
        
        JLabel fileNameLabel = new JLabel("文件名");
        fileNameLabel.setBounds(575, 32, 72, 18);
        notePanel.add(fileNameLabel);
        
        fileNameField = new JTextField();
        fileNameField.setColumns(10);
        fileNameField.setBounds(637, 29, 177, 24);
        notePanel.add(fileNameField);
        
        //自定义组件 处理民警多选框  
        /*List<String> policeList = new ArrayList<>();
        Object[] defaultValue = new Object[] {};

        CaseService caseService = new CaseService();
        List<Police> polices = caseService.listPolice();
        for (int i = 0; i < polices.size(); i++) {
            policeList.add(polices.get(i).getId() + "_" + polices.get(i).getName());
        }
        defaultValue = new String[] {"请选择"};
        JLabel lblNewLabel = new JLabel("民警");
        lblNewLabel.setBounds(590, 72, 30, 18);
        notePanel.add(lblNewLabel);
        
        mulit = new MulitCombobox(policeList.toArray(new String[policeList.size()]), defaultValue);
        mulit.setBounds(637, 65, 177, 28);
        mulit.arrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        notePanel.add(mulit);*/
        
        JButton noteSaveButton = new JButton("保存");
        noteSaveButton.setBounds(818, 144, 113, 27);
        notePanel.add(noteSaveButton);
        
        noteSaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String noteName = noteNameField.getText();
                String place = placeField.getText();
                String startTimeStr = startTime.getText();
                String endTimeStr = endTime.getText();
                String fileName = fileNameField.getText();
                String remark = remarkTextArea.getText();
                // 警员
                /*Object[] objs = mulit.getSelectedValues();
                StringBuilder sb = new StringBuilder();
                for (Object obj : objs) {
                	String policeId = obj.toString().split("_")[0];
                    sb.append(policeId+",");
                }*/
                CaseService caseService = new CaseService();
                ResultDTO resultDTO = new ResultDTO();
                if (noteId == 0) {
                    // 新增笔录信息
                    resultDTO = caseService.addNote(caseId, noteName, startTimeStr, endTimeStr, remark, place,
                            fileName, "");
                } else {
                    Note note = new Note(noteId, caseId, noteName, startTimeStr, endTimeStr, remark, place, fileName, "");
                    resultDTO = caseService.updateNote(note);
                }
                if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                    MainFrame.alert(resultDTO.getMessage());
                    return;
                }
                /*// 保存被询问人
                noteId = Integer.parseInt(String.valueOf(addNoteResult.getData()));
                ResultDTO addAskedPerson = caseService.addAskedPerson(noteId, askedName, String.valueOf(askedSex),
                        null, null, idCard, null);
                if (CommonConstant.RESULT_CODE_FAIL.equals(addAskedPerson.getCode())) {
                    MainFrame.alert(addAskedPerson.getMessage());
                    return;
                }
                // 保存其他人员
                ResultDTO addOtherPerson = caseService.addOtherPerson(noteId, null, String.valueOf(null),
                        null, null);
                ;
                
                if (CommonConstant.RESULT_CODE_FAIL.equals(addOtherPerson.getCode())) {
                    MainFrame.alert(addOtherPerson.getMessage());
                    return;
                }*/
                MainFrame.alert("保存成功");
                ViewCasePanel.getInstance().updateNoteTable();
            }
        });
        
        /*mulit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] v = mulit.getSelectedValues();
                StringBuilder builder = new StringBuilder();
                for (Object dv : v) {

                    builder.append(dv);
                    builder.append("--");
                }
            }
        });*/

        JScrollPane policePanel = new JScrollPane();
        policePanel.setLocation(14, 220);
        policePanel.setSize(972, 124);
        add(policePanel);
        
        Border policeTitleBorder, policeLineBorder;
        policeLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        policeTitleBorder = BorderFactory.createTitledBorder(policeLineBorder, "民警信息", TitledBorder.LEFT,
                TitledBorder.CENTER);
        policePanel.setBorder(policeTitleBorder);
        
        askedPersonTableModel = new AskedPersonTableModel();
        //askedPersonTableModel.setList(noteId);
        askedPersonTable = new JTable(askedPersonTableModel);
        askedPersonTable.setRowHeight(30);
        JTableHeader head = askedPersonTable.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
        policePanel.setViewportView(askedPersonTable);
        
        askedTypeListener = new NoteAskedTypeListener();

        JButton askedAddButton = new JButton("新增");
        askedAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AskedPersonDialog askedPersonDialog = AskedPersonDialog.getInstance();
                askedPersonDialog.nameField.setText("");
                askedPersonDialog.sexComboBox.setSelectedIndex(0);
                askedPersonDialog.idCardField.setText("");
                askedPersonDialog.askedTypeGroup.getElements().nextElement().setSelected(true);
                askedPersonDialog.askedAdultTypeGroup.getElements().nextElement().setSelected(true);
                askedPersonDialog.askedAbleTypeGroup.getElements().nextElement().setSelected(true);
                askedPersonDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(askedPersonDialog);
                askedPersonDialog.setVisible(true);
            }
        });
        askedAddButton.setBounds(263, 357, 113, 27);
        add(askedAddButton);
        
        JButton askedEditButton = new JButton("编辑");
        askedEditButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AskedPersonDialog askedPersonDialog = AskedPersonDialog.getInstance();
                askedPersonDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(askedPersonDialog);
                int i = askedPersonTable.getSelectedRow();
                askedPersonDialog.setAskedPersonId(Integer.parseInt(askedPersonTableModel.getValueAt(i, 0) + ""));
                askedPersonDialog.nameField.setText(askedPersonTableModel.getValueAt(i, 1) + "");
                String sex = askedPersonTableModel.getValueAt(i, 2) + "";
                askedPersonDialog.sexComboBox.setSelectedIndex(sex.equals("男") ? 0 : 1);
                askedPersonDialog.idCardField.setText(askedPersonTableModel.getValueAt(i, 3) + "");
                //回显radioButton
                Enumeration<AbstractButton> radioBtns = askedPersonDialog.askedTypeGroup.getElements();  
                while (radioBtns.hasMoreElements()) {  
                    AbstractButton btn = radioBtns.nextElement();  
                    if(btn.getActionCommand().equals(askedPersonTableModel.getValueAt(i, 4) + "")){  
                        btn.setSelected(true);;
                        break;  
                    }  
                } 
                Enumeration<AbstractButton> adultRadioBtns = askedPersonDialog.askedAdultTypeGroup.getElements();  
                while (adultRadioBtns.hasMoreElements()) {  
                    AbstractButton btn = adultRadioBtns.nextElement();  
                    if(btn.getActionCommand().equals(askedPersonTableModel.getValueAt(i, 5) + "")){  
                        btn.setSelected(true);;
                        break;  
                    }  
                } 
                Enumeration<AbstractButton> ableRadioBtns = askedPersonDialog.askedAbleTypeGroup.getElements();  
                while (ableRadioBtns.hasMoreElements()) {  
                    AbstractButton btn = ableRadioBtns.nextElement();  
                    if(btn.getActionCommand().equals(askedPersonTableModel.getValueAt(i, 6) + "")){  
                        btn.setSelected(true);;
                        break;  
                    }  
                } 
                askedPersonDialog.setVisible(true);
            }
        });
        askedEditButton.setBounds(387, 357, 113, 27);
        add(askedEditButton);
        
        JButton askedDeleteButton = new JButton("删除");
        askedDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.prompt("确定删除该被询问人吗？")){
                    int i = askedPersonTable.getSelectedRow();
                    int askedId = Integer.parseInt(askedPersonTableModel.getValueAt(i, 0) + "");
                    CaseService caseService = new CaseService();
                    ResultDTO resultDTO = caseService.delAskedPerson(askedId);
                    if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                        MainFrame.alert(resultDTO.getMessage());
                        return;
                    }
                    MainFrame.alert("删除成功");
                }
                instance.updateAskedTable();
            }
        });
        askedDeleteButton.setBounds(514, 357, 113, 27);
        add(askedDeleteButton);

        JScrollPane otherPanel = new JScrollPane();
        otherPanel.setBounds(14, 403, 972, 124);
        add(otherPanel);
        
        Border otherTitleBorder, otherLineBorder;
        otherLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        otherTitleBorder = BorderFactory.createTitledBorder(otherLineBorder, "其他人信息", TitledBorder.LEFT,
                TitledBorder.CENTER);
        otherPanel.setBorder(otherTitleBorder);
        
        otherPersonTableModel = new OtherPersonTableModel();
        otherPersonTableModel.setList(2);
        otherPersonTable = new JTable(otherPersonTableModel);
        otherPersonTable.setRowHeight(30);
        head.setPreferredSize(new Dimension(otherPersonTable.getTableHeader().getWidth(), 30));
        
        /*TableColumn column = otherPersonTable.getColumnModel().getColumn(2);
        column.setCellEditor(new ComboBoxEditor(values));*/
        
        otherPanel.setViewportView(otherPersonTable);

        JButton otherAddButton = new JButton("新增");
        otherAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OtherPersonDialog otherPersonDialog = OtherPersonDialog.getInstance();
                otherPersonDialog.nameField.setText("");
                otherPersonDialog.sexComboBox.setSelectedIndex(0);
                otherPersonDialog.idCardField.setText("");
                otherPersonDialog.otherTypeGroup.getElements().nextElement().setSelected(true);
                otherPersonDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(otherPersonDialog);
                otherPersonDialog.setVisible(true);
            }
        });
        otherAddButton.setBounds(263, 540, 113, 27);
        add(otherAddButton);
        
        JButton otherEditButton = new JButton("编辑");
        otherEditButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OtherPersonDialog otherPersonDialog = OtherPersonDialog.getInstance();
                otherPersonDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(otherPersonDialog);
                int i = otherPersonTable.getSelectedRow();
                otherPersonDialog.setOtherPersonId(Integer.parseInt(otherPersonTableModel.getValueAt(i, 0) + ""));
                otherPersonDialog.nameField.setText(otherPersonTableModel.getValueAt(i, 1) + "");
                String sex = otherPersonTableModel.getValueAt(i, 2) + "";
                otherPersonDialog.sexComboBox.setSelectedIndex(sex.equals("男") ? 0 : 1);
                otherPersonDialog.idCardField.setText(otherPersonTableModel.getValueAt(i, 3) + "");
                //回显radioButton
                Enumeration<AbstractButton> radioBtns = otherPersonDialog.otherTypeGroup.getElements();  
                while (radioBtns.hasMoreElements()) {  
                    AbstractButton btn = radioBtns.nextElement();  
                    if(btn.getActionCommand().equals(otherPersonTableModel.getValueAt(i, 4) + "")){  
                        btn.setSelected(true);;
                        break;  
                    }  
                } 
                otherPersonDialog.setVisible(true);
            }
        });
        otherEditButton.setBounds(387, 540, 113, 27);
        add(otherEditButton);
        
        JButton otherDeleteButton = new JButton("删除");
        otherDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.prompt("确定删除该其他类型人员吗？")){
                    int i = otherPersonTable.getSelectedRow();
                    int otherId = Integer.parseInt(otherPersonTableModel.getValueAt(i, 0) + "");
                    CaseService caseService = new CaseService();
                    ResultDTO resultDTO = caseService.delOtherPerson(otherId);
                    if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                        MainFrame.alert(resultDTO.getMessage());
                        return;
                    }
                    MainFrame.alert("删除成功");
                }
                instance.updateOtherTable();
            }
        });
        otherDeleteButton.setBounds(514, 540, 113, 27);
        add(otherDeleteButton);
        
        JPanel askedPanel = new JPanel();
        askedPanel.setBounds(14, 579, 972, 188);
        add(askedPanel);
        askedPanel.setLayout(null);
        Border askedTitleBorder, asekdLineBorder;
        asekdLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        askedTitleBorder = BorderFactory.createTitledBorder(asekdLineBorder, "被询问人信息", TitledBorder.LEFT,
                TitledBorder.CENTER);
        askedPanel.setBorder(askedTitleBorder);
        
        JLabel label = new JLabel("姓名：");
        label.setBounds(31, 27, 54, 19);
        askedPanel.add(label);
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(82, 27, 156, 21);
        askedPanel.add(textField);
        
        JLabel label_1 = new JLabel("性别：");
        label_1.setBounds(284, 27, 54, 19);
        askedPanel.add(label_1);
        
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setBounds(342, 26, 121, 21);
        askedPanel.add(comboBox);
        
        JButton askedSaveButton = new JButton("保存");
        askedSaveButton.setBounds(831, 146, 113, 27);
        askedPanel.add(askedSaveButton);
        
        JLabel label_2 = new JLabel("身份证号：");
        label_2.setBounds(561, 24, 77, 22);
        askedPanel.add(label_2);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(637, 24, 177, 22);
        askedPanel.add(textField_1);
        
        JLabel label_3 = new JLabel("被询问人类型");
        label_3.setBounds(31, 59, 90, 15);
        askedPanel.add(label_3);
        
        JLabel label_4 = new JLabel("是否成人");
        label_4.setBounds(31, 101, 90, 21);
        askedPanel.add(label_4);
        
        JLabel label_5 = new JLabel("是否健全");
        label_5.setBounds(31, 156, 95, 17);
        askedPanel.add(label_5);
        
        JRadioButton radioButton = new JRadioButton("健全");
        radioButton.setActionCommand("1");
        radioButton.setBounds(139, 156, 90, 23);
        askedPanel.add(radioButton);
        
        JRadioButton radioButton_1 = new JRadioButton("成年");
        radioButton_1.setActionCommand("1");
        radioButton_1.setBounds(139, 101, 66, 23);
        askedPanel.add(radioButton_1);
        
        JRadioButton radioButton_2 = new JRadioButton("被害人");
        radioButton_2.setActionCommand("1");
        radioButton_2.setBounds(139, 55, 83, 23);
        askedPanel.add(radioButton_2);
        
        JRadioButton radioButton_3 = new JRadioButton("嫌疑人");
        radioButton_3.setActionCommand("2");
        radioButton_3.setBounds(244, 55, 83, 23);
        askedPanel.add(radioButton_3);
        
        JRadioButton radioButton_4 = new JRadioButton("证人");
        radioButton_4.setActionCommand("3");
        radioButton_4.setBounds(333, 55, 77, 23);
        askedPanel.add(radioButton_4);
        
        JRadioButton radioButton_5 = new JRadioButton("未成年");
        radioButton_5.setActionCommand("2");
        radioButton_5.setBounds(216, 101, 90, 23);
        askedPanel.add(radioButton_5);
        
        JRadioButton radioButton_6 = new JRadioButton("非健全");
        radioButton_6.setActionCommand("2");
        radioButton_6.setBounds(231, 156, 121, 23);
        askedPanel.add(radioButton_6);
    }

    public void updateAskedTable() {
        askedPersonTableModel.setList(noteId);
        askedPersonTable.updateUI();
    }
    
    public void updateOtherTable() {
        otherPersonTableModel.setList(noteId);
        otherPersonTable.updateUI();
    }
    
    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public JTextField getNoteNameField() {
        return noteNameField;
    }

    public void setNoteNameField(JTextField noteNameField) {
        this.noteNameField = noteNameField;
    }

    public DatePicker getStartTime() {
        return startTime;
    }

    public void setStartTime(DatePicker startTime) {
        this.startTime = startTime;
    }

    public DatePicker getEndTime() {
        return endTime;
    }

    public void setEndTime(DatePicker endTime) {
        this.endTime = endTime;
    }

    public JTextField getPlaceField() {
        return placeField;
    }

    public void setPlaceField(JTextField placeField) {
        this.placeField = placeField;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public JTextArea getRemarkTextArea() {
        return remarkTextArea;
    }

    public void setRemarkTextArea(JTextArea remarkTextArea) {
        this.remarkTextArea = remarkTextArea;
    }

    public JTextField getFileNameField() {
        return fileNameField;
    }

    public void setFileNameField(JTextField fileNameField) {
        this.fileNameField = fileNameField;
    }
}
