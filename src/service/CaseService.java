package service;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import constant.CommonConstant;
import service.base.BaseService;
import util.DateUtil;
import vo.CaseItemVO;
import dao.AskedPersonDAO;
import dao.CaseDAO;
import dao.ClockDAO;
import dao.NoteDAO;
import dao.OtherPersonDAO;
import dao.PoliceDAO;
import dao.ProcedureDAO;
import dto.ResultDTO;
import entity.AskedPerson;
import entity.Clock;
import entity.LegalCase;
import entity.Note;
import entity.OtherPerson;
import entity.Police;
import entity.Procedure;

public class CaseService extends BaseService {

	private CaseDAO caseDAO = new CaseDAO();
	private NoteDAO noteDAO = new NoteDAO();
	private PoliceDAO policeDAO = new PoliceDAO();
	private ProcedureDAO proceduresDAO = new ProcedureDAO();
	private OtherPersonDAO otherPersonDAO = new OtherPersonDAO();
	private AskedPersonDAO askedPersonDAO = new AskedPersonDAO();
	private ClockDAO clockDAO = new ClockDAO();
	
	/**
	 * 判断起始时间是否早于结束时间
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public boolean checkTime(String startTime, String endTime) throws Exception{
		return DateUtil.checkTime(startTime, endTime);
	}

	/**
	 * 校验参数：名称不能为空，不能超过50个字符；时间不能为空；备注不能超过500个字符
	 * 
	 * @param name
	 * @param time
	 * @param remark
	 * @return
	 */
	private ResultDTO checkParameters(String name, String time, String remark) {
		if (StringUtils.isBlank(name)) {
			return requestFail("名称不能为空");
		}
		if (name.length() > 50) {
			return requestFail("名称不能超过50个字符");
		}
		if (StringUtils.isBlank(time)) {
			return requestFail("时间不能为空");
		}
		if (null != remark && remark.length() > 500) {
			return requestFail("备注不能超过500个字符");
		}
		return requestSuccess();
	}

	/**
	 * 新增案件
	 * 
	 * @param legalCase
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addCase(String name, String time, String remark) {
		ResultDTO result = checkParameters(name, time, remark);
		if (result.getCode().equals(CommonConstant.RESULT_CODE_FAIL)) {
			return result;
		}
		try {
			caseDAO.add(new LegalCase(name, time, remark));
		} catch (Exception e) {
			e.printStackTrace();
			return requestFail();
		}
		return requestSuccess();
	}

	/**
	 * 查询单个案件
	 * 
	 * @param legalCase
	 * @return
	 */
	public LegalCase selectCaseById(int id) {
		try {
			return caseDAO.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新案件
	 * 
	 * @param legalCase
	 * @return
	 */
	public ResultDTO updateCase(LegalCase legalCase) {
		ResultDTO result = checkParameters(legalCase.getName(), legalCase.getTime(), legalCase.getRemark());
		if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
			return result;
		}
		try {
			caseDAO.update(legalCase);
		} catch (Exception e) {
			e.printStackTrace();
			return requestFail();
		}
		return requestSuccess();
	}

	/**
	 * 删除案件
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO delCase(int id) {
		try {
			List<Note> notes = selectNoteByCaseId(id);
			if (notes.size() > 0) {
				return requestFail("请先删除案件相关的笔录");
			}
			List<Procedure> procedures = selectProceduresByCaseId(id);
			if (procedures.size() > 0) {
				return requestFail("请先删除案件相关的法律手续");
			}
			List<Clock> clocks = selectClocksByCaseId(id);
			if (clocks.size() > 0) {
				return requestFail("请先删除案件相关的闹钟");
			}
			if (caseDAO.delete(id) == 1) {
				return requestSuccess();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestFail();
	}

	/**
	 * 查询案件列表
	 * 
	 * @return
	 */
	public List<LegalCase> listCase() {
		return caseDAO.list();
	}
	
	/**
	 * 通过关键字模糊查询案件
	 * 
	 * @param keyWord
	 * @return
	 */
	public List<LegalCase> listCaseByKeyWord(String keyWord) {
		return caseDAO.listCaseByKeyWord(keyWord);
	}

	/**
	 * 根据案件Id获取案件细则列表
	 * 
	 * @param caseId
	 * @return
	 */
	public List<CaseItemVO> getCaseItems(int caseId) {
		return caseDAO.getCaseItems(caseId);
	}

	/**
	 * 根据类别和关键字模糊查询案件细则列表
	 * 
	 * @param caseId 案件id
	 * @param keyWord 关键字
	 * @param itemType 案件细则种类：0全部；1笔录；2法律手续；3闹钟
	 * @return
	 */
	public List<CaseItemVO> getCaseItemsByKeyWord(int caseId, String keyWord, String itemType) {
		if (CommonConstant.CASE_ITEM_TYPE_ALL.equals(itemType)) {
			return caseDAO.getCaseItemsByKeyWord(caseId, keyWord);
		}else {
			return caseDAO.getCaseItemsByKeyWord(caseId, keyWord, itemType);
		}
	}

	/**
	 * 添加法律手续
	 * 
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addProcedure(int caseId, String name, String time, String remark) {
		ResultDTO result = checkParameters(name, time, remark);
		if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
			return result;
		}
		try {
			proceduresDAO.add(new Procedure(caseId, name, time, remark));
		} catch (Exception e) {
			e.printStackTrace();
			return requestFail();
		}
		return requestSuccess();
	}

	/**
	 * 更新法律程序
	 * 
	 * @param procedures
	 * @return
	 */
	public ResultDTO updateProcedure(Procedure procedures) {
		ResultDTO result = checkParameters(procedures.getName(), procedures.getTime(), procedures.getRemark());
		if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
			return result;
		}
		if (proceduresDAO.update(procedures) == 1) {
			return requestSuccess();
		}
		return requestFail();
	}

	/**
	 * 删除法律程序
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO delProcedure(int id) {
		if (proceduresDAO.delete(id) == 1) {
			return requestSuccess();
		}
		return requestFail();
	}

	/**
	 * 根据案件id查询案件下所有法律程序
	 * 
	 * @param caseId
	 * @return
	 */
	public List<Procedure> selectProceduresByCaseId(int caseId) {
		return proceduresDAO.selectByCaseId(caseId);
	}

	/**
	 * 查询单个法律程序
	 * 
	 * @param id
	 * @return
	 */
	public Procedure selectProceduresById(int id) {
		return proceduresDAO.selectById(id);
	}

	/**
	 * 添加笔录
	 * 
	 * @param caseId
	 *            案件id
	 * @param name
	 *            笔录名称
	 * @param startTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @param remark
	 *            备注
	 * @param place
	 *            笔录记录地点
	 * @param fileName
	 *            对应文件名称
	 * @return
	 */
	public ResultDTO addNote(int caseId, String name, String startTime, String endTime, String remark, String place,
			String fileName, int askedPersonId) {

		if (StringUtils.isBlank(name)) {
			return requestFail("笔录名称不能为空");
		}
		if (name.length() > 20) {
			return requestFail("笔录名称不能超过20个字符");
		}
		if (StringUtils.isBlank(startTime)) {
			return requestFail("起始时间不能为空");
		}
		if (StringUtils.isBlank(endTime)) {
			return requestFail("结束时间不能为空");
		}
		try {
			if (!DateUtil.checkTime(startTime, endTime)) {
				return requestFail("起始时间应小于结束时间");
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			return requestFail("时间格式错误");
		}
		if (remark.length() > 500) {
			return requestFail("备注不能超过500个字符");
		}
		if (StringUtils.isBlank(place)) {
			return requestFail("地点不能为空");
		}
		/*if (StringUtils.isBlank(fileName)) {
			return requestFail("对应文件名不能为空");
		}*/


		Note note = new Note(caseId, name, startTime, endTime, remark, place, fileName, askedPersonId);

		// 校验笔录
		ResultDTO result = checkNote(note, true);
		if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
			return result;
		}

		try {
			// 返回主键
			Integer id = noteDAO.add(note);
			return requestSuccess(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestFail();
	}

	/**
	 * 校验笔录
	 * 
	 * @param note
	 * @param addFlag
	 * @return
	 */
	private ResultDTO checkNote(Note note, boolean addFlag) {
		List<Note> notes = noteDAO.selectConflictingNotes(note);
		if (addFlag) {
			if (notes.size() > 0) {
				return requestFail("时间、地点与同一案件下其他笔录冲突", notes);
			}
		} else {
			if (notes.size() > 1) {
				return requestFail("时间、地点与同一案件下其他笔录冲突", notes);
			}
		}
		return requestSuccess();
	}

	/**
	 * 更新笔录
	 * 
	 * @param note
	 * @return
	 */
	public ResultDTO updateNote(Note note) {
		
		try {
			if (!DateUtil.checkTime(note.getStartTime(), note.getEndTime())) {
				return requestFail("起始时间应小于结束时间");
			}

			// 校验笔录
			ResultDTO result = checkNote(note, false);
			if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
				return result;
			}
	
			AskedPerson askedPerson = askedPersonDAO.selectById(note.getAskedPersonId());
			if (null != askedPerson) {
				// 校验被询问人
				result = checkAskedPerson(askedPerson, note, "2", null);
				if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
					return result;
				}
			}
	
			// 更新笔录
			if (1 == noteDAO.update(note)) {
				return requestSuccess();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestFail();

	}

	/**
	 * 校验其他工作人员
	 * 
	 * @param note
	 *            笔录
	 * @param otherPersonIdCard
	 *            警员姓名
	 * @param addFlag
	 *            true:添加;false:更新
	 * @return
	 */
	private ResultDTO checkOtherPerson(Note note, String otherPersonIdCard, boolean addFlag, String otherPersonType) {
		List<Note> notes = noteDAO.selectConflictingNotesForOtherPerson(note, otherPersonIdCard);
		if (addFlag) {
			if (notes.size() > 0) {
				return requestFail("其他工作人员、时间与同一案件下其他笔录冲突", notes);
			}
		} else {
			if (notes.size() > 1) {
				return requestFail("其他工作人员、时间与同一案件下其他笔录冲突", notes);
			}
		}
		
		// 被询问人不能作为监护人
		if (CommonConstant.OTHER_PERSON_TYPE_1.equals(otherPersonType)) {
			AskedPerson askedPerson = askedPersonDAO.selectByIdCardInCase(otherPersonIdCard, note.getCaseId());
			if (null != askedPerson) {
				return requestFail("同一案件下被询问人不能作为监护人");
			}
		}
		
		return requestSuccess();
	}

	/**
	 * 校验警员
	 * 
	 * @param note
	 *            笔录
	 * @param policeName
	 *            警员姓名
	 * @param addFlag
	 *            true:添加;false:更新
	 * @return
	 */
	private ResultDTO checkPolic(Note note, String policeName, boolean addFlag) {
		List<Note> notes = noteDAO.selectConflictingNotesForPolice(note, policeName);
		if (addFlag) {
			if (notes.size() > 0) {
				return requestFail("警员、时间与同一案件下其他笔录冲突", notes);
			}
		} else {
			if (notes.size() > 1) {
				return requestFail("警员、时间与同一案件下其他笔录冲突", notes);
			}
		}
		return requestSuccess();
	}

	/**
	 * 校验被询问人
	 * 
	 * @param note
	 *            笔录
	 * @param checkType 1新增被询问人；2更新被询问人；3删除警员；4删除其他工作人员
	 * @return
	 */
	private ResultDTO checkAskedPerson(AskedPerson askedPerson, Note note, String checkType, Integer policeOrOtherPersonId) {
		
		List<Police> polices = selectPoliceForNote(note.getId());
		if ("3".equals(checkType)) {
			Iterator<Police> iterator = polices.iterator();
			while (iterator.hasNext()) {
				Police police = iterator.next();
				if (police.getId() == policeOrOtherPersonId) {
					iterator.remove();
				}
			}
		}
		
		List<OtherPerson> otherPersons = selectOtherPersonByNoteId(note.getId());
		if ("4".equals(checkType)) {
			Iterator<OtherPerson> iterator = otherPersons.iterator();
			while (iterator.hasNext()) {
				OtherPerson otherPerson = iterator.next();
				if (otherPerson.getId() == policeOrOtherPersonId) {
					iterator.remove();
				}
			}
		}

		// 翻译标记
		boolean interpreterFlag = true;
		// 监护人标记
		boolean guardianFlag = true;
		// 女性警员标记
		boolean policeSexFlag = true;

		// 聋哑人少数民族及外国人需要翻译在场
		if (askedPerson.getDisabledFlag().equals(CommonConstant.YES)) {
			interpreterFlag = false;
		}
		// 未成年被询问人需有监护人在场
		if (askedPerson.getAdultFlag().equals(CommonConstant.NO)) {
			guardianFlag = false;
			// 女性未成年人需要女警员在场
			if (askedPerson.getSex().equals(CommonConstant.SEX_FEMALE)) {
				policeSexFlag = false;
			}
		}

		// 判断是否有翻译
		if (!interpreterFlag) {
			for (OtherPerson otherPerson : otherPersons) {
				if (otherPerson.getType().equals(CommonConstant.OTHER_PERSON_TYPE_2)) {
					interpreterFlag = true;
					break;
				}
			}
		}
		if (!interpreterFlag) {
			return requestFail("聋哑人少数民族及外国人需要翻译在场");
		}

		// 判断是否有监护人
		if (!guardianFlag) {
			for (OtherPerson otherPerson : otherPersons) {
				if (otherPerson.getType().equals(CommonConstant.OTHER_PERSON_TYPE_1)) {
					guardianFlag = true;
					break;
				}
			}
		}
		if (!guardianFlag) {
			return requestFail("未成年被询问人需有监护人在场");
		}

		// 判断是否有女警员
		if (!policeSexFlag) {
			for (Police police : polices) {
				if (police.getSex().equals(CommonConstant.SEX_FEMALE)) {
					policeSexFlag = true;
					break;
				}
			}
		}
		if (!policeSexFlag) {
			return requestFail("女性未成年人需要女警员在场");
		}
		
		// 校验此人是否在同一案件下已为监护人
		OtherPerson otherPerson = otherPersonDAO.selectByIdCardInCase(askedPerson.getIdCard(), note.getCaseId());
		if (null != otherPerson) {
			return requestFail("当前被询问人在同一案件下已为监护人");
		}

		// 校验同一案件内所有笔录被询问人是否冲突
		List<Note> notes = noteDAO.selectConflictingNotesForAskedPerson(note, askedPerson.getIdCard());
		if ("1".equals(checkType)) {
			if (notes.size() > 0) {
				return requestFail("被询问人、时间与同案件下其他笔录冲突", notes);
			}
		} else if("2".equals(checkType)){
			if (notes.size() > 1) {
				return requestFail("被询问人、时间与同案件下其他笔录冲突", notes);
			}
		}
		return requestSuccess();
	}

	/**
	 * 删除笔录
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO delNote(int noteId) {
		List<Police> polices = policeDAO.listByNoteId(noteId);
		if (polices.size() > 0) {
			return requestFail("请先删除笔录关联的警员");
		}
		List<OtherPerson> otherPersons = otherPersonDAO.selectByNoteId(noteId);
		if (otherPersons.size() > 0) {
			return requestFail("请先删除笔录关联的其他工作人员");
		}

		if (noteDAO.delete(noteId) == 1) {
			return requestSuccess();
		}
		return requestFail();
	}

	/**
	 * 根据案件id查询笔录
	 * 
	 * @param caseId
	 * @return
	 */
	public List<Note> selectNoteByCaseId(int caseId) {
		return noteDAO.selectByCaseId(caseId);
	}

	/**
	 * 查询单个笔录
	 * 
	 * @param id
	 * @return
	 */
	public Note selectNoteById(int id) {
		return noteDAO.selectById(id);
	}

	/**
	 * 添加警员
	 * 
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addPolice(String name, String sex, int noteId) {
		if (StringUtils.isBlank(name)) {
			return requestFail("名称不能为空");
		}
		if (name.length() > 20) {
			return requestFail("名称不能超过20个字符");
		}
		try {

			Note note = noteDAO.selectById(noteId);
			ResultDTO result = checkPolic(note, name, true);
			if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
				return result;
			}

			policeDAO.add(new Police(name, sex, noteId));
			return requestSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestFail();
	}

	/**
	 * 更新警员
	 * 
	 * @param police
	 * @return
	 */
	public ResultDTO updatePolice(Police police, int noteId) {

		Note note = noteDAO.selectById(noteId);
		ResultDTO result = checkPolic(note, police.getName(), false);
		if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
			return result;
		}

		if (1 == policeDAO.update(police)) {
			return requestSuccess();
		}
		return requestFail();
	}

	/**
	 * 根据警号查询警员
	 * 
	 * @param policeNumber
	 * @return
	 */
	// public Police selectPoliceByPoliceNumber(String policeNumber) {
	// return policeDAO.selectByPoliceNumber(policeNumber);
	// }

	/**
	 * 根据ID查询警员
	 * 
	 * @param policeNumber
	 * @return
	 */
	public Police selectPoliceById(int id) {
		return policeDAO.selectById(id);
	}

	/**
	 * 删除警员
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO delPolice(int id) {
		Police police = selectPoliceById(id);
		Note note = noteDAO.selectById(police.getNoteId());
		AskedPerson askedPerson = askedPersonDAO.selectById(note.getAskedPersonId());
		if (null != askedPerson) {
			ResultDTO resultDTO = checkAskedPerson(askedPerson, noteDAO.selectById(police.getNoteId()), "3", id);
			if (resultDTO.getCode() == CommonConstant.RESULT_CODE_FAIL) {
				return requestFail(resultDTO.getMessage());
			}
		}

		if (1 == policeDAO.delete(id)) {
			return requestSuccess();
		}
		return requestFail();
	}

	/**
	 * 查询所有警员
	 * 
	 * @return
	 */
	// public List<Police> listPolice() {
	// return policeDAO.list();
	// }

	/**
	 * 获取笔录中涉及的警员
	 * 
	 * @param policeList
	 * @return
	 */
	public List<Police> selectPoliceForNote(int noteId) {
		return policeDAO.listByNoteId(noteId);
	}

	/**
	 * 添加其他人员
	 * 
	 * @param legalCase
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addOtherPerson(int noteId, String name, String sex, String idCard, String type) {
		if (StringUtils.isBlank(name)) {
			return requestFail("姓名不能为空");
		}
		if (name.length() > 20) {
			return requestFail("姓名不能超过20个字符");
		}
		if (StringUtils.isBlank(idCard)) {
			return requestFail("身份证号不能为空");
		}
		if (idCard.length() < 15 || idCard.length() > 18) {
			return requestFail("请输入15-18位的正确身份证号");
		}
		try {

			// 校验其他人员
			Note note = noteDAO.selectById(noteId);
			ResultDTO result = checkOtherPerson(note, idCard, true, type);
			if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
				return result;
			}

			otherPersonDAO.add(new OtherPerson(noteId, name, sex, type, idCard));
			return requestSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestFail();
	}

	/**
	 * 修改其他人员
	 * 
	 * @param otherPerson
	 * @return
	 */
	public ResultDTO updateOtherPerson(OtherPerson otherPerson) {

		// 校验其他人员
		Note note = noteDAO.selectById(otherPerson.getNoteId());
		ResultDTO result = checkOtherPerson(note, otherPerson.getIdCard(), false,otherPerson.getType());
		if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
			return result;
		}

		otherPersonDAO.update(otherPerson);
		return requestSuccess();
	}

	/**
	 * 删除其他人员
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO delOtherPerson(int id) {

		OtherPerson otherPerson = otherPersonDAO.selectById(id);
		Note note = noteDAO.selectById(otherPerson.getNoteId());
		AskedPerson askedPerson = askedPersonDAO.selectById(note.getAskedPersonId());
		if (null != askedPerson) {
			ResultDTO resultDTO = checkAskedPerson(askedPerson, note, "4", id);
			if (resultDTO.getCode() == CommonConstant.RESULT_CODE_FAIL) {
				return requestFail(resultDTO.getMessage());
			}	
		}

		if (1 == otherPersonDAO.delete(id)) {
			return requestSuccess();
		}
		return requestFail();
	}

	/**
	 * 查询单个其他人员
	 * 
	 * @param id
	 * @return
	 */
	public OtherPerson selectOtherPersonById(int id) {
		return otherPersonDAO.selectById(id);
	}

	/**
	 * 查询笔录涉及的其他人员
	 * 
	 * @param noteId
	 * @return
	 */
	public List<OtherPerson> selectOtherPersonByNoteId(int noteId) {
		return otherPersonDAO.selectByNoteId(noteId);
	}

	/**
	 * 添加被询问人
	 * 
	 * @param legalCase
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addAskedPerson(int noteId, String name, String sex, String type, String adultFlag, String idCard,
			String disabledFlag) {
		if (StringUtils.isBlank(name)) {
			return requestFail("姓名不能为空");
		}
		if (name.length() > 20) {
			return requestFail("姓名不能超过20个字符");
		}
		if (StringUtils.isBlank(idCard)) {
			return requestFail("身份证号不能为空");
		}
		if (idCard.length() < 15 || idCard.length() > 18) {
			return requestFail("请输入15-18位的正确身份证号");
		}

		Note note = noteDAO.selectById(noteId);

		AskedPerson askedPerson = new AskedPerson(noteId, name, sex, type, adultFlag, idCard, disabledFlag);

		// 校验被询问人
		ResultDTO result = checkAskedPerson(askedPerson, note, "1", null);
		if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
			return result;
		}

		try {		
			AskedPerson askedPersonOld = askedPersonDAO.selectById(note.getAskedPersonId());
			if (null == askedPersonOld) {
				int askedPersonId = askedPersonDAO.add(askedPerson);
				note.setAskedPersonId(askedPersonId);
				noteDAO.update(note);
			}else {
				askedPerson.setId(askedPersonOld.getId());
				noteDAO.update(note);
			}
			return requestSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestFail();
	}

	/**
	 * 更新被询问人
	 * 
	 * @param askedPerson
	 * @return
	 */
	public ResultDTO updateAskedPerson(AskedPerson askedPerson, int noteId) {
		Note note = noteDAO.selectById(noteId);
		ResultDTO resultDTO = checkAskedPerson(askedPerson, note, "2", null);
		if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
			return resultDTO;
		}
		if (1 == askedPersonDAO.update(askedPerson)) {
			return requestSuccess();
		}
		return requestFail();
	}

	/**
	 * 删除被询问人
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO delAskedPerson(int id) {
		askedPersonDAO.delete(id);
		return requestSuccess();
	}

	/**
	 * 查询笔录涉及的被询问人
	 * 
	 * @param noteId
	 * @return
	 */
	public AskedPerson selectAskedPersonByNoteId(int noteId) {
		Note note = noteDAO.selectById(noteId);
		return askedPersonDAO.selectById(note.getAskedPersonId());
	}

	/**
	 * 查询单个被询问人信息
	 * 
	 * @param id
	 * @return
	 */
	public AskedPerson selectAskedPersonById(int id) {
		return askedPersonDAO.selectById(id);
	}

	/**
	 * 添加闹钟
	 * 
	 * @param clock
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addClock(String name, String time, String remark) {
		return addClock(name, time, remark, 0);
	}

	/**
	 * 添加闹钟（支持法律手续等依赖关系）
	 * 
	 * @param name
	 * @param time
	 * @param remark
	 * @param type
	 *            闹钟关联类型：null无关联;1笔录；2法律手续
	 * @param ownerId
	 *            关联者id
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addClock(String name, String time, String remark, int caseId) {
		if (StringUtils.isBlank(time)) {
			return requestFail("时间不能为空");
		}
		if (StringUtils.isBlank(name)) {
			return requestFail("闹钟名称不能为空");
		}
		if (name.length() > 50) {
			return requestFail("闹钟名称不能超过50个字符");
		}
		if (remark.length() > 500) {
			return requestFail("备注不能超过500个字符");
		}
		try {
			clockDAO.add(new Clock(name, time, remark, "", 0, caseId, CommonConstant.NO));
			return requestSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestFail();
	}

	/**
	 * 获取过去一天和未来三天的闹钟
	 * 
	 * @return
	 */
	public List<Clock> getClocksInThreeDaysAndLastDay() {
		return clockDAO.getClocksInThreeDaysAndLastDay();
	}

	/**
	 * 获取单个闹钟
	 * 
	 * @param id
	 * @return
	 */
	public Clock getClockById(int id) {
		return clockDAO.selectById(id);
	}

	/**
	 * 根据闹钟关联类型和关联者获取闹钟列表
	 * 
	 * @param type
	 * @param ownerId
	 * @return
	 */
	public List<Clock> selectClocksByTypeAndOwnerId(String type, int ownerId) {
		return clockDAO.selectByTypeAndOwnerId(type, ownerId);
	}

	/**
	 * 删除闹钟
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO delClock(int id) {
		if (1 == clockDAO.delete(id)) {
			return requestSuccess();
		} else {
			return requestFail();
		}
	}

	/**
	 * 修改闹钟
	 * 
	 * @param clock
	 * @return
	 */
	public ResultDTO updateClock(Clock clock) {
		if (1 == clockDAO.update(clock)) {
			return requestSuccess();
		} else {
			return requestFail();
		}
	}

	/**
	 * 根据案件id获取闹钟列表
	 * 
	 * @param caseId
	 * @return
	 */
	public List<Clock> selectClocksByCaseId(int caseId) {
		return clockDAO.selectClocksByCaseId(caseId);
	}

	public static void main(String[] args) {
		CaseService caseService = new CaseService();
		ResultDTO resultDTO = new ResultDTO();
//		resultDTO = caseService.addCase("案件1", "2019-01-01 00:00:00", "备注1");
//		System.out.println(resultDTO);
//		resultDTO = caseService.addClock("闹钟1", "2019-01-01 00:00:00", "备注1");
//		System.out.println(resultDTO);
//		resultDTO = caseService.addClock("闹钟2", "2019-01-01 00:00:00", "备注2", 1);
//		System.out.println(resultDTO);
//		resultDTO = caseService.addProcedure(1, "法律手续1", "2019-01-01 00:00:00", "备注1");
//		System.out.println(resultDTO);
//		resultDTO = caseService.addNote(2, "笔录1", "2019-01-01 00:00:00", "2019-01-01 00:10:00", 
//				"备注1", "地点2", "文件1", 0);
//		System.out.println(resultDTO);
		resultDTO = caseService.addNote(2, "笔录1", "2019-01-01 00:00:00", "2019-01-01 00:10:00", 
				"备注1", "地点1", "文件1", 0);
		System.out.println(resultDTO);
//		resultDTO = caseService.addPolice("警员1", "0", 16);
//		System.out.println(resultDTO);
//		resultDTO = caseService.addOtherPerson(16, "其他1", "1", "110111198612101112", CommonConstant.OTHER_PERSON_TYPE_1);
//		System.out.println(resultDTO);
//		resultDTO = caseService.addOtherPerson(1, "其他2", "1", "110111198612101112", CommonConstant.OTHER_PERSON_TYPE_1);
//		System.out.println(resultDTO);
		resultDTO = caseService.addAskedPerson(1, "被询问人1", "0", CommonConstant.ASKED_PERSON_TYPE_1, "1", "110111198612101111", "0");
		System.out.println(resultDTO);
		resultDTO = caseService.addAskedPerson(2, "被询问人1", "0", CommonConstant.ASKED_PERSON_TYPE_1, "1", "110111198612101111", "0");
		System.out.println(resultDTO);
		
	}
}
