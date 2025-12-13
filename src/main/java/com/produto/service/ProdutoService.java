package com.produto.service;

import com.produto.model.Produto;
import com.util.BusinessException;

import java.util.List;

public interface ProdutoService {

    Produto createProduto(Produto produto) throws BusinessException;
    List<Produto> findAll();
    void updateProduto (Produto produto) throws BusinessException;
    void deleteProduto (Produto produto) throws BusinessException;

}
