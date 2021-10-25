package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.Products;

import java.util.List;

public interface ProductsRepositoryCustom {

    List<Products> searchByDescription(String description);

}
