package service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import service.base.BaseService;
import dao.AskedPersonDAO;
import dao.CaseDAO;
import dao.ClockDAO;
import dao.NoteDAO;
import dao.OtherPersonDAO;
import dao.PoliceDAO;
import dao.ProceduresDAO;
import dto.ResultDTO;
import entity.AskedPerson;
import entity.Clock;
import entity.LegalCase;
import entity.Note;
import entity.OtherPerson;
import entity.Police;
import entity.Procedures;

public class CaseService extends BaseService {

	private CaseDAO caseDAO = new CaseDAO();
	private NoteDAO noteDAO = new NoteDAO();
	private PoliceDAO policeDAO = new PoliceDAO();
	private ProceduresDAO proceduresDAO = new ProceduresDAO();
	private OtherPersonDAO otherPersonDAO = new OtherPersonDAO();
	private AskedPersonDAO askedPersonDAO = new AskedPersonDAO();
	private ClockDAO clockDAO = new ClockDAO();

	/**
	 * 案件
	 * 
	 * @param legalCase
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addCase(String name, String time) throws Exception {
		if (StringUtils.isBlank(name)) {
			return requestFail("案件名称不能为空");
		}
		if (name.length() > 20) {
			return requestFail("案件名称不能超过20个字符");
		}
		if (StringUtils.isBlank(time)) {
			return requestFail("时间不能为空");
		}
		caseDAO.add(new LegalCase(name, time));
		return requestSuccess();
	}

	public ResultDTO updateCase(LegalCase legalCase) {
		caseDAO.update(legalCase);
		return requestSuccess();
	}

	public ResultDTO delCase(int id) {
		if (caseDAO.delete(id) == 1) {
			return requestSuccess();
		}
		return requestFail();
	}

	public List<LegalCase> listCase() {
		return caseDAO.list();
	}

	/**
	 * 添加法律手续
	 * 
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addProcedure(Procedures procedures) throws Exception {
		String time = procedures.getTime();
		String name = procedures.getName();
		if (StringUtils.isBlank(name)) {
			return requestFail("法律手续名称不能为空");
		}
		if (name.length() > 20) {
			return requestFail("法律手续名称不能超过20个字符");
		}
		if (StringUtils.isBlank(time)) {
			return requestFail("时间不能为空");
		}
		proceduresDAO.add(procedures);
		return requestSuccess();
	}

	public ResultDTO updateProcedure(Procedures procedures) {
		if (proceduresDAO.update(procedures) == 1) {
			return requestSuccess();
		}
		return requestFail();
	}

	public ResultDTO delProcedure(int id) {
		if (proceduresDAO.delete(id) == 1) {
			return requestSuccess();
		}
		return requestFail();
	}

	public List<Procedures> listProcedure() {
		return proceduresDAO.list();
	}
	
	public List<Procedures> selectByCaseId(int caseId) {
		return proceduresDAO.selectByCaseId(caseId);
	}
	
	public Procedures selectById(int id) {
		return proceduresDAO.selectById(id);
	}

	/**
	 * 添加笔录
	 * 
	 * @param legalCase
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addNote(Note note) throws Exception {
		String name = note.getName();
		String startTime = note.getStartTime();
		String endTime = note.getEndTime();
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
		note.setName(startTime.substring(10) + name);
		noteDAO.add(note);
		return requestSuccess();
	}

	public ResultDTO updateProcedure(Note note) {
		noteDAO.update(note);
		return requestSuccess();
	}

	public ResultDTO delProcedure(Note note) {
		noteDAO.delete(note.getId());
		return requestSuccess();
	}

	public List<Note> listNote() {
		return noteDAO.list();
	}
	public List<Note> selectNoteByCaseId(int caseId) {
		return noteDAO.selectByCaseId(caseId);
	}
	public Note selectNoteById(int id) {
		return noteDAO.selectById(id);
	}

	/**
	 * 查询警员、时间、地点冲突的笔录
	 * 
	 * @param policeNumber
	 *            要添加的警员警号
	 * @param startTime
	 *            已存在的笔录起始时间
	 * @param endTime
	 *            已存在的笔录结束时间
	 * @param place
	 *            笔录地点
	 * @return
	 */
	public List<Note> selectNoteByTimeAndPlaceAndPolic(String policeNumber, String startTime, String endTime,
			String place) {
		return noteDAO.selectNoteByTimeAndPlaceAndPolic(policeNumber, startTime, endTime, place);
	}

	public static void main(String[] args) {
		CaseService caseService = new CaseService();
		List<Note> notes = caseService.selectNoteByTimeAndPlaceAndPolic("123456", "2018-09-19 01:00:00",
				"2018-09-19 02:00:00", "aaa");
		System.out.println(notes.size());
		 List<LegalCase> listCases = caseService.listCase();
		 System.out.println(listCases);
	}

	/**
	 * 添加警员
	 * 
	 * @param legalCase
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addPolice(Police police) throws Exception {

		policeDAO.add(police);
		return requestSuccess();
	}

	public ResultDTO updatePolice(Police police) {
		policeDAO.update(police);
		return requestSuccess();
	}

	public ResultDTO delPolice(Police police) {
		policeDAO.delete(police.getId());
		return requestSuccess();
	}

	public List<Police> listPolice() {
		return policeDAO.list();
	}

	/**
	 * 其他人员
	 * 
	 * @param legalCase
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addOtherPerson(OtherPerson otherPerson) throws Exception {

		otherPersonDAO.add(otherPerson);
		return requestSuccess();
	}

	public ResultDTO updateOtherPerson(OtherPerson otherPerson) {
		otherPersonDAO.update(otherPerson);
		return requestSuccess();
	}

	public ResultDTO delOtherPerson(OtherPerson otherPerson) {
		otherPersonDAO.delete(otherPerson.getId());
		return requestSuccess();
	}

	public List<OtherPerson> listOtherPerson() {
		return otherPersonDAO.list();
	}

	/**
	 * 被询问人
	 * 
	 * @param legalCase
	 * @return
	 * @throws Exception
	 */
	public ResultDTO addAskedPerson(AskedPerson askedPerson) throws Exception {

		askedPersonDAO.add(askedPerson);
		return requestSuccess();
	}

	public ResultDTO updateAskedPerson(AskedPerson askedPerson) {
		askedPersonDAO.update(askedPerson);
		return requestSuccess();
	}

	public ResultDTO delAskedPerson(AskedPerson askedPerson) {
		askedPersonDAO.delete(askedPerson.getId());
		return requestSuccess();
	}

	public List<AskedPerson> listAskedPerson() {
		return askedPersonDAO.list();
	}

	/**
	 * 添加闹钟
	 * 
	 * @param clock
	 * @return
	 * @throws Exception
	 */
	private ResultDTO addClock(String name, String time, String remark) throws Exception {
		return addClock(name, time, remark, null, 0);
	}

	/**
	 * 添加闹钟
	 * 
	 * @param name
	 * @param time
	 * @param remark
	 * @param type
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
	private ResultDTO addClock(String name, String time, String remark, String type, int ownerId) throws Exception {
		if (StringUtils.isBlank(time)) {
			return requestFail("时间不能为空");
		}
		if (StringUtils.isBlank(name)) {
			return requestFail("闹钟名称不能为空");
		}
		if (name.length() > 20) {
			return requestFail("闹钟名称不能超过20个字符");
		}
		clockDAO.add(new Clock(name, time, remark, type, ownerId));
		return requestSuccess();
	}

	/**
	 * 闹钟列表
	 * 
	 * @return
	 */
	private List<Clock> getClocks() {
		return clockDAO.list();
	}

	/**
	 * 删除闹钟
	 * 
	 * @param id
	 * @return
	 */
	private ResultDTO delClock(Clock clock) {
		if (1 == clockDAO.delete(clock.getId())) {
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
	private ResultDTO updateClock(Clock clock) {
		if (1 == clockDAO.update(clock)) {
			return requestSuccess();
		} else {
			return requestFail();
		}
	}

}
