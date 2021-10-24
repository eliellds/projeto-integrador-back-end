package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardDTO {
    private Long cardNumber;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private LocalDate dueDate;
    private FlagDTO flag;
}
