package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.entity.*;
import br.com.rd.projetoVelhoLuxo.repository.contract.InvoiceRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.TipoNfRepository;
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
    TipoNfRepository tipoNfRepository;

    @Autowired
    UserRepository userRepository;


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

//      Usuário
        if (business.getUserId() != null){
            UserDTO userDTO = new UserDTO();

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

//    public InvoiceDTO createInvoice(InvoiceDTO invoice){
//        Invoice newInvoice = this.dtoToBusiness(invoice);
//
//        if (newInvoice.getUserId() != null) {
//            Long id = newInvoice.getUserId().getId();
//            MyUser myUser;
//
//            if (id != null) {
//                myUser = this.userRepository.getById(id);
//            } else {
//                myUser = this.userRepository.save(newInvoice.getUserId());
//            }
//            newInvoice.setUserId(myUser);
//        }
//        newInvoice = invoiceRepository.save(newInvoice);
//        return this.businessToDto(newInvoice);
//    }
//
//    public InvoiceDTO updateInvoice(InvoiceDTO dto, Long id) {
//        Optional<Invoice> op = invoiceRepository.findById(id);
//
//        if(op.isPresent()){
//            Invoice obj = op.get();
//
//            if (dto.getInvoiceNumber() != null){
//                obj.setInvoiceNumber(dto.getInvoiceNumber());
//            }
//            if (dto.getStateInvoice() != null){
//                obj.setStateInvoice(dto.getStateInvoice());
//            }
//            if (dto.getShipping() != null){
//                obj.setShipping(dto.getShipping());
//            }
//            if (dto.getAccessKeyNumber() != null){
//                obj.setAccessKeyNumber(dto.getAccessKeyNumber());
//            }
//            if (dto.getIssueDate() != null){
//                obj.setIssueDate(dto.getIssueDate());
//            }
//            if (dto.getTotalIPI() != null){
//                obj.setTotalIPI(dto.getTotalIPI());
//            }
//            if (dto.getTotalICMS() != null){
//                obj.setTotalICMS(dto.getTotalICMS());
//            }
//            if (dto.getTotalDiscount() != null){
//                obj.setTotalDiscount(dto.getTotalDiscount());
//            }
//            if (dto.getTotalPrice() != null){
//                obj.setTotalPrice(dto.getTotalPrice());
//            }
//
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
//
//            invoiceRepository.save(obj);
//            return businessToDto(obj);
//        }
//        return null;
//    }
//
//    public void deleteInvoice(Long id) {
//        if (invoiceRepository.existsById(id)){
//            invoiceRepository.deleteById(id);
//        }
//    }
}
