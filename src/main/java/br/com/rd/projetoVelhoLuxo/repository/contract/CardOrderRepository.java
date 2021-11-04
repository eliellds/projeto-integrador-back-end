package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.entity.CardOrder;
import br.com.rd.projetoVelhoLuxo.model.entity.Order;
import br.com.rd.projetoVelhoLuxo.repository.custom.CardOrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardOrderRepository extends JpaRepository<CardOrder, Order>, CardOrderRepositoryCustom {



}
