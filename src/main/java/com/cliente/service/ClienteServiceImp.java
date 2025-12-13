package com.cliente.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cliente.model.Cliente;
import com.cliente.repository.ClienteRepository;
import com.util.BusinessException;

@Stateless
public class ClienteServiceImp implements ClienteService{
	
	@Inject
	private ClienteRepository clienteRepository;

	public Cliente createCliente (Cliente cliente) throws BusinessException {
		if(cliente.getNome() != null) {
			Cliente novoCliente = new Cliente();
			novoCliente.setNome(cliente.getNome());
			clienteRepository.save(novoCliente);
			return novoCliente;
		} else {
			throw new BusinessException("Nome não pode estar vazio!");
		}
	}
	
	public Cliente getCliente (Long id) {
		return clienteRepository.findById(id);
	}
	
	public List<Cliente> getAllClientes (){
		return clienteRepository.findAll();
	}
	
	public void removeCliente (Long id) throws BusinessException {
		clienteRepository.deleteById(id);
	}
	
	public void updateCliente (Cliente cliente) throws BusinessException {
		if(cliente.getNome() != null) {
			Cliente novoCliente = clienteRepository.findById(cliente.getId());
			novoCliente.setNome(cliente.getNome());
			clienteRepository.update(novoCliente);			
		} else {
			throw new BusinessException("Nome não pode estar vazio!");
		}

	}
}
