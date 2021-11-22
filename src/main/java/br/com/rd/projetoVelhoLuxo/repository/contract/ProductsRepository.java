package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>,
                                            ProductsRepositoryCustom,
                                            ProductsRepositoryCustom2 {

    @Query(value = "SELECT *, AVG(cl_preco - cl_preco_promocional) AS discount FROM TB_PRODUTOS TP INNER JOIN TB_PRECO_PRODUTO TPP on (TP.ID = TPP.ID_PRODUTO) WHERE id_promocao is NOT NULL GROUP BY ID ORDER BY discount DESC LIMIT 2", nativeQuery = true)
    List<Products> highDiscountEmphasis();
    @Query(value = "SELECT * FROM tb_preco_produto " +
            "INNER JOIN tb_produtos ON (tb_produtos.id = tb_preco_produto.id_produto) " +
            "INNER JOIN tb_categoria ON (tb_categoria.id = tb_produtos.cl_id_categoria) " +
            "WHERE cl_info_produto LIKE '%' :descript '%' " +
            "OR cl_nm_produto LIKE '%' :descript '%' " +
            "OR cl_caracteristica_produto LIKE '%' :descript '%' " +
            "OR cl_ano_fabricacao LIKE '%' :descript '%' " +
            "OR cl_nm_categoria LIKE '%' :descript '%' " +
            "ORDER BY data_vigencia DESC ",
            nativeQuery = true)
    List<Products> searchByDescription2(@Param("descript") String description);
}
