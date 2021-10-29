package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.InvoiceDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.UserDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Invoice;
import br.com.rd.projetoVelhoLuxo.model.entity.MyUser;
import br.com.rd.projetoVelhoLuxo.repository.contract.InvoiceRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.UserRepository;
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
    UserRepository userRepository;

    //conversão business to dto (Nota Fiscal)
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

        if (dto.getUserId() != null){
            MyUser myUser  = new MyUser();
            if (dto.getUserId().getId() != null){
                myUser.setId(dto.getUserId().getId());
            } else {
                myUser.setBorn(dto.getUserId().getBorn());
                myUser.setCpf(dto.getUserId().getCpf());
                myUser.setEmail(dto.getUserId().getEmail());
                myUser.setFirstName(dto.getUserId().getFirstName());
                myUser.setLastName(dto.getUserId().getLastName());
//                myUser.setTelephone(dto.getUserId().getTelephone());
//                myUser.setPassword(dto.getUserId().getPassword());
            }
            business.setUserId(myUser);
        }
        return dto;
    }

    //convert para dto to business (Nota fiscal)
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

        if (business.getUserId() != null){

            UserDTO userDTO  = new UserDTO();
            userDTO.setId(business.getUserId().getId());
            userDTO.setBorn(business.getUserId().getBorn());
            userDTO.setCpf(business.getUserId().getCpf());
            userDTO.setEmail(business.getUserId().getEmail());
            userDTO.setFirstName(business.getUserId().getFirstName());
            userDTO.setLastName(business.getUserId().getLastName());
//            userDTO.setTelephone(business.getUserId().getTelephone());
//            userDTO.setPassword(business.getUserId().getPassword());

            dto.setUserId(userDTO);
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

//    public InvoiceDTO createInvoice(InvoiceDTO invoice){
//        Invoice newInvoice = this.dtoToBusiness(invoice);
//        newInvoice = invoiceRepository.save(newInvoice);
//        return this.businessToDto(newInvoice);
//    }

    public InvoiceDTO createInvoice(InvoiceDTO invoice){
        Invoice newInvoice = this.dtoToBusiness(invoice);

        if (newInvoice.getUserId() != null) {
            Long id = newInvoice.getUserId().getId();
            MyUser myUser;

            if (id != null) {
                myUser = this.userRepository.getById(id);
            } else {
                myUser = this.userRepository.save(newInvoice.getUserId());
            }
            newInvoice.setUserId(myUser);
        }
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

            if (dto.getInvoiceNumber() != null){
                obj.setInvoiceNumber(dto.getInvoiceNumber());
            }
            if (dto.getStateInvoice() != null){
                obj.setStateInvoice(dto.getStateInvoice());
            }
            if (dto.getShipping() != null){
                obj.setShipping(dto.getShipping());
            }
            if (dto.getAccessKeyNumber() != null){
                obj.setAccessKeyNumber(dto.getAccessKeyNumber());
            }
            if (dto.getIssueDate() != null){
                obj.setIssueDate(dto.getIssueDate());
            }
            if (dto.getTotalIPI() != null){
                obj.setTotalIPI(dto.getTotalIPI());
            }
            if (dto.getTotalICMS() != null){
                obj.setTotalICMS(dto.getTotalICMS());
            }
            if (dto.getTotalDiscount() != null){
                obj.setTotalDiscount(dto.getTotalDiscount());
            }
            if (dto.getTotalPrice() != null){
                obj.setTotalPrice(dto.getTotalPrice());
            }

//            if (dto.getUserId() != null){
//                if (dto.getUserId().getId() != null){
//                    if (userRepository.existsById(obj.getUserId().getId())){
//                        obj.setUserId(userRepository.getById(dto.getUserId().getId()));
//                    } else {
//                        obj.getIdBandeira().setDescription(dto.getFlag().getDescription());
//                        obj.setIdBandeira(flagRepository.save(obj.getIdBandeira()));
//                    }
//                } else {
//                    obj.getIdBandeira().setDescription(dto.getFlag().getDescription());
//                    obj.setIdBandeira(flagRepository.save(obj.getIdBandeira()));
//                }
//            }

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
