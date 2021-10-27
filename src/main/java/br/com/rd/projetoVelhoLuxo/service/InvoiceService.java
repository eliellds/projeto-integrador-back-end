package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.FlagDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.InvoiceDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Flag;
import br.com.rd.projetoVelhoLuxo.model.entity.Invoice;
import br.com.rd.projetoVelhoLuxo.repository.contract.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;


    private Invoice dtoToBusiness(InvoiceDTO dto) {
        Invoice business = new Invoice();
        business.setId(dto.getId());
        business.setDescription(dto.getDescription());

        return business;
    }

    private InvoiceDTO businessToDto(Invoice business) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(business.getId());
        dto.setDescription(business.getDescription());

        return dto;
    }

    private List<InvoiceDTO> listToDto(List<Invoice> list){
        List<InvoiceDTO> listDto = new ArrayList<InvoiceDTO>();
        for (Invoice invoice: list){
            listDto.add(this.businessToDto(invoice));
        }
        return listDto;
    }

    public InvoiceDTO createInvoice(InvoiceDTO invoice){
        Invoice newInvoice = this.dtoToBusiness(invoice);
        newInvoice = invoiceRepository.save(newInvoice);
        return this.businessToDto(newInvoice);
    }

    public List<InvoiceDTO> findAllInvoices(){
        List<Invoice> allList = invoiceRepository.findAll();
        return this.listToDto(allList);
    }

    public InvoiceDTO updateInvoice(InvoiceDTO dto, Long id) {
        Optional<Invoice> op = invoiceRepository.findById(id);

        if(op.isPresent()){
            Invoice obj = op.get();

            if (dto.getDescription() != null){
                obj.setDescription(dto.getDescription());
            }

            invoiceRepository.save(obj);
            return businessToDto(obj);
        }
        return null;
    }

    public void deleteInvoice(Long id) {
        if (invoiceRepository.existsById(id)){
            invoiceRepository.deleteById(id);
        }
    }
}
