package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.PaymentMethodsDTO;
import br.com.rd.projetoVelhoLuxo.service.PaymentMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentMethodsController {

    @Autowired
    PaymentMethodsService paymentMethodsService;

    @PostMapping
    public PaymentMethodsDTO create(@RequestBody PaymentMethodsDTO paymentMethod) {
        return paymentMethodsService.createPaymentMethod(paymentMethod);
    }

    @GetMapping
    public List<PaymentMethodsDTO> findAll() {
        return paymentMethodsService.findAllPaymentMethods();
    }

    @GetMapping("/{id}")
    public PaymentMethodsDTO searchById(@PathVariable("id") Long id) {
        return paymentMethodsService.searchById(id);
    }

    @PutMapping("/{id}")
    public PaymentMethodsDTO updateById(@RequestBody PaymentMethodsDTO dto, @PathVariable("id") Long id){
        return paymentMethodsService.updatePaymentMethod(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        paymentMethodsService.deletePaymentMethod(id);
    }
}
