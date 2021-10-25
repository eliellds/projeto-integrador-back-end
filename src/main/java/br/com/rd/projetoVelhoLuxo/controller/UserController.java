package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.UserDTO;
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
    private UserDTO create(@RequestBody UserDTO create){
        return service.createUser(create);
    }

    @PutMapping
    private UserDTO update(@RequestBody UserDTO update){
        return service.update(update);
    }
    @GetMapping
    private List<UserDTO> showList(){
        return service.showList();
    }

    @GetMapping("/{id}")
    private UserDTO findUser(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    private  void deleteById(@PathVariable("id") Long id){
         service.deleteById(id);
    }


}
