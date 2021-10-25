package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.entity.Category;
import br.com.rd.projetoVelhoLuxo.model.dto.CategoryDTO;
import br.com.rd.projetoVelhoLuxo.repository.contract.CategoryREPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategorySERV {
    @Autowired
    CategoryREPO categoryREPO;

    private List<CategoryDTO> listToDto(List<Category> list){
        List<CategoryDTO> listDto = new ArrayList<CategoryDTO>();
        for(Category c : list){
            listDto.add(businessToDto(c));
        }
        return listDto;
    }

    public CategoryDTO newCategory (CategoryDTO dto){
        Category category = dtoToBusiness(dto);
        category = this.categoryREPO.save(category);
        return this.businessToDto(category);
    }

    public List<CategoryDTO> showCategory(){
        List<Category> allTasks = this.categoryREPO.findAll();
        return listToDto(allTasks);
    }

    public CategoryDTO showCategoryById(Long id){
        Optional<Category> optional = this.categoryREPO.findById(id);
        if (optional.isPresent()){
            return businessToDto(optional.get());
        }
        return null;
    }

    public CategoryDTO updateCategoryById(CategoryDTO dto, Long id){
        Optional<Category> optional = this.categoryREPO.findById(id);
        if (optional.isPresent()){
            Category category = optional.get();

            if(dto.getCategory() != null){
                category.setCategory(dto.getCategory());
            }

            category = this.categoryREPO.save(category);
            return businessToDto(category);
        }
        return null;
    }

    public CategoryDTO deleteCategory (Long id) {
        CategoryDTO msg = this.showCategoryById(id);
        if (categoryREPO.existsById(id)) {
            categoryREPO.deleteById(id);
        }
        return msg;
    }

    private Category dtoToBusiness(CategoryDTO dto) {
        Category business = new Category();
        business.setCategory(dto.getCategory());
        return business;
    }

    private CategoryDTO businessToDto(Category business) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(business.getId());
        dto.setCategory(business.getCategory());
        return dto;
    }
}
