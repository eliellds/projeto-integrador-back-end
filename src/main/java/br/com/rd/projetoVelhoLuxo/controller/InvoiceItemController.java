package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.InvoiceDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.InvoiceItemDTO;
import br.com.rd.projetoVelhoLuxo.service.InvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoiceItem")
public class InvoiceItemController {

    @Autowired
    InvoiceItemService invoiceItemService;

    @PostMapping
    public InvoiceItemDTO create(@RequestBody InvoiceItemDTO invoiceItem) throws Exception {
        return invoiceItemService.createInvoiceItem(invoiceItem);
    }

    @GetMapping
    public List<InvoiceItemDTO> findAll(){
        return invoiceItemService.findAll();
    }

    @GetMapping("/{id}/{invoiceId}")
    public InvoiceItemDTO searchId(@PathVariable("id") Long id, @PathVariable("invoiceId") InvoiceDTO invoiceId){
        return invoiceItemService.searchById(id, invoiceId);
    }
}
