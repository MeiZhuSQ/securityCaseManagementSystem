package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.eltima.components.ui.DatePicker;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.Clock;
import entity.LegalCase;
import service.CaseService;
import util.DateUtil;

/**
 * 案件面板
 * @author LiuPF
 * @date 2018-10-15
 */
public class ClockDialog extends JDialog {
    
    private static final long serialVersionUID = 7334759086622449699L;
    public JTextField clockNameField;
    public DatePicker datePickerField;
    private static ClockDialog instance;
    public JTextField remarkField;
    private int clockId = 0;
    
    public static ClockDialog getInstance () {
        if (instance == null) {
            instance = new ClockDialog();
        }
        return instance;
    }
    
    public ClockDialog() {
    	setModal(true);
        getContentPane().setLayout(null);
        
        JLabel caseName = new JLabel("闹钟名称");
        caseName.setBounds(81, 49, 72, 18);
        getContentPane().add(caseName);
        
        clockNameField = new JTextField();
        clockNameField.setBounds(167, 46, 208, 24);
        getContentPane().add(clockNameField);
        clockNameField.setColumns(10);
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = clockNameField.getText();
                String time = datePickerField.getText();
                String remark = remarkField.getText();
                CaseService caseService = new CaseService();
                ResultDTO resultDTO = new ResultDTO();
                //新增
                if (clockId == 0) {
                    resultDTO = caseService.addClock(name, time, remark);
                } else {
                    //更新
                    Clock clock = caseService.getClockById(clockId);
                    clock.setName(name);
                    clock.setTime(time);
                    clock.setRemark(remark);
                    resultDTO = caseService.updateClock(clock);
                }
                if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                    MainFrame.alert(resultDTO.getMessage());
                }
                MainFrame.alert("保存成功");
                getInstance().setVisible(false);
                MainFrame.getInstance().updateCaseTable();
            }
        });
        saveButton.setBounds(90, 254, 113, 27);
        getContentPane().add(saveButton);        
        
        JLabel lblNewLabel = new JLabel("闹钟时间");
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
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.frame.setEnabled(true);
                getInstance().setVisible(false);
            }
        });
        cancelButton.setBounds(248, 254, 113, 27);
        getContentPane().add(cancelButton);

    }

    public int getCaseId() {
        return clockId;
    }

    public void setCaseId(int caseId) {
        this.clockId = caseId;
    }
    
}
