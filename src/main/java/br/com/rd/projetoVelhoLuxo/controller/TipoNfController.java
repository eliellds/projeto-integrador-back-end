package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.TipoNfDTO;

import br.com.rd.projetoVelhoLuxo.service.TipoNfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoNF")
public class TipoNfController {
    @Autowired
    TipoNfService tipoNfService;

    @PostMapping
    public TipoNfDTO create(@RequestBody TipoNfDTO tipoNf){
        return tipoNfService.addTipoNf(tipoNf);
    }

    @GetMapping
    public List<TipoNfDTO> findAll(){
        return tipoNfService.findAllTipoNf();
    }

    @GetMapping("/{id}")
    public TipoNfDTO searchById(@PathVariable("id") Long id){
        return tipoNfService.searchTipoNfById(id);
    }

    @PutMapping("/{id}")
    public TipoNfDTO updateById(@RequestBody TipoNfDTO dto, @PathVariable("id") Long id){
        return tipoNfService.updateById(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        tipoNfService.deleteById(id);
    }

}

