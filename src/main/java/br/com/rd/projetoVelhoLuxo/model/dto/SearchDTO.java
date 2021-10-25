package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchDTO {

    private Long id;
    private LocalDateTime searchDate;
    private String searchContent;

}
