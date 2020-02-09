package au.com.per.delv.controller.response;

import java.util.ArrayList;
import java.util.List;

import au.com.per.delv.persistence.entity.IEntity;

public class AbstractResponse<T extends IEntity> {

	private List<T> list;
	private String successMessage;
	private String errorMessage;
	
	public void add(T obj) {
		if(list == null) {
			list = new ArrayList<>();
		}
		list.add(obj);
	}
	
	public void add(List<T> obj) {
		if(list == null) {
			list = new ArrayList<>();
		}
		list.addAll(obj);
	}

	public List<T> getList() {
		return list;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}