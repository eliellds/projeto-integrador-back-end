package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.PriceProduct;
import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepositoryCustom {

    List<Products> searchByDescription(String description);

    List<Products> searchByOffers();

}
