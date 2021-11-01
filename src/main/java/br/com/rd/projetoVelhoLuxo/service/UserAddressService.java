package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.AddressDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.UserAddressCompositeKeyDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.UserAddressDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.UserAddressViewDTO;
import br.com.rd.projetoVelhoLuxo.model.embeddable.UserAddressCompositeKey;
import br.com.rd.projetoVelhoLuxo.model.entity.Address;
import br.com.rd.projetoVelhoLuxo.model.entity.MyUser;
import br.com.rd.projetoVelhoLuxo.model.entity.UserAddress;
import br.com.rd.projetoVelhoLuxo.repository.contract.AddressRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.UserAddressRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAddressService {
    @Autowired
    UserAddressRepository repositoryUAddress;
    @Autowired
    MyUserRepository myUserRepository;
    @Autowired
    AddressRepository addressRepository;

    public UserAddressViewDTO linkUAddress(UserAddressDTO toLink) {
        UserAddress linked = convertToUAddress(toLink);

        if (myUserRepository.existsById(linked.getId().getIdUser())) {
            Address address;
            MyUser myUser = myUserRepository.getById(linked.getId().getIdUser());

            linked.setMyUser(myUser);
            //verifica se o endereço não esta nulo

            //verifica se tem um id no endereço
            if (linked.getId().getIdAddress() != null) {
                //verifica se existe o id
                if (addressRepository.existsById(linked.getId().getIdAddress())) {


                    address = addressRepository.getById(linked.getId().getIdAddress());

                    linked.setAddress(address);

                }
            }

            if (toLink.getAddress() != null) {
                address = convertToAddress(toLink.getAddress());
                address = addressRepository.save(address);

                linked.setAddress(address);

            }
            linked = repositoryUAddress.save(linked);
            return convertUAddressToViewDTO(linked);



        }

        return null;
    }


    public List<UserAddressViewDTO> findAllByUser(Long userId){

        return convertToListView(repositoryUAddress.findAllByUserId(userId));


    }

    private List<UserAddressViewDTO> convertToListView(List<UserAddress> toConvert){
        List<UserAddressViewDTO> converted = new ArrayList<>();

        for (UserAddress UAddress: toConvert) {
            converted.add(convertUAddressToViewDTO(UAddress));
        }

        return converted;
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
    //Conversões
    //convert Para ViewDTO
    private UserAddressViewDTO convertUAddressToViewDTO(UserAddress toConvert){
        UserAddressViewDTO converted =new UserAddressViewDTO();
        UserAddressCompositeKeyDTO key = new UserAddressCompositeKeyDTO();
        key.setIdAddress(toConvert.getId().getIdAddress());
        key.setIdUser(toConvert.getId().getIdUser());

        if(toConvert.getAddress()!=null){
            AddressDTO addresDTO = new AddressDTO();
            //id
            addresDTO.setId(toConvert.getAddress().getId());
            //cep
            addresDTO.setCep(toConvert.getAddress().getCep());
            //cidade
            addresDTO.setCity(toConvert.getAddress().getCity());
            //complemento
            addresDTO.setComplement(toConvert.getAddress().getComplement());
            //bairro
            addresDTO.setDistrict(toConvert.getAddress().getDistrict());
            //numero
            addresDTO.setNumber(toConvert.getAddress().getNumber());
            //referencia
            addresDTO.setReference(toConvert.getAddress().getReference());
            //estado
            addresDTO.setState(toConvert.getAddress().getState());

            //rua
            addresDTO.setStreet(toConvert.getAddress().getStreet());
            converted.setAddress(addresDTO);

        }


        converted.setId(key);
        if(toConvert.getDescription() != null){
            converted.setDescription(toConvert.getDescription());
        }


        return converted;
    }
    //DTO request
    private UserAddressViewDTO convertUAddressToDTO(UserAddress toConvert){
        UserAddressViewDTO converted =new UserAddressViewDTO();
        UserAddressCompositeKeyDTO key = new UserAddressCompositeKeyDTO();
        key.setIdAddress(toConvert.getId().getIdAddress());
        key.setIdUser(toConvert.getId().getIdUser());

        converted.setId(key);
        if(toConvert.getDescription() != null){
            converted.setDescription(toConvert.getDescription());
        }


        return converted;
    }

    //convert to final object
    private UserAddress convertToUAddress(UserAddressDTO toConvert){
        UserAddress converted =new UserAddress();
        UserAddressCompositeKey key = new UserAddressCompositeKey();
        if(toConvert.getId().getIdAddress()!=null) {
            key.setIdAddress(toConvert.getId().getIdAddress());
        }
        key.setIdUser(toConvert.getId().getIdUser());


        converted.setId(key);
        if(toConvert.getDescription() != null){
            converted.setDescription(toConvert.getDescription());
        }


        return converted;
    }
}
