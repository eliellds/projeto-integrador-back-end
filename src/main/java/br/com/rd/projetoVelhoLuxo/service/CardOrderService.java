package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.embeddable.CardOrderKey;
import br.com.rd.projetoVelhoLuxo.model.entity.*;
import br.com.rd.projetoVelhoLuxo.repository.contract.CardOrderRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.CardRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardOrderService {

    @Autowired
    CardOrderRepository cardOrderRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderRepository orderRepository;

    public CardOrderDTO linkCardToOrder(CardOrderDTO toLink) {
        CardOrder cardOrder = dtoToBusiness(toLink);

        cardOrder = cardOrderRepository.save(cardOrder);
        return businessToDto(cardOrder);

    }

    public List<CardOrderDTO> findall() {
        return listToDto(cardOrderRepository.findAll());
    }

    public List<CardOrderDTO> findById(Long id) {
        return listToDto(cardOrderRepository.findAllByCompositeKeyOrderId(id));
    }

//    public List<CardOrderDTO> findByCard(String cardNumber) {
//        return listToDto(cardOrderRepository.findAllByCardCardNumberOrderByCompositeKeyOrderIdDesc(cardNumber));
//    }

    private List<CardOrderDTO> listToDto(List<CardOrder> toConvert) {
        List<CardOrderDTO> list = new ArrayList<>();

        for (CardOrder convert : toConvert) {
            list.add(businessToDto(convert));
        }

        return list;
    }

    private CardOrder dtoToBusiness(CardOrderDTO dto) {
        CardOrder business = new CardOrder();
        CardOrderKey key = new CardOrderKey();
        key.setOrder(orderRepository.getById(dto.getCompositeKey().getOrderDTO().getId()));

        business.setCompositeKey(key);
        business.setCard(cardRepository.getById(dto.getCardDTO().getCardNumber()));

        return business;

    }

    private CardOrderDTO businessToDto(CardOrder business) {
        CardOrderDTO dto = new CardOrderDTO();
        CardOrderKeyDTO key = new CardOrderKeyDTO();

        key.setOrderDTO(convertToDTO(business.getCompositeKey().getOrder()));

        dto.setCompositeKey(key);
        dto.setCardDTO(businessToCardDto(business.getCard()));

        return dto;

    }

    private OrderDTO convertToDTO(Order toConvert){
        OrderDTO converted = new OrderDTO();

        converted.setId(toConvert.getId());

//         myUser;

        converted.setMyUser(
                convertToUserDTO(toConvert.getMyUser())
        );

//
//        private PaymentMethodsDTO payment;
        if(toConvert.getPayment()!=null) {
            PaymentMethodsDTO convertedPayment = new PaymentMethodsDTO();
            convertedPayment.setDescription(toConvert.getPayment().getDescription());
            convertedPayment.setId(toConvert.getPayment().getId());
            convertedPayment.setInstallments(toConvert.getPayment().getInstallments());
            convertedPayment.setInstallments(toConvert.getPayment().getInstallments());
            converted.setPayment(convertedPayment);
        }

//        private AddressDTO address;
        if(toConvert.getAddress()!=null) {
            AddressDTO address ;

            address = convertToAddressDTO(toConvert.getAddress());

            converted.setAddress(address);
        }

//        private TelephoneDTO telephone;
        if(toConvert.getTelephone()!=null){
            TelephoneDTO telephone = new TelephoneDTO();

            telephone.setId(toConvert.getTelephone().getId());


            telephone = convertToDTOTelphone(toConvert.getTelephone());


            converted.setTelephone(telephone);
        }

//
//        private CardDTO card;
        if(toConvert.getCard()!=null){
            CardDTO card = new CardDTO();

            card.setCardNumber(toConvert.getCard().getCardNumber());

            card = businessToCardDto(toConvert.getCard());

            converted.setCard(card);
        }
//        private DeliveryDTO delivery;
        if(toConvert.getDelivery()!=null){
            DeliveryDTO deliveryDTO = new DeliveryDTO();

            deliveryDTO.setId(toConvert.getDelivery().getId());

            deliveryDTO = businessToDeliveryDto(toConvert.getDelivery());

            converted.setDelivery(deliveryDTO);
        }
//        private LocalDate date_order;
        if(toConvert.getDateOrder()!=null){
            converted.setDateOrder(toConvert.getDateOrder());
        }
//        private LocalDate delivery_date;
        //data de entrega
        if(toConvert.getDeliveryDate()!=null){
            converted.setDeliveryDate(toConvert.getDeliveryDate());
        }

//        private Long qty_total;
        if(toConvert.getQtyTotal()!=null){
            converted.setQtyTotal(toConvert.getQtyTotal());
        }
//        private Double delivery_value;
        if(toConvert.getDeliveryValue()!=null){
            converted.setDeliveryValue(toConvert.getDeliveryValue());
        }
//        private Double totalDiscont;
        if(toConvert.getTotalDiscounts()!=null){
            converted.setTotalDiscounts(toConvert.getTotalDiscounts());
        }

//        private Double amount;
        //valor total
        if(toConvert.getAmount()!=null){
            converted.setAmount(toConvert.getAmount());
        }

//        private String bank_slip;
        if(toConvert.getBankSlip()!=null){
            converted.setBankSlip(toConvert.getBankSlip());
        }

        return converted;
    }

    private CardDTO businessToCardDto(Card business) {
        CardDTO dto = new CardDTO();

        dto.setCardNumber(business.getCardNumber());
        dto.setCpf(business.getCpf());
        dto.setBirthDate(business.getBirthDate());
        dto.setDueDate(business.getDueDate());
        dto.setName(business.getName());

        if (business.getIdBandeira() != null){
            FlagDTO flagDTO  = new FlagDTO();
            flagDTO.setId(business.getIdBandeira().getId());
            flagDTO.setDescription(business.getIdBandeira().getDescription());
            dto.setFlag(flagDTO);
        }
        return dto;
    }
    private Delivery dtoToDeliveryBusiness(DeliveryDTO dto){
        Delivery business = new Delivery();
        business.setDescricao(dto.getDescricao());
        return business;
    }

    //convertUser
    private MyUserDTO convertToUserDTO(MyUser toConvert){
        MyUserDTO converted = new MyUserDTO();
        //nascimento
        converted.setBorn(toConvert.getBorn());
        //cpf
        converted.setCpf(toConvert.getCpf());
        //primeiro nome
        converted.setFirstName(toConvert.getFirstName());
        //sobrenome
        converted.setLastName(toConvert.getLastName());
        //email
        converted.setEmail(toConvert.getEmail());
        //senha
        converted.setPassword(toConvert.getPassword());
        //id
        converted.setId(toConvert.getId());
        //telephone
        if(toConvert.getTelephone()!=null){
            converted.setTelephone(convertToDTOTelphone(toConvert.getTelephone()));
        }


        return converted;

    }

    //convert telefone DTO
    private TelephoneDTO convertToDTOTelphone(Telephone toConvert){
        TelephoneDTO converted = new TelephoneDTO();
        if(toConvert.getId()!=null) {
            converted.setId(toConvert.getId());
        }
        if(toConvert.getNumber()!=null) {
            converted.setNumber(toConvert.getNumber());
        }
        return converted;
    }

    private DeliveryDTO businessToDeliveryDto(Delivery business){
        DeliveryDTO dto = new DeliveryDTO();
        dto.setId(business.getId());
        dto.setDescricao(business.getDescricao());
        return dto;
    }

    //conversões de address
    private AddressDTO convertToAddressDTO(Address address){
        AddressDTO addressDTO = new AddressDTO();
        //id
        if(address.getId()!=null) {
            addressDTO.setId(address.getId());
        }
        //cep
        addressDTO.setCep(address.getCep());
        //cidade
        addressDTO.setCity(address.getCity());
        //complemento
        addressDTO.setComplement(address.getComplement());
        //bairro
        addressDTO.setDistrict(address.getDistrict());
        //numero
        addressDTO.setNumber(address.getNumber());
        //referencia
        addressDTO.setReference(address.getReference());
        //estado
        addressDTO.setState(address.getState());

        //rua
        addressDTO.setStreet(address.getStreet());

        return addressDTO;

    }

}
