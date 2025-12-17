package com.produto.controller;

import com.cliente.model.Cliente;
import com.produto.model.Produto;
import com.produto.service.ProdutoService;
import com.util.BusinessException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class ProdutoController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProdutoService produtoService;

    private Produto produto;
    private Produto searchProduto;
    private List<Produto> produtos;
    private static final String STANDARDERROR = "Um erro está impedindo a conclusão da operação. Por favor tente novamente mais tarde.";
    private static final Logger LOGGER = Logger.getLogger(ProdutoController.class.getName());

    @PostConstruct
    public void init() {
        this.searchProduto = new Produto();
        this.produto = new Produto();
        this.produtos = produtoService.findAll();
    }

    private void addMessageProduto(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void saveProduto() {
        try {
            if (produto.getId() == null) {
                produtoService.createProduto(this.produto);
                addMessageProduto(FacesMessage.SEVERITY_INFO, "Success", "Produto cadastrado.");
            } else {
                produtoService.updateProduto(this.produto);
                addMessageProduto(FacesMessage.SEVERITY_INFO, "Success", "Produto atualizado.");
            }
            this.produto = new Produto();
            this.produtos = produtoService.findAll();
        } catch (BusinessException be) {
            addMessageProduto(FacesMessage.SEVERITY_ERROR, "Error", be.getMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            addMessageProduto(FacesMessage.SEVERITY_ERROR, "Error", STANDARDERROR);
        }
    }

    public void updateProduto(Produto produto) {
        this.produto = produto;
    }

    public void getSearch() {
        try {
            List<Produto> search = produtoService.getSearch(searchProduto);
            if (!search.isEmpty()) {
                this.produtos = search;
            } else {
                addMessageProduto(
                        FacesMessage.SEVERITY_INFO,
                        "Não foram encontrados resultados para a pesquisa feita.",
                        ""
                );
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            addMessageProduto(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        }
    }

    public void deleteProduto(Produto p) {
        try {
            this.produtos.remove(p);
            produtoService.deleteProduto(p);
            addMessageProduto(FacesMessage.SEVERITY_INFO, "Success", "Produto deletado.");
        } catch (BusinessException be) {
            addMessageProduto(FacesMessage.SEVERITY_ERROR, "Error", be.getMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            addMessageProduto(FacesMessage.SEVERITY_ERROR, "Error", STANDARDERROR);
        }
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return this.produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Produto getSearchProduto() {
        return searchProduto;
    }

    public void setSearchProduto(Produto searchProduto) {
        this.searchProduto = searchProduto;
    }
}
