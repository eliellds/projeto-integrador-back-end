package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.CategoryDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.PriceProductDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.PriceProductKeyDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.ProductsDTO;
import br.com.rd.projetoVelhoLuxo.model.embeddable.PriceProductKey;
import br.com.rd.projetoVelhoLuxo.model.entity.Category;
import br.com.rd.projetoVelhoLuxo.model.entity.PriceProduct;
import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import br.com.rd.projetoVelhoLuxo.repository.ProductsREPO;
import br.com.rd.projetoVelhoLuxo.repository.contract.PriceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceProductService {

    @Autowired
    PriceProductRepository priceProductRepository;

    @Autowired
    ProductsREPO productsREPO;

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
        }
        return dto;
    }

    private PriceProductDTO businessToDto(PriceProduct pp){
        PriceProductDTO dto = new PriceProductDTO();
        PriceProductKeyDTO key = new PriceProductKeyDTO();

        key.setProduct(businessToDto(pp.getPriceProductKey().getProduct()));
        key.setEffectiveDate(pp.getPriceProductKey().getEffectiveDate());

        dto.setPriceProductKey(key);
        dto.setPrice(pp.getPrice());
        dto.setSalePrice(pp.getSalePrice());
        dto.setSales(pp.getSales());
        return dto;
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
        return business;
    }

    private PriceProduct dtoToBusiness(PriceProductDTO dto){
        PriceProduct business = new PriceProduct();
        PriceProductKey key = new PriceProductKey();

        key.setProduct(dtoToBusiness(dto.getPriceProductKey().getProduct()));
        key.setEffectiveDate(dto.getPriceProductKey().getEffectiveDate());

        business.setPriceProductKey(key);
        business.setPrice(dto.getPrice());
        business.setSalePrice(dto.getSalePrice());
        business.setSales(dto.getSales());
        return business;
    }

    private List<PriceProductDTO> listToDto(List<PriceProduct> list){
        List<PriceProductDTO> listDto = new ArrayList<PriceProductDTO>();
        for (PriceProduct pp: list) {
            listDto.add(this.businessToDto(pp));
        }
        return listDto;
    }

    public PriceProductDTO createPriceProduct(PriceProductDTO priceProduct) throws Exception {
        PriceProduct pp = dtoToBusiness(priceProduct);

        if (priceProductRepository.existsById(pp.getPriceProductKey())){
            throw new Exception("Primary Key already exists!");
        }
        pp = priceProductRepository.save(pp);
        return businessToDto(pp);

    }

    public List<PriceProductDTO> findAll(){
        List<PriceProduct> list = priceProductRepository.findAll();
        return listToDto(list);
    }

    public PriceProductDTO searchById(ProductsDTO id, LocalDate date){
        PriceProductKey key = new PriceProductKey();

        Products p = dtoToBusiness(id);
        p.setId(id.getId());
        key.setProduct(p);
        key.setEffectiveDate(date);
        Optional<PriceProduct> pp = priceProductRepository.findById(key);

        if (pp.isPresent()){
            return businessToDto(pp.get());
        }

        return null;
    }

}
