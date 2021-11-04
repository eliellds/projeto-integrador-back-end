package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.embeddable.ItemsOrderKey;
import br.com.rd.projetoVelhoLuxo.model.entity.ItemsOrder;
import br.com.rd.projetoVelhoLuxo.repository.custom.ItemsOrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsOrderRepository extends JpaRepository<ItemsOrder, ItemsOrderKey>, ItemsOrderRepositoryCustom {



}
