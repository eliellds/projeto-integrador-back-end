package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "tb_endereco")
@Data
public class Address {
    @Id
    @Column(name="cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="cl_cep", nullable = false)
    private String cep;
    @Column(name="cl_cidade", nullable = false)
    private String city;
    @Column(name = "cl_estado")
    private String state;
    @Column(name="cl_bairro", nullable = false)
    private String district;
    @Column (name="cl_logradouro", nullable = false)
    private String street;
    @Column(name="cl_numero", nullable = false)
    private Integer number;
    @Column(name="cl_complemento")
    private String complement;
    @Column(name="cl_ponto_referencia")
    private String reference;
}
