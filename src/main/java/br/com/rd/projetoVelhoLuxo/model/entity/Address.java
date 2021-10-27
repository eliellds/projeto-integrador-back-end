package br.com.rd.projetoVelhoLuxo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "tb_endereco")
@Data
public class Address  {
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
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private Set<UserAddress> address = new HashSet<>();
}
