package br.com.rd.projetoVelhoLuxo.repository.contract;

import br.com.rd.projetoVelhoLuxo.model.embeddable.InvoiceItemKey;
import br.com.rd.projetoVelhoLuxo.model.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository <InvoiceItem, InvoiceItemKey>{
}
