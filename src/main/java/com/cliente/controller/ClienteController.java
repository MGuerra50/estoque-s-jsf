package com.cliente.controller;

import java.io.Serializable;
import java.util.logging.*;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cliente.model.Cliente;
import com.cliente.service.ClienteService;
import com.util.BusinessException;

@Named
@ViewScoped
public class ClienteController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService clienteService;
	private Cliente cliente;
	private List<Cliente> clientes;
	private final String standardError = "Ocorreu um erro que está impedindo a operação. Por favor tente novamente mais tarde.";
	private static final Logger LOGGER = Logger.getLogger(ClienteController.class.getName());
	
	@PostConstruct
	public void init() {
		this.cliente = new Cliente();
		this.clientes = clienteService.getAllClientes();
	}
	
	private void addMessage (FacesMessage.Severity severity, String summary, String details) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, details));
	}
	
	public void getClienteById (Long id) {
		Cliente c = clienteService.getCliente(id);
		this.setCliente(c);
	}
	
	public void save () {
		try {
			if(this.cliente.getId() == null) {
				clienteService.createCliente(this.cliente);
				addMessage(FacesMessage.SEVERITY_INFO, "Success", "Cliente cadastrado");
			} else {
				clienteService.updateCliente(this.cliente);
				addMessage(FacesMessage.SEVERITY_INFO, "Success", "Cliente atualizado");
			}
			this.cliente = new Cliente();
			this.clientes = clienteService.getAllClientes();
		} catch (BusinessException be) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", be.getMessage());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", getStandardError());
		}
	}
	
	public void delete (Cliente c) {
		try {
			clienteService.removeCliente(c.getId());
			this.clientes.remove(c);
			addMessage(FacesMessage.SEVERITY_INFO, "Success", "Cliente excluído.");
		} catch (BusinessException be) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", be.getMessage());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", getStandardError());
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getStandardError() {
		return standardError;
	}
	
}
