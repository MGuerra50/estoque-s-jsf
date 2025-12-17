package com.produto.repository;

import com.produto.model.Produto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProdutoRepository {

    @PersistenceContext(unitName = "jsf-pu")
    private EntityManager entityManager;

    public void save(Produto produto) {
        entityManager.persist(produto);
    }

    public void delete(Produto produto) {
        entityManager.remove(produto);
    }

    public Produto find(Long id) {
        return entityManager.find(Produto.class, id);
    }

    public void  update(Produto produto) {
        entityManager.merge(produto);
    }

    public List<Produto> findAll()  {
        return entityManager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    public List<Produto> findByNome(String nome) {
        return entityManager.createQuery("SELECT p FROM Produto p WHERE p.nome LIKE :nome", Produto.class)
                .setParameter("nome", "%"+nome+"%").getResultList();
    }
}
