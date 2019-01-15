package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;

import com.eltima.components.ui.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

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
    //public DatePicker datePickerField;
    public DateTimePicker dateTimePicker;
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
        caseName.setBounds(51, 49, 72, 18);
        getContentPane().add(caseName);
        
        clockNameField = new JTextField();
        clockNameField.setBounds(127, 46, 280, 24);
        getContentPane().add(clockNameField);
        clockNameField.setColumns(10);
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = clockNameField.getText();
                String remark = remarkField.getText();
                CaseService caseService = new CaseService();
                if (StringUtils.isBlank(name)) {
                    MainFrame.alert("请填写案件名称");
                    return;
                }
                LocalDateTime localDateTime = dateTimePicker.getDateTimePermissive();
                if (localDateTime == null) {
                    MainFrame.alert("请填写开始时间");
                    return;
                }
                String clockTime = localDateTime.toString().replace("T", " ") + ":00";
                ResultDTO resultDTO = new ResultDTO();
                //新增
                if (clockId == 0) {
                    resultDTO = caseService.addClock(name, clockTime, remark, ViewCasePanel.getInstance().getCaseId());
                } else {
                    //更新
                    Clock clock = caseService.getClockById(clockId);
                    clock.setName(name);
                    clock.setTime(clockTime);
                    clock.setRemark(remark);
                    resultDTO = caseService.updateClock(clock);
                }
                if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                    MainFrame.alert(resultDTO.getMessage());
                    return;
                }
                MainFrame.alert("保存成功");
                getInstance().setVisible(false);
                ViewCasePanel.getInstance().updateCaseDetailTable();
                MainFrame.getInstance().clockListModel.removeAllElements();
                List<Clock> clocks = new CaseService().getClocksInThreeDaysAndLastDay();
                for (Clock clock : clocks) {
                	MainFrame.getInstance().clockListModel.addElement(clock);
                }
                
            }
        });
        saveButton.setBounds(90, 254, 113, 27);
        getContentPane().add(saveButton);        
        
        JLabel lblNewLabel = new JLabel("闹钟时间");
        lblNewLabel.setBounds(51, 90, 72, 18);
        getContentPane().add(lblNewLabel);

        //datePickerField = DateUtil.getDatePicker(DateUtil.FORMAT_YYYYMMDDHHMMSS);
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
        cancelButton.setBounds(248, 254, 113, 27);
        getContentPane().add(cancelButton);

    }

	public int getClockId() {
		return clockId;
	}

	public void setClockId(int clockId) {
		this.clockId = clockId;
	}

}
