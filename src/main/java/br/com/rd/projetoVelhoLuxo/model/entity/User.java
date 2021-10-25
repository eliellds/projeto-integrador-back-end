package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "tb_usuario")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cl_id")
    private Long id;
    @Column(name="cl_nm_usuario", length = 50, nullable = false)
    private String firstName;
    @Column(name="cl_sobrenome_usuario",nullable = false, length = 150)
    private String lastName;
    @Column(name =  "cl_cpf",unique = true, length = 11, nullable = false)
    private String cpf;
    @Column(name="cl_email", unique = true, nullable = false)
    private String email;
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "cl_id_telefone", referencedColumnName = "cl_id")
    private Telephone telephone;
    @Column(name="cl_data_nascimento", nullable = false)
    private LocalDate born;
    @Column(name = "cl_senha",nullable = false)
    private String password;
}
