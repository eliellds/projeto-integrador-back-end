package br.com.rd.projetoVelhoLuxo.service;


import br.com.rd.projetoVelhoLuxo.model.dto.*;
import br.com.rd.projetoVelhoLuxo.model.embeddable.InventoryKey;
import br.com.rd.projetoVelhoLuxo.model.entity.Category;
import br.com.rd.projetoVelhoLuxo.model.entity.Inventory;
import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import br.com.rd.projetoVelhoLuxo.repository.contract.InventoryREPO;
import br.com.rd.projetoVelhoLuxo.repository.contract.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventorySERV {

    @Autowired
    InventoryREPO inventoryREPO;

    @Autowired
    ProductsRepository productsREPO;

    private ProductsDTO ProdToDto(Products p) {
        ProductsDTO prodDTO = new ProductsDTO();

        prodDTO.setId(p.getId());
        prodDTO.setProduct(p.getProduct());
        prodDTO.setDescription(p.getDescription());
        prodDTO.setFeature(p.getFeature());
        prodDTO.setYear  (p.getYear());
        prodDTO.setQuantity(p.getQuantity());

        if (p.getConservationState() != null) {
            ConservationStateDTO conDto = new ConservationStateDTO();
            conDto.setId(p.getConservationState().getId());
            conDto.setDescription(p.getConservationState().getDescription());
            prodDTO.setConservationState(conDto);
        }

        if (p.getCategoryID() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(p.getCategoryID().getId());
            categoryDTO.setCategory(p.getCategoryID().getCategory());

            prodDTO.setCategoryDTO(categoryDTO);
        }
        return prodDTO;
    }

    private InventoryDTO InvToDto(Inventory inv) {
        InventoryDTO invDto = new InventoryDTO();
        InventoryKeyDTO invKey = new InventoryKeyDTO();

        invKey.setId(inv.getInventoryKey().getId());
        invKey.setProducts(ProdToDto(inv.getInventoryKey().getProducts()));

        invDto.setInventoryKey(invKey);
        invDto.setQty_products(inv.getQty_products());

        return invDto;
    }

    private Products dtoToProd (ProductsDTO prodDto) {
        Products p = new Products();
        if(prodDto.getId() !=null){
            p.setId(prodDto.getId());
        }
        p.setProduct(prodDto.getProduct());
        p.setDescription(prodDto.getDescription());
        p.setFeature(prodDto.getFeature());
        p.setYear(prodDto.getYear());
        p.setQuantity(prodDto.getQuantity());

        if (prodDto.getConservationState() != null) {
            p.getConservationState().setId(prodDto.getConservationState().getId());
            p.getConservationState().setDescription((prodDto.getConservationState().getDescription()));
        }

        if (prodDto.getCategoryDTO() != null) {
            Category category = new Category();
            if (prodDto.getCategoryDTO().getId() != null) {
                category.setId(prodDto.getCategoryDTO().getId());
            } else {
                category.setCategory(prodDto.getCategoryDTO().getCategory());
            }

            p.setCategoryID(category);
        }
        return p;
    }

    private Inventory DtoToInv(InventoryDTO invDto) {
        Inventory inv = new Inventory();
        InventoryKey invKey = new InventoryKey();
        Products prod = new Products();

        invKey.setId(invDto.getInventoryKey().getId());
        prod.setId(invDto.getInventoryKey().getProducts().getId());
        invKey.setProducts(prod);

        inv.setInventoryKey(invKey);
        inv.setQty_products(invDto.getQty_products());

        return inv;
    }

    private List<InventoryDTO> listToInvDto(List<Inventory> invList) {
        List<InventoryDTO> listInvDTO = new ArrayList<InventoryDTO>();

        for (Inventory inv : invList) {
            listInvDTO.add(this.InvToDto(inv));
        }
        return listInvDTO;
    }

    public InventoryDTO newInventory(InventoryDTO inventory) throws Exception{
        Inventory inv = DtoToInv(inventory);

        if (inventoryREPO.existsById(inv.getInventoryKey())) {
            throw new Exception("Primary key already exists");
        }

        if (inv.getInventoryKey() != null) {
            if (productsREPO.existsById(inv.getInventoryKey().getProducts().getId())) {
                inv.getInventoryKey().setProducts(productsREPO.getById(inv.getInventoryKey().getProducts().getId()));
            } else {
                Products products = productsREPO.save(inv.getInventoryKey().getProducts());
                inv.getInventoryKey().setProducts(products);
            }
        }
        inv = inventoryREPO.save(inv);
        return InvToDto(inv);
    }

    public List<InventoryDTO> showAllInventory() {
        List<Inventory> list = inventoryREPO.findAll();
        return listToInvDto(list);
    }

    public InventoryDTO showInventoryById(ProductsDTO prodDTO) {
        InventoryKey invKey = new InventoryKey();

        Products prod = dtoToProd(prodDTO);
        prod.setId(prodDTO.getId());
        invKey.setProducts(prod);
        Optional<Inventory> opList = this.inventoryREPO.findById(invKey);

        if (opList.isPresent()) {
            return InvToDto(opList.get());
        }
        return null;
    }

    public InventoryDTO updateInventory(InventoryDTO invDTO) {
        InventoryKey invKey = new InventoryKey();

        Products prod = dtoToProd(invDTO.getInventoryKey().getProducts());
        invKey.setProducts(prod);
        Optional<Inventory> opList = this.inventoryREPO.findById(invKey);

        if (opList.isPresent()) {
            Inventory inv = opList.get();

                if (invDTO.getQty_products() != null) {
                    inv.setQty_products(invDTO.getQty_products());
                }
                return InvToDto(inv);
        }
    return null;
    }


}
