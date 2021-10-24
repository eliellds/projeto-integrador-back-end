package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.TelephoneDTO;
import br.com.rd.projetoVelhoLuxo.service.TelephoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telephone")
public class TelephoneController {
    @Autowired
    TelephoneService service;
    @PostMapping
    public TelephoneDTO create(@RequestBody TelephoneDTO toCreate){

        return service.create(toCreate);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        service.deleteById(id);
    }

    @GetMapping
    public List<TelephoneDTO> showList(){
        return service.showList();
    }

    @GetMapping("/find/{id}")
    public TelephoneDTO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PutMapping
    public TelephoneDTO update (@RequestBody TelephoneDTO toUpdate){
        return service.updateById(toUpdate);
    }

}
