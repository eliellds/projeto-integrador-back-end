package br.com.rd.projetoVelhoLuxo.controller;


import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.service.ProductsSERV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductCONTR {

    @Autowired
    ProductsSERV productsSERV;

    @PostMapping
    public ProductsDTO newProduct(@RequestBody ProductsDTO product) {
        return productsSERV.newProduct(product);
    }

    @GetMapping
    public List<ProductsDTO> showProducts() {
        return productsSERV.showProducts();
    }

    @GetMapping("/{id}")
    public ProductsDTO showProductsById(@PathVariable("id") Long id) {
        return productsSERV.showProductsById(id);
    }

    @PutMapping("/{id}")
    public ProductsDTO updateProducts(@RequestBody ProductsDTO product, @PathVariable("id") Long id) {
        return productsSERV.updateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public  ProductsDTO deleteProducts(@PathVariable("id") Long id) {
        return productsSERV.deleteProduct(id);
    }
}