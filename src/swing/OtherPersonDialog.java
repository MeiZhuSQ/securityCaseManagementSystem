package swing;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class OtherPersonDialog extends JDialog {
    public OtherPersonDialog() {
        getContentPane().setLayout(null);
        
        JLabel label = new JLabel("姓名：");
        label.setBounds(39, 74, 59, 21);
        getContentPane().add(label);
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(89, 76, 156, 21);
        getContentPane().add(textField);
        
        JLabel label_1 = new JLabel("性别：");
        label_1.setBounds(295, 74, 54, 21);
        getContentPane().add(label_1);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(343, 77, 66, 21);
        getContentPane().add(comboBox);
        
        JLabel label_2 = new JLabel("身份证号：");
        label_2.setBounds(39, 125, 85, 19);
        getContentPane().add(label_2);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(124, 125, 244, 21);
        getContentPane().add(textField_1);
        
        JLabel label_3 = new JLabel("其他人员类型");
        label_3.setBounds(39, 183, 108, 21);
        getContentPane().add(label_3);
        
        JRadioButton radioButton = new JRadioButton("监护人");
        radioButton.setActionCommand("1");
        radioButton.setBounds(157, 182, 88, 23);
        getContentPane().add(radioButton);
        
        JRadioButton radioButton_1 = new JRadioButton("翻译");
        radioButton_1.setActionCommand("2");
        radioButton_1.setBounds(251, 182, 66, 23);
        getContentPane().add(radioButton_1);
        
        JRadioButton radioButton_2 = new JRadioButton("其他人员");
        radioButton_2.setActionCommand("3");
        radioButton_2.setBounds(332, 182, 97, 23);
        getContentPane().add(radioButton_2);
    }

    private static final long serialVersionUID = -5898195535936731794L;
    private JTextField textField;
    private JTextField textField_1;

}
