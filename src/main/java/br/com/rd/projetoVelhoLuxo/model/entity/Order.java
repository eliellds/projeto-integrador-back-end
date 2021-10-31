package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@Entity(name = "tb_pedido")
public class Order {

    @Id
    @Column(name="cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name = "cl_id_cliente" )
    private MyUser myUser;


    @ManyToOne
    @JoinColumn(name = "cl_nr_cartao")
    private Card card;

    // forma de pagamento
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_forma_pagamento" )
    private PaymentMethods payment;

    // endereço de entrega
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cl_id_endereço_entrega")
    private Address address;

    //telefone entrega
    @ManyToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinColumn (name="cl_id_telefone_entrega")
    private Telephone telephone;


    //Tipo de entrega
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cl_id_tipo_entrega" )
    private Delivery delivery;


    @Column(name = "cl_data_pedido")
    private LocalDate dateOrder;

    @Column(name="cl_prazo_entrega" )
    private LocalDate deliveryDate;

    @Column(name="cl_qtd_itens")
    private Long qtyTotal;

    @Column(name="cl_valor_frete")
    private Double deliveryValue;

    @Column(name = "cl_desconto_total")
    private Double totalDiscounts;

    @Column(name="cl_valor_total")
    private Double amount;

    @Column(name="cl_nr_boleto")
    private String bankSlip;
}
