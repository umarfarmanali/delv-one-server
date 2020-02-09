package au.com.per.delv.persistence.entity;

import java.util.HashSet;
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
import javax.persistence.Transient;

@Entity
@Table(name = "user_info")
public class UserInfo implements IEntity {

	public UserInfo() {
	}
//
//	public UserInfo(String username, String email, byte[] encryptedPassword, byte[] salt, Integer active) {
//		this.username = username;
//		this.email = email;
//		this.encryptedPassword = encryptedPassword;
//		this.salt = salt;
//		this.active = active;
//	}

	@Id
	@Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "encrypted_password")
	private byte[] encryptedPassword;

	@Transient
	private String password;

	@Column(name = "salt")
	private byte[] salt;

	@Column(name = "active")
	private Integer active;

	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_info_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	private Set<Role> roleSet = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(byte[] encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
}