package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.SalesDTO;
import br.com.rd.projetoVelhoLuxo.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    SalesService salesService;

    @PostMapping
    public SalesDTO create(@RequestBody SalesDTO sale) {
        return salesService.createSale(sale);
    }

    @GetMapping
    public List<SalesDTO> findAll() {
        return salesService.findAllSales();
    }

    @GetMapping("/{id}")
    public SalesDTO searchById(@PathVariable("id") Long id) {
        return salesService.searchById(id);
    }

    @PutMapping("/{id}")
    public SalesDTO updateById(@RequestBody SalesDTO dto, @PathVariable("id") Long id){
        return salesService.updateSale(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        salesService.deleteSale(id);
    }
}