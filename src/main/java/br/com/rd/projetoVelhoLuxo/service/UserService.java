package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.TelephoneDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.UserDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Telephone;
import br.com.rd.projetoVelhoLuxo.model.entity.User;
import br.com.rd.projetoVelhoLuxo.repository.contract.TelephoneRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TelephoneRepository telephoneRepository;

    public UserDTO createUser(UserDTO toCreate){
        User create= convertToUser(toCreate);
        //verifica se tem o "produto"
        if(create.getTelephone()!=null){
            //verifica se tem um id
            if (create.getTelephone().getId() != null) {
                //verifica se esse id existe no banco
                if(telephoneRepository.existsById(create.getTelephone().getId())){
                    create.setTelephone(telephoneRepository.getById(create.getTelephone().getId()));

                }



            //se ele não ta nulo mas não tem o id ele tem os atributos
            }else{
                //salva os atributos como um novo "produto" e recebe um id
                Telephone savedTel = telephoneRepository.save(create.getTelephone());
                //adiciona de volta a tabela principal
                create.setTelephone(savedTel);
            }
        }
        create = userRepository.save(create);
        return convertToDTO(create);
    }

    //pegar lista de usuario


    //update user
    public UserDTO update (UserDTO toUpdate) {
        if (toUpdate.getId() != null) {
            if (userRepository.existsById(toUpdate.getId())) {
                User update = userRepository.getById(toUpdate.getId());
                //data de nascimento
                if (toUpdate.getBorn() != null) {
                    update.setBorn(toUpdate.getBorn());
                }
                //primeiro nome
                if (toUpdate.getFirstName() != null) {
                    update.setFirstName(toUpdate.getFirstName());
                }
                //sobrenome
                if (toUpdate.getLastName() != null) {
                    update.setLastName(toUpdate.getLastName());
                }
                //cpf
//                if (toUpdate.getCpf() != null) { onde for unique key ele da erro se atualiza para o mesmo valor
//                    update.setCpf(toUpdate.getCpf());
//                }

                if (toUpdate.getTelephone()!=null){
                    Telephone telephoneToUpdate = convertToTelephone(toUpdate.getTelephone());
                    if (telephoneToUpdate.getId() != null) {

                        //verifica se existe esse id
                        if(telephoneRepository.existsById(telephoneToUpdate.getId())) {
                            //verfica se tem um numero para atualizar
                            if(telephoneToUpdate.getNumber()!=null){
                                telephoneToUpdate = telephoneRepository.save(telephoneToUpdate);
                                //se não tiver, recupera o que tem do banco
                            }else{
                                telephoneToUpdate = telephoneRepository.getById(telephoneToUpdate.getId());
                            }
                        }
                        //se ele não tem um id ele tem um numero
                    }else{
                        telephoneToUpdate = telephoneRepository.save(telephoneToUpdate);

                    }
                    //ao fim das verificações adiciona no update
                    update.setTelephone(telephoneToUpdate);
                }
                update = userRepository.save(update);
                return convertToDTO(update);
            }


        }
        return null;
    }

    public List<UserDTO> showList(){
        List<User> list = userRepository.findAll();

        return convertListToDTO(list);
    }

    //recuperar usuario
    public UserDTO findById(Long id){
        if(userRepository.existsById(id)){

            return convertToDTO( userRepository.getById(id));
        }
        return null;

    }
    //deletenaod usuario
    public void deleteById(Long id){
        if(userRepository.existsById(id)){

             userRepository.deleteById(id);
        }

    }


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
        if(toConvert.getTelephone()!=null){
            converted.setTelephone(convertToDTOTelphone(toConvert.getTelephone()));
        }


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
        if(toConvert.getTelephone()!=null) {
            converted.setTelephone(convertToTelephone(toConvert.getTelephone()));
        }
        return converted;

    }
    //convert list
    private List<UserDTO> convertListToDTO (List<User> toConvert){
        List<UserDTO> converted = new ArrayList<>();

        for (User a: toConvert) {
            converted.add(convertToDTO(a));

        }
        return converted;
    }

    /////////////conversões de telefone////////
    //convert Para telefone final
    private Telephone convertToTelephone(TelephoneDTO toConvert){
        Telephone converted = new Telephone();
        if (toConvert.getId()!=null){
            converted.setId(toConvert.getId());
        }
        if(toConvert.getNumber()!=null){
            converted.setNumber(toConvert.getNumber());
        }
        return converted;
    }

    //convert telefone DTO
    private TelephoneDTO convertToDTOTelphone(Telephone toConvert){
        TelephoneDTO converted = new TelephoneDTO();
        if(toConvert.getId()!=null) {
            converted.setId(toConvert.getId());
        }
        if(toConvert.getNumber()!=null) {
            converted.setNumber(toConvert.getNumber());
        }
        return converted;
    }
}
