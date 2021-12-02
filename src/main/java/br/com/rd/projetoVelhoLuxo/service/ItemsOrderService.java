package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.dto.response.OrderDashboardDTO;
import br.com.rd.projetoVelhoLuxo.model.embeddable.InventoryKey;
import br.com.rd.projetoVelhoLuxo.model.embeddable.ItemsOrderKey;
import br.com.rd.projetoVelhoLuxo.model.entity.*;
import br.com.rd.projetoVelhoLuxo.repository.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemsOrderService {

    @Autowired
    ItemsOrderRepository itemsOrderRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    InventoryREPO inventoryREPO;

    @Autowired
    StoreRepository storeRepository;

    //////////////////////////////////
    // cria relacao de item com pedido
    public ItemsOrderDTO linkItemsToOrder(ItemsOrderDTO toLink) throws Exception {
        ItemsOrder itemOrder = new ItemsOrder();

        Products products = productsRepository.getById(toLink.getProductsDTO().getId());
        Store store = storeRepository.getById(toLink.getCompositeKey().getOrderDTO().getIdStore());

        InventoryKey inventoryKey = new InventoryKey();
        inventoryKey.setId(store.getId());
        inventoryKey.setProducts(products);

        Optional<Inventory> inventoryOpt;

        if (inventoryREPO.existsById(inventoryKey)) {
             inventoryOpt = inventoryREPO.findById(inventoryKey);

            if (inventoryOpt.get().getQty_products() >= 1 &&
                    toLink.getQuantity() <= inventoryOpt.get().getQty_products()) {

                inventoryOpt.get().setQty_products(
                        Integer.parseInt(
                                String.valueOf(
                                        inventoryOpt.get().getQty_products() - toLink.getQuantity()
                                )
                        )
                );
            } else {
                throw new Exception("Products quantity must be equal or smaller than inventory quantity!");
            }
        }            else {
            throw new Exception("Product not found!");
        }

        itemOrder.setTotalPrice(toLink.getTotalPrice());
        itemOrder.setQuantity(toLink.getQuantity());
        itemOrder.setTotalDicount(toLink.getTotalDiscount());
        itemOrder.setUnitDiscount(toLink.getUnityDiscount());

        // conferindo se a composite key foi preenchida
        if (toLink.getCompositeKey() != null) {
            ItemsOrderKey key = new ItemsOrderKey();
            key.setIdItem(toLink.getCompositeKey().getIdItem());
            key.setOrder(orderRepository.getById(toLink.getCompositeKey().getOrderDTO().getId()));

            // conferindo se a combinacao item id e pedido(order) ja existe
            if (!itemsOrderRepository.existsById(key)) {

                // conferindo se order foi preenchida
                if (key.getOrder() != null) {
                    Order order = new Order();

                    if (key.getOrder().getId() != null) {

                        if (orderRepository.existsById(key.getOrder().getId())) {
                            order = orderRepository.getById(key.getOrder().getId());

                        }

                    } else {
                        throw new NullPointerException("Order id must be inserted!");
                    }

                    key.setOrder(order);

                } else {
                    throw new NullPointerException("Order must be inserted!");
                }

//                // conferindo ultimo item do pedido e adicionando + 1 ao valor do id item atual
//                if (!findOneByOrderId(key.getOrder().getId()).isEmpty()) {
//                    List<ItemsOrderDTO> list = findOneByOrderId(toLink.getCompositeKey().getOrderDTO().getId());
//                    key.setIdItem(
//                            (list.get(0).getCompositeKey().getIdItem()) + 1
//                    );
//
//                } else {
//                    key.setIdItem(1L);
//                }

            } else {
                throw new Exception("Composite key already exists!");
            }

            itemOrder.setCompositeKey(key);

        } else {
            throw new NullPointerException("Composite key must be inserted!");
        }

        // conferindo se produto foi preenchido
        if (toLink.getProductsDTO() != null) {
            Products product = new Products();

            if (toLink.getProductsDTO().getId() != null) {
                Long id = toLink.getProductsDTO().getId();

                if (productsRepository.existsById(id)) {
                    product = productsRepository.getById(id);

                }

                itemOrder.setProduct(product);

            } else {
                throw new NullPointerException("Product id must be inserted!");
            }

        } else {
            throw new NullPointerException("Product id must be inserted!");
        }

        itemOrder = itemsOrderRepository.save(itemOrder);
        inventoryREPO.save(inventoryOpt.get());

        return businessToDto(itemOrder);

    }

    public List<OrderDashboardDTO> findByUser(Long id) {
        List<ItemsOrder> list = itemsOrderRepository.findAllByCompositeKeyOrderMyUserIdOrderByCompositeKeyOrderIdDesc(id);
        return listToViewDTO(list);
    }

    private List<OrderDashboardDTO> listToViewDTO(List<ItemsOrder> businessList) {
        List<OrderDashboardDTO> dashList = new ArrayList<>();

        Long order = 0L;

        for (ItemsOrder convert : businessList) {

            if (!convert.getCompositeKey().getOrder().getId().equals(order)) {
                dashList.add(businessToDashDTO(convert));
                order = convert.getCompositeKey().getOrder().getId();
            }

        }

        return dashList;
    }

    private OrderDashboardDTO businessToDashDTO(ItemsOrder business) {
        OrderDashboardDTO dto = new OrderDashboardDTO();

        dto.setOrderNumber(business.getCompositeKey().getOrder().getId());
        dto.setStatus(business.getCompositeKey().getOrder().getStatus().getStatusDescription());
        dto.setDate(business.getCompositeKey().getOrder().getDateOrder());
        dto.setPrice(business.getCompositeKey().getOrder().getAmount());
        dto.setIdStatus(business.getCompositeKey().getOrder().getStatus().getId());
        dto.setDeliveryDate(business.getCompositeKey().getOrder().getDeliveryDate());
        dto.setPaymentID(business.getCompositeKey().getOrder().getPayment().getId());

        List<ItemsOrder> items = itemsOrderRepository.findAllByCompositeKeyOrderIdOrderByCompositeKeyOrderIdDesc(business.getCompositeKey().getOrder().getId());

        dto.setProductList(listToDto(items));

        return dto;
    }


    ///////////////////////////////
    // encontra todos os itemsOrder
    public List<ItemsOrderDTO> findAllList() {
        List<ItemsOrder> list = itemsOrderRepository.findAllByOrderByCompositeKeyOrderIdDesc();
        return listToDto(list);
    }

    /////////////////////////////////////////////////
    // encontra lista de itemsOrder pelo compositeKey
    public ItemsOrderDTO findByCompositeKey(Long idItem, Order order) {
        ItemsOrderKey key = new ItemsOrderKey();
        key.setIdItem(idItem);
        key.setOrder(order);

        Optional<ItemsOrder> opt = itemsOrderRepository.findById(key);

        if (opt.isPresent()) {
            return businessToDto(opt.get());
        }

        return null;

    }

    public List<ItemsOrderDTO> findAllByOrderId(Long id) {
        return listToDto(itemsOrderRepository.findAllByCompositeKeyOrderIdOrderByCompositeKeyOrderIdDesc(id));
    }

    private List<ItemsOrderDTO> findOneByOrderId(Long id) {
        return listToDto(itemsOrderRepository.findFirst1ByCompositeKeyOrderIdOrderByCompositeKeyIdItemDesc(id));

    }

    ////////////////////////////////////////////////////
    // converter lista de objeto final order para lista de dto
    private List<ItemsOrderDTO> listToDto(List<ItemsOrder> itemsOrderList) {
        List<ItemsOrderDTO> list = new ArrayList<>();

        for (ItemsOrder convert : itemsOrderList) {
            list.add(businessToDto(convert));
        }

        return list;

    }

    //////////////////////////////////////////
    // convertendo dto para objeto final order
    private ItemsOrder dtoToBusiness(ItemsOrderDTO dto) {
        ItemsOrder business = new ItemsOrder();
        ItemsOrderKey key = new ItemsOrderKey();

        if (dto.getCompositeKey().getOrderDTO() != null) {
            key.setOrder(convertToOrder(dto.getCompositeKey().getOrderDTO()));
        }
        key.setIdItem(dto.getCompositeKey().getIdItem());

        business.setCompositeKey(key);

        business.setProduct(dtoToBusiness(dto.getProductsDTO()));
        business.setQuantity(dto.getQuantity());
        business.setUnitDiscount(dto.getUnityDiscount());
        business.setTotalDicount(dto.getTotalDiscount());
        business.setTotalPrice(dto.getTotalPrice());

        return business;

    }

    //////////////////////////////////////////
    // convertendo objeto final order para dto
    private ItemsOrderDTO businessToDto(ItemsOrder business) {
        ItemsOrderDTO dto = new ItemsOrderDTO();
        ItemsOrderKeyDTO key = new ItemsOrderKeyDTO();
        key.setIdItem(business.getCompositeKey().getIdItem());
        key.setOrderDTO(convertToDTO(business.getCompositeKey().getOrder()));

        dto.setCompositeKey(key);

        dto.setProductsDTO(businessToDto(business.getProduct()));
        dto.setQuantity(business.getQuantity());
        dto.setUnityDiscount(business.getUnitDiscount());
        dto.setTotalDiscount(business.getTotalDicount());
        dto.setTotalPrice(business.getTotalPrice());

        return dto;

    }

    //////////////////////////////////////////
    // converte dto para objeto final products
    private Products dtoToBusiness(ProductsDTO dto) {
        Products business = new Products();
        business.setProduct(dto.getProduct());
        business.setDescription(dto.getDescription());
        business.setFeature(dto.getFeature());
        business.setYear(dto.getYear());
        business.setQuantity(dto.getQuantity());
        business.setImage(dto.getImage());

        if (dto.getCategoryDTO() != null) {
            Category category = new Category();

            if (dto.getCategoryDTO().getId() != null) {
                category.setId(dto.getCategoryDTO().getId());
            } else {
                category.setCategory(dto.getCategoryDTO().getCategory());
                category.setDescription(dto.getCategoryDTO().getDescription());
            }
            business.setCategoryID(category);
        }

        if (dto.getConservationState() != null) {
            ConservationState conservationState = new ConservationState();

            if (dto.getConservationState().getId() != null) {
                conservationState.setId(dto.getConservationState().getId());
            } else {
                conservationState.setDescription(dto.getConservationState().getDescription());
            }
            business.setConservationState(conservationState);
        }
        return business;
    }

    //////////////////////////////////////////
    // converte objeto final products para dto
    private ProductsDTO businessToDto(Products business) {
        ProductsDTO dto = new ProductsDTO();
        dto.setId(business.getId());
        dto.setProduct(business.getProduct());
        dto.setDescription(business.getDescription());
        dto.setFeature(business.getFeature());
        dto.setYear(business.getYear());
        dto.setQuantity(business.getQuantity());
        dto.setImage(business.getImage());

        if (business.getCategoryID() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(business.getCategoryID().getId());
            categoryDTO.setCategory(business.getCategoryID().getCategory());
            categoryDTO.setDescription(business.getCategoryID().getDescription());

            dto.setCategoryDTO(categoryDTO);
        }
        if (business.getConservationState() != null) {
            ConservationStateDTO conservationStateDTO = new ConservationStateDTO();
            conservationStateDTO.setId((business.getConservationState().getId()));
            conservationStateDTO.setDescription(business.getConservationState().getDescription());

            dto.setConservationState(conservationStateDTO);
        }

        return dto;
    }

    ///////////////////////////////////////
    // converte objeto final order para dto
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

    ///////////////////////////////////////
    // converte dto para objeto final order
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

    /////////////////////////////////////////////////
    // converte lista de order para lista de orderDTO
    private List<OrderDTO> convertList (List<Order> list){
        List<OrderDTO> listDTO = new ArrayList<>();
        for (Order a : list) {
            listDTO.add(convertToDTO(a));
        }

        return listDTO;
    }

    ////////////////////////////////////////
    // converte objeto final myUser para dto
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

    ////////////////////////////////////////
    // converte dto para objeto final myUser
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

    ///////////////////////////////////////////
    // converte dto para objeto final telephone
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

    ///////////////////////////////////////////
    // converte objeto final telephone para dto
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

    /////////////////////////////////////////
    // converte dto para objeto final address
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

    /////////////////////////////////////////
    // converte objeto final address para dto
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

    //////////////////////////////////////
    // converte dto para objeto final card
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

    //////////////////////////////////////
    // converte objeto final card para dto
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

    //////////////////////////////////////////
    // converte dto para objeto final delivery
    private Delivery dtoToDeliveryBusiness(DeliveryDTO dto){
        Delivery business = new Delivery();
        business.setDescricao(dto.getDescricao());
        return business;
    }

    //////////////////////////////////////////
    // converte objeto final delivery para dto
    private DeliveryDTO businessToDeliveryDto(Delivery business){
        DeliveryDTO dto = new DeliveryDTO();
        dto.setId(business.getId());
        dto.setDescricao(business.getDescricao());
        return dto;
    }

}
