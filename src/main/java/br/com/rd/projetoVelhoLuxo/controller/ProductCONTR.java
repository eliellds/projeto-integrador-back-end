package br.com.rd.projetoVelhoLuxo.controller;


import br.com.rd.projetoVelhoLuxo.model.dto.PriceProductDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductViewDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.PriceProduct;
import br.com.rd.projetoVelhoLuxo.service.PriceProductService;
import br.com.rd.projetoVelhoLuxo.service.ProductsSERV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ProductCONTR {

    @Autowired
    ProductsSERV productsSERV;

    @Autowired
    PriceProductService priceProductService;

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
    public ProductsDTO deleteProducts(@PathVariable("id") Long id) {
        return productsSERV.deleteProduct(id);
    }

    @GetMapping("/category/{categoryName}")
    public List<ProductViewDTO> searchByCategory(@PathVariable("categoryName") String categoryName) {
        return productsSERV.searchByCategory(categoryName);
    }

    @GetMapping("/newer")
    public List<ProductsDTO> searchByYearNewer() {
        return productsSERV.searchByYearNewer(); // retorna uma lista com os mais novos
    }

    @GetMapping("/older")
    public List<ProductsDTO> searchByYearOlder() {
        return productsSERV.searchByYearOlder(); // retorna uma lista com os mais antigos
    }

    @GetMapping("/offers")
    public List<ProductViewDTO> searchByOffers() {
        return productsSERV.searchByOffers();
    }

    @GetMapping("/recentlyAdd")
    public List<ProductViewDTO> searchByNewerAdd(){
        return productsSERV.findAllByOrderByIdDesc();
    }
}
