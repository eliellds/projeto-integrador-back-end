package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name = "tb_nota_fiscal")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "cl_descricao")
    private String description;

}
