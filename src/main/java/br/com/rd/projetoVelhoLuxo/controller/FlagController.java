package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.FlagDTO;
import br.com.rd.projetoVelhoLuxo.service.FlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flags")
public class FlagController {
    @Autowired
    FlagService flagService;

    @PostMapping
    public FlagDTO create(@RequestBody FlagDTO flag) {
        return flagService.createFlag(flag);
    }

    @GetMapping
    public List<FlagDTO> findAll() {
        return flagService.findAllFlags();
    }

    @GetMapping("/{id}")
    public FlagDTO searchById(@PathVariable("id") Long id) {
        return flagService.searchById(id);
    }

    @PutMapping("/{id}")
    public FlagDTO updateById(@RequestBody FlagDTO dto, @PathVariable("id") Long id){
        return flagService.updateFlag(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        flagService.deleteFlag(id);
    }
}
