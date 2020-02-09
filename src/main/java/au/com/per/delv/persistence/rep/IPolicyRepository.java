package au.com.per.delv.persistence.rep;

import org.springframework.data.repository.CrudRepository;

import au.com.per.delv.persistence.entity.Policy;

public interface IPolicyRepository extends CrudRepository<Policy, Integer>{

}
