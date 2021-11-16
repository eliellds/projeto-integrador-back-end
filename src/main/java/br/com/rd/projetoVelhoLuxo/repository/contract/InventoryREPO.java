package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.embeddable.InventoryKey;
import br.com.rd.projetoVelhoLuxo.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryREPO extends JpaRepository<Inventory, InventoryKey> {
    Inventory findByInventoryKeyProductsId(Long id);
}
