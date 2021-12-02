package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.DeliveryDateDTO;
import br.com.rd.projetoVelhoLuxo.service.DeliveryDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveryDate")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class DeliveryDateController {

    @Autowired
    DeliveryDateService dateService;

    @GetMapping("/{uf}")
    public DeliveryDateDTO searchById(@PathVariable("uf") String state) {
        return dateService.getByState(state);
    }

}
