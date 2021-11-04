package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.embeddable.PriceProductKey;
import br.com.rd.projetoVelhoLuxo.model.entity.Category;
import br.com.rd.projetoVelhoLuxo.model.entity.ConservationState;
import br.com.rd.projetoVelhoLuxo.model.entity.PriceProduct;
import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import br.com.rd.projetoVelhoLuxo.repository.contract.PriceProductRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
    ProductsRepository productsREPO;

//    Conversão Business para DTO (Produto)
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
            categoryDTO.setDescription(business.getCategoryID().getDescription());

            dto.setCategoryDTO(categoryDTO);
        }
        if (business.getConservationState() != null) {
            ConservationStateDTO conservationState = new ConservationStateDTO();
            conservationState.setId(business.getId());
            conservationState.setDescription(business.getDescription());

            dto.setConservationState(conservationState);
        }
        return dto;
    }

    //    Conversão Business para DTO (Preço Produto)
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

    //    Conversão DTO para Business (Produto)
    private Products dtoToBusiness(ProductsDTO dto) {
        Products business = new Products();
        if(dto.getId() !=null){
        business.setId(dto.getId());
        }
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
                category.setDescription(dto.getCategoryDTO().getDescription());
            }

            business.setCategoryID(category);
        }
        return business;
    }

//    Conversão DTO para Business (Preço produto)
    private PriceProduct dtoToBusiness(PriceProductDTO dto){
        PriceProduct business = new PriceProduct();
        PriceProductKey key = new PriceProductKey();
        Products produto = dtoToBusiness(dto.getPriceProductKey().getProduct());

        key.setProduct(produto);
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

//    Criar Preço produto
    public PriceProductDTO createPriceProduct(PriceProductDTO priceProduct) throws Exception {
        PriceProduct pp = dtoToBusiness(priceProduct);

        if (priceProductRepository.existsById(pp.getPriceProductKey())){
            throw new Exception("Primary Key already exists!");
        }

//       Long id = pp.getPriceProductKey().getProduct().getId())

        if(pp.getPriceProductKey().getProduct() != null){
            if (pp.getPriceProductKey().getProduct().getId() != null) {
                if(productsREPO.existsById(pp.getPriceProductKey().getProduct().getId())){
                    pp.getPriceProductKey().setProduct(productsREPO.getById(pp.getPriceProductKey().getProduct().getId()));
                }
            }else{
                Products products = productsREPO.save(pp.getPriceProductKey().getProduct());
                pp.getPriceProductKey().setProduct(products);
            }
        }

        pp = priceProductRepository.save(pp);
        return businessToDto(pp);
    }

//    Encontrar Todos os Produtos
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

//    Encontrar por preços menor para maior
    public List<PriceProductDTO> findAllPricesAsc(){
        List<PriceProduct> priceList = this.priceProductRepository.findByOrderByPriceAsc();
        return listToDto(priceList);
    }

//    Encontrar por preços maior para menor
    public List<PriceProductDTO> findAllPricesDesc(){
        List<PriceProduct> priceList = this.priceProductRepository.findByOrderByPriceDesc();
        return listToDto(priceList);
    }

//    Encontrar por maior desconto
    public List<PriceProductDTO> findByOrderBySalePriceDesc(){
        List<PriceProduct> priceList = this.priceProductRepository.findByOrderBySalePriceDesc();
        return listToDto(priceList);
    }

    public List<PriceProductDTO> searchByDescription(String description) {
        return listToDto(priceProductRepository.searchByDescription(description));
    }

    public List<PriceProductDTO> searchByOffers() {
        return listToDto(priceProductRepository.searchByOffers());
    }

    public List<PriceProductDTO> searchByCategory(String categoryName) {
        return listToDto(priceProductRepository.searchByCategory(categoryName));
    }

}