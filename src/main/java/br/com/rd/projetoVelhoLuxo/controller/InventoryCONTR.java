package br.com.rd.projetoVelhoLuxo.controller;


import br.com.rd.projetoVelhoLuxo.model.dto.InventoryDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.service.InventorySERV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryCONTR {

    @Autowired
    InventorySERV inventorySERV;

    @PostMapping
    public InventoryDTO newInventory(@RequestBody InventoryDTO inventoryDTO) throws Exception {
        return inventorySERV.newInventory(inventoryDTO);
    }

    @GetMapping
    public List<InventoryDTO> showAllInventories() {
        return inventorySERV.showAllInventory();
    }

    @GetMapping("{id}")
    public InventoryDTO showInvById(@PathVariable("id") ProductsDTO id) {
        return inventorySERV.showInventoryById(id);
    }

    @PutMapping("/{id}")
    public InventoryDTO updateInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventorySERV.updateInventory(inventoryDTO);
    }
}
