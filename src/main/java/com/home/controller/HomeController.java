package com.home.controller;

import com.cliente.service.ClienteService;
import com.produto.service.ProdutoService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class HomeController implements Serializable {

    @Inject
    private ClienteService clienteService;

    @Inject
    private ProdutoService produtoService;

    private int totalClientes;
    private int totalProdutos;

    @PostConstruct
    public void init(){
        this.totalClientes = clienteService.getAllClientes().size();
        this.totalProdutos = produtoService.findAll().size();
    }

    public int getTotalClientes() { return totalClientes; }
    public void setTotalClientes(int totalClientes) { this.totalClientes = totalClientes; }
    public int getTotalProdutos() { return totalProdutos; }
    public void setTotalProdutos(int totalProdutos) { this.totalProdutos = totalProdutos; }
}
