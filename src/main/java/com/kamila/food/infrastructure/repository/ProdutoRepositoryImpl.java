package com.kamila.food.infrastructure.repository;

import com.kamila.food.domain.model.FotoProduto;
import com.kamila.food.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto foto) {
        manager.persist(foto);
        return foto;
    }

    @Override
    public void delete(FotoProduto foto) {
        manager.remove(foto);
    }

}
