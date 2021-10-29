package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.Products;

import java.util.List;

public interface ProductsRepositoryCustom2 {

    List<Products> findAllByCategoryIDCategoryContaining(String categoryName);
    List<Products> findFirst5ByOrderByIdDesc();
    List<Products> findAllByOrderByYearDesc();

    List<Products> findAllByOrderByYearAsc();

}
