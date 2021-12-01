package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.DeliveryDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.DeliveryDateDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.SubjectDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.DeliveryDate;
import br.com.rd.projetoVelhoLuxo.model.entity.Subject;
import br.com.rd.projetoVelhoLuxo.repository.contract.DeliveryDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryDateService {

    @Autowired
    DeliveryDateRepository dateRepository;

    public DeliveryDateDTO getByState(String state) {
        return businessToDto(dateRepository.findAllByState(state));
    }

    public List<DeliveryDateDTO> listToDto(List<DeliveryDate> list) {
        List<DeliveryDateDTO> listDto = new ArrayList<>();

        for (DeliveryDate business : list) {
            listDto.add(businessToDto(business));
        }
        return listDto;
    }

    public DeliveryDate dtoToBusiness(DeliveryDateDTO dto) {
        DeliveryDate business = new DeliveryDate();
        business.setState(dto.getState());
        business.setAddDate(dto.getAddDate());

        return business;
    }

    private DeliveryDateDTO businessToDto(DeliveryDate business) {
        DeliveryDateDTO dto = new DeliveryDateDTO();
        dto.setId(business.getId());
        dto.setState(business.getState());
        dto.setAddDate(business.getAddDate());

        return dto;
    }

}
