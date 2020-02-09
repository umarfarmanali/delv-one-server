package au.com.per.delv.service;

import java.util.List;

import au.com.per.delv.persistence.entity.IEntity;

public interface IPersistenceService<E extends IEntity> {

	public List<E> getAll();
	public E add(E obj) throws Exception;
	public E update(E obj);
	public void deleteById(Integer id);
}
