package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.TelephoneDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.UserDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Telephone;
import br.com.rd.projetoVelhoLuxo.model.entity.User;
import br.com.rd.projetoVelhoLuxo.repository.contract.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository UserRepository;

    public UserDTO createUser()

    //conversões
    private UserDTO convertToDTO(User toConvert){
        UserDTO converted = new UserDTO();
        //nascimento
        converted.setBorn(toConvert.getBorn());
        //cpf
        converted.setCpf(toConvert.getCpf());
        //primeiro nome
        converted.setFirstName(toConvert.getFirstName());
        //sobrenome
        converted.setLastName(toConvert.getLastName());
        //email
        converted.setEmail(toConvert.getEmail());
        //senha
        converted.setPassword(toConvert.getPassword());
        //id
        converted.setId(toConvert.getId());
        //telephone
        converted.setTelephone(convertToDTOTelphone(toConvert.getTelephone()));

        return converted;

    }
    //convert para usuario final
    private User convertToUser(UserDTO toConvert){
        User converted = new User();
        //nascimento
        converted.setBorn(toConvert.getBorn());
        //cpf
        converted.setCpf(toConvert.getCpf());
        //primeiro nome
        converted.setFirstName(toConvert.getFirstName());
        //sobrenome
        converted.setLastName(toConvert.getLastName());
        //email
        converted.setEmail(toConvert.getEmail());
        //senha
        converted.setPassword(toConvert.getPassword());
        //id
        converted.setId(toConvert.getId());
        //telephone
        converted.setTelephone(convertToTelephone(toConvert.getTelephone()));

        return converted;

    }

    /////////////conversões de telefone////////
    //convert Para telefone final
    private Telephone convertToTelephone(TelephoneDTO toConvert){
        Telephone converted = new Telephone();
        converted.setNumber(toConvert.getNumber());
        return converted;
    }

    //convert telefone DTO
    private TelephoneDTO convertToDTOTelphone(Telephone toConvert){
        TelephoneDTO converted = new TelephoneDTO();
        converted.setId(toConvert.getId());
        converted.setNumber(toConvert.getNumber());
        return converted;
    }
}
