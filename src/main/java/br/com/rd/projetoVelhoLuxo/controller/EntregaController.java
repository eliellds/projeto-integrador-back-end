package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.EntregaDTO;
import br.com.rd.projetoVelhoLuxo.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrega")
public class EntregaController {
    @Autowired
    EntregaService entregaService;

    @PostMapping
    public EntregaDTO create(@RequestBody EntregaDTO entrega){
        return entregaService.addEntrega(entrega);
    }

    @GetMapping
    public List<EntregaDTO> findAll(){
        return entregaService.findAllEntrega();
    }

    @GetMapping("/{id}")
    public EntregaDTO searchById(@PathVariable("id") Long id){
        return entregaService.searchEntregaById(id);
    }

    @PutMapping("/{id}")
    public EntregaDTO updateById(@RequestBody EntregaDTO dto, @PathVariable("id") Long id){
        return entregaService.updateById(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        entregaService.deleteById(id);
    }

}

