package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.CardOrderDTO;
import br.com.rd.projetoVelhoLuxo.service.CardOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card-order")
public class CardOrderController {

    @Autowired
    CardOrderService cardOrderService;

    @PostMapping
    public CardOrderDTO linkCardToOrder(@RequestBody CardOrderDTO toLink) {
        return cardOrderService.linkCardToOrder(toLink);
    }

    @GetMapping
    public List<CardOrderDTO> findAll() {
        return cardOrderService.findall();
    }

    @GetMapping("/{id}")
    public List<CardOrderDTO> findByOrderId(@PathVariable("id") Long id) {
        return cardOrderService.findById(id);
    }

//    @GetMapping("/card/{number}")
//    public List<CardOrderDTO> findByCardNumber(@PathVariable("number") String number) {
//        return cardOrderService.findByCard(number);
//    }

}
