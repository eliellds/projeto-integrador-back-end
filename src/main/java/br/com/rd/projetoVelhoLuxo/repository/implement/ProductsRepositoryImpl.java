package br.com.rd.projetoVelhoLuxo.repository.implement;

import br.com.rd.projetoVelhoLuxo.model.entity.PriceProduct;
import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import br.com.rd.projetoVelhoLuxo.repository.contract.ProductsRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Products> searchByDescription(String description) {

        Query sql = entityManager.createNativeQuery
                ("SELECT * FROM tb_produtos " +
                                "INNER JOIN tb_categoria ON (tb_categoria.id = tb_produtos.id)" +
                                "WHERE cl_info_produto LIKE '%' :descript '%' " +
                                "OR cl_nm_produto LIKE '%' :descript '%' " +
                                "OR cl_caracteristica_produto LIKE '%' :descript '%' " +
                                "OR cl_nm_categoria LIKE '%' :descript '%'",
                        Products.class);
        sql.setParameter("descript", description);

        return sql.getResultList();

    }

    @Override
    public List<Products> searchByOffers() {

        Query sql = entityManager.createNativeQuery
                ("SELECT * FROM tb_preco_produto " +
                                "INNER JOIN tb_produtos ON (tb_produtos.id = tb_preco_produto.id_produto)" +
                                "WHERE id_promocao IS NOT NULL",
                        Products.class);

        return sql.getResultList();

    }

}
