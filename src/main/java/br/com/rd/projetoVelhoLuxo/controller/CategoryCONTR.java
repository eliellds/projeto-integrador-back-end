package br.com.rd.projetoVelhoLuxo.controller;


import br.com.rd.projetoVelhoLuxo.model.dto.CategoryDTO;
import br.com.rd.projetoVelhoLuxo.service.CategorySERV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryCONTR {

    @Autowired
    CategorySERV categorySERV;

    @PostMapping
    public CategoryDTO newCategory(@RequestBody CategoryDTO dto) {
        return categorySERV.newCategory(dto);
    }

    @GetMapping
    public List<CategoryDTO> showCategory() {
        return categorySERV.showCategory();
    }

    @GetMapping("/{id}")
    public CategoryDTO showVategoryById(@PathVariable("id") Long id) {
        return categorySERV.showCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@RequestBody CategoryDTO dto, @PathVariable("id") Long id) {
        return categorySERV.updateCategoryById(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CategoryDTO deleteCategory(@PathVariable("id") Long id) {
        return categorySERV.deleteCategory(id);
    }
}