package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.exception.UserNotFoundException;
import br.com.rd.projetoVelhoLuxo.model.dto.PasswordRecoveryDTO;
import br.com.rd.projetoVelhoLuxo.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgotpassword")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public PasswordRecoveryDTO sendPasswordRecoveryEmail(@RequestBody PasswordRecoveryDTO emailForRecovery){
        String email = emailForRecovery.getEmail();
        String token = RandomString.make(45);
        userService.findByEmail(email);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:3000/newpassword" + token;
            userService.sendPasswordRecoveryEmail(email, resetPasswordLink);
        } catch (UserNotFoundException e){
            e.getMessage();
        }

        return emailForRecovery;
    }


}
