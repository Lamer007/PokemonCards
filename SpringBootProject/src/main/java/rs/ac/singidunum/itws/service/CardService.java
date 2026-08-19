package rs.ac.singidunum.itws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.itws.entity.Card;
import rs.ac.singidunum.itws.entity.Pokemon;
import rs.ac.singidunum.itws.repo.CardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepo;

    public List<Card> getCards() {
        return cardRepo.findAll();
    }

    public Optional<Card> getCardById(Integer id) {
        return cardRepo.findOneById(id);
    }

    public Optional<Card> getCardByPokemon(Integer pokemonId) {
        return cardRepo.findOneByPokemonId(pokemonId);
    }

    public void createCard(Card card) {
        checkCardConsistency(card);
        Card newCard = new Card();
        newCard.setPokemon(card.getPokemon());
        newCard.setCount(1);
        newCard.setFirstTimeObtained(LocalDateTime.now());
        cardRepo.save(newCard);
    }

    public void updateCard(Integer id, Card card) {
        Card existing = cardRepo.findOneById(id).orElseThrow();
        existing.setCount(card.getCount());
        cardRepo.save(existing);
    }

    public void deleteCard(Integer id) {
        Card existing = cardRepo.findOneById(id).orElseThrow();
        cardRepo.delete(existing);
    }

    private void checkCardConsistency(Card card) {
        if (cardRepo.existsByPokemon(card.getPokemon())) {
            throw new RuntimeException("CARD_EXISTS_BY_POKEMON");
        }
    }
}
