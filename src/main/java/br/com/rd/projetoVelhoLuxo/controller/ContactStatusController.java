package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.ContactStatusDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.SubjectDTO;
import br.com.rd.projetoVelhoLuxo.service.ContactStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class ContactStatusController {

    @Autowired
    ContactStatusService statusService;

    @PostMapping
    public ContactStatusDTO create(@RequestBody ContactStatusDTO newStatus) {
        return statusService.create(newStatus);
    }

    @GetMapping
    public List<ContactStatusDTO> findAll() {
        return statusService.findAll();
    }

    @GetMapping("/{id}")
    public ContactStatusDTO searchById(@PathVariable("id") Byte id) {
        return statusService.searchById(id);
    }

    @PutMapping("/{id}")
    public ContactStatusDTO updateById(@RequestBody ContactStatusDTO dto, @PathVariable("id") Byte id) {
        return statusService.updateById(dto, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ContactStatusDTO deleteByIdReturningDTO(@PathVariable("id") Byte id) {
        return statusService.deleteByIdReturningDTO(id);
    }

}
