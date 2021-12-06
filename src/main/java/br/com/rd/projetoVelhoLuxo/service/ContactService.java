package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.enums.StatusEmail;
import br.com.rd.projetoVelhoLuxo.model.dto.ContactDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ContactStatusDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.MyUserDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.SubjectDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Contact;
import br.com.rd.projetoVelhoLuxo.model.entity.ContactStatus;
import br.com.rd.projetoVelhoLuxo.model.entity.EmailModel;
import br.com.rd.projetoVelhoLuxo.model.entity.Subject;
import br.com.rd.projetoVelhoLuxo.repository.contract.ContactRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.ContactStatusRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.EmailRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ContactStatusRepository statusRepository;

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public ContactDTO create(ContactDTO newContact) throws SQLIntegrityConstraintViolationException {
        Contact contact = dtoToBusiness(newContact);

        if (contact.getSubject() != null) {
            Subject subject = contact.getSubject();

            if (contact.getSubject().getId() != null) {
                Long id = contact.getSubject().getId();

                if (subjectRepository.existsById(id)) {
                    subject = subjectRepository.getById(id);
                } else {
                    subject.setId(null);
                }

            }

            contact.setSubject(subject);
        }

        contact.setEmail(contact.getEmail());
        contact.setContactDate(LocalDateTime.now());
        contact.setStatus(statusRepository.getById(1L));
        contact = contactRepository.save(contact);
        newContact = businessToDto(contact);
        sendContactEmail(newContact);

        return businessToDto(contact);
    }

    public List<ContactDTO> findAll() {
        List<Contact> allList = contactRepository.findAll();
        return listToDto(allList);
    }

    public ContactDTO searchById(Long id) {
        Optional<Contact> option = contactRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

    public ContactDTO updateById(ContactDTO dto, Long id) throws SQLIntegrityConstraintViolationException {
        Optional<Contact> option = contactRepository.findById(id);

        if (option.isPresent()) {
            Contact contact = option.get();

            if (dto.getId() != null) {
                contact = contactRepository.getById(id);
            }
            if (dto.getSubject() != null) {
                Subject subject = new Subject();

                if (dto.getSubject().getId() != null) {
                    Long subjectId = dto.getSubject().getId();

                    if (subjectRepository.existsById(subjectId)) {
                        subject = subjectRepository.getById(subjectId);
                    } else {
                        subject.setId(null);
                    }
                }

                if (dto.getSubject().getSubjectDescription() != null) {
                    if (subject.getId() == null) {
                        subject.setSubjectDescription(dto.getSubject().getSubjectDescription());
                    }
                }

                contact.setSubject(subject);
            }
            if (dto.getName() != null) {
                contact.setName(dto.getName());
            }
            if (dto.getPhoneNumber() != null) {
                contact.setPhoneNumber(dto.getPhoneNumber());
            }
            if (dto.getEmail() != null) {
                contact.setEmail(dto.getEmail());
            }
            if (dto.getContent() != null) {
                contact.setContent(dto.getContent());
            }
            if (dto.getContactDate() != null) {
                contact.setContactDate(dto.getContactDate());
            }
            if (dto.getReplyDate() != null) {
                contact.setReplyDate(dto.getReplyDate());
            }
            if (dto.getStatus() != null) {
                ContactStatus status = new ContactStatus();

                if (dto.getStatus().getId() != null) {
                    Long statusId = dto.getStatus().getId();

                    if (statusRepository.existsById(statusId)) {
                        status = statusRepository.getById(statusId);
                    } else {
                        status.setId(null);
                    }
                }

                if (dto.getStatus().getStatusDescription() != null) {
                    if (status.getId() == null) {
                        status.setStatusDescription(dto.getSubject().getSubjectDescription());
                    }
                }

                contact.setStatus(status);
            }

            contactRepository.save(contact);
            return businessToDto(contact);

        }
        return null;
    }

    public ContactDTO deleteByIdReturningDTO(Long id) {
        ContactDTO contact = searchById(id);

        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        }
        return contact;
    }

    public List<ContactDTO> listToDto(List<Contact> list) {
        List<ContactDTO> listDto = new ArrayList<>();

        for (Contact business : list) {
            listDto.add(businessToDto(business));
        }
        return listDto;
    }

    private Contact dtoToBusiness(ContactDTO dto) {
        Contact business = new Contact();

        if (dto.getSubject() != null) {
            Subject subject = new Subject();

            subject.setId(dto.getSubject().getId());
            subject.setSubjectDescription(dto.getSubject().getSubjectDescription());

            business.setSubject(subject);
        }

        business.setName(dto.getName());
        business.setPhoneNumber(dto.getPhoneNumber());
        business.setEmail(dto.getEmail());
        business.setContent(dto.getContent());
        business.setContactDate(dto.getContactDate());
        business.setReplyDate(dto.getReplyDate());

        if (dto.getStatus() != null) {
            ContactStatus status = new ContactStatus();

            status.setId(dto.getStatus().getId());
            status.setStatusDescription(dto.getStatus().getStatusDescription());

            business.setStatus(status);
        }

        return business;
    }

    private ContactDTO businessToDto(Contact business) {
        ContactDTO dto = new ContactDTO();

        dto.setId(business.getId());

        if (business.getSubject() != null) {
            SubjectDTO subject = new SubjectDTO();

            subject.setId(business.getSubject().getId());
            subject.setSubjectDescription(business.getSubject().getSubjectDescription());

            dto.setSubject(subject);
        }

        dto.setName(business.getName());
        dto.setPhoneNumber(business.getPhoneNumber());
        dto.setEmail(business.getEmail());
        dto.setContent(business.getContent());
        dto.setContactDate(business.getContactDate());
        dto.setReplyDate(business.getReplyDate());

        if (business.getStatus() != null) {
            ContactStatusDTO status = new ContactStatusDTO();

            status.setId(business.getStatus().getId());
            status.setStatusDescription(business.getStatus().getStatusDescription());

            dto.setStatus(status);
        }

        return dto;
    }

    public void sendContactEmail(ContactDTO toCreate){
        EmailModel email = new EmailModel();

        email.setSendDateEmail(LocalDateTime.now());
        email.setOwnerRef(toCreate.getId());
        email.setEmailTo("velholuxosac@gmail.com");
        email.setEmailFrom(toCreate.getEmail());
        email.setSubject(toCreate.getSubject().getSubjectDescription());
        email.setText(toCreate.getContent());
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(email.getEmailFrom());
            helper.setTo(email.getEmailTo());
            helper.setSubject(email.getSubject());
            helper.setText("Nome do contato: " + toCreate.getName() + "<br>\n" +
                            "E-mail de contato: " + email.getEmailFrom() + "<br>\n" +
                            "Telefone de contato: " + toCreate.getPhoneNumber() + "<br>\n" +
                            "Corpo da mensagem: <br>\n<p>" + email.getText() + "<p><br>" +
                            "<hr><img src='cid:logoImage' />", true);

            ClassPathResource resource = new ClassPathResource("static/images/velho-luxo.png");
            helper.addInline("logoImage", resource);

            emailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);

        } catch (MailException | MessagingException e){
            email.setStatusEmail(StatusEmail.ERROR);

        } finally {
            emailRepository.save(email);
        }
    }

}
