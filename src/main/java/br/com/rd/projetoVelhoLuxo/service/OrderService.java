package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.enums.StatusEmail;
import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.dto.response.OrderDashboardDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.*;
import br.com.rd.projetoVelhoLuxo.repository.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    ItemsOrderRepository itemsOrderRepository;

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

            }

            created.setDelivery(delivery);
        }

        if(created.getCard() != null) {

            if (created.getCard().getIdBandeira() != null) {

                Flag flag = new Flag();
                if (created.getCard().getIdBandeira().getId() != null) {
                    if (flagRepository.existsById(created.getCard().getIdBandeira().getId())) {

                        flag = flagRepository.getById(created.getCard().getIdBandeira().getId());

                    }
                }

                created.getCard().setIdBandeira(flag);

            }
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
        created.setStatus(orderStatusRepository.getById(1L)); // sempre criado com status aguardando pagamento
        LocalDate today = LocalDate.now();

        created.setDateOrder(today);
        created.setDeliveryDate(toCreate.getDeliveryDate());

        created = orderRepository.save(created);

        return convertToDTO(created);
    }
    public List<OrderDTO> findOrderByUser(Long id){

        return convertList(orderRepository.findAllByMyUserId(id));

    }

    public OrderDTO findByIdOrder(Long id){
            if(orderRepository.existsById(id)){

               OrderDTO orderDTO = convertToDTO(orderRepository.getById(id));
                return orderDTO;
            }
        return null;
    }

    // Atualiza o status do pedido passando o id do pedido no path e o id do status no body
    public OrderDTO updateOrderStatusByIdOrder(OrderDTO dto, Long id) {
        Optional<Order> option = orderRepository.findById(id);

        if (option.isPresent()) {
            Order order = option.get();
            OrderStatus status = new OrderStatus();

            if (dto.getStatus().getId() != null) {
                status = orderStatusRepository.getById(dto.getStatus().getId());
            }

            if (dto.getStatus().getStatusDescription() != null) {
                status.setStatusDescription(dto.getStatus().getStatusDescription());
                orderStatusRepository.save(status);
            }

            order.setStatus(status);
            orderRepository.save((order));

            return convertToDTO(order);
        }
        return null;
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

        if(toConvert.getIdStore()!=null){
            converted.setIdStore(toConvert.getIdStore());
        }

        if(toConvert.getStatus()!=null) {
            converted.setStatus(businessToDtoStatus(toConvert.getStatus()));
        }

        return converted;
    }

    // Converte Status do Pedido business para Status do Pedido DTO
    private OrderStatusDTO businessToDtoStatus(OrderStatus business) {
        OrderStatusDTO dto = new OrderStatusDTO();
        dto.setId(business.getId());
        dto.setStatusDescription(business.getStatusDescription());

        return dto;
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
        if(toConvert.getAddress()!=null) {
            Address address;
            address = convertToAddress(toConvert.getAddress());
            converted.setAddress(address);
        }

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

        if(toConvert.getIdStore()!=null){
            converted.setIdStore(toConvert.getIdStore());
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
        if(addressDTO.getId()!=null) {
            address.setId(addressDTO.getId());
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
        if(dto.getId()!=null){
        business.setId(dto.getId());
        }
        business.setDescricao(dto.getDescricao());
        return business;
    }

    private DeliveryDTO businessToDeliveryDto(Delivery business){
        DeliveryDTO dto = new DeliveryDTO();
        dto.setId(business.getId());
        dto.setDescricao(business.getDescricao());
        return dto;
    }

    private String renderPayment(OrderDTO order) {

        Locale ptBr = new Locale("pt", "BR");

        if (order.getPayment().getId() == 1 || order.getPayment().getId() == 13) {
            return order.getPayment().getDescription();
        } else if (order.getPayment().getId() >=2 && order.getPayment().getId() <= 3) {
            return order.getPayment().getDescription() + " " +
                    order.getCard().getFlag().getDescription() + " " +
                    order.getPayment().getInstallments();
        } else if (order.getPayment().getId() >= 4 && order.getPayment().getId() <= 12) {
            String installments = NumberFormat.getCurrencyInstance(ptBr)
                    .format(order.getAmount() / Integer.parseInt(order.getPayment().getInstallments()));
            return order.getPayment().getDescription() + " " +
                    order.getCard().getFlag().getDescription() + " " +
                    order.getPayment().getInstallments() + "x de " +
                    (installments);
        }
        return null;
    }

    private String renderOrderItem(Long orderId){
        List <ItemsOrder> list = itemsOrderRepository.findAllByCompositeKeyOrderIdOrderByCompositeKeyOrderIdDesc(orderId);
        StringBuilder productList = new StringBuilder();
        Locale ptBr = new Locale("pt", "BR");

        for (ItemsOrder item : list) {
            String unit = NumberFormat.getCurrencyInstance(ptBr).format(item.getTotalPrice()/item.getQuantity());
            String total = NumberFormat.getCurrencyInstance(ptBr).format(item.getTotalPrice());
            productList.append("<div><b>Produto:</b> ").append(item.getProduct().getProduct()).append("<br>\n")
                    .append("<b>Quantidade:</b> ").append(item.getQuantity()).append("<br>\n")
                    .append("<b>Valor unitário:</b> ").append(unit).append("<br>\n")
                    .append("<b>Valor agregado:</b> ").append(total).append("</div><br><br>\n\n");
        }

        return productList.toString();
    }

    public Boolean sendConfirmationOrderEmail(OrderDTO toCreate) throws ParseException {
        EmailModel email = new EmailModel();
        Locale ptBr = new Locale("pt", "BR");
        String fretFormat = NumberFormat.getCurrencyInstance(ptBr).format(toCreate.getDeliveryValue());
        String totalFormat = NumberFormat.getCurrencyInstance(ptBr).format(toCreate.getAmount());
        String strDate = toCreate.getDateOrder().toString();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = formato.parse(strDate);
        formato.applyPattern("dd/MM/yyyy");
        String dataFormatada = formato.format(data);

        email.setSendDateEmail(LocalDateTime.now());
        email.setOwnerRef(toCreate.getId());
        email.setEmailTo(toCreate.getMyUser().getEmail());
        email.setEmailFrom("velholuxosac@gmail.com");
        email.setSubject("Pedido realizado com sucesso!");
        email.setText(String.format ("<img src='cid:logoImage' /><br>" +
                "<h1><b>Olá, " + toCreate.getMyUser().getFirstName() + ".</b></h1>" +
                "\n<p>Agradecemos sua aquisição no Velho Luxo Antiquário. " +
                "\n<br>Qualquer dúvida não hesite em nos procurar. " +
                "\n<br>A equipe Velho Luxo agradece!</p><br>\n" +
                "\n<br>" +
                "<hr><h2><b>Número do pedido:</b> " + toCreate.getId() + "<br><br>\n\n" + "</h2>" +
                renderOrderItem(toCreate.getId()) + "\n" +
                "<div><b>Nome do cliente:</b> " + toCreate.getMyUser().getFirstName() + " " + toCreate.getMyUser().getLastName() + "<br>\n" +
                "<b>Data do pedido:</b> " + dataFormatada + "<br>\n" +
                "<b>Frete:</b> " + fretFormat + "<br>\n" +
                "<b>Forma de pagamento:</b> " + renderPayment(toCreate) + "</div>" + "<br>\n" +
                "\n<br>" +
                "<div><b>Total:</b> " + totalFormat + "</div><br><br>\n\n" +
                "<hr><p>Esta mensagem servirá como o seu recibo.<br>\n" +
                "Você também pode visualizar o seu histórico de compras a qualquer momento em http://localhost:3000/dashboard/myorder</p>" +
                "\n\n<br><br><hr><img src='cid:logoImage' /><br>\n" +
                "<div>Velho Luxo Antiquário - Todos os direitos reservados</div><br>\n" +
                "<div>E-COMMERCE DE COLEÇÃO E DECORAÇÃO ANTIGAS E VALIOSAS LMTD.<br>\n" +
                "CNPJ: 21.636.886/0001-34 <br>\n" +
                "RUA FRANCISCO MARENGO, 1111 - 1 ANDAR<br>\n" +
                "TATUAPÉ - São Paulo/SP - CEP: 03313-000<br>\n</div>",
                toCreate.getMyUser().getFirstName()));
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(email.getEmailFrom());
            helper.setTo(email.getEmailTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getText(), true);

            ClassPathResource resource = new ClassPathResource("/static/images/velho-luxo.png");
            helper.addInline("logoImage", resource);

            emailSender.send(message);
            email.setStatusEmail(StatusEmail.SENT);

        } catch (MailException e){
            email.setStatusEmail(StatusEmail.ERROR);
            return false;

        } finally {
            emailRepository.save(email);
            return true;
        }
    }


}
