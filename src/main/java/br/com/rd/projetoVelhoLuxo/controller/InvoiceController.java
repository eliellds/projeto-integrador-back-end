package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.FlagDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.InvoiceDTO;
import br.com.rd.projetoVelhoLuxo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/{id}")
    public InvoiceDTO updateById(@RequestBody InvoiceDTO dto, @PathVariable("id") Long id){
        return invoiceService.updateInvoice(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        invoiceService.deleteInvoice(id);
    }
}
