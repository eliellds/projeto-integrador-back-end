package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.MyUserDTO;
import br.com.rd.projetoVelhoLuxo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;
    @PostMapping
    private MyUserDTO create(@RequestBody MyUserDTO create){
        return service.createUser(create);
    }

    @PutMapping
    private MyUserDTO update(@RequestBody MyUserDTO update){
        return service.update(update);
    }
    @GetMapping
    private List<MyUserDTO> showList(){
        return service.showList();
    }

    @GetMapping("/{id}")
    private MyUserDTO findUser(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    private  void deleteById(@PathVariable("id") Long id){
         service.deleteById(id);
    }


}
