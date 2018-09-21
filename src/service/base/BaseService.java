package service.base;


import constant.CommonConstant;
import dto.ResultDTO;

public abstract class BaseService {

	
	protected ResultDTO requestSuccess(Object object) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setData(object);
		resultDTO.setMessage("请求成功");
		resultDTO.setCode(CommonConstant.RESULT_CODE_SUCCESS);
		return resultDTO;
	}
	
	protected ResultDTO requestSuccess() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setData("");
		resultDTO.setMessage("请求成功");
		resultDTO.setCode(CommonConstant.RESULT_CODE_SUCCESS);
		return resultDTO;
	}
	
	protected ResultDTO requestFail() {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setData("");
		resultDTO.setMessage("请求失败");
		resultDTO.setCode(CommonConstant.RESULT_CODE_FAIL);
		return resultDTO;
	}
	
	protected ResultDTO requestFail(String message) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setData("");
		resultDTO.setMessage(message);
		resultDTO.setCode(CommonConstant.RESULT_CODE_FAIL);
		return resultDTO;
	}
}
