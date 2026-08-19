package rs.ac.singidunum.itws.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.itws.entity.Pokemon;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {


    Optional<Pokemon> findOneById(Integer id);
    List<Pokemon>  findAllByRarity(String rarity);

    Boolean existsByName(String name);
    Boolean existsByApiId(Integer id);
}
