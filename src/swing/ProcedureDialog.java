package swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.commons.lang.StringUtils;

import com.eltima.components.ui.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.LegalCase;
import entity.Procedure;
import service.CaseService;
import util.DateUtil;

/**
 * 法律程序面板
 * @author LiuPF
 * @date 2018-10-15
 */
public class ProcedureDialog extends JDialog {
    
    private static final long serialVersionUID = 7334759086622449699L;
    public JTextField procedureNameField;
    //public DatePicker datePickerField;
    public DateTimePicker dateTimePicker;
    private static ProcedureDialog instance;
    public JTextArea remarkField;
    private int caseId = 0;
    private int procedureId = 0;
    
    public static ProcedureDialog getInstance () {
        if (instance == null) {
            instance = new ProcedureDialog();
        }
        return instance;
    }
    
    public ProcedureDialog() {
    	setModal(true);
        getContentPane().setLayout(null);
        
        JLabel caseName = new JLabel("手续名称");
        caseName.setBounds(51, 49, 72, 18);
        getContentPane().add(caseName);
        
        procedureNameField = new JTextField();
        procedureNameField.setBounds(127, 46, 280, 24);
        getContentPane().add(procedureNameField);
        procedureNameField.setColumns(10);
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String procedureName = procedureNameField.getText();
                String remark = remarkField.getText();
                if (StringUtils.isBlank(procedureName)) {
                    MainFrame.alert("请填写法律手续名称");
                    return;
                }
                LocalDateTime localDateTime = dateTimePicker.getDateTimePermissive();
                if (localDateTime == null) {
                    MainFrame.alert("请填写时间");
                    return;
                }
                String procedureTime = localDateTime.toString().replace("T", " ") + ":00";
                CaseService caseService = new CaseService();
                ResultDTO resultDTO = new ResultDTO();
                //新增
                if (procedureId == 0) {
                    resultDTO = caseService.addProcedure(ViewCasePanel.getInstance().getCaseId(), procedureName, procedureTime, remark);
                } else {
                    //更新
                	Procedure procedure = caseService.selectProceduresById(procedureId);
                	procedure.setName(procedureName);
                	procedure.setTime(procedureTime);
                	procedure.setRemark(remark);
                    resultDTO = caseService.updateProcedure(procedure);
                }
                if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                    MainFrame.alert(resultDTO.getMessage());
                    return;
                }
                MainFrame.alert("保存成功");
                getInstance().setVisible(false);
                ViewCasePanel.getInstance().updateCaseDetailTable();
            }
        });
        saveButton.setBounds(90, 254, 113, 27);
        getContentPane().add(saveButton);        
        
        JLabel lblNewLabel = new JLabel("时间");
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
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(127, 133, 280, 82);
        getContentPane().add(scrollPane);
        remarkField = new JTextArea();
        remarkField.setBorder(new LineBorder(Color.LIGHT_GRAY));
        remarkField.setLineWrap(true);        
        remarkField.setWrapStyleWord(true);
        scrollPane.setViewportView(remarkField);
        
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getInstance().setVisible(false);
            }
        });
        cancelButton.setBounds(248, 254, 113, 27);
        getContentPane().add(cancelButton);

    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

	public int getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(int procedureId) {
		this.procedureId = procedureId;
	}
    
}
