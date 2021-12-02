package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "tb_prazo_frete")
@Data
public class DeliveryDate {

    @Id
    @Column(name = "cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "cl_uf", length = 20)
    private String state;

    @Column(nullable = false, name = "cl_acrescimo_data", length = 20)
    private Long addDate;

    @Column(name = "cl_valor_frete")
    private Double deliveryPrice;
}
