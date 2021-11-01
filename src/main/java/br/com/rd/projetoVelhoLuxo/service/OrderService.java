package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.entity.*;
import br.com.rd.projetoVelhoLuxo.repository.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    TelephoneRepository telephoneRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    MyUserRepository myUserRepository;
    @Autowired
    FlagRepository flagRepository;
    @Autowired
    PaymentMethodsRepository paymentRepository;
    public OrderDTO create(OrderDTO toCreate){
        Order created = convertToOrder(toCreate);

        if(toCreate.getAddress()!=null) {
            Address address = new Address();
            if (toCreate.getAddress().getId() != null) {

                if (addressRepository.existsById(created.getAddress().getId())) {
                    address = addressRepository.getById(toCreate.getAddress().getId());

                }
            }else{
                address = addressRepository.save(convertToAddress(toCreate.getAddress()));
            }

            created.setAddress(address);
        }

        //user
        if(toCreate.getMyUser()!=null) {
            if (toCreate.getMyUser().getId() != null) {
                MyUser myUser = new MyUser();
                if (myUserRepository.existsById(toCreate.getMyUser().getId())) {
                    myUser = myUserRepository.getById(toCreate.getMyUser().getId());
                }

                created.setMyUser(myUser);
            }
        }

        //telephone
        if(created.getTelephone()!=null) {
            Telephone tel = new Telephone();
            if (created.getTelephone().getId() != null) {

                if (telephoneRepository.existsById(created.getTelephone().getId())) {
                    tel = telephoneRepository.getById(created.getTelephone().getId());
                }
            }else{
                tel = telephoneRepository.save(created.getTelephone());
            }
            created.setTelephone(tel);
        }
        //delivery
        if(created.getDelivery()!=null) {
            Delivery delivery = new Delivery();
            if (created.getDelivery().getId() != null) {

                if (deliveryRepository.existsById(created.getDelivery().getId())) {
                    delivery = deliveryRepository.getById(created.getDelivery().getId());
                }

            }else{
                delivery = deliveryRepository.save(created.getDelivery());
            }
            created.setDelivery(delivery);
        }
        if(created.getCard().getIdBandeira() != null){

            Flag flag = new Flag();
            if(flag.getId()!=null){
                if(flagRepository.existsById(created.getCard().getIdBandeira().getId())){

                    flag = flagRepository.getById(created.getCard().getIdBandeira().getId());

                }
            }else{
                flag = flagRepository.save(created.getCard().getIdBandeira());

            }

            created.getCard().setIdBandeira(flag);

        }

        //
        if(created.getCard()!=null) {
            Card card = new Card();
            if (created.getCard().getCardNumber() != null) {

                if (cardRepository.existsById(created.getCard().getCardNumber())) {
                    card = cardRepository.getById(created.getCard().getCardNumber());
                }else{
                    card = cardRepository.save(created.getCard());

                }

            }



            created.setCard(card);
        }
        if(created.getPayment()!=null) {
            PaymentMethods pay = new PaymentMethods();
            if (created.getPayment().getId() != null) {

                if (paymentRepository.existsById(created.getPayment().getId())) {
                    pay = paymentRepository.getById(created.getPayment().getId());
                }else{
                    pay = paymentRepository.save(created.getPayment());

                }

            }



            created.setPayment(pay);
        }
        LocalDate today = LocalDate.now();
        LocalDate deliveryDate = LocalDate.now().plusDays(120);

        created.setDateOrder(today);
        created.setDeliveryDate(deliveryDate);

        created = orderRepository.save(created);

        return convertToDTO(created);
    }
    public List<OrderDTO> findOrderByUser(Long id){

        return convertList(orderRepository.findAllByMyUserId(id));

    }




    //Conversões
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

    //convert to List()
    private List<OrderDTO> convertList (List<Order> list){
        List<OrderDTO> listDTO = new ArrayList<>();
        for (Order a : list) {
            listDTO.add(convertToDTO(a));
        }

        return listDTO;
    }

    //convert to final
    private Order convertToOrder(OrderDTO toConvert){
        Order converted = new Order();



//         myUser;
        if(toConvert.getMyUser()!=null) {

            converted.setMyUser(convertToUser(toConvert.getMyUser()));
        }
//
//        private PaymentMethodsDTO payment;
        if(toConvert.getPayment()!=null) {
            PaymentMethods convertedPayment = new PaymentMethods();
            convertedPayment.setDescription(toConvert.getPayment().getDescription());
            convertedPayment.setId(toConvert.getPayment().getId());
            convertedPayment.setInstallments(toConvert.getPayment().getInstallments());
            convertedPayment.setInstallments(toConvert.getPayment().getInstallments());
            converted.setPayment(convertedPayment);
        }

//        private AddressDTO address;

            Address address ;
            address = convertToAddress(toConvert.getAddress());
            converted.setAddress(address);


//        private TelephoneDTO telephone;
        if(toConvert.getTelephone()!=null){
            Telephone telephone = convertToTelephone(toConvert.getTelephone());
            converted.setTelephone(telephone);
        }

//
//        private CardDTO card;
        if(toConvert.getCard()!=null){
            Card card = dtoToCardBusiness(toConvert.getCard());
            converted.setCard(card);
        }
//        private DeliveryDTO delivery;
        if(toConvert.getDelivery()!=null){
            Delivery deliveryDTO = dtoToDeliveryBusiness(toConvert.getDelivery());
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
//        private Double deliveryValue;
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
    private MyUser convertToUser(MyUserDTO toConvert){
        MyUser converted = new MyUser();
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
        if(toConvert.getTelephone()!=null) {
            converted.setTelephone(convertToTelephone(toConvert.getTelephone()));
        }
        return converted;

    }

    /////////////conversões de telefone////////
    //convert Para telefone final
    private Telephone convertToTelephone(TelephoneDTO toConvert){
        Telephone converted = new Telephone();
        if (toConvert.getId()!=null){
            converted.setId(toConvert.getId());
        }
        if(toConvert.getNumber()!=null){
            converted.setNumber(toConvert.getNumber());
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

    private Address convertToAddress(AddressDTO addressDTO){
        Address address = new Address();
        //id
        if(address.getId()!=null) {
            address.setId(address.getId());
        }
        //cep
        address.setCep(addressDTO.getCep());
        //cidade
        address.setCity(addressDTO.getCity());
        //complemento
        if(addressDTO.getComplement()!=null){
            address.setComplement(addressDTO.getComplement());
        }
        //bairro
        address.setDistrict(addressDTO.getDistrict());
        //numero
        address.setNumber(addressDTO.getNumber());
        //referencia
        if(addressDTO.getReference() !=null){
            address.setReference(addressDTO.getReference());
        }
        //estado
        address.setState(addressDTO.getState());

        address.setStreet(addressDTO.getStreet());

        return address;

    }

    //card Conversões

    private Card dtoToCardBusiness(CardDTO dto) {
        Card business = new Card();

        business.setCardNumber(dto.getCardNumber());
        business.setCpf(dto.getCpf());
        business.setName(dto.getName());
        business.setBirthDate(dto.getBirthDate());
        business.setDueDate(dto.getDueDate());

        if (dto.getFlag() != null){
            Flag c  = new Flag();
            if (dto.getFlag().getId() != null){
                c.setId(dto.getFlag().getId());
            }
            if(dto.getFlag().getDescription()!=null){
                c.setDescription(dto.getFlag().getDescription());
            }
            business.setIdBandeira(c);
        }
        return business;
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

    private DeliveryDTO businessToDeliveryDto(Delivery business){
        DeliveryDTO dto = new DeliveryDTO();
        dto.setId(business.getId());
        dto.setDescricao(business.getDescricao());
        return dto;
    }


}
