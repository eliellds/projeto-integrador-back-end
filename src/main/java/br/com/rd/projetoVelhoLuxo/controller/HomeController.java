package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.CategoryDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.service.CategorySERV;
import br.com.rd.projetoVelhoLuxo.service.ProductsSERV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    ProductsSERV productsSERV;

    @Autowired
    CategorySERV categorySERV;

    @GetMapping
    public List<List> getHomeLists() {
        List<List> list = new ArrayList<>();
        List<CategoryDTO> categories = categorySERV.showCategory();
        List<ProductsDTO> products = productsSERV.searchByOffers();

        list.add(categories);
        list.add(products);

        return list;
    }

}
