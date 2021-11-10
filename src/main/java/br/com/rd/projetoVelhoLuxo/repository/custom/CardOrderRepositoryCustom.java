package br.com.rd.projetoVelhoLuxo.repository.custom;

import br.com.rd.projetoVelhoLuxo.model.entity.CardOrder;

import java.util.List;

public interface CardOrderRepositoryCustom {

    List<CardOrder> findAllByCompositeKeyOrderId(Long id);

//    List<CardOrder> findAllByCardCardNumberOrderByCompositeKeyOrderIdDesc(String cardNumber);

}
