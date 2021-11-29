package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class OrderDTO {
    private Long id;
    private MyUserDTO myUser;
    private PaymentMethodsDTO payment;
    private AddressDTO address;
    private TelephoneDTO telephone;
    private CardDTO card;
    private DeliveryDTO delivery;
    private LocalDate dateOrder;
    private LocalDate deliveryDate;
    private Long qtyTotal;
    private Double deliveryValue;
    private Double totalDiscounts;
    private Double amount;
    private String bankSlip;
    private Long idStore;
    private OrderStatusDTO status;

}
