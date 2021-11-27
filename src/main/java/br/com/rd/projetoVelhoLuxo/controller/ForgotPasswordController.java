package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.exception.UserNotFoundException;
import br.com.rd.projetoVelhoLuxo.model.dto.MyUserDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.NewPasswordDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.PasswordDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.PasswordRecoveryDTO;
import br.com.rd.projetoVelhoLuxo.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/forgotpassword")
    public PasswordRecoveryDTO sendPasswordRecoveryEmail(@RequestBody PasswordRecoveryDTO emailForRecovery){
        String email = emailForRecovery.getEmail();
        String token = RandomString.make(45);
        userService.findByEmail(email);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:3000/newpassword/" + token;
            userService.sendPasswordRecoveryEmail(email, resetPasswordLink);
        } catch (UserNotFoundException e){
            e.getMessage();
        }

        return emailForRecovery;
    }

    @GetMapping("/reset")
    public MyUserDTO searchByToken(@Param(value = "token") String token){
        return userService.getByResetPasswordToken(token);
    }

    @PostMapping("/newpassword/{token}")
    public NewPasswordDTO resetPassword(@RequestBody NewPasswordDTO passwordDTO, @PathVariable("token") String token){
        String password = passwordDTO.getPassword();
        MyUserDTO myUserDTO = userService.getByResetPasswordToken(token);

        if(myUserDTO == null){
            throw new IllegalArgumentException("Token inválido");
        } else {
            userService.updatePassword(myUserDTO, password);
        }
        return passwordDTO;
    }

}
