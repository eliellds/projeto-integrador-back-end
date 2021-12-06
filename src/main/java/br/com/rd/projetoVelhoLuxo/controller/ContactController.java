package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.ContactDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.SubjectDTO;
import br.com.rd.projetoVelhoLuxo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping(consumes = {MediaType.ALL_VALUE})
    public void create(@RequestBody MultipartFile multi,
                             @RequestParam("subject") Long subjectId,
                             @RequestParam("name") String name,
                             @RequestParam("phoneNumber") String number,
                             @RequestParam("email") String email,
                             @RequestParam("content") String content) {
        try {
            System.out.println(multi);
            SubjectDTO newSubject = new SubjectDTO();
            newSubject.setId(subjectId);
            ContactDTO newStatus = new ContactDTO();
            newStatus.setSubject(newSubject);
            newStatus.setName(name);
            newStatus.setPhoneNumber(number);
            newStatus.setEmail(email);
            newStatus.setContent(content);
            contactService.create(newStatus, multi);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<ContactDTO> findAll() {
        return contactService.findAll();
    }

    @GetMapping("/{id}")
    public ContactDTO searchById(@PathVariable("id") Long id) {
        return contactService.searchById(id);
    }

    @PutMapping("/{id}")
    public ContactDTO updateById(@RequestBody ContactDTO dto, @PathVariable("id") Long id) {
        try {
            return contactService.updateById(dto, id);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ContactDTO deleteByIdReturningDTO(@PathVariable("id") Long id) {
        return contactService.deleteByIdReturningDTO(id);
    }

}
