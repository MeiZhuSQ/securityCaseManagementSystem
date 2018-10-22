package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import com.eltima.components.ui.DatePicker;

import constant.CommonConstant;
import dto.ResultDTO;
import service.CaseService;
import util.DateUtil;

/**
 * 案件面板
 * @author LiuPF
 * @date 2018-10-15
 */
public class CaseDialog extends JDialog {
    
    private static final long serialVersionUID = 7334759086622449699L;
    private JTextField caseNameField;
    private DatePicker datePickerField;
    private static CaseDialog instance;
    private JTextField remarkField;
    
    public static CaseDialog getInstance () {
        if (instance == null) {
            instance = new CaseDialog();
        }
        return instance;
    }
    
    public CaseDialog() {
        getContentPane().setLayout(null);
        
        JLabel caseName = new JLabel("案件名称");
        caseName.setBounds(81, 49, 72, 18);
        getContentPane().add(caseName);
        
        caseNameField = new JTextField();
        caseNameField.setBounds(167, 46, 208, 24);
        getContentPane().add(caseNameField);
        caseNameField.setColumns(10);
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String caseName = caseNameField.getText();
                String date = datePickerField.getText();
                String remark = remarkField.getText();
                CaseService caseService = new CaseService();
                try {
                    ResultDTO addCase = caseService.addCase(caseName, date, remark);
                    if (CommonConstant.RESULT_CODE_FAIL.equals(addCase.getCode())) {
                        MainFrame.alert(addCase.getMessage());
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    MainFrame.alert("保存失败");
                }
            }
        });
        saveButton.setBounds(90, 254, 113, 27);
        getContentPane().add(saveButton);        
        
        JLabel lblNewLabel = new JLabel("时间");
        lblNewLabel.setBounds(81, 90, 72, 18);
        getContentPane().add(lblNewLabel);

        datePickerField = DateUtil.getDatePicker();
        datePickerField.setBounds(167, 90, 181, 24);
        getContentPane().add(datePickerField);
        
        JLabel label = new JLabel("备注");
        label.setBounds(81, 136, 72, 18);
        getContentPane().add(label);
        
        remarkField = new JTextField();
        remarkField.setBounds(167, 133, 208, 82);
        getContentPane().add(remarkField);
        remarkField.setColumns(10);
        
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(248, 254, 113, 27);
        getContentPane().add(cancelButton);

    }
}