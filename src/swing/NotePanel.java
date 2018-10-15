package swing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;

import com.eltima.components.ui.DatePicker;

import util.DateUtil;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class NotePanel extends JPanel {
	private JTextField askedNameField;
	private JTextField askedIdCardField;
	private JTextField otherNameField;
	private JTextField otherIDField;
	private JTextField noteNameField;
    private DatePicker startTime;
    private DatePicker endTime;
    private JTextField placeField;
    private JTextField policeCodeField;

    /**
     * Create the panel.
     */
    public NotePanel() {
    	setLayout(null);
    	
    	JPanel notePanel = new JPanel();
        notePanel.setBounds(14, 13, 807, 175);
        add(notePanel);
        Border noteTitleBorder,noteLineBorder;
        noteLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        noteTitleBorder = BorderFactory
                .createTitledBorder(noteLineBorder 
                       , "笔录基本信息" , TitledBorder.LEFT 
                       , TitledBorder.CENTER );
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
        remarkLabel.setBounds(24, 94, 61, 18);
        notePanel.add(remarkLabel);
        
        JTextArea remarkTextArea = new JTextArea();
        remarkTextArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        remarkTextArea.setBounds(99, 92, 462, 63);
        notePanel.add(remarkTextArea);
        
    	JPanel askedPanel = new JPanel();
    	askedPanel.setBounds(14, 223, 733, 155);
    	add(askedPanel);
    	Border askedTitleBorder,askedLineBorder;
    	askedLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
    	askedTitleBorder = BorderFactory
                .createTitledBorder(askedLineBorder 
                       , "被询问人" , TitledBorder.LEFT 
                       , TitledBorder.CENTER );
        askedPanel.setBorder(askedTitleBorder);
        askedPanel.setLayout(null);
        
        JLabel nameLabel = new JLabel("姓名：");
        nameLabel.setBounds(24, 21, 54, 19);
        askedPanel.add(nameLabel);
        
        askedNameField = new JTextField();
        askedNameField.setBounds(75, 21, 156, 21);
        askedPanel.add(askedNameField);
        askedNameField.setColumns(10);
        
        JLabel askedSexLabel = new JLabel("性别：");
        askedSexLabel.setBounds(277, 21, 54, 19);
        askedPanel.add(askedSexLabel);
        
        JComboBox askedSexComboBox = new JComboBox();
        askedSexComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
             // 只处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("选中: " + askedSexComboBox.getSelectedIndex() + " = " + askedSexComboBox.getSelectedItem());
                }
            }
        });
        askedSexComboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女"}));
        askedSexComboBox.setBounds(326, 22, 66, 21);
        askedPanel.add(askedSexComboBox);
        
        JLabel askedIdCardLabel = new JLabel("身份证号：");
        askedIdCardLabel.setBounds(428, 21, 77, 19);
        askedPanel.add(askedIdCardLabel);
        
        askedIdCardField = new JTextField();
        askedIdCardField.setBounds(504, 21, 168, 21);
        askedPanel.add(askedIdCardField);
        askedIdCardField.setColumns(10);
        
        JLabel askedTypeLabel = new JLabel("被询问人类型");
        askedTypeLabel.setBounds(24, 60, 90, 15);
        askedPanel.add(askedTypeLabel);
        
        JLabel askedAdultLabel = new JLabel("是否成人");
        askedAdultLabel.setBounds(24, 102, 90, 21);
        askedPanel.add(askedAdultLabel);
        
        AskedAdultListener askedAdultListener = new AskedAdultListener();
        JRadioButton CnRadioButton = new JRadioButton("成年");
        CnRadioButton.addActionListener(askedAdultListener);
        CnRadioButton.setBounds(132, 102, 66, 23);
        askedPanel.add(CnRadioButton);
        
        JRadioButton WcnRadioButton = new JRadioButton("未成年");
        WcnRadioButton.addActionListener(askedAdultListener);
        WcnRadioButton.setBounds(209, 102, 90, 23);
        askedPanel.add(WcnRadioButton);
        
        JLabel askedAbleLabel = new JLabel("是否健全");
        askedAbleLabel.setBounds(326, 106, 95, 17);
        askedPanel.add(askedAbleLabel);
        
        AskedAbleListener askedAbleListener = new AskedAbleListener();
        JRadioButton JqRadioButton = new JRadioButton("健全");
        JqRadioButton.addActionListener(askedAbleListener);
        JqRadioButton.setBounds(391, 102, 90, 23);
        askedPanel.add(JqRadioButton);
        
        JRadioButton FjqRadioButton = new JRadioButton("非健全");
        FjqRadioButton.addActionListener(askedAbleListener);
        FjqRadioButton.setBounds(483, 102, 121, 23);
        askedPanel.add(FjqRadioButton);
        
        AskedTypeListener askedTypeListener = new AskedTypeListener();
        JRadioButton BhrRadioButton = new JRadioButton("被害人");
        BhrRadioButton.addActionListener(askedTypeListener);
        BhrRadioButton.setBounds(132, 56, 83, 23);
        askedPanel.add(BhrRadioButton);
        
        JRadioButton XyrRadioButton = new JRadioButton("嫌疑人");
        XyrRadioButton.addActionListener(askedTypeListener);
        XyrRadioButton.setBounds(237, 56, 83, 23);
        askedPanel.add(XyrRadioButton);
        
        JRadioButton ZrRadioButton = new JRadioButton("证人");
        ZrRadioButton.addActionListener(askedTypeListener);
        ZrRadioButton.setBounds(326, 56, 77, 23);
        askedPanel.add(ZrRadioButton);
        
        ButtonGroup askedAdultTypeGroup = new ButtonGroup();
        askedAdultTypeGroup.add(CnRadioButton);
        askedAdultTypeGroup.add(WcnRadioButton);
        
        ButtonGroup askedAbleTypeGroup = new ButtonGroup();
        askedAbleTypeGroup.add(JqRadioButton);
        askedAbleTypeGroup.add(FjqRadioButton);
        
        ButtonGroup askedTypeGroup = new ButtonGroup();
        askedTypeGroup.add(BhrRadioButton);
        askedTypeGroup.add(XyrRadioButton);
        askedTypeGroup.add(ZrRadioButton);
        
        JPanel otherPanel = new JPanel();
        otherPanel.setBounds(14, 391, 743, 101);
        add(otherPanel);
        Border otherTitleBorder,otherLineBorder;
    	otherLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
    	otherTitleBorder = BorderFactory
                .createTitledBorder(otherLineBorder 
                       , "其他人" , TitledBorder.LEFT 
                       , TitledBorder.CENTER );
    	otherPanel.setBorder(otherTitleBorder);
    	otherPanel.setLayout(null);
    	
    	JLabel otherNamelabel = new JLabel("姓名：");
    	otherNamelabel.setBounds(23, 22, 59, 21);
    	otherPanel.add(otherNamelabel);
    	
    	otherNameField = new JTextField();
    	otherNameField.setColumns(10);
    	otherNameField.setBounds(73, 24, 156, 21);
    	otherPanel.add(otherNameField);
    	
    	JLabel otherSexLabel = new JLabel("性别：");
    	otherSexLabel.setBounds(279, 22, 54, 21);
    	otherPanel.add(otherSexLabel);
    	
    	JComboBox otherSexComboBox = new JComboBox();
    	otherSexComboBox.addItemListener(new ItemListener() {
    	    public void itemStateChanged(ItemEvent e) {
    	        if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("选中: " + otherSexComboBox.getSelectedIndex() + " = " + otherSexComboBox.getSelectedItem());
                }
    	    }
    	});
    	otherSexComboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女"}));
    	otherSexComboBox.setBounds(327, 25, 66, 21);
    	otherPanel.add(otherSexComboBox);
    	
    	JLabel otherIDlabel = new JLabel("身份证号：");
    	otherIDlabel.setBounds(430, 24, 85, 19);
    	otherPanel.add(otherIDlabel);
    	
    	otherIDField = new JTextField();
    	otherIDField.setColumns(10);
    	otherIDField.setBounds(515, 24, 168, 21);
    	otherPanel.add(otherIDField);
    	
    	otherTypeListener otherTypeListener = new otherTypeListener();
    	JLabel otherTypeLabel = new JLabel("其他人员类型");
    	otherTypeLabel.setBounds(23, 68, 108, 21);
    	otherPanel.add(otherTypeLabel);
    	
    	JRadioButton jHRRadioButton = new JRadioButton("监护人");
    	jHRRadioButton.addActionListener(otherTypeListener);
    	jHRRadioButton.setBounds(141, 67, 88, 23);
    	otherPanel.add(jHRRadioButton);
    	
    	JRadioButton fYRadioButton = new JRadioButton("翻译");
    	fYRadioButton.addActionListener(otherTypeListener);
    	fYRadioButton.setBounds(235, 67, 66, 23);
    	otherPanel.add(fYRadioButton);
    	
    	JRadioButton otherPersonRadioButton = new JRadioButton("其他人员");
    	otherPersonRadioButton.addActionListener(otherTypeListener);
    	otherPersonRadioButton.setBounds(316, 67, 97, 23);
    	otherPanel.add(otherPersonRadioButton);
    	
    	ButtonGroup otherTypeGroup = new ButtonGroup();
    	otherTypeGroup.add(jHRRadioButton);
    	otherTypeGroup.add(fYRadioButton);
    	otherTypeGroup.add(otherPersonRadioButton);
    	
    	JPanel policePanel = new JPanel();
    	policePanel.setBounds(14, 505, 758, 71);
    	add(policePanel);
    	Border policeTitleBorder,policeLineBorder;
    	policeLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        policeTitleBorder = BorderFactory
                .createTitledBorder(policeLineBorder 
                       , "民警" , TitledBorder.LEFT 
                       , TitledBorder.CENTER );
        policePanel.setBorder(policeTitleBorder);
        policePanel.setLayout(null);
        
        JLabel policeCodeLabel = new JLabel("警号");
        policeCodeLabel.setBounds(27, 32, 57, 25);
        policePanel.add(policeCodeLabel);
        
        policeCodeField = new JTextField();
        policeCodeField.setBounds(75, 32, 401, 24);
        policePanel.add(policeCodeField);
        policeCodeField.setColumns(10);
        
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String noteName = noteNameField.getText();
                String place = placeField.getText();
                String startTimeStr = startTime.getText();
                String endTimeStr = endTime.getText();
                String remark = remarkTextArea.getText();
                if (StringUtils.isBlank(noteName)) {
                    MainFrame.alert("笔录名称不能为空！");
                }
                if (StringUtils.isBlank(place)) {
                    MainFrame.alert("地点不能为空！");
                }
                if (StringUtils.isBlank(startTimeStr)) {
                    MainFrame.alert("开始时间不能为空！");
                }
                if (StringUtils.isBlank(endTimeStr)) {
                    MainFrame.alert("结束时间不能为空！");
                }
                String askedName = askedNameField.getText();
                int selectedIndex = askedSexComboBox.getSelectedIndex();
                String idCard = askedIdCardField.getText();
                
            }
        });
        saveButton.setBounds(315, 589, 113, 27);
        add(saveButton);
    }
}
