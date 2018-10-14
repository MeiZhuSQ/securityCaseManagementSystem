package swing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class NotePanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

    /**
     * Create the panel.
     */
    public NotePanel() {
    	setLayout(null);
    	
    	JPanel askedPanel = new JPanel();
    	askedPanel.setBounds(0, 0, 747, 107);
    	add(askedPanel);
    	Border caseTitleBorder,caseLineBorder;
        caseLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        caseTitleBorder = BorderFactory
                .createTitledBorder(caseLineBorder 
                       , "被询问人" , TitledBorder.LEFT 
                       , TitledBorder.CENTER );
        askedPanel.setBorder(caseTitleBorder);
        askedPanel.setLayout(null);
        
        JLabel nameLabel = new JLabel("姓名：");
        nameLabel.setBounds(24, 25, 54, 15);
        askedPanel.add(nameLabel);
        
        textField = new JTextField();
        textField.setBounds(58, 22, 103, 21);
        askedPanel.add(textField);
        textField.setColumns(10);
        
        JLabel sexLabel = new JLabel("性别：");
        sexLabel.setBounds(204, 25, 54, 15);
        askedPanel.add(sexLabel);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(239, 22, 44, 21);
        askedPanel.add(comboBox);
        
        JLabel lblNewLabel = new JLabel("身份证号：");
        lblNewLabel.setBounds(319, 25, 66, 15);
        askedPanel.add(lblNewLabel);
        
        textField_1 = new JTextField();
        textField_1.setBounds(386, 22, 168, 21);
        askedPanel.add(textField_1);
        textField_1.setColumns(10);
    }
}
