package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.PriceProductDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductViewDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.SearchDTO;
import br.com.rd.projetoVelhoLuxo.service.PriceProductService;
import br.com.rd.projetoVelhoLuxo.service.ProductsSERV;
import br.com.rd.projetoVelhoLuxo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller para gravar as buscas e retornar os resultados buscados
@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    PriceProductService priceProductService;

    @Autowired
    ProductsSERV productsSERV;

    // grava a busca no banco de dados e retorna o json da busca criada
    @PostMapping
    public SearchDTO saveSearch(@RequestParam("content") String search) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchContent(search);
        return searchService.create(searchDTO);
    }

    // retorna uma lista com todos os produtos que contenham nome, descrição ou categoria buscada
    @GetMapping()
    public List<PriceProductDTO> searchByDescription(@RequestParam("description") String description) {
        return priceProductService.searchByDescription(description);
    }

    @GetMapping("/product")
    public  List<ProductViewDTO> searchByProductDescription(@RequestParam("description") String description) {
        return productsSERV.searchByDescription(description);
    }

}
