package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.embeddable.UserAddressCompositeKey;
import br.com.rd.projetoVelhoLuxo.model.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UserAddressCompositeKey> {
}
