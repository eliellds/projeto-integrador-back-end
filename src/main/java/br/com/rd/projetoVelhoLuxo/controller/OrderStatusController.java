package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.OrderStatusDTO;
import br.com.rd.projetoVelhoLuxo.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderStatus")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class OrderStatusController {

    @Autowired
    OrderStatusService orderStatusService;

    @GetMapping("/{id}")
    public OrderStatusDTO searchById(@PathVariable("id") Long id) {
        return orderStatusService.searchById(id);
    }

}
