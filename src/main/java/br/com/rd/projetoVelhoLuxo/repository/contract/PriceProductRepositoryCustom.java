package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.embeddable.PriceProductKey;
import br.com.rd.projetoVelhoLuxo.model.entity.PriceProduct;
import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PriceProductRepositoryCustom {

    // metodo que retorna lista de produtos pela descricao, ano ou categoria
    @Query(value = "SELECT * FROM tb_preco_produto " +
            "INNER JOIN tb_produtos ON (tb_produtos.id = tb_preco_produto.id_produto) " +
            "INNER JOIN tb_categoria ON (tb_categoria.id = tb_produtos.cl_id_categoria) " +
            "WHERE cl_info_produto LIKE '%' :descript '%' " +
            "OR cl_nm_produto LIKE '%' :descript '%' " +
            "OR cl_caracteristica_produto LIKE '%' :descript '%' " +
            "OR cl_ano_fabricacao LIKE '%' :descript '%' " +
            "OR cl_nm_categoria LIKE '%' :descript '%' " +
            "GROUP BY id_produto " +
            "ORDER BY data_vigencia DESC",
    nativeQuery = true)
    List<PriceProduct> searchByDescription(@Param("descript") String description);

    // metodo que retorna lista de produtos em oferta
    @Query(value = "SELECT * FROM tb_preco_produto " +
            "INNER JOIN tb_produtos ON (tb_preco_produto.id_produto = tb_produtos.id)" +
            "WHERE id_promocao IS NOT NULL",
    nativeQuery = true)
    List<PriceProduct> searchByOffers();

    // metodo para retornar lista de produtos por categoria
    @Query(value = "SELECT * FROM tb_preco_produto " +
            "INNER JOIN tb_produtos ON (tb_produtos.id = tb_preco_produto.id_produto) " +
            "INNER JOIN tb_categoria ON (tb_categoria.id = tb_produtos.cl_id_categoria) " +
            "WHERE cl_nm_categoria LIKE '%' :category '%' " +
            "GROUP BY id_produto " +
            "ORDER BY data_vigencia DESC",
    nativeQuery = true)
    List<PriceProduct> searchByCategory(@Param("category") String category);

}
