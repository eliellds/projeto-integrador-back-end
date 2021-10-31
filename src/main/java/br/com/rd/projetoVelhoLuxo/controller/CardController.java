package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.CardDTO;
import br.com.rd.projetoVelhoLuxo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping
    public CardDTO create(@RequestBody CardDTO card) {
        return cardService.createCard(card);
    }

    @GetMapping
    public List<CardDTO> findAll() {
        return cardService.findAllCards();
    }

    @GetMapping("/{id}")
    public CardDTO searchById(@PathVariable("id") Long id) {
        return cardService.searchById(id);
    }

    @PutMapping("/{number}")
    public CardDTO updateByNumber(@RequestBody CardDTO dto, @PathVariable("number") Long number){
        return cardService.updateCard(dto, number);
    }

    @DeleteMapping("/{number}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteByNumber(@PathVariable("number") Long number){
        cardService.deleteCard(number);
    }

}
