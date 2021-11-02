package br.com.rd.projetoVelhoLuxo.model.entity;


import br.com.rd.projetoVelhoLuxo.model.entity.Category;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "tb_produtos")
@Data
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "cl_nm_produto")
    private String product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_categoria")
    private Category categoryID;

    @Column(nullable = false, name = "cl_info_produto", length = 500)
    private String description;
    @Column(nullable = false, name = "cl_caracteristica_produto", length = 500)
    private String feature;
    @Column(nullable = true, name = "cl_ano_fabricacao")
    private String year;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_estado_conservacao")
    private ConservationState conservationState;

    @Column(nullable = false, name = "cl_qtd_pecas")
    private Integer quantity;
}
