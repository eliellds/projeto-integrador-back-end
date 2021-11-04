package br.com.rd.projetoVelhoLuxo.model.entity;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "tb_categoria")
@Data
public class Category {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, name = "cl_nm_categoria")
    private String category;

    @Column(nullable = true, name = "cl_descricao")
    private String description;
}
