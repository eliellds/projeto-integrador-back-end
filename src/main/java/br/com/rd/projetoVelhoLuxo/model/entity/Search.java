package br.com.rd.projetoVelhoLuxo.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

// entidade para armazenar todas as buscas feitas na barra de busca do site
@Data
@Entity(name = "tb_busca")
public class Search {

    @Id
    @Column(name = "cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cl_data_busca", nullable = false)
    private LocalDateTime searchDate; // data da busca
    @Column(name = "cl_conteudo_busca", nullable = false, length = 100)
    private String searchContent; // conteúdo buscado

}
