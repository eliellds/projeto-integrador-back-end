package br.com.rd.projetoVelhoLuxo.repository.custom;

import br.com.rd.projetoVelhoLuxo.model.entity.ItemsOrder;

import java.util.List;

public interface ItemsOrderRepositoryCustom {

    List<ItemsOrder> findFirst1ByCompositeKeyOrderIdOrderByCompositeKeyIdItemDesc(Long id);

    List<ItemsOrder> findAllByCompositeKeyOrderIdOrderByCompositeKeyOrderIdDesc(Long id);

    List<ItemsOrder> findAllByOrderByCompositeKeyOrderIdDesc();

    List<ItemsOrder> findAllByCompositeKeyOrderMyUserIdOrderByCompositeKeyOrderIdDesc(Long id);

}
