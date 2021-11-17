package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.ConservationStateDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductViewDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.*;
import br.com.rd.projetoVelhoLuxo.model.dto.CategoryDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.repository.contract.*;

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
    @Autowired
    ConservationStateRepository conservationStateRepository;
    @Autowired
    PriceProductRepository price;
    @Autowired
    InventoryREPO inventory;


    private List<ProductsDTO> listToDto(List<Products> list) {
        List<ProductsDTO> listDto = new ArrayList<>();
        for (Products p : list) {
            listDto.add(this.businessToDto(p));
        }
        return listDto;
    }
//    public List<ProductViewDTO> getListByCategory(String category){
//        List<ProductViewDTO> listView = new ArrayList<>();
//
//        return listView;
//    }

    private List<ProductViewDTO> convertListToDTOView(List<ProductsDTO> listDTO){
        List<ProductViewDTO> convertedList = new ArrayList<>();
        for (ProductsDTO dto: listDTO) {

            convertedList.add(convertToDTOView(dto));
        }

        return convertedList;
    }


    private ProductViewDTO convertToDTOView(ProductsDTO toConvert){
        ProductViewDTO converted = new ProductViewDTO();
        converted.setProduct(toConvert);
        PriceProduct toAddPrice = price.findByPriceProductKeyProductsId(converted.getProduct().getId());
        if(toAddPrice != null) {
            converted.setPrice(toAddPrice.getPrice());
            if (toAddPrice.getSalePrice() != null) {
                converted.setSalePrice(toAddPrice.getSalePrice());
            }
        }
        Inventory qtyToAdd = inventory.findByInventoryKeyProductsId(converted.getProduct().getId());
        converted.setQty(qtyToAdd.getQty_products());


        return converted;
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
        if (newProduct.getConservationState()!= null) {
            Long id = newProduct.getConservationState().getId();
            ConservationState cc;
            if (id != null) {
                cc = this.conservationStateRepository.getById(id);
            } else {
                cc = this.conservationStateRepository.save(newProduct.getConservationState());
            }
            newProduct.setConservationState(cc);
        }
        newProduct = productsRepository.save(newProduct);
        return this.businessToDto(newProduct);
    }

    //produtos mais recentes adicionados
    public List<ProductViewDTO> findAllByOrderByIdDesc(){
        List<ProductsDTO> toConvert = listToDto(productsRepository.findFirst8ByOrderByIdDesc());
        return convertListToDTOView(toConvert);

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

            if (dto.getImage() != null) {
                pAux.setImage(dto.getImage());
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

     public List<ProductViewDTO> searchByDescription(String description) {
        List<ProductsDTO> toConvert = listToDto(productsRepository.searchByDescription2(description));
        return convertListToDTOView(toConvert);
     }

     public List<ProductViewDTO> searchByCategory(String categoryName) {
         List<ProductsDTO> toConverted = listToDto(productsRepository.findAllByCategoryIDCategoryContaining(categoryName));
        return convertListToDTOView(toConverted);
     }

     public List<ProductsDTO> searchByYearNewer() {
        return listToDto(productsRepository.findAllByOrderByYearDesc());
     }

     public List<ProductsDTO> searchByYearOlder() {
        return listToDto(productsRepository.findAllByOrderByYearAsc());
     }

     public List<ProductViewDTO> searchByOffers() {
         List<ProductsDTO> toConvert = listToDto(productsRepository.searchByOffers());

         return convertListToDTOView(toConvert);
     }

    private Products dtoToBusiness(ProductsDTO dto) {
        Products business = new Products();
        business.setProduct(dto.getProduct());
        business.setDescription(dto.getDescription());
        business.setFeature(dto.getFeature());
        business.setYear(dto.getYear());
        business.setQuantity(dto.getQuantity());
        business.setImage(dto.getImage());

        if (dto.getCategoryDTO() != null) {
            Category category = new Category();

            if (dto.getCategoryDTO().getId() != null) {
                category.setId(dto.getCategoryDTO().getId());
            } else {
                category.setCategory(dto.getCategoryDTO().getCategory());
                category.setDescription(dto.getCategoryDTO().getDescription());
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
        dto.setImage(business.getImage());

        if (business.getCategoryID() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(business.getCategoryID().getId());
            categoryDTO.setCategory(business.getCategoryID().getCategory());
            categoryDTO.setDescription(business.getCategoryID().getDescription());

            dto.setCategoryDTO(categoryDTO);
        }
        if (business.getConservationState() != null) {
            ConservationStateDTO conservationStateDTO = new ConservationStateDTO();
            conservationStateDTO.setId((business.getConservationState().getId()));
            conservationStateDTO.setDescription(business.getConservationState().getDescription());
            dto.setConservationState(conservationStateDTO);
        }

        return dto;
    }
}
