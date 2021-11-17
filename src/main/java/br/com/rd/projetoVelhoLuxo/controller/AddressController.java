package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.AddressDTO;
import br.com.rd.projetoVelhoLuxo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("address")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
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

    @GetMapping("/find/{id}")
    public AddressDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

}
