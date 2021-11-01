package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.TipoNfDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.TipoNf;
import br.com.rd.projetoVelhoLuxo.repository.contract.TipoNfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoNfService {

    @Autowired
    TipoNfRepository tipoNfRepository;

    public TipoNfDTO addTipoNf(TipoNfDTO tipoNf) {
        TipoNf newTipoNf = this.dtoToBusiness(tipoNf);
        newTipoNf = tipoNfRepository.save(newTipoNf);
        return this.businessToDto(newTipoNf);
    }

    public List<TipoNfDTO> findAllTipoNf() {
        List<TipoNf> allList = tipoNfRepository.findAll();
        return this.listToDto(allList);
    }

    private List<TipoNfDTO> listToDto(List<TipoNf> list) {
        List<TipoNfDTO> listDto = new ArrayList<TipoNfDTO>();
        for (TipoNf t : list) {
            listDto.add(this.businessToDto(t));
        }
        return listDto;
    }

    public TipoNfDTO searchTipoNfById(Long id) {
        Optional<TipoNf> op = tipoNfRepository.findById(id);

        if (op.isPresent()) {
            return businessToDto(op.get());
        }
        return null;
    }

    public TipoNfDTO updateById(TipoNfDTO dto, Long id) {
        Optional<TipoNf> op = tipoNfRepository.findById(id);

        if (op.isPresent()) {
            TipoNf obj = op.get();

            if (dto.getDescription() != null) {
                ((TipoNf) obj).setDescription(dto.getDescription());
            }

            tipoNfRepository.save(obj);
            return businessToDto(obj);
        }
        return null;
    }


    public void deleteById(Long id){
        if(tipoNfRepository.existsById(id)){
            tipoNfRepository.deleteById(id);
        }
    }

    private TipoNf dtoToBusiness(TipoNfDTO dto){
        TipoNf business = new TipoNf();
        business.setDescription(dto.getDescription());
        return business;
    }

    private TipoNfDTO businessToDto(TipoNf business){
        TipoNfDTO dto = new TipoNfDTO();
        dto.setId(business.getId());
        dto.setDescription(business.getDescription());
        return dto;
    }
}