package br.com.rd.projetoVelhoLuxo.model.entity;

import br.com.rd.projetoVelhoLuxo.model.dto.UserAddressCompositeKeyDTO;
import br.com.rd.projetoVelhoLuxo.model.embeddable.UserAddressCompositeKey;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "Endereco_Usuario")
@Data
public class UserAddress {
    @EmbeddedId
    private UserAddressCompositeKey id;
    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name="cl_id_usuario")
    private User user;
    @ManyToOne
    @MapsId("idAddress")
    @JoinColumn(name="cl_id_endereco")
    private Address address;
    @Column(name = "cl_descricao")
    private String description;





}
