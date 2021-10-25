package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.repository.contract.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override // método que encontra o usuário pelo email (aqui passado como parametro username)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // atribuindo o usuário encontrado pelo método findByEmailEquals ao objeto user
        br.com.rd.projetoVelhoLuxo.model.entity.User user = userRepository.findByEmailEquals(username);

        if (user == null) { // conferindo se o usuário retornou null
            throw new UsernameNotFoundException("User not found");
        }

        // caso encontre o usuário, o email(username) e o password são passados
        // para o objeto User da spring security,
        // então o email e a senha serão comparados com o que foi digitado
        // para gerar a autorização
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());

    }
}
