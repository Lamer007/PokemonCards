package rs.ac.singidunum.itws.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.itws.entity.Card;
import rs.ac.singidunum.itws.entity.Pokemon;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findOneById(Integer id);
    Optional<Card> findOneByPokemonId(Integer id);
    Boolean existsByPokemon(Pokemon pokemon);
}
