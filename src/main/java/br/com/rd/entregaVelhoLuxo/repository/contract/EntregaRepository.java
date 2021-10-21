package br.com.rd.entregaVelhoLuxo.repository.contract;

import br.com.rd.entregaVelhoLuxo.model.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}
