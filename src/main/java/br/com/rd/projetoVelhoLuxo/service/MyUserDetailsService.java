package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.entity.MyUser;
import br.com.rd.projetoVelhoLuxo.repository.contract.MyUserRepository;
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
    MyUserRepository userRepository;

    @Override // método que encontra o usuário pelo email (aqui passado como parametro username)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // atribuindo o usuário encontrado pelo método findByEmailEquals ao objeto user
        MyUser myUser = userRepository.findByEmailEquals(username);

        if (myUser == null) { // conferindo se o usuário retornou null
            throw new UsernameNotFoundException(username);
        }

        // retorna novo objeto User (classe UserDetails)
        return new User(myUser.getEmail(), myUser.getPassword(), new ArrayList<>());

    }

}
