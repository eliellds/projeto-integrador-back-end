package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.ItemsOrderDTO;
import br.com.rd.projetoVelhoLuxo.service.ItemsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemsOrder")
public class ItemsOrderController {

    @Autowired
    ItemsOrderService itemsOrderService;

    @PostMapping
    public ItemsOrderDTO linkItemsToOrders(@RequestBody ItemsOrderDTO itemsOrder) {
        try {
            return itemsOrderService.linkItemsToOrder(itemsOrder);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                e.getMessage();
            }
            e.printStackTrace();
        }
        return null;
    }

}
