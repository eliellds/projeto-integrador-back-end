package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.entity.Card;
import br.com.rd.projetoVelhoLuxo.model.entity.Flag;
import br.com.rd.projetoVelhoLuxo.model.dto.CardDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.FlagDTO;
import br.com.rd.projetoVelhoLuxo.repository.contract.CardRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.FlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    FlagRepository flagRepository;

    private Card dtoToBusiness(CardDTO dto) {
        Card business = new Card();

        business.setCardNumber(dto.getCardNumber());
        business.setCpf(dto.getCpf());
        business.setName(dto.getName());
        business.setBirthDate(dto.getBirthDate());
        business.setDueDate(dto.getDueDate());

        if (dto.getFlag() != null){
            Flag c  = new Flag();
            if (dto.getFlag().getId() != null){
                c.setId(dto.getFlag().getId());
            } else {
                c.setDescription(dto.getFlag().getDescription());
            }
            business.setIdBandeira(c);
        }
        return business;
    }

    private CardDTO businessToDto(Card business) {
        CardDTO dto = new CardDTO();

        dto.setCardNumber(business.getCardNumber());
        dto.setCpf(business.getCpf());
        dto.setBirthDate(business.getBirthDate());
        dto.setDueDate(business.getDueDate());
        dto.setName(business.getName());

        if (business.getIdBandeira() != null){
            FlagDTO flagDTO  = new FlagDTO();
            flagDTO.setId(business.getIdBandeira().getId());
            flagDTO.setDescription(business.getIdBandeira().getDescription());
            dto.setFlag(flagDTO);
        }
        return dto;
    }

    private List<CardDTO> listToDto(List<Card> list){
        List<CardDTO> listDto = new ArrayList<CardDTO>();
        for (Card c: list){
            listDto.add(this.businessToDto(c));
        }
        return listDto;
    }

    public CardDTO createCard(CardDTO card){
        Card newCard = this.dtoToBusiness(card);

        if (newCard.getIdBandeira() != null) {
            Long id = newCard.getIdBandeira().getId();
            Flag f;

            if (id != null) {
                f = this.flagRepository.getById(id);
            } else {
                f = this.flagRepository.save(newCard.getIdBandeira());
            }
            newCard.setIdBandeira(f);
        }
        newCard= cardRepository.save(newCard);
        return this.businessToDto(newCard);
    }

    public List<CardDTO> findAllCards(){
        List<Card> allList = cardRepository.findAll();
        return this.listToDto(allList);
    }

    public CardDTO searchById(Long id) {
        Optional<Card> option = cardRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }


    public CardDTO updateCard(CardDTO dto, Long numero) {
        Optional<Card> op = cardRepository.findById(numero);

        if(op.isPresent()){
            Card obj = op.get();

            if (dto.getCardNumber() != null){
                obj.setCardNumber(dto.getCardNumber());
            }
            if (dto.getCpf() != null){
                obj.setCpf(dto.getCpf());
            }
            if (dto.getBirthDate() != null){
                obj.setBirthDate(dto.getBirthDate());
            }
            if (dto.getDueDate() != null){
                obj.setDueDate(dto.getDueDate());
            }
            if (dto.getName() != null){
                obj.setName(dto.getName());
            }
            if (dto.getFlag() != null){
                if (dto.getFlag().getId() != null){
                    if (flagRepository.existsById(obj.getIdBandeira().getId())){
                        obj.setIdBandeira(flagRepository.getById(dto.getFlag().getId()));
                    } else {
                        obj.getIdBandeira().setDescription(dto.getFlag().getDescription());
                        obj.setIdBandeira(flagRepository.save(obj.getIdBandeira()));
                    }
                } else {
                    obj.getIdBandeira().setDescription(dto.getFlag().getDescription());
                    obj.setIdBandeira(flagRepository.save(obj.getIdBandeira()));
                }
            }

            cardRepository.save(obj);
            return businessToDto(obj);
        }
        return null;
    }

    public void deleteCard(Long number) {
        if (cardRepository.existsById(number)){
            cardRepository.deleteById(number);
        }
    }
}
