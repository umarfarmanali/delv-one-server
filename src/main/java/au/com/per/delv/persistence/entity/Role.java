package au.com.per.delv.persistence.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements IEntity {

	public Role() {}
	
	public Role(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@JoinTable(name = "role_policy", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "policy_id"))
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	private Set<Policy> policySet;
	
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

	public Set<Policy> getPolicySet() {
		return policySet;
	}

	public void setPolicySet(Set<Policy> policySet) {
		this.policySet = policySet;
	}
}