package au.com.per.delv.service;

import java.util.List;
import java.util.Optional;

import au.com.per.delv.dto.IDto;
import au.com.per.delv.persistence.entity.IEntity;

public interface IRootService<D extends IDto, E extends IEntity> {

	public List<D> getList();
	public Optional<D> add(D dto);
	public Optional<D> update(D dto);
	public void delete(Integer id);
	
	public E convertFromDtoToEntity(D dto) throws Exception;
	public D convertFromEntityToDto(E entity) throws Exception;
}
