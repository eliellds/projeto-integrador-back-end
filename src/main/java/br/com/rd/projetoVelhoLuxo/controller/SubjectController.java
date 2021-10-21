package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.SubjectDTO;
import br.com.rd.projetoVelhoLuxo.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping
    public SubjectDTO create(@RequestBody SubjectDTO newSubject) {
        return subjectService.create(newSubject);
    }

    @GetMapping
    public List<SubjectDTO> findAll() {
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public SubjectDTO searchById(@PathVariable("id") Byte id) {
        return subjectService.searchById(id);
    }

    @PutMapping("/{id}")
    public SubjectDTO updateById(@RequestBody SubjectDTO dto, @PathVariable("id") Byte id) {
        return subjectService.updateById(dto, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public SubjectDTO deleteByIdReturningDTO(@PathVariable("id") Byte id) {
        return subjectService.deleteByIdReturningDTO(id);
    }

}
