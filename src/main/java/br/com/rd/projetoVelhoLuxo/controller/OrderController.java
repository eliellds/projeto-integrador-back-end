package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.OrderDTO;
import br.com.rd.projetoVelhoLuxo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService service;

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO toCreate){
        return service.create(toCreate);
    }

    @GetMapping("/{id}")
    public List<OrderDTO> findByUser(@PathVariable("id") Long id){
        return service.findOrderByUser(id);
    }
}
