package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "tb_status_pedido")
@Data
public class OrderStatus {

    @Id
    @Column(name = "cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "cl_descricao_status", length = 20)
    private String statusDescription;

}
