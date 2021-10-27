package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
}
