package au.com.per.delv.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class CustomerType implements IEntity {

	@Id
	@Column(name = "type")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
