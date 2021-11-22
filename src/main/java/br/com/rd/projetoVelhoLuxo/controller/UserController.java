package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.MyUserDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.response.UserHeaderDTO;
import br.com.rd.projetoVelhoLuxo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
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

    @GetMapping("/email/{email}")
    private UserHeaderDTO findByEmail(@PathVariable("email") String email) {
        try {
            return service.findByEmail(email);
        } catch (Exception e) {
            System.out.println("E-mail não encontrado " + e);
        }
        return null;

    }

    @GetMapping("/cpf/{cpf}")
    private UserHeaderDTO findByCpf(@PathVariable("cpf") String cpf) {
        try {
            return service.findByCpf(cpf);
        } catch (Exception e) {
            System.out.println("CPF não encontrado " + e);
        }
        return null;

    }

    @DeleteMapping("/{id}")
    private  void deleteById(@PathVariable("id") Long id){
         service.deleteById(id);
    }


}
