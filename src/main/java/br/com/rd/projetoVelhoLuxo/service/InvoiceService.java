package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.entity.*;
import br.com.rd.projetoVelhoLuxo.repository.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    TipoNfRepository tipoNfRepository;

    @Autowired
    MyUserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    StoreRepository storeRepository;

/*    conversão business to dto (Nota Fiscal)*/
    private InvoiceDTO businessToDto(Invoice business) {
        InvoiceDTO dto = new InvoiceDTO();

        dto.setId(business.getId());
        dto.setInvoiceNumber(business.getInvoiceNumber());
        dto.setAccessKeyNumber(business.getAccessKeyNumber());
        dto.setStateInvoice(business.getStateInvoice());
        dto.setShipping(business.getShipping());
        dto.setTotalIPI(business.getTotalIPI());
        dto.setTotalICMS(business.getTotalICMS());
        dto.setTotalDiscount(business.getTotalDiscount());
        dto.setTotalPrice(business.getTotalPrice());

//        Tipo Nota Fiscal
        if (business.getInvoiceTypeId() != null){
            TipoNfDTO invoiceTypeDTO  = new TipoNfDTO();

            invoiceTypeDTO.setId(business.getInvoiceTypeId().getId());
            invoiceTypeDTO.setDescription(business.getInvoiceTypeId().getDescription());

            dto.setInvoiceTypeId(invoiceTypeDTO);
        }

//        Loja
        if (business.getStoreId() != null){
            StoreDTO storeDTO = new StoreDTO();

            storeDTO.setId(business.getStoreId().getId());
            storeDTO.setName(business.getStoreId().getName());
            storeDTO.setEmail(business.getStoreId().getEmail());
            storeDTO.setCnpj(business.getStoreId().getCnpj());
            storeDTO.setStateRegistration(business.getStoreId().getStateRegistration());

            if (business.getStoreId().getTelephoneID() != null) {
                TelephoneDTO telephoneDTO = new TelephoneDTO();

                telephoneDTO.setId(business.getStoreId().getTelephoneID().getId());
                telephoneDTO.setNumber(business.getStoreId().getTelephoneID().getNumber());

                storeDTO.setTelephoneID(telephoneDTO);
            }

            if (business.getStoreId().getAddressID() != null) {
                AddressDTO addressDTO= new AddressDTO();

                addressDTO.setId(business.getStoreId().getAddressID().getId());
                addressDTO.setStreet(business.getStoreId().getAddressID().getStreet());
                addressDTO.setNumber(business.getStoreId().getAddressID().getNumber());
                addressDTO.setComplement(business.getStoreId().getAddressID().getComplement());
                addressDTO.setReference(business.getStoreId().getAddressID().getReference());
                addressDTO.setCity(business.getStoreId().getAddressID().getCity());
                addressDTO.setDistrict(business.getStoreId().getAddressID().getDistrict());
                addressDTO.setState(business.getStoreId().getAddressID().getState());
                addressDTO.setCep(business.getStoreId().getAddressID().getCep());

                storeDTO.setAddressID(addressDTO);
            }

            dto.setStoreId(storeDTO);
        }

//      Usuário
        if (business.getUserId() != null){
            MyUserDTO userDTO = new MyUserDTO();

            userDTO.setId(business.getUserId().getId());
            userDTO.setFirstName(business.getUserId().getFirstName());
            userDTO.setLastName(business.getUserId().getLastName());
            userDTO.setBorn(business.getUserId().getBorn());
            userDTO.setEmail(business.getUserId().getEmail());
            userDTO.setCpf(business.getUserId().getCpf());

            if (business.getUserId().getTelephone() != null) {
                TelephoneDTO telephoneDTO = new TelephoneDTO();

                telephoneDTO.setId(business.getUserId().getTelephone().getId());
                telephoneDTO.setNumber(business.getUserId().getTelephone().getNumber());

                userDTO.setTelephone(telephoneDTO);
            }
            dto.setUserId(userDTO);
        }

//        Pedido
        if (business.getOrderID() != null){
            OrderDTO orderDTO = new OrderDTO();

            orderDTO.setId(business.getOrderID().getId());
            orderDTO.setDateOrder(business.getOrderID().getDateOrder());
            orderDTO.setAmount(business.getOrderID().getAmount());
            orderDTO.setDeliveryValue(business.getOrderID().getDeliveryValue());
            orderDTO.setQtyTotal(business.getOrderID().getQtyTotal());
            orderDTO.setTotalDiscounts(business.getOrderID().getTotalDiscounts());
//            orderDTO.setBankSlip(business.getOrderID().getBankSlip());

//            tipo entrega
            if (business.getOrderID().getDelivery() != null) {
                DeliveryDTO deliveryDTO = new DeliveryDTO();

                deliveryDTO.setId(business.getOrderID().getDelivery().getId());
                deliveryDTO.setDescricao(business.getOrderID().getDelivery().getDescricao());

                orderDTO.setDelivery(deliveryDTO);
            }

//            endereço
            if (business.getOrderID().getAddress() != null) {
                AddressDTO addressDTO = new AddressDTO();

                addressDTO.setId(business.getOrderID().getAddress().getId());
                addressDTO.setStreet(business.getOrderID().getAddress().getStreet());
                addressDTO.setNumber(business.getOrderID().getAddress().getNumber());
                addressDTO.setComplement(business.getOrderID().getAddress().getComplement());
                addressDTO.setDistrict(business.getOrderID().getAddress().getDistrict());
                addressDTO.setCity(business.getOrderID().getAddress().getCity());
                addressDTO.setReference(business.getOrderID().getAddress().getReference());
                addressDTO.setState(business.getOrderID().getAddress().getState());
                addressDTO.setState(business.getOrderID().getAddress().getState());
                addressDTO.setCep(business.getOrderID().getAddress().getCep());

                orderDTO.setAddress(addressDTO);
            }

//            forma de pagamento
            if (business.getOrderID().getPayment()!= null) {
                PaymentMethodsDTO paymentMethodsDTO = new PaymentMethodsDTO();

                paymentMethodsDTO.setId(business.getOrderID().getPayment().getId());
                paymentMethodsDTO.setDescription(business.getOrderID().getPayment().getDescription());
                paymentMethodsDTO.setInstallments(business.getOrderID().getPayment().getInstallments());

                orderDTO.setPayment(paymentMethodsDTO);
            }

//            cartão
            if (business.getOrderID().getCard()!= null) {
                CardDTO cardDTO = new CardDTO();

                cardDTO.setName(business.getOrderID().getCard().getName());
                cardDTO.setCardNumber(business.getOrderID().getCard().getCardNumber());
                cardDTO.setCpf(business.getOrderID().getCard().getCpf());
                cardDTO.setDueDate(business.getOrderID().getCard().getDueDate());
                cardDTO.setBirthDate(business.getOrderID().getCard().getBirthDate());

//                bandeira
                if (business.getOrderID().getCard().getIdBandeira() != null) {
                    FlagDTO flagDTO = new FlagDTO();
                    Flag flag = business.getOrderID().getCard().getIdBandeira();

                    flagDTO.setId(flag.getId());
                    flagDTO.setDescription(flag.getDescription());

                    cardDTO.setFlag(flagDTO);
                }

                orderDTO.setCard(cardDTO);
            }

            dto.setOrderId(orderDTO);
        }

        return dto;
    }

/*    convert para dto to business (Nota fiscal)*/
    private Invoice dtoToBusiness(InvoiceDTO dto) {
        Invoice business = new Invoice();

        business.setId(dto.getId());
        business.setInvoiceNumber(dto.getInvoiceNumber());
        business.setStateInvoice(dto.getStateInvoice());
        business.setShipping(dto.getShipping());
        business.setIssueDate(dto.getIssueDate());
        business.setAccessKeyNumber(dto.getAccessKeyNumber());
        business.setTotalDiscount(dto.getTotalDiscount());
        business.setTotalICMS(dto.getTotalICMS());
        business.setTotalIPI(dto.getTotalIPI());
        business.setTotalPrice(dto.getTotalPrice());

//       Tipo Nota Fiscal
        if (dto.getInvoiceTypeId() != null){
            TipoNf invoiceType  = new TipoNf();

            if (dto.getInvoiceTypeId().getId() != null){
                invoiceType.setId(dto.getInvoiceTypeId().getId());
            } else {
                invoiceType.setDescription(dto.getInvoiceTypeId().getDescription());
            }
            business.setInvoiceTypeId(invoiceType);
        }

//        Loja
        if (dto.getStoreId() != null){
            Store store  = new Store();

            if (dto.getStoreId().getId() != null){
                store.setId(dto.getStoreId().getId());
            } else {
                store.setName(dto.getStoreId().getName());
                store.setEmail(dto.getStoreId().getEmail());
                store.setCnpj(dto.getStoreId().getCnpj());
                store.setStateRegistration(dto.getStoreId().getStateRegistration());
            }

            business.setStoreId(store);
        }

//      Usuário
        if (dto.getUserId() != null){
            MyUser myUser  = new MyUser();

            if (dto.getUserId().getId() != null){
                myUser.setId(dto.getUserId().getId());
            } else {
                myUser.setFirstName(dto.getUserId().getFirstName());
                myUser.setLastName(dto.getUserId().getLastName());
                myUser.setCpf(dto.getUserId().getCpf());
                myUser.setBorn(dto.getUserId().getBorn());
                myUser.setEmail(dto.getUserId().getEmail());
            }

            business.setUserId(myUser);
        }

//        Pedido
        if (dto.getOrderId() != null){
            Order order = new Order();

            if (dto.getOrderId().getId() != null){
                order.setId(dto.getOrderId().getId());
            } else {
                order.setDateOrder(dto.getOrderId().getDateOrder());
                order.setDeliveryValue(dto.getOrderId().getDeliveryValue());
                order.setDateOrder(dto.getOrderId().getDateOrder());
                order.setAmount(dto.getOrderId().getAmount());
                order.setQtyTotal(dto.getOrderId().getQtyTotal());
                order.setTotalDiscounts(dto.getOrderId().getTotalDiscounts());
//                order.setBankSlip(dto.getOrderId().getBankSlip());
            }

            business.setOrderID(order);
        }

        return business;
    }


    private List<InvoiceDTO> listToDto(List<Invoice> list){
        List<InvoiceDTO> listDto = new ArrayList<InvoiceDTO>();
        for (Invoice invoice: list){
            listDto.add(this.businessToDto(invoice));
        }
        return listDto;
    }

/*     Criar nova Nota Fiscal            */
    public InvoiceDTO createInvoice(InvoiceDTO invoice){
        Invoice newInvoice = this.dtoToBusiness(invoice);

//        Tipo de nota
        if (newInvoice.getInvoiceTypeId() != null) {
            Long id = newInvoice.getInvoiceTypeId().getId();
            TipoNf invoiceType;

            if (id != null) {
                invoiceType = this.tipoNfRepository.getById(id);
            } else {
                invoiceType = this.tipoNfRepository.save(newInvoice.getInvoiceTypeId());
            }
           newInvoice.setInvoiceTypeId(invoiceType);
        }

//      Loja
        if (newInvoice.getStoreId() != null) {
            Long id = newInvoice.getStoreId().getId();
            Store store;

            if (id != null) {
                store = this.storeRepository.getById(id);
            } else {
                store = this.storeRepository.save(newInvoice.getStoreId());
            }
            newInvoice.setStoreId(store);
        }

//        Usuario
        if (newInvoice.getUserId() != null) {
            Long id = newInvoice.getUserId().getId();
            MyUser user;

            if (id != null) {
                user = this.userRepository.getById(id);
            } else {
                user = this.userRepository.save(newInvoice.getUserId());
            }
            newInvoice.setUserId(user);
        }

//        Pedido
        if (newInvoice.getOrderID() != null) {
            Long id = newInvoice.getOrderID().getId();
            Order order;

            if (id != null) {
                order = this.orderRepository.getById(id);
            } else {
                order = this.orderRepository.save(newInvoice.getOrderID());
            }
            newInvoice.setOrderID(order);
        }

        newInvoice = invoiceRepository.save(newInvoice);
        return this.businessToDto(newInvoice);
    }

/*     Exibir Todas as notas Fiscais     */
    public List<InvoiceDTO> findAllInvoices(){
        List<Invoice> allList = invoiceRepository.findAll();
        return this.listToDto(allList);
    }


/*    Buscar Nota fiscal por ID         */
    public InvoiceDTO searchById(Long id) {
        Optional<Invoice> option = invoiceRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

}
