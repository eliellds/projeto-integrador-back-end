package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.DeliveryDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDateRepository extends JpaRepository<DeliveryDate, Long> {

    DeliveryDate findAllByState(String state);

}
