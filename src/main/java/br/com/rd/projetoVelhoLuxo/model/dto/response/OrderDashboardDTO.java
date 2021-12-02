package br.com.rd.projetoVelhoLuxo.model.dto.response;

import br.com.rd.projetoVelhoLuxo.model.dto.ItemsOrderDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.OrderDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDashboardDTO {

    private List<ItemsOrderDTO> productList;
    private Long orderNumber;
    private LocalDate date;
    private String status;
    private Double price;
    private LocalDate deliveryDate;
    private Long paymentID;

}
