package br.com.rd.projetoVelhoLuxo.model.embeddable;

import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
public class PriceProductKey implements Serializable {

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_produto")
    private Products products;

    @Column (nullable = false, name = "data_vigencia")
    private LocalDate effectiveDate;
}