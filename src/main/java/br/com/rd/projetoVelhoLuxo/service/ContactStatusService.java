package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.ContactStatusDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.ContactStatus;
import br.com.rd.projetoVelhoLuxo.repository.contract.ContactStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactStatusService {

    @Autowired
    ContactStatusRepository statusRepository;

    public ContactStatusDTO create(ContactStatusDTO newStatus) {
        ContactStatus status = dtoToBusiness(newStatus);
        status = statusRepository.save(status);

        return businessToDto(status);
    }

    public List<ContactStatusDTO> findAll() {
        List<ContactStatus> allList = statusRepository.findAll();
        return listToDto(allList);
    }

    public ContactStatusDTO searchById(Byte id) {
        Optional<ContactStatus> option = statusRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

    public ContactStatusDTO updateById(ContactStatusDTO dto, Byte id) {
        Optional<ContactStatus> option = statusRepository.findById(id);

        if (option.isPresent()) {
            ContactStatus status = option.get();

            if (dto.getId() != null) {
                status = statusRepository.getById(id);
            }

            if (dto.getStatusDescription() != null) {
                status.setStatusDescription(dto.getStatusDescription());
            }

            statusRepository.save(status);
            return businessToDto(status);

        }
        return null;
    }

    public ContactStatusDTO deleteByIdReturningDTO(Byte id) {
        ContactStatusDTO dto = searchById(id);

        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
        }
        return dto;
    }

    public List<ContactStatusDTO> listToDto(List<ContactStatus> list) {
        List<ContactStatusDTO> listDto = new ArrayList<>();

        for (ContactStatus business : list) {
            listDto.add(businessToDto(business));
        }
        return listDto;
    }

    public ContactStatus dtoToBusiness(ContactStatusDTO dto) {
        ContactStatus business = new ContactStatus();
        business.setStatusDescription(dto.getStatusDescription());

        return business;
    }

    private ContactStatusDTO businessToDto(ContactStatus business) {
        ContactStatusDTO dto = new ContactStatusDTO();
        dto.setId(business.getId());
        dto.setStatusDescription(business.getStatusDescription());

        return dto;
    }

}
