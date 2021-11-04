package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.MyUserDTO;
import br.com.rd.projetoVelhoLuxo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// controler para cadastrar novos usuários
@RestController
@RequestMapping("/sign-up") // endpoint da página de cadastro
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping
    public MyUserDTO registerUser(@RequestBody MyUserDTO myUserDTO) {
        return userService.createUser(myUserDTO);
    }

}
