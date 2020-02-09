package au.com.per.delv.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "policy")
public class Policy implements IEntity {

	public Policy() {}
	
	public Policy(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Set<Role> getRoleSet() {
//		return roleSet;
//	}
//
//	public void setRoleSet(Set<Role> roleSet) {
//		this.roleSet = roleSet;
//	}
}