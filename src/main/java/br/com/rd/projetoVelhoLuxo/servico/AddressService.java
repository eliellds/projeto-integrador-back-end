package br.com.rd.projetoVelhoLuxo.servico;

import br.com.rd.projetoVelhoLuxo.model.entity.Address;
import br.com.rd.projetoVelhoLuxo.model.dto.AddressDTO;
import br.com.rd.projetoVelhoLuxo.repository.contract.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
@Autowired
AddressRepository addressRepository;



    public AddressDTO Create(AddressDTO toCreate){
        Address beSave = convertToAddress(toCreate);
        AddressDTO saved;
          beSave = addressRepository.save(beSave);
          saved = convertToDTO(beSave);
          return saved;

    }
    
    public List<AddressDTO> showList(){
        List<Address> list = addressRepository.findAll();

        return convertListDTO(list);

    }

    public void deleteById(Long id){
        Optional<Address> beDel = addressRepository.findById(id);
        if(beDel.isPresent()){
            addressRepository.deleteById(id);
        }
    }

    public AddressDTO updateById(AddressDTO beUpdate){

        if(addressRepository.existsById(beUpdate.getId())) {
            Address updated = addressRepository.getById(beUpdate.getId());
            //cep
            if (beUpdate.getCep() != null) {
                updated.setCep(beUpdate.getCep());
            }
            //cidade
            if (beUpdate.getCity() != null) {
                updated.setCity(beUpdate.getCity());

            }
            //complemento

            if (beUpdate.getComplement() != null){
                updated.setComplement(beUpdate.getComplement());
            }

            //bairro
            if(beUpdate.getDistrict()!= null){

                updated.setDistrict(beUpdate.getDistrict());
            }

            //numero
            if (beUpdate.getNumber() != null) {
                updated.setNumber(beUpdate.getNumber());
            }

            //referencia
            if(beUpdate.getReference()!=null) {
                updated.setReference(beUpdate.getReference());
            }
            //estado
            if(beUpdate.getState()!= null) {
                updated.setState(beUpdate.getState());
            }

            //rua
            if(beUpdate.getState()!= null) {
                updated.setStreet(beUpdate.getStreet());
            }
            updated = addressRepository.save(updated);
            return convertToDTO(updated);
        }
        return null;
    }

    public AddressDTO findById(Long id){
        if(addressRepository.existsById(id)){
            Address find = addressRepository.getById(id);
            return convertToDTO(find);

        }
        return null;

    }









    private AddressDTO convertToDTO(Address address){
        AddressDTO addresDTO = new AddressDTO();
        //id
        addresDTO.setId(address.getId());
        //cep
        addresDTO.setCep(address.getCep());
        //cidade
        addresDTO.setCity(address.getCity());
        //complemento
        addresDTO.setComplement(address.getComplement());
        //bairro
        addresDTO.setDistrict(address.getDistrict());
        //numero
        addresDTO.setNumber(address.getNumber());
        //referencia
        addresDTO.setReference(address.getReference());
        //estado
        addresDTO.setState(address.getState());

        //rua
        addresDTO.setStreet(address.getStreet());

        return addresDTO;

    }

    private List<AddressDTO> convertListDTO(List<Address> list ){
        List<AddressDTO> listConvert = new ArrayList<>();

        for (Address a: list) {
            AddressDTO converted = convertToDTO(a);
            listConvert.add(converted);

        }
        return listConvert;


    }

    private Address convertToAddress(AddressDTO addressDTO){
        Address addres = new Address();

        //cep
        addres.setCep(addressDTO.getCep());
        //cidade
        addres.setCity(addressDTO.getCity());
        //complemento
        if(addressDTO.getComplement()!=null){
        addres.setComplement(addressDTO.getComplement());
        }
        //bairro
        addres.setDistrict(addressDTO.getDistrict());
        //numero
        addres.setNumber(addressDTO.getNumber());
        //referencia
        if(addressDTO.getReference() !=null){
        addres.setReference(addressDTO.getReference());
        }
        //estado
        addres.setState(addressDTO.getState());

        addres.setStreet(addressDTO.getStreet());

        return addres;

    }


}
