package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.TelephoneDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Telephone;
import br.com.rd.projetoVelhoLuxo.repository.contract.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelephoneService {
    @Autowired
    TelephoneRepository repository;
    //criando no Banco
    public TelephoneDTO create(TelephoneDTO toCreate){
        Telephone create = convertToTelephone(toCreate);
        create = repository.save(create);
        return convertToDTO(create);

    }

    //retorna lista
    public List<TelephoneDTO> showList(){

        return convertList(repository.findAll());
    }


    public TelephoneDTO findById(Long id){
        if (repository.existsById(id)) {
            TelephoneDTO find = convertToDTO(repository.getById(id));
            return find;
        }
        return null;
    }

    public void deleteById(Long id){
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }

    }

    //Atualizar
    public TelephoneDTO updateById(TelephoneDTO toUpdate){
        if(repository.existsById(toUpdate.getId())){
            Telephone update = repository.getById(toUpdate.getId());
            if (toUpdate.getNumber() != null){
                update.setNumber(toUpdate.getNumber());
            }
            update = repository.save(update);
            return convertToDTO(update);
        }
        return null;
    }






    private List<TelephoneDTO> convertList(List<Telephone> beConvert){
        List<TelephoneDTO> convertedList= new ArrayList<>();
        for (Telephone convert : beConvert) {
            convertedList.add(convertToDTO(convert));

        }
        return convertedList;
    }

    //conversões
    private TelephoneDTO convertToDTO(Telephone toConvert){
        TelephoneDTO converted = new TelephoneDTO();
        converted.setId(toConvert.getId());
        converted.setNumber(toConvert.getNumber());
        return converted;
    }

    private Telephone convertToTelephone(TelephoneDTO toConvert){
        Telephone converted = new Telephone();
        converted.setNumber(toConvert.getNumber());
        return converted;
    }



}
