package rs.ac.singidunum.itws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.itws.entity.Pokemon;
import rs.ac.singidunum.itws.repo.PokemonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository repository;

    public List<Pokemon> getPokemons() {
        return repository.findAll();
    }

    public List<Pokemon> getPokemonsByRarity(String rarity) {
        return repository.findAllByRarity(rarity);
    }

    public Optional<Pokemon> getPokemonById(Integer id) {
        return repository.findOneById(id);
    }

    public void createPokemon(Pokemon pokemon) {
        checkPokemonConsistency(pokemon);
        Pokemon newPokemon = new Pokemon();
        newPokemon.setName(pokemon.getName());
        newPokemon.setRarity(pokemon.getRarity());
        newPokemon.setApiId(pokemon.getApiId());
        repository.save(newPokemon);
    }

    public void updatePokemon(Integer id, Pokemon pokemon) {
        Pokemon existing = repository.findOneById(id).orElseThrow();
        checkPokemonConsistencyUpdate(pokemon, existing);
        existing.setName(pokemon.getName());
        existing.setRarity(pokemon.getRarity());
        existing.setApiId(pokemon.getApiId());
        repository.save(existing);
    }

    public void deletePokemon(Integer id) {
        Pokemon existing = repository.findOneById(id).orElseThrow();
        repository.delete(existing);
    }

    private void checkPokemonConsistency(Pokemon pokemon) {
        if (pokemon.getName().isEmpty()) {
            throw new RuntimeException("POKEMON_NAME_CANT_BE_EMPTY");
        }

        if (pokemon.getRarity().isEmpty()) {
            throw new RuntimeException("POKEMON_RARITY_CANT_BE_EMPTY");
        }

        if (repository.existsByApiId(pokemon.getApiId())) {
            throw new RuntimeException("POKEMON_EXISTS_BY_API_ID");
        }

        if (repository.existsByName(pokemon.getName())) {
            throw new RuntimeException("POKEMON_EXISTS_BY_NAME");
        }
    }

    private void checkPokemonConsistencyUpdate(Pokemon pokemon, Pokemon existing) {
        if((!pokemon.getName().equals(existing.getName())) && (repository.existsByName(pokemon.getName()))) {
            throw new RuntimeException("POKEMON_EXISTS_BY_NAME");
        }

        if((!pokemon.getApiId().equals(existing.getApiId())) && (repository.existsByApiId(pokemon.getApiId()))) {
            throw new RuntimeException("POKEMON_EXISTS_BY_API");
        }
    }
}
