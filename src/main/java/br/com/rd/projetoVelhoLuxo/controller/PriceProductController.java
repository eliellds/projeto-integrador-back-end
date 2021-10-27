package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.PriceProductDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.service.PriceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/prices")
public class PriceProductController {

    @Autowired
    PriceProductService priceProductService;

    @PostMapping
    public PriceProductDTO create(@RequestBody PriceProductDTO priceProduct) throws Exception {
        return priceProductService.createPriceProduct(priceProduct);
    }

    @GetMapping
    public List<PriceProductDTO> findAll(){
        return priceProductService.findAll();
    }

    @GetMapping("/{id}/{date}")
    public PriceProductDTO searchId(@PathVariable("id") ProductsDTO id, @PathVariable("date")LocalDate date){
        return priceProductService.searchById(id, date);
    }

    @GetMapping("/allpricesasc")
    public List<PriceProductDTO> searchAllPricesAsc(){
        return this.priceProductService.findAllPricesAsc();
    }

    @GetMapping("/allpricesdesc")
    public List<PriceProductDTO> searchAllPricesDesc(){
        return this.priceProductService.findAllPricesDesc();
    }

}
