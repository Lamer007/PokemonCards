package rs.ac.singidunum.itws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.itws.entity.Card;
import rs.ac.singidunum.itws.entity.Pokemon;
import rs.ac.singidunum.itws.service.CardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(path = "/api/card")
public class CardController {
    private final CardService service;

    @GetMapping
    public List<Card> getAll() {
        return service.getCards();
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Card> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getCardById(id));
    }

    @GetMapping(path = "/pokemon/{pokemonId}")
    public ResponseEntity<Card> getByPokemonId(@PathVariable Integer pokemonId) {
        return ResponseEntity.of(service.getCardByPokemon(pokemonId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody Card card) {
        service.createCard(card);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Card card) {
        service.updateCard(id, card);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteCard(id);
    }
}
