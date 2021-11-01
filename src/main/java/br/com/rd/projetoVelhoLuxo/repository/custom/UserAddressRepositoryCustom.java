package br.com.rd.projetoVelhoLuxo.repository.custom;

import br.com.rd.projetoVelhoLuxo.model.entity.UserAddress;

import java.util.List;

public interface UserAddressRepositoryCustom {
    List<UserAddress> findAllByUser(Long id);
}
