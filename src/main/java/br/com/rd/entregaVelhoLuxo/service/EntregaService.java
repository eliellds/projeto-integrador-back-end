package br.com.rd.entregaVelhoLuxo.service;

import br.com.rd.entregaVelhoLuxo.model.dto.EntregaDTO;
import br.com.rd.entregaVelhoLuxo.model.entity.Entrega;
import br.com.rd.entregaVelhoLuxo.repository.contract.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    @Autowired
    EntregaRepository entregaRepository;

    public EntregaDTO addEntrega(EntregaDTO entrega) {
        Entrega newEntrega = this.dtoToBusiness(entrega);
        newEntrega = entregaRepository.save(newEntrega);
        return this.businessToDto(newEntrega);
    }

    public List<EntregaDTO> findAllEntrega() {
        List<Entrega> allList = entregaRepository.findAll();
        return this.listToDto(allList);
    }

    private List<EntregaDTO> listToDto(List<Entrega> list) {
        List<EntregaDTO> listDto = new ArrayList<EntregaDTO>();
        for (Entrega e : list) {
            listDto.add(this.businessToDto(e));
        }
        return listDto;
    }

    public EntregaDTO searchEntregaById(Long id) {
        Optional<Entrega> op = entregaRepository.findById(id);

        if (op.isPresent()) {
            return businessToDto(op.get());
        }
        return null;
    }

    public EntregaDTO updateById(EntregaDTO dto, Long id) {
        Optional<Entrega> op = entregaRepository.findById(id);

        if (op.isPresent()) {
            Entrega obj = op.get();

            if (dto.getDescricao() != null) {
                obj.setDescricao(dto.getDescricao());
            }

            entregaRepository.save(obj);
            return businessToDto(obj);
        }
        return null;
    }


    public void deleteById(Long id){
        if(entregaRepository.existsById(id)){
            entregaRepository.deleteById(id);
        }
    }

    private Entrega dtoToBusiness(EntregaDTO dto){
        Entrega business = new Entrega();
        business.setDescricao(dto.getDescricao());
        return business;
    }

    private EntregaDTO businessToDto(Entrega business){
        EntregaDTO dto = new EntregaDTO();
        dto.setId(business.getId());
        dto.setDescricao(business.getDescricao());
        return dto;
    }
}