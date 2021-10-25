package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.User;

public interface UserRepositoryCustom {

    User findByEmailEquals(String email);

}
