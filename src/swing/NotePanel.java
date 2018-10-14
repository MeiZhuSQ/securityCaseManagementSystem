package swing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class NotePanel extends JPanel {
	private JTextField askedNameField;
	private JTextField askedIdCardField;
	private JTextField otherNameField;
	private JTextField otherIDField;

    /**
     * Create the panel.
     */
    public NotePanel() {
    	setLayout(null);
    	
    	JPanel askedPanel = new JPanel();
    	askedPanel.setBounds(0, 0, 747, 155);
    	add(askedPanel);
    	Border askedTitleBorder,askedLineBorder;
    	askedLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
    	askedTitleBorder = BorderFactory
                .createTitledBorder(askedLineBorder 
                       , "被询问人" , TitledBorder.LEFT 
                       , TitledBorder.CENTER );
        askedPanel.setBorder(new TitledBorder(new LineBorder(new Color(64, 64, 64)), "\u88AB\u8BE2\u95EE\u4EBA", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        askedPanel.setLayout(null);
        
        JLabel nameLabel = new JLabel("姓名：");
        nameLabel.setBounds(24, 25, 36, 15);
        askedPanel.add(nameLabel);
        
        askedNameField = new JTextField();
        askedNameField.setBounds(58, 22, 156, 21);
        askedPanel.add(askedNameField);
        askedNameField.setColumns(10);
        
        JLabel sexLabel = new JLabel("性别：");
        sexLabel.setBounds(277, 25, 54, 15);
        askedPanel.add(sexLabel);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(313, 22, 66, 21);
        askedPanel.add(comboBox);
        
        JLabel askedIdCardLabel = new JLabel("身份证号：");
        askedIdCardLabel.setBounds(439, 25, 66, 15);
        askedPanel.add(askedIdCardLabel);
        
        askedIdCardField = new JTextField();
        askedIdCardField.setBounds(495, 22, 168, 21);
        askedPanel.add(askedIdCardField);
        askedIdCardField.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("被询问人类型");
        lblNewLabel_1.setBounds(24, 60, 77, 15);
        askedPanel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("是否成人");
        lblNewLabel_2.setBounds(24, 102, 54, 15);
        askedPanel.add(lblNewLabel_2);
        
        JRadioButton rdbtnNewRadioButton = new JRadioButton("成年");
        rdbtnNewRadioButton.setBounds(75, 98, 49, 23);
        askedPanel.add(rdbtnNewRadioButton);
        
        JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("未成年");
        rdbtnNewRadioButton_1.setBounds(121, 98, 61, 23);
        askedPanel.add(rdbtnNewRadioButton_1);
        
        JLabel lblNewLabel_3 = new JLabel("是否健全");
        lblNewLabel_3.setBounds(198, 102, 54, 15);
        askedPanel.add(lblNewLabel_3);
        
        JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("健全");
        rdbtnNewRadioButton_2.setBounds(258, 98, 54, 23);
        askedPanel.add(rdbtnNewRadioButton_2);
        
        JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("非健全");
        rdbtnNewRadioButton_3.setBounds(314, 98, 121, 23);
        askedPanel.add(rdbtnNewRadioButton_3);
        
        JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("被害人");
        rdbtnNewRadioButton_4.setBounds(115, 56, 71, 23);
        askedPanel.add(rdbtnNewRadioButton_4);
        
        JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("嫌疑人");
        rdbtnNewRadioButton_5.setBounds(188, 56, 66, 23);
        askedPanel.add(rdbtnNewRadioButton_5);
        
        JRadioButton rdbtnNewRadioButton_6 = new JRadioButton("证人");
        rdbtnNewRadioButton_6.setBounds(266, 56, 77, 23);
        askedPanel.add(rdbtnNewRadioButton_6);
        
        JPanel otherPanel = new JPanel();
        otherPanel.setBounds(0, 154, 747, 131);
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
    	otherNamelabel.setBounds(23, 28, 36, 15);
    	otherPanel.add(otherNamelabel);
    	
    	otherNameField = new JTextField();
    	otherNameField.setColumns(10);
    	otherNameField.setBounds(57, 25, 156, 21);
    	otherPanel.add(otherNameField);
    	
    	JLabel label_1 = new JLabel("性别：");
    	label_1.setBounds(267, 31, 54, 15);
    	otherPanel.add(label_1);
    	
    	JComboBox comboBox_1 = new JComboBox();
    	comboBox_1.setBounds(303, 28, 66, 21);
    	otherPanel.add(comboBox_1);
    	
    	JLabel otherIDlabel = new JLabel("身份证号：");
    	otherIDlabel.setBounds(429, 31, 66, 15);
    	otherPanel.add(otherIDlabel);
    	
    	otherIDField = new JTextField();
    	otherIDField.setColumns(10);
    	otherIDField.setBounds(485, 28, 168, 21);
    	otherPanel.add(otherIDField);
    	
    	JLabel otherTypeLabel = new JLabel("其他人员类型");
    	otherTypeLabel.setBounds(22, 56, 77, 15);
    	otherPanel.add(otherTypeLabel);
    	
    	JRadioButton radioButton = new JRadioButton("监护人");
    	radioButton.setBounds(113, 52, 71, 23);
    	otherPanel.add(radioButton);
    	
    	JRadioButton radioButton_1 = new JRadioButton("翻译");
    	radioButton_1.setBounds(186, 52, 66, 23);
    	otherPanel.add(radioButton_1);
    	
    	JRadioButton radioButton_2 = new JRadioButton("其他人员");
    	radioButton_2.setBounds(256, 52, 77, 23);
    	otherPanel.add(radioButton_2);
    }
}
