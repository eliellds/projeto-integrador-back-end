package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "tb_metodo_pagamento")
public class PaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "cl_descricao")
    private String description;

    @Column(nullable = true, name = "cl_parcela")
    private String installments;
}
