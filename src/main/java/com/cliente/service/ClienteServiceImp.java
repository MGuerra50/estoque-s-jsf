package com.cliente.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cliente.model.Cliente;
import com.cliente.repository.ClienteRepository;
import com.produto.model.Produto;
import com.util.BusinessException;

@Stateless
public class ClienteServiceImp implements ClienteService {

    @Inject
    private ClienteRepository clienteRepository;

    public Cliente createCliente(Cliente cliente) throws BusinessException {
        if(!checkCpfDisponivel(cliente)){
            throw new BusinessException("Esse cpf já está em uso no sistema.");
        }
        if (cliente.getNome().isEmpty()) {
            throw new BusinessException("O campo nome não pode estar vazio!");
        } else if (cliente.getCpf().isEmpty()) {
            throw new BusinessException("CPF é um campo obrigatório, e não pode estar vazio!");
        }
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(cliente.getNome());
        novoCliente.setCpf(cliente.getCpf().replaceAll("\\D", ""));
        clienteRepository.save(novoCliente);
        return novoCliente;
    }

    public Cliente getCliente(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public void removeCliente(Cliente cliente) throws BusinessException {
        clienteRepository.deleteById(cliente);
    }

    public void updateCliente(Cliente cliente) throws BusinessException {
        if (cliente.getNome() == null) {
            throw new BusinessException("Nome não pode estar vazio!");
        } else if(cliente.getCpf() == null) {
            throw new BusinessException("CPF não pode estar vazio!");
        }
        cliente.setCpf(cliente.getCpf().replaceAll("\\D", ""));
        clienteRepository.update(cliente);
    }

    private boolean checkCpfDisponivel (Cliente cliente) throws BusinessException {
        List<Cliente> clientes = clienteRepository.findByCpf(cliente.getCpf().replaceAll("\\D", ""));
        if (!clientes.isEmpty()) {
            throw new BusinessException("Este cpf já está cadastrado no sistema.");
        }
        return true;
    }

    public List<Cliente> getSearch(Cliente cliente) {
        try{
            if(!cliente.getCpf().isEmpty()) {
                return clienteRepository.findByCpf(cliente.getCpf().replaceAll("\\D", ""));
            } else if (!cliente.getNome().isEmpty()) {
                return clienteRepository.findByNome(cliente.getNome());
            }
            return getAllClientes();

        } catch (Exception e) {
            throw new RuntimeException ("Nenhum cliente encontrado!");
        }
    }
}
