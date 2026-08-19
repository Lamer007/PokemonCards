<script setup lang="ts">
  import Loading from '@/components/Loading.vue';
  import { formatDate, getImageUrl } from '@/utils';
  import { DataService } from '@/services/data.service';
  import { onBeforeUnmount, onMounted, ref,computed } from 'vue';
  import type { CardModel } from '@/models/card.model';
  import type { PokemonModel } from '@/models/pokemon.model';

  const cards = ref<CardModel[]>();
  const pokemons = ref<PokemonModel[]>();

  function loadData() {
      DataService.getCards()
          .then(rsp => cards.value = rsp.data)
          .catch(err => console.error(err));

      DataService.getPokemons()
          .then(rsp => pokemons.value = rsp.data)
          .catch(err => console.error(err));
  }

  const interval = setInterval(() => loadData(), 5 * 60 * 1000)
  onMounted(()=> loadData())
  onBeforeUnmount(()=>clearInterval(interval))
</script>

<template>
    <div class="text-center" v-if="pokemons && cards && cards.length > 0">
      <h1>Collected: {{ cards.length }}/{{ pokemons.length}}</h1>
      <div class="card-wrapper" >
          <div :class="`card tilt-card ${card.pokemon.rarity}-card` " v-for="card in cards">
              <img :src="getImageUrl(card.pokemon.apiId)" class="card-img-top" :alt="card.pokemon.name">
              <div class="card-body">
                  <h5 class="card-title">{{ card.pokemon.name }}</h5>
                  <h6 class="card-subtitle mb-2 text-body-secondary">Collected: {{ card.count }}</h6>
                  <h6 class="card-subtitle mb-2 text-body-secondary">Obtained: {{ formatDate(card.firstTimeObtained) }}</h6>
              </div>
          </div>
      </div>
    </div>
    <Loading v-else />
</template>

<style>
.card-wrapper {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;

}

.tilt-card {
    perspective: 1000px; 
    transition: transform 0.1s ease-out;
    transform-style: preserve-3d;
    text-align: center;
    margin: 1rem;
}

.common-card {
  background: #252525;
  background-size: 400% 400%;
  border: none;
  color: #fff;
  max-width: 18rem;
}

.rare-card {
  background: linear-gradient(270deg, #ff7e5f, #feb47b, #349e24, #166f08);
  background-size: 400% 400%;
  border: none;
  color: #fff;
  max-width: 18rem;
}

.epic-card {
  background: linear-gradient(270deg, #ff7e5f, #feb47b, #86a8e7, #7f7fdd);
  background-size: 400% 400%;
  border: 1px solid #86a8e7;
  box-shadow: 0 0 15px #7f7fdd;
  transition: box-shadow 0.3s ease-in-out;
  color: #fff;
  max-width: 18rem;
}

.epic-card:hover {
  box-shadow: 0 0 25px #7f7fdd;
}

.legendary-card {
  background: linear-gradient(270deg, #f6da87, #feb47b, #86a8e7, #7f7fdd);
  background-size: 400% 400%;
  animation: gradientShimmer 8s ease infinite;
  border: none;
  box-shadow: 0 0 15px #7f7fdd;
  transition: box-shadow 0.3s ease-in-out;
  color: #fff;
  max-width: 18rem;
}

.legendary-card:hover {
  box-shadow: 0 0 25px #c9caee;
}

.card-img-top {
    background-color: #2e2e2e2f;
}

@keyframes gradientShimmer {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}
</style>