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
    public DatePicker datePickerField;
    private static ProcedureDialog instance;
    public JTextField remarkField;
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
        
        JLabel caseName = new JLabel("案件名称");
        caseName.setBounds(81, 49, 72, 18);
        getContentPane().add(caseName);
        
        procedureNameField = new JTextField();
        procedureNameField.setBounds(167, 46, 208, 24);
        getContentPane().add(procedureNameField);
        procedureNameField.setColumns(10);
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String caseName = procedureNameField.getText();
                String date = datePickerField.getText();
                String remark = remarkField.getText();
                CaseService caseService = new CaseService();
                ResultDTO resultDTO = new ResultDTO();
                //新增
                if (procedureId == 0) {
                    resultDTO = caseService.addProcedure(caseId, caseName, date, remark);
                } else {
                    //更新
                	Procedure procedure = caseService.selectProceduresById(procedureId);
                	procedure.setName(caseName);
                	procedure.setTime(date);
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
        lblNewLabel.setBounds(81, 90, 72, 18);
        getContentPane().add(lblNewLabel);

        datePickerField = DateUtil.getDatePicker(DateUtil.FORMAT_YYYYMMDDHHMMSS);
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
