package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.entity.Flag;
import br.com.rd.projetoVelhoLuxo.model.dto.FlagDTO;
import br.com.rd.projetoVelhoLuxo.repository.contract.FlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlagService {

    @Autowired
    FlagRepository flagRepository;

    private Flag dtoToBusiness(FlagDTO dto) {
        Flag business = new Flag();
        business.setId(dto.getId());
        business.setDescription(dto.getDescription());

        return business;
    }

    private FlagDTO businessToDto(Flag business) {
        FlagDTO dto = new FlagDTO();
        dto.setId(business.getId());
        dto.setDescription(business.getDescription());

        return dto;
    }

    private List<FlagDTO> listToDto(List<Flag> list){
        List<FlagDTO> listDto = new ArrayList<FlagDTO>();
        for (Flag f: list){
            listDto.add(this.businessToDto(f));
        }
        return listDto;
    }

    public FlagDTO createFlag(FlagDTO flag){
        Flag newFlag = this.dtoToBusiness(flag);
        newFlag = flagRepository.save(newFlag);
        return this.businessToDto(newFlag);
    }

    public List<FlagDTO> findAllFlags(){
        List<Flag> allList = flagRepository.findAll();
        return this.listToDto(allList);
    }
    public FlagDTO updateFlag(FlagDTO dto, Long id) {
        Optional<Flag> op = flagRepository.findById(id);

        if(op.isPresent()){
            Flag obj = op.get();

            if (dto.getDescription() != null){
                obj.setDescription(dto.getDescription());
            }

            flagRepository.save(obj);
            return businessToDto(obj);
        }
        return null;
    }

    public void deleteFlag(Long id) {
        if (flagRepository.existsById(id)){
            flagRepository.deleteById(id);
        }
    }
}