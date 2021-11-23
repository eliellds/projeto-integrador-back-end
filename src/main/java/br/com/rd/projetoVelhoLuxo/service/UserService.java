package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.enums.StatusEmail;
import br.com.rd.projetoVelhoLuxo.model.dto.TelephoneDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.MyUserDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.response.UserHeaderDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.EmailModel;
import br.com.rd.projetoVelhoLuxo.model.entity.MyUser;
import br.com.rd.projetoVelhoLuxo.model.entity.Telephone;
import br.com.rd.projetoVelhoLuxo.repository.contract.EmailRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.MyUserRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.TelephoneRepository;
import javassist.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    MyUserRepository userRepository;
    @Autowired
    TelephoneRepository telephoneRepository;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;

    public MyUserDTO createUser(MyUserDTO toCreate){
        String encryptedPassword = new BCryptPasswordEncoder().encode(toCreate.getPassword());
        toCreate.setPassword(encryptedPassword);
        MyUser create= convertToUser(toCreate);

        sendSingUpEmail(toCreate);

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
    public MyUserDTO update (MyUserDTO toUpdate) {
        if (toUpdate.getId() != null) {

            if (userRepository.existsById(toUpdate.getId())) {
                MyUser update = userRepository.getById(toUpdate.getId());
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

    public List<MyUserDTO> showList(){
        List<MyUser> list = userRepository.findAll();

        return convertListToDTO(list);
    }

    public UserHeaderDTO findByEmail(String email) {
        if (userRepository.findByEmailEquals(email) != null) {
            MyUser myUser = userRepository.findByEmailEquals(email);
            return new UserHeaderDTO(myUser.getId(),
                                     myUser.getFirstName(),
                                     myUser.getLastName(),
                                     myUser.getEmail());
        } else {
            throw new BadCredentialsException("User not found!");
        }
    }

    public Boolean findEmailExists(String email) throws Exception {
        if (userRepository.findByEmailEquals(email) != null) {
            return true;
        } else {
            throw new Exception("E-mail not found!");
        }
    }

    public Boolean findByCpf(String cpf) throws Exception {
        if (userRepository.findByCpfEquals(cpf) != null) {
            return true;
        } else {
            throw new Exception("User not found!");
        }
    }

    //recuperar usuario
    public MyUserDTO findById(Long id){
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
    private MyUserDTO convertToDTO(MyUser toConvert){
        MyUserDTO converted = new MyUserDTO();
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
    private MyUser convertToUser(MyUserDTO toConvert){
        MyUser converted = new MyUser();
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
    private List<MyUserDTO> convertListToDTO (List<MyUser> toConvert){
        List<MyUserDTO> converted = new ArrayList<>();

        for (MyUser a: toConvert) {
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

    public void sendSingUpEmail(MyUserDTO toCreate){
        EmailModel email = new EmailModel();

        email.setSendDateEmail(LocalDateTime.now());
        email.setOwnerRef(toCreate.getId());
        email.setEmailTo(toCreate.getEmail());
        email.setEmailFrom("velholuxosac@gmail.com");
        email.setSubject("Bem-vindo ao Velho Luxo!");
        email.setText(String.format ("Olá! %s Bem-vindo(a) ao nosso antiquário Velho Luxo. \nAproveite nossos produtos exclusivos e com qualidade garantida. \nQualquer dúvida não hesite em nos procurar. \nA equipe Velho Luxo agradece!", toCreate.getFirstName()));
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            emailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);

        } catch (MailException e){
            email.setStatusEmail(StatusEmail.ERROR);

        } finally {
            emailRepository.save(email);
        }
    }
}
