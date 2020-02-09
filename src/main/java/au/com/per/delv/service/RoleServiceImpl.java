package au.com.per.delv.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.per.delv.persistence.entity.Role;
import au.com.per.delv.persistence.rep.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public RoleServiceImpl(IRoleRepository repository) {
		 this.repository = repository; 
	}
	
	private IRoleRepository repository;
	
	@Override
	public List<Role> getAll() {
		
		List<Role> list = new ArrayList<>();
		repository.findAll().forEach(list::add);
		return list;
	}

	@Override
	public Role add(Role obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role update(Role obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}
}