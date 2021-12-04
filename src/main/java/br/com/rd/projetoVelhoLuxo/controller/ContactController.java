package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.ContactDTO;
import br.com.rd.projetoVelhoLuxo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping
    public ContactDTO create(@RequestBody ContactDTO newStatus) {
        try {
            return contactService.create(newStatus);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
        return null;
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
