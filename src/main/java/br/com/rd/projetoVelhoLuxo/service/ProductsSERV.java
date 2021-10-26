package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.ConservationStateDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Category;
import br.com.rd.projetoVelhoLuxo.model.entity.ConservationState;
import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import br.com.rd.projetoVelhoLuxo.model.dto.CategoryDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.repository.contract.CategoryREPO;
import br.com.rd.projetoVelhoLuxo.repository.contract.ProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsSERV {

    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    CategoryREPO categoryREPO;

    public List<ProductsDTO> listToDto(List<Products> list) {
        List<ProductsDTO> listDto = new ArrayList<>();
        for (Products p : list) {
            listDto.add(this.businessToDto(p));
        }
        return listDto;
    }

    public ProductsDTO newProduct (ProductsDTO product) {
        Products newProduct = this.dtoToBusiness(product);

        if (newProduct.getCategoryID()!= null) {
            Long id = newProduct.getCategoryID().getId();
            Category c;
            if (id != null) {
                c = this.categoryREPO.getById(id);
            } else {
                c = this.categoryREPO.save(newProduct.getCategoryID());
            }
            newProduct.setCategoryID(c);
        }
        newProduct = productsRepository.save(newProduct);
        return this.businessToDto(newProduct);
    }

    public List<ProductsDTO> showProducts() {
        List<Products> allList = productsRepository.findAll();
        return this.listToDto(allList);
    }

    public ProductsDTO showProductsById(Long id) {
        Optional<Products> opProducts = productsRepository.findById(id);
        if (opProducts.isPresent()) {
            return businessToDto(opProducts.get());
        }
        return null;
    }

    public ProductsDTO updateProduct(ProductsDTO dto, Long id) {
        Optional<Products> opProducts = productsRepository.findById(id);

        if (opProducts.isPresent()) {
            Products pAux = opProducts.get();

            if (dto.getProduct() != null) {
                pAux.setProduct(dto.getProduct());
            }

            if (dto.getDescription() != null) {
                pAux.setDescription(dto.getDescription());
            }

            if (dto.getFeature() != null) {
                pAux.setFeature(dto.getFeature());
            }

            if (dto.getYear() != null) {
                pAux.setYear(dto.getYear());
            }

            if (dto.getQuantity() != null) {
                pAux.setQuantity(dto.getQuantity());
            }

            productsRepository.save(pAux);
            return businessToDto(pAux);
        }
        return null;
     }

     public ProductsDTO deleteProduct(Long id) {
        ProductsDTO message = this.showProductsById(id);
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
        }
        return message;
     }

     public List<ProductsDTO> searchByDescription(String description) {
        return listToDto(productsRepository.searchByDescription(description));
     }

     public List<ProductsDTO> searchByCategory(String categoryName) {
        return listToDto(productsRepository.findAllByCategoryIDCategoryContaining(categoryName));
     }

     public List<ProductsDTO> searchByYearNewer() {
        return listToDto(productsRepository.findAllByOrderByYearDesc());
     }

     public List<ProductsDTO> searchByYearOlder() {
        return listToDto(productsRepository.findAllByOrderByYearAsc());
     }

     public List<ProductsDTO> searchByOffers() {
        return listToDto(productsRepository.searchByOffers());
     }

    private Products dtoToBusiness(ProductsDTO dto) {
        Products business = new Products();
        business.setProduct(dto.getProduct());
        business.setDescription(dto.getDescription());
        business.setFeature(dto.getFeature());
        business.setYear(dto.getYear());
        business.setQuantity(dto.getQuantity());

        if (dto.getCategoryDTO() != null) {
            Category category = new Category();

            if (dto.getCategoryDTO().getId() != null) {
                category.setId(dto.getCategoryDTO().getId());
            } else {
                category.setCategory(dto.getCategoryDTO().getCategory());
            }
            business.setCategoryID(category);
        }

        if (dto.getConservationState() != null) {
            ConservationState conservationState = new ConservationState();

            if (dto.getConservationState().getId() != null) {
                conservationState.setId(dto.getConservationState().getId());
            } else {
                conservationState.setDescription(dto.getConservationState().getDescription());
            }
            business.setConservationState(conservationState);
        }
        return business;
    }

    private ProductsDTO businessToDto(Products business) {
        ProductsDTO dto = new ProductsDTO();
        dto.setId(business.getId());
        dto.setProduct(business.getProduct());
        dto.setDescription(business.getDescription());
        dto.setFeature(business.getFeature());
        dto.setYear  (business.getYear());
        dto.setQuantity(business.getQuantity());

        if (business.getCategoryID() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(business.getCategoryID().getId());
            categoryDTO.setCategory(business.getCategoryID().getCategory());

            dto.setCategoryDTO(categoryDTO);
        }
        if (business.getConservationState() != null) {
            ConservationStateDTO conservationStateDTO = new ConservationStateDTO();
            conservationStateDTO.setDescription(business.getConservationState().getDescription());
        }

        return dto;
    }
}
