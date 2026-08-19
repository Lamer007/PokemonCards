<script setup lang="ts">
  import Loading from '@/components/Loading.vue';
  import { getImageUrl } from '@/utils';
  import { DataService } from '@/services/data.service';
  import { ref, computed, onMounted } from 'vue';
  import type { CardModel } from '@/models/card.model';
  import type { PokemonModel } from '@/models/pokemon.model';

  const cards = ref<CardModel[]>();
  const pokemons = ref<PokemonModel[]>();
  const pokemon = ref<PokemonModel>({ id: 1, name: "Let's go gambling", rarity: "common", apiId: 400 });

  function loadData() {
      DataService.getCards()
          .then(rsp => cards.value = rsp.data)
          .catch(err => console.error(err));

      DataService.getPokemons()
          .then(rsp => pokemons.value = rsp.data)
          .catch(err => console.error(err));
  }

  async function randomPokemon() {
    const rand = Math.floor(Math.random() * 100) + 1;
    let rarity = "";

    if(rand <= 60) {
      rarity = "common";
    } else if(rand > 60 && rand <= 85) {
      rarity = "rare";
    } else if(rand > 85 && rand <= 95) {
      rarity = "epic";
    } else if(rand > 95) {
      rarity = "legendary";
    }

    const pokemonsRarity = ref<PokemonModel[]>();
    await DataService.getPokemonsByRarity(rarity)
          .then(rsp => pokemonsRarity.value = rsp.data)
          .catch(err => console.error(err));


    const selectedPokemon = pokemonsRarity.value?.[Math.floor(Math.random() * (pokemonsRarity.value?.length || 0))];
    
    if (!selectedPokemon) return;
    const pokemonId = selectedPokemon.id;

    await DataService.getPokemonById(pokemonId)
      .then(rsp => pokemon.value = rsp.data)
      .catch(err => console.error(err));
    
    const card = ref<CardModel>();
    let isCardExists = true;

    await DataService.getCardByPokemonId(pokemonId)
      .then(rsp => {
          card.value = rsp.data;
          card.value!.count += 1;
      })
      .catch(err => isCardExists = false);

    if (isCardExists) {
      await DataService.updateCard(card.value!.id, card.value!)
        .then(rsp => {return true})
        .catch(err => console.error(err));
    } else {
      const newCard: CardModel = {
        id: 0,
        pokemon: pokemon.value!,
        count: 1,
        firstTimeObtained: new Date().toISOString()
      };

      await DataService.createCard(newCard)
        .then(rsp => {return true})
        .catch(err => console.error(err));
    }
  }

  onMounted(()=> loadData())

  const rotateX = ref(0);
  const rotateY = ref(0);

  const cardStyle = computed(() => ({
    transform: `perspective(1000px) rotateX(${rotateX.value}deg) rotateY(${rotateY.value}deg)`,
  }));

  const handleMove = (e: MouseEvent) => {
    const card = e.currentTarget as HTMLElement;
    const box = card.getBoundingClientRect();
    
    const x = e.clientX - box.left - box.width / 2;
    const y = e.clientY - box.top - box.height / 2;
    
    rotateX.value = -(y / box.height) * 20;
    rotateY.value = (x / box.width) * 20;
  };

  const handleLeave = () => {
    rotateX.value = 0;
    rotateY.value = 0;
  };
</script>

<template>
    <div class="text-center" v-if="pokemon && pokemons && cards && cards.length > 0">
      <div class="card-wrapper">
          <div :class="`card tilt-card ${pokemon.rarity}-card`" @mousemove="handleMove" @mouseleave="handleLeave" :style="cardStyle">
              <img :src="getImageUrl(pokemon.apiId)" class="card-img-top" :alt="pokemon.name">
              <div class="card-body">
                  <h1 class="card-title">{{ pokemon.name }}</h1>
              </div>
          </div>
      </div>
      <button type="button" class="big-button" @click="randomPokemon()"><h1 class="card-title">Gamble</h1></button>
    </div>
    <Loading v-else />
</template>

<style scoped>

.tilt-card {
  max-width: none;

}

.card-wrapper {
  margin-top: 10rem;
}

.card-title {
    color: #fff;
    text-shadow: 0 0 5px #000;
}

.big-button {
  background-color: #1cb04b;
  border: 1px solid #1cb04b;
  box-shadow: 0 0 10px #1cb04b;
  transition: box-shadow 0.3s ease-in-out;
  padding: 2rem 5rem;
  margin-top: 10rem;
  font-size: 1.25rem;
  border-radius: 0.75rem;
  display: inline-block;
}

.big-button:hover {
  background-color: #158c3a;
  border: 1px solid #158c3a;
  box-shadow: 0 0 10px #158c3a;
}

.big-button:active {
  background-color: #0c5d25;
  border: 1px solid #0c5d25;
  box-shadow: 0 0 10px #0c5d25;
}

</style>