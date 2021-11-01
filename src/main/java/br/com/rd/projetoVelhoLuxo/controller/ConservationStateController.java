package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.ConservationStateDTO;
import br.com.rd.projetoVelhoLuxo.service.ConservationStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
public class ConservationStateController {

    @Autowired
    ConservationStateService conservationStateService;

    @PostMapping
    public ConservationStateDTO create(@RequestBody ConservationStateDTO conservationState) {
        return conservationStateService.createConservationState(conservationState);
    }

    @GetMapping
    public List<ConservationStateDTO> findAll() {
        return conservationStateService.findAllStates();
    }

    @GetMapping("/{id}")
    public ConservationStateDTO searchById(@PathVariable("id") Long id) {
        return conservationStateService.searchById(id);
    }

    @PutMapping("/{id}")
    public ConservationStateDTO updateById(@RequestBody ConservationStateDTO dto, @PathVariable("id") Long id){
        return conservationStateService.updateConservationState(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        conservationStateService.deleteConservationState(id);
    }
}
