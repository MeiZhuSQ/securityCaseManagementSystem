package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import com.eltima.components.ui.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.LegalCase;
import service.CaseService;
import util.DateUtil;

/**
 * 案件面板
 * @author LiuPF
 * @date 2018-10-15
 */
public class CaseDialog extends JDialog {
    
    private static final long serialVersionUID = 7334759086622449699L;
    public JTextField caseNameField;
    //public static DatePicker datePickerField;
    public DateTimePicker dateTimePicker;
    private static CaseDialog instance;
    public JTextField remarkField;
    private int caseId = 0;
    
    public static CaseDialog getInstance () {
        if (instance == null) {
            instance = new CaseDialog();
        }
        return instance;
    }
    
    public CaseDialog() {
    	setModal(true);
        getContentPane().setLayout(null);
        
        JLabel caseName = new JLabel("案件名称");
        caseName.setBounds(51, 49, 72, 18);
        getContentPane().add(caseName);
        
        caseNameField = new JTextField();
        caseNameField.setBounds(127, 46, 280, 24);
        getContentPane().add(caseNameField);
        caseNameField.setColumns(10);
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String caseName = caseNameField.getText();
                if (StringUtils.isBlank(caseName)) {
                    MainFrame.alert("请填写案件名称");
                    return;
                }
                LocalDateTime localDateTime = dateTimePicker.getDateTimePermissive();
                if (localDateTime == null) {
                    MainFrame.alert("请填写开始时间");
                    return;
                }
                String caseTime = localDateTime.toString().replace("T", " ") + ":00";
                String remark = remarkField.getText();
                CaseService caseService = new CaseService();
                ResultDTO resultDTO = new ResultDTO();
                //新增
                if (caseId == 0) {
                    resultDTO = caseService.addCase(caseName, caseTime, remark);
                } else {
                    //更新
                    LegalCase legalCase = caseService.selectCaseById(caseId);
                    legalCase.setName(caseName);
                    legalCase.setTime(caseTime);
                    legalCase.setRemark(remark);
                    resultDTO = caseService.updateCase(legalCase);
                }
                if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                    MainFrame.alert(resultDTO.getMessage());
                    return;
                }
                MainFrame.alert("保存成功");
                getInstance().setVisible(false);
                MainFrame.getInstance().updateCaseTable();
            }
        });
        saveButton.setBounds(100, 254, 113, 27);
        getContentPane().add(saveButton);        
        
        JLabel lblNewLabel = new JLabel("时间");
        lblNewLabel.setBounds(51, 90, 72, 18);
        getContentPane().add(lblNewLabel);

        DatePickerSettings dateSettings = new DatePickerSettings();
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setDisplaySpinnerButtons(true);
        timeSettings.use24HourClockFormat();
        dateTimePicker = new DateTimePicker(dateSettings, timeSettings);
        dateTimePicker.setBounds(127, 90, 280, 24);
        getContentPane().add(dateTimePicker);
        
        JLabel label = new JLabel("备注");
        label.setBounds(51, 136, 72, 18);
        getContentPane().add(label);
        
        remarkField = new JTextField();
        remarkField.setBounds(127, 133, 280, 82);
        getContentPane().add(remarkField);
        remarkField.setColumns(10);
        
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getInstance().setVisible(false);
            }
        });
        cancelButton.setBounds(300, 254, 113, 27);
        getContentPane().add(cancelButton);

    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }
    
}
