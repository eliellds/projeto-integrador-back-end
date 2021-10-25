package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.SearchDTO;
import br.com.rd.projetoVelhoLuxo.service.ProductsSERV;
import br.com.rd.projetoVelhoLuxo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller para gravar as buscas e retornar os resultados buscados
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

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
    public List<ProductsDTO> searchByDescription(@RequestParam("content") String description) {
        return productsSERV.searchByDescription(description);
    }

}
