package br.com.rd.projetoVelhoLuxo.model.entity;

import br.com.rd.projetoVelhoLuxo.model.embeddable.UserAddressCompositeKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "tb_endereco_usuario")
@Data
@EqualsAndHashCode
public class UserAddress {

    @EmbeddedId
    private UserAddressCompositeKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cl_id_usuario")
    @MapsId("idUser")
    private MyUser myUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idAddress")
    @JoinColumn(name="cl_id_endereco")
    private Address address;

    @Column(name = "cl_descricao")
    private String description;





}
