package com.produto.service;

import com.produto.model.Produto;
import com.produto.repository.ProdutoRepository;
import com.util.BusinessException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ProdutoServiceImp implements ProdutoService {

    @Inject
    private ProdutoRepository produtoRepository;

    public Produto createProduto(Produto produto) throws BusinessException {
        if (produto.getNome() == null || produto.getQuantidade() <= 0) {
            throw new BusinessException("Verifique os campos de nome e a quantidade e tente novamente!");
        }
        if (!checkProduto(produto)) {
            throw new RuntimeException("Não foi possível cadastrar o produto, tente novamente mais tarde!");
        }

        return produto;
    }

    private boolean checkProduto(Produto produto) throws BusinessException {
        List<Produto> produtos = produtoRepository.findByNome(produto.getNome());
        if(!produtos.isEmpty()){
            throw new BusinessException("Este produto já está cadastrado no sistema. Tente utilizar outro nome.");
        }
        return true;
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public void updateProduto (Produto produto) throws BusinessException {
        if (produto.getNome() == null || produto.getQuantidade() <= 0) {
            throw new BusinessException("Verifique os campos de nome e a quantidade e tente novamente!");
        }
        produtoRepository.update(produto);
    }

    public void deleteProduto (Produto produto) throws BusinessException {
        Produto p = produtoRepository.find(produto.getId());
        if (p != null) {
            produtoRepository.delete(produto);
        } else {
            throw new BusinessException("Produto já foi excluído!");
        }
    }
}
