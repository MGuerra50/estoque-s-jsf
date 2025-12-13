package com.cliente.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cliente.model.Cliente;
import com.util.BusinessException;

@Stateless
public class ClienteRepository {

	@PersistenceContext(unitName = "jsf-pu")
	private EntityManager entityManager;
	
	public Cliente findById(Long id) {
		return entityManager.find(Cliente.class, id);
	}
	
	public List<Cliente> findAll() {
		return entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
	}
	
	public void save (Cliente cliente) {
		entityManager.persist(cliente);
	}
	
	public void deleteById (Long id) throws BusinessException {
		Cliente cliente = findById(id);
		if(cliente == null) {
			entityManager.remove(cliente);			
		} else {
			throw new BusinessException("Este cliente já foi excluído!");
		}
	}
	
	public void update(Cliente cliente) {
		entityManager.merge(cliente);
	}
	
}
