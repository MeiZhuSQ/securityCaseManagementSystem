package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

import com.eltima.components.ui.DatePicker;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.Police;
import service.CaseService;
import util.DateUtil;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class NotePanel extends JPanel {

    private static final long serialVersionUID = 5820832059732422488L;
    private JTextField noteNameField;
    private DatePicker startTime;
    private DatePicker endTime;
    private JTextField placeField;
    private JTextArea remarkTextArea;
    private int caseId;
    private final MulitCombobox mulit;
    private int noteId;
    private int procedureId;
    private JTextField fileNameField;
    public NoteAskedTypeListener askedTypeListener;
    private JTable askedPersonTable;
    private JTable otherPersonTable;
    private AskedPersonTableModel askedPersonTableModel;
    private OtherPersonTableModel otherPersonTableModel;
    
    public NotePanel(int caseId) {
        this.setCaseId(caseId);
        setLayout(null);
        JPanel notePanel = new JPanel();
        notePanel.setBounds(14, 13, 807, 241);
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
        startTimeLabel.setBounds(24, 68, 72, 18);
        notePanel.add(startTimeLabel);

        startTime = DateUtil.getDatePicker();
        JPanel startTimePanel = new JPanel();
        startTimePanel.setBounds(99, 55, 177, 30);
        startTimePanel.add(startTime);
        notePanel.add(startTimePanel);

        JLabel endTimelabel = new JLabel("结束时间");
        endTimelabel.setBounds(303, 65, 72, 18);
        notePanel.add(endTimelabel);

        JPanel endTimePanel = new JPanel();
        endTimePanel.setBounds(374, 55, 177, 34);
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
        remarkLabel.setBounds(25, 133, 61, 18);
        notePanel.add(remarkLabel);

        remarkTextArea = new JTextArea();
        remarkTextArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        remarkTextArea.setBounds(100, 131, 462, 63);
        notePanel.add(remarkTextArea);
        
        JLabel fileNameLabel = new JLabel("文件名");
        fileNameLabel.setBounds(559, 35, 72, 18);
        notePanel.add(fileNameLabel);
        
        fileNameField = new JTextField();
        fileNameField.setColumns(10);
        fileNameField.setBounds(637, 32, 137, 24);
        notePanel.add(fileNameField);
        
        //自定义组件 处理民警多选框  
        List<String> policeList = new ArrayList<>();
        Object[] defaultValue = new Object[] {};

        CaseService caseService = new CaseService();
        List<Police> polices = caseService.listPolice();
        for (int i = 0; i < polices.size(); i++) {
            policeList.add(polices.get(i).getName() + "_" + polices.get(i).getPoliceNumber());
        }
        defaultValue = new String[] {"请选择"};
        JLabel lblNewLabel = new JLabel("民警");
        lblNewLabel.setBounds(24, 99, 30, 18);
        notePanel.add(lblNewLabel);
        
        JPanel multiPanel = new JPanel();
        multiPanel.setBounds(99, 92, 72, 30);
        notePanel.add(multiPanel);
        
        mulit = new MulitCombobox(policeList.toArray(new String[policeList.size()]), defaultValue);
        mulit.arrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        multiPanel.add(mulit);
        
        JButton saveButton = new JButton("保存");
        saveButton.setBounds(311, 201, 113, 27);
        notePanel.add(saveButton);
        
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String noteName = noteNameField.getText();
                String place = placeField.getText();
                String startTimeStr = startTime.getText();
                String endTimeStr = endTime.getText();
                String fileName = fileNameField.getText();
                String remark = remarkTextArea.getText();
                // 被询问人
                String askedName = "";/*askedNameField.getText();*/
                Integer askedSex = 0;/*askedSexComboBox.getSelectedIndex();*/
                String idCard = "";/*askedIdCardField.getText();*/
                /*String selectedAskedType = askedTypeListener.selectedAskedType;
                String selectedAskedAudlt = askedAdultListener.selectedAskedAudlt;
                String selectedAbled = askedAbleListener.selectedAbled;
                // 其他人
                String otherName = "";otherNameField.getText();
                Integer otherSex = 0;otherSexComboBox.getSelectedIndex();
                String otherIdCard = "";otherIDField.getText();
                String selectedOtherType = otherTypeListener.selectedOtherType;*/
                // 警员
                Object[] objs = mulit.getSelectedValues();
                StringBuilder sb = new StringBuilder();
                for (Object obj : objs) {
                	String code = obj.toString().split("_")[1];
                    sb.append(code+",");
                }
                CaseService caseService = new CaseService();
                // 保存笔录信息
                ResultDTO addNoteResult = caseService.addNote(caseId, noteName, startTimeStr, endTimeStr, remark, place,
                        fileName, sb.deleteCharAt(sb.length()-1).toString());
                if (CommonConstant.RESULT_CODE_FAIL.equals(addNoteResult.getCode())) {
                    MainFrame.alert(addNoteResult.getMessage());
                    return;
                }
                // 保存被询问人
                int noteId = Integer.parseInt(String.valueOf(addNoteResult.getData()));
                ResultDTO addAskedPerson = caseService.addAskedPerson(noteId, askedName, String.valueOf(askedSex),
                        null, null, idCard, null);
                if (CommonConstant.RESULT_CODE_FAIL.equals(addAskedPerson.getCode())) {
                    MainFrame.alert(addAskedPerson.getMessage());
                    return;
                }
                // 保存其他人员
                ResultDTO addOtherPerson = caseService.addOtherPerson(noteId, null, String.valueOf(null),
                        null, null);
                if (CommonConstant.RESULT_CODE_FAIL.equals(addOtherPerson.getCode())) {
                    MainFrame.alert(addOtherPerson.getMessage());
                    return;
                }
                MainFrame.alert("保存成功");
            }
        });
        
        mulit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] v = mulit.getSelectedValues();
                StringBuilder builder = new StringBuilder();
                for (Object dv : v) {

                    builder.append(dv);
                    builder.append("--");
                }
            }
        });

        JScrollPane askedPanel = new JScrollPane();
        askedPanel.setLocation(14, 267);
        askedPanel.setSize(807, 124);
        add(askedPanel);
        
        askedPersonTableModel = new AskedPersonTableModel();
        askedPersonTableModel.setList(2);
        askedPersonTable = new JTable(askedPersonTableModel);
        askedPersonTable.setRowHeight(30);
        JTableHeader head = askedPersonTable.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
        askedPanel.setViewportView(askedPersonTable);
        
        NoteAskedAdultListener askedAdultListener = new NoteAskedAdultListener();

        NoteAskedAbleListener askedAbleListener = new NoteAskedAbleListener();

        askedTypeListener = new NoteAskedTypeListener();

        ButtonGroup askedAdultTypeGroup = new ButtonGroup();

        ButtonGroup askedAbleTypeGroup = new ButtonGroup();

        ButtonGroup askedTypeGroup = new ButtonGroup();

        JScrollPane otherPanel = new JScrollPane();
        otherPanel.setBounds(14, 456, 807, 124);
        add(otherPanel);
        
        otherPersonTableModel = new OtherPersonTableModel();
        otherPersonTableModel.setList(noteId);
        otherPersonTable = new JTable(otherPersonTableModel);
        otherPersonTable.setRowHeight(30);
        head.setPreferredSize(new Dimension(otherPersonTable.getTableHeader().getWidth(), 30));
        otherPanel.setViewportView(otherPersonTable);

        OtherTypeListener otherTypeListener = new OtherTypeListener();

        ButtonGroup otherTypeGroup = new ButtonGroup();
        
        JButton btnNewButton = new JButton("New button");
        btnNewButton.setBounds(193, 404, 113, 27);
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("New button");
        btnNewButton_1.setBounds(317, 404, 113, 27);
        add(btnNewButton_1);
        
        JButton button = new JButton("New button");
        button.setBounds(444, 404, 113, 27);
        add(button);
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

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
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
