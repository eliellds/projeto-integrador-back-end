package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.AddressDTO;
import br.com.rd.projetoVelhoLuxo.servico.AddressService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("address")
public class AddressController {
    @Autowired
    AddressService service;

    @PostMapping
    public AddressDTO create(@RequestBody AddressDTO address){


        return service.Create(address);

    }

    @GetMapping
    public List<AddressDTO> showList(){

        return service.showList();

    }

    @PutMapping
    public AddressDTO updateById(@RequestBody AddressDTO beUpdate){
        return service.updateById(beUpdate);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id){
        service.deleteById(id);
    }

}
