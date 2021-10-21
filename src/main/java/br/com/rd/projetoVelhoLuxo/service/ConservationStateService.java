package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.ConservationStateDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.ConservationState;
import br.com.rd.projetoVelhoLuxo.repository.contract.ConservationStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConservationStateService {

    @Autowired
    ConservationStateRepository conservationStateRepository;

    private ConservationState dtoToBusiness(ConservationStateDTO dto) {
        ConservationState business = new ConservationState();
        business.setId(dto.getId());
        business.setDescription(dto.getDescription());

        return business;
    }

    private ConservationStateDTO businessToDto(ConservationState business) {
        ConservationStateDTO dto = new ConservationStateDTO();
        dto.setId(business.getId());
        dto.setDescription(business.getDescription());

        return dto;
    }

    private List<ConservationStateDTO> listToDto(List<ConservationState> list){
        List<ConservationStateDTO> listDto = new ArrayList<ConservationStateDTO>();
        for (ConservationState c: list){
            listDto.add(this.businessToDto(c));
        }
        return listDto;
    }

    public ConservationStateDTO createConservationState(ConservationStateDTO conservationState){
        ConservationState newConservationState = this.dtoToBusiness(conservationState);
        newConservationState= conservationStateRepository.save(newConservationState);
        return this.businessToDto(newConservationState);
    }

    public List<ConservationStateDTO> findAllStates(){
        List<ConservationState> allList = conservationStateRepository.findAll();
        return this.listToDto(allList);
    }
    public ConservationStateDTO updateConservationState(ConservationStateDTO dto, Long id) {
        Optional<ConservationState> op = conservationStateRepository.findById(id);

        if(op.isPresent()){
            ConservationState obj = op.get();

            if (dto.getDescription() != null){
                obj.setDescription(dto.getDescription());
            }

            conservationStateRepository.save(obj);
            return businessToDto(obj);
        }
        return null;
    }

    public void deleteConservationState(Long id) {
        if (conservationStateRepository.existsById(id)){
            conservationStateRepository.deleteById(id);
        }
    }
}
