package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.MyUserDTO;
import br.com.rd.projetoVelhoLuxo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// controler para cadastrar novos usuários
@RestController
@RequestMapping("/sign-up") // endpoint da página de cadastro
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping
    public MyUserDTO registerUser(@RequestBody MyUserDTO myUserDTO) {
        return userService.createUser(myUserDTO);
    }

}
