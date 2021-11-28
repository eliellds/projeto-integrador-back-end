package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByEmailEquals(String email);
    MyUser findByCpfEquals(String cpf);
    MyUser findByResetPasswordToken(String token);

}


