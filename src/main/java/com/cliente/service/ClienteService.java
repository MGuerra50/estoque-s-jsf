package com.cliente.service;

import java.util.List;

import com.cliente.model.Cliente;
import com.util.BusinessException;

public interface ClienteService {
	
	public Cliente createCliente (Cliente cliente) throws BusinessException;
	public Cliente getCliente (Long id);
	public List<Cliente> getAllClientes ();
	public void removeCliente (Long id) throws BusinessException;
	public void updateCliente (Cliente cliente) throws BusinessException;

}
