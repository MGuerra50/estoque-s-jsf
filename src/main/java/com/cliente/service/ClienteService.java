package com.cliente.service;

import java.util.List;

import com.cliente.model.Cliente;
import com.util.BusinessException;

public interface ClienteService {
	
    Cliente createCliente (Cliente cliente) throws BusinessException;
	Cliente getCliente (Long id);
	List<Cliente> getAllClientes ();
	void removeCliente (Cliente cliente) throws BusinessException;
	void updateCliente (Cliente cliente) throws BusinessException;
    List<Cliente> getSearch(Cliente cliente);
}
