package rs.ac.singidunum.itws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.itws.entity.Pokemon;
import rs.ac.singidunum.itws.service.PokemonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(path = "/api/pokemon")
public class PokemonController {
    private final PokemonService service;

    @GetMapping
    public List<Pokemon> getAll() {
        return service.getPokemons();
    }

    @GetMapping(path = "/rarity/{rarity}")
    public List<Pokemon> getByRarity(@PathVariable String rarity) {
        return service.getPokemonsByRarity(rarity);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pokemon> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getPokemonById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody Pokemon pokemon) {
        service.createPokemon(pokemon);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Pokemon pokemon) {
        service.updatePokemon(id, pokemon);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deletePokemon(id);
    }
}
