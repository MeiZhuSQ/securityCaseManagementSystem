package swing;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class AskedPersonDialog extends JDialog {
    public AskedPersonDialog() {
        getContentPane().setLayout(null);
        
        JLabel label = new JLabel("姓名：");
        label.setBounds(59, 61, 54, 19);
        getContentPane().add(label);
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(110, 61, 156, 21);
        getContentPane().add(textField);
        
        JLabel label_1 = new JLabel("性别：");
        label_1.setBounds(312, 61, 54, 19);
        getContentPane().add(label_1);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(361, 62, 66, 21);
        getContentPane().add(comboBox);
        
        JLabel label_2 = new JLabel("身份证号：");
        label_2.setBounds(59, 106, 77, 19);
        getContentPane().add(label_2);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(135, 106, 315, 21);
        getContentPane().add(textField_1);
        
        JLabel label_3 = new JLabel("被询问人类型");
        label_3.setBounds(59, 153, 90, 15);
        getContentPane().add(label_3);
        
        JLabel label_4 = new JLabel("是否成人");
        label_4.setBounds(59, 195, 90, 21);
        getContentPane().add(label_4);
        
        JRadioButton radioButton = new JRadioButton("成年");
        radioButton.setActionCommand("1");
        radioButton.setBounds(167, 195, 66, 23);
        getContentPane().add(radioButton);
        
        JRadioButton radioButton_1 = new JRadioButton("未成年");
        radioButton_1.setActionCommand("2");
        radioButton_1.setBounds(244, 195, 90, 23);
        getContentPane().add(radioButton_1);
        
        JLabel label_5 = new JLabel("是否健全");
        label_5.setBounds(59, 250, 95, 17);
        getContentPane().add(label_5);
        
        JRadioButton radioButton_2 = new JRadioButton("健全");
        radioButton_2.setActionCommand("1");
        radioButton_2.setBounds(167, 250, 90, 23);
        getContentPane().add(radioButton_2);
        
        JRadioButton radioButton_3 = new JRadioButton("非健全");
        radioButton_3.setBounds(259, 250, 121, 23);
        getContentPane().add(radioButton_3);
        
        JRadioButton radioButton_4 = new JRadioButton("被害人");
        radioButton_4.setActionCommand("1");
        radioButton_4.setBounds(167, 149, 83, 23);
        getContentPane().add(radioButton_4);
        
        JRadioButton radioButton_5 = new JRadioButton("嫌疑人");
        radioButton_5.setActionCommand("2");
        radioButton_5.setBounds(272, 149, 83, 23);
        getContentPane().add(radioButton_5);
        
        JRadioButton radioButton_6 = new JRadioButton("证人");
        radioButton_6.setActionCommand("3");
        radioButton_6.setBounds(361, 149, 77, 23);
        getContentPane().add(radioButton_6);
    }

    private static final long serialVersionUID = -5898195535936731794L;
    private JTextField textField;
    private JTextField textField_1;
}
