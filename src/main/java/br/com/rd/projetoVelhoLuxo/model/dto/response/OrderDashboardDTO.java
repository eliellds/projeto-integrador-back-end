package br.com.rd.projetoVelhoLuxo.model.dto.response;

import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDashboardDTO {

    private List<ProductsDTO> productList;
    private Long orderNumber;
    private LocalDate date;
    private String status;
    private Double price;

}
