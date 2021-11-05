package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.InvoiceDTO;
import br.com.rd.projetoVelhoLuxo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping
    public InvoiceDTO create(@RequestBody InvoiceDTO invoice) {
        return invoiceService.createInvoice(invoice);
    }

    @GetMapping
    public List<InvoiceDTO> findAll() {
        return invoiceService.findAllInvoices();
    }

    @GetMapping("/{id}")
    public InvoiceDTO searchById(@PathVariable("id") Long id) {
        return invoiceService.searchById(id);
    }

}
