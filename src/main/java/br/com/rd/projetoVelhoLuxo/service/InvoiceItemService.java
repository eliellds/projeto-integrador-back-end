package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.embeddable.InvoiceItemKey;
import br.com.rd.projetoVelhoLuxo.model.entity.*;
import br.com.rd.projetoVelhoLuxo.repository.contract.InvoiceRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceItemService {

    @Autowired
    InvoiceItemRepository invoiceItemRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    private InvoiceDTO businessToDto(Invoice business) {
        InvoiceDTO dto = new InvoiceDTO();

        dto.setId(business.getId());
//        dto.setDescription(business.getDescription());

        return dto;
    }

    private InvoiceItemDTO businessToDto(InvoiceItem business){
        InvoiceItemDTO dto = new InvoiceItemDTO();
        InvoiceItemKeyDTO key = new InvoiceItemKeyDTO();

        key.setId(business.getInvoiceItemKey().getId());
        key.setInvoiceId(businessToDto(business.getInvoiceItemKey().getInvoiceId()));

        dto.setInvoiceItemKey(key);
        dto.setRateIPI(business.getRateIPI());
        dto.setCalculationIPI(business.getCalculationIPI());
        dto.setRateICMS(business.getRateICMS());
        dto.setCalculationICMS(business.getCalculationICMS());
        dto.setProductQuantity(business.getProductQuantity());
        dto.setDiscountUnity(business.getDiscountUnity());
        dto.setDiscountTotal(business.getDiscountTotal());
        dto.setTotal(business.getTotal());
        dto.setProductID(business.getProductID());

        return dto;
    }

    private Invoice dtoToBusiness(InvoiceDTO dto) {
        Invoice business = new Invoice();

        if(dto.getId() !=null){
            business.setId(dto.getId());
        }
//        business.setDescription(dto.getDescription());

        return business;
    }

    private InvoiceItem dtoToBusiness(InvoiceItemDTO dto){
        InvoiceItem business = new InvoiceItem();
        InvoiceItemKey key = new InvoiceItemKey();

        Invoice invoice = dtoToBusiness(dto.getInvoiceItemKey().getInvoiceId());
        key.setId(dto.getInvoiceItemKey().getId());
        key.setInvoiceId(invoice);

        business.setInvoiceItemKey(key);
        business.setDiscountTotal(dto.getDiscountTotal());
        business.setDiscountUnity(dto.getDiscountUnity());
        business.setCalculationICMS(dto.getCalculationICMS());
        business.setRateICMS(dto.getRateICMS());
        business.setCalculationIPI(dto.getCalculationIPI());
        business.setRateIPI(dto.getRateIPI());
        business.setTotal(dto.getTotal());
        business.setProductQuantity(dto.getProductQuantity());
        business.setProductID(dto.getProductID());

        return business;
    }

    private List<InvoiceItemDTO> listToDto(List<InvoiceItem> list){
        List<InvoiceItemDTO> listDto = new ArrayList<InvoiceItemDTO>();
        for (InvoiceItem ii: list) {
            listDto.add(this.businessToDto(ii));
        }
        return listDto;
    }

    public InvoiceItemDTO createInvoiceItem(InvoiceItemDTO invoiceItemDTO) throws Exception {
        InvoiceItem ii = dtoToBusiness(invoiceItemDTO);

        if (invoiceItemRepository.existsById(ii.getInvoiceItemKey())){
            throw new Exception("Primary Key already exists!");
        }

        Long id = ii.getInvoiceItemKey().getInvoiceId().getId();

        if(ii.getInvoiceItemKey().getInvoiceId() != null){
            if (id != null) {
                if(invoiceRepository.existsById(id)){
                    ii.getInvoiceItemKey().setInvoiceId(invoiceRepository.getById(id));
                }
            }else{
                Invoice invoice = invoiceRepository.save(ii.getInvoiceItemKey().getInvoiceId());
                ii.getInvoiceItemKey().setInvoiceId(invoice);
            }
        }

        ii = invoiceItemRepository.save(ii);
        return businessToDto(ii);
    }

    public List<InvoiceItemDTO> findAll(){
        List<InvoiceItem> list = invoiceItemRepository.findAll();
        return listToDto(list);
    }

    public InvoiceItemDTO searchById(Long id, InvoiceDTO invoiceId){
        InvoiceItemKey key = new InvoiceItemKey();

        Invoice invoice = dtoToBusiness(invoiceId);
        invoice.setId(invoiceId.getId());

        key.setInvoiceId(invoice);
        key.setId(id);

        Optional<InvoiceItem> ii = invoiceItemRepository.findById(key);

        if (ii.isPresent()){
            return businessToDto(ii.get());
        }

        return null;
    }

}
