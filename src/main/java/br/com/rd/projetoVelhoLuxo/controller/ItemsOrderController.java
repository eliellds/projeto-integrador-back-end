package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.ItemsOrderDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Order;
import br.com.rd.projetoVelhoLuxo.repository.contract.OrderRepository;
import br.com.rd.projetoVelhoLuxo.service.ItemsOrderService;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/itemsOrder")
public class ItemsOrderController {

    @Autowired
    ItemsOrderService itemsOrderService;

    @Autowired
    OrderRepository orderRepository;

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

    @GetMapping
    public List<ItemsOrderDTO> findAll() {
        return itemsOrderService.findAllList();
    }

    @GetMapping("/{orderId}")
    public List<ItemsOrderDTO> findByOrderId(@PathVariable("orderId") Long orderId) {
        return itemsOrderService.findAllByOrderId(orderId);
    }

    @GetMapping("/find")
    public ItemsOrderDTO findByCompositeKey(@RequestParam("item") Long item,
                                            @RequestParam("order") Long order) {
        try {
            Order newOrder = orderRepository.getById(order);
            return itemsOrderService.findByCompositeKey(item, newOrder);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                e.getMessage();
            }
            e.printStackTrace();
        }
        return null;
    }

//    @DeleteMapping("/delete")
//    public ItemsOrderDTO deleteById(@RequestParam("item") Long item,
//                                    @RequestParam("order") Long order) {
//        return itemsOrderService.deleteById(item, order);
//    }


}
