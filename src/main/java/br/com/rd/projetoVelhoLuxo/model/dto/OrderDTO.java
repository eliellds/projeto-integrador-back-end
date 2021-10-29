package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
@Data
public class OrderDTO {
    private Long id;
//    ID_CLIENTE (FK) INT NOT NULL
//    ID_FORMA_PAGAMENTO (FK) TINY INT NOT NULL
//    ID_ENDERECO_ENTREGA (FK) INT NOT NULL
//    ID_TELEFONE_ENTREGA (FK) INT NOT NULL
//    ID_TIPO_ENTREGA (FK) TINY INT NOT NULL
    private LocalDate date_order;
    private LocalDate delivery_date;
    private Long qty_total;
    private Double delivery_value;
    private Double totalDiscont;
    private Double amount;
    private String bank_slip;
}
