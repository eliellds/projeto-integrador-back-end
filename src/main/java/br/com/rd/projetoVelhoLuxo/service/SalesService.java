package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.SalesDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Sales;
import br.com.rd.projetoVelhoLuxo.repository.contract.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalesService {

    @Autowired
    SalesRepository salesRepository;

    private Sales dtoToBusiness(SalesDTO dto) {
        Sales business = new Sales();
        business.setId(dto.getId());
        business.setSaleType(dto.getSaleType());

        return business;
    }

    private SalesDTO businessToDto(Sales business) {
        SalesDTO dto = new SalesDTO();
        dto.setId(business.getId());
        dto.setSaleType(business.getSaleType());

        return dto;
    }

    private List<SalesDTO> listToDto(List<Sales> list){
        List<SalesDTO> listDto = new ArrayList<SalesDTO>();
        for (Sales s: list){
            listDto.add(this.businessToDto(s));
        }
        return listDto;
    }

    public SalesDTO createSale(SalesDTO sales){
        Sales newSale = this.dtoToBusiness(sales);
        newSale = salesRepository.save(newSale);
        return this.businessToDto(newSale);
    }

    public List<SalesDTO> findAllSales(){
        List<Sales> allList = salesRepository.findAll();
        return this.listToDto(allList);
    }

    public SalesDTO searchById(Long id) {
        Optional<Sales> option = salesRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

    public SalesDTO updateSale(SalesDTO dto, Long id) {
        Optional<Sales> op = salesRepository.findById(id);

        if(op.isPresent()){
            Sales obj = op.get();

            if (dto.getSaleType() != null){
                obj.setSaleType(dto.getSaleType());
            }

            salesRepository.save(obj);
            return businessToDto(obj);
        }
        return null;
    }

    public void deleteSale(Long id) {
        if (salesRepository.existsById(id)){
            salesRepository.deleteById(id);
        }
    }
}
