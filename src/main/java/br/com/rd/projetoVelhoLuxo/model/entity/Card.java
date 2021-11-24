package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity(name = "tb_cartao")
public class Card {

    @Id
    @Column(nullable = false, name = "cl_nr_cartao",length = 555)
    private String cardNumber;

    @Column(nullable = false, name = "cl_nm_titular")
    private String name;

    @Column(nullable = false, name = "cl_cpf_titular")
    private String cpf;

    @Column(nullable = false, name = "cl_data_nascimento")
    private LocalDate birthDate;

    @Column(nullable = false, name = "cl_data_vencimento")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "cl_id_bandeira")
    private Flag idBandeira;

}
