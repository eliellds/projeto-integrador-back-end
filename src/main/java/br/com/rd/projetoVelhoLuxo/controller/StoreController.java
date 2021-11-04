package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.StoreDTO;
import br.com.rd.projetoVelhoLuxo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    StoreService storeService;

    @PostMapping
    public StoreDTO create(@RequestBody StoreDTO storeDTO) {
        return storeService.createStore(storeDTO);
    }

    @GetMapping
    public List<StoreDTO> findAll() {
        return storeService.findAllStores();
    }

    @GetMapping("/{id}")
    public StoreDTO searchById(@PathVariable("id") Long id) {
        return storeService.searchById(id);
    }

    @PutMapping("/{id}")
    public StoreDTO updateById(@RequestBody StoreDTO dto, @PathVariable("id") Long id){
        return storeService.updateStore(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        storeService.deleteStore(id);
    }
}
