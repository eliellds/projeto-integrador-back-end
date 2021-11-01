package br.com.rd.projetoVelhoLuxo.repository.contract;


import br.com.rd.projetoVelhoLuxo.model.entity.MyUser;

public interface UserRepositoryCustom {

    MyUser findByEmailEquals(String email);

}
