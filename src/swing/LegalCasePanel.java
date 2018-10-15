package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import com.eltima.components.ui.DatePicker;
import service.CaseService;
import util.DateUtil;

public class LegalCasePanel extends JPanel {
    
    private static final long serialVersionUID = 7334759086622449699L;
    private JTextField caseCodeField;
    private JTextField caseNameField;
    private DatePicker datePickerField;

    public LegalCasePanel() {
        setLayout(null);
        
        JLabel caseCode = new JLabel("案件编号");
        caseCode.setBounds(76, 99, 72, 18);
        add(caseCode);
        
        caseCodeField = new JTextField();
        caseCodeField.setBounds(162, 96, 181, 24);
        add(caseCodeField);
        caseCodeField.setColumns(10);
        
        JLabel caseName = new JLabel("案件名称");
        caseName.setBounds(76, 149, 72, 18);
        add(caseName);
        
        caseNameField = new JTextField();
        caseNameField.setBounds(162, 146, 181, 24);
        add(caseNameField);
        caseNameField.setColumns(10);
        
        JButton submitButton = new JButton("保存");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String caseCode = caseCodeField.getText();
                if (StringUtils.isBlank(caseCode)) {
                    MainFrame.alert("请填写案件编号");
                    return;
                }
                String caseName = caseNameField.getText();
                if (StringUtils.isBlank(caseName)) {
                    MainFrame.alert("请填写案件名称");
                    return;
                }
                String date = datePickerField.getText();
                CaseService caseService = new CaseService();
                try {
                    caseService.addCase(caseName, date, "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        submitButton.setBounds(162, 241, 113, 27);
        add(submitButton);        
        
        JLabel lblNewLabel = new JLabel("时间");
        lblNewLabel.setBounds(76, 191, 72, 18);
        add(lblNewLabel);

        datePickerField = DateUtil.getDatePicker();
        datePickerField.setBounds(162, 196, 181, 24);
        add(datePickerField);

    }
    

}
