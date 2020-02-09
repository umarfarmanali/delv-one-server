package au.com.per.delv.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.per.delv.persistence.entity.Policy;
import au.com.per.delv.persistence.rep.IPolicyRepository;

@Service
public class PolicyServiceImpl implements IPolicyService{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public PolicyServiceImpl(IPolicyRepository repository) {
		 this.repository = repository; 
	}
	
	private IPolicyRepository repository;
	
	@Override
	public List<Policy> getAll() {
		
		List<Policy> list = new ArrayList<>();
		repository.findAll().forEach(list::add);
		return list;
	}

	@Override
	public Policy add(Policy obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Policy update(Policy obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}
}