package br.com.rd.projetoVelhoLuxo.model.dto;

import br.com.rd.projetoVelhoLuxo.model.entity.Telephone;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
@Data
public class MyUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private TelephoneDTO telephone;
    private LocalDate born;
    private String password;
}
