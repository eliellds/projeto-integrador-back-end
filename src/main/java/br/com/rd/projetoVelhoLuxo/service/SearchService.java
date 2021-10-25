package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.SearchDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Search;
import br.com.rd.projetoVelhoLuxo.repository.contract.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    public SearchDTO create(SearchDTO newSearch) {
        Search search = dtoToBusiness(newSearch);
        search.setSearchDate(LocalDateTime.now());

        search = searchRepository.save(search);

        return businessToDto(search);
    }

    public List<SearchDTO> findAll() {
        List<Search> allList = searchRepository.findAll();
        return listToDto(allList);
    }

    public SearchDTO searchById(Long id) {
        Optional<Search> option = searchRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

    public SearchDTO deleteByIdReturningDTO(Long id) {
        SearchDTO dto = searchById(id);

        if (searchRepository.existsById(id)) {
            searchRepository.deleteById(id);
        }
        return dto;
    }

    public List<SearchDTO> listToDto(List<Search> list) {
        List<SearchDTO> listDto = new ArrayList<>();

        for (Search business : list) {
            listDto.add(businessToDto(business));
        }
        return listDto;
    }

    public Search dtoToBusiness(SearchDTO dto) {
        Search business = new Search();
        business.setSearchDate(dto.getSearchDate());
        business.setSearchContent(dto.getSearchContent());

        return business;
    }

    private SearchDTO businessToDto(Search business) {
        SearchDTO dto = new SearchDTO();
        dto.setId(business.getId());
        dto.setSearchDate(business.getSearchDate());
        dto.setSearchContent(business.getSearchContent());

        return dto;
    }

}
