package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.DeliveryDTO;
import br.com.rd.projetoVelhoLuxo.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrega")
public class EntregaController {
    @Autowired
    DeliveryService deliveryService;

    @PostMapping
    public DeliveryDTO create(@RequestBody DeliveryDTO entrega){
        return deliveryService.addEntrega(entrega);
    }

    @GetMapping
    public List<DeliveryDTO> findAll(){
        return deliveryService.findAllEntrega();
    }

    @GetMapping("/{id}")
    public DeliveryDTO searchById(@PathVariable("id") Long id){
        return deliveryService.searchEntregaById(id);
    }

    @PutMapping("/{id}")
    public DeliveryDTO updateById(@RequestBody DeliveryDTO dto, @PathVariable("id") Long id){
        return deliveryService.updateById(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        deliveryService.deleteById(id);
    }

}

