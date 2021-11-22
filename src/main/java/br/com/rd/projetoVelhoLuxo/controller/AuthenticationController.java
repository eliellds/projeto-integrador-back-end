package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.request.AuthenticationRequestDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.response.AuthenticationResponseDTO;
import br.com.rd.projetoVelhoLuxo.service.util.JwtUtil;
import br.com.rd.projetoVelhoLuxo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping // endpoint para logar com email e senha de usuário já registrado
//    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody  AuthenticationRequestDTO authenticationRequest) throws Exception {

        try {
            // authenticationManager verifica as credenciais passadas (email e senha)
            // método vindo de SecurityConfigurer
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword()
                    )
            );

        } catch (Exception e) {
            // tratamento de erro caso usuário ou senha estejam incorretos
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        // caso a verificação seja feita com sucesso, é um objeto do tipo UserDetails é instanciado
        // e é atribuído a ele os dados do usuário encontrado pelo método loadUserByUsername
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        // é então gerado um token passando userDetails como parâmetro para criação
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // por fim, retorna para a view um token jwt através da classe AuthenticationResponseDTO
        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt, authenticationRequest.getUsername()));
    }

}
