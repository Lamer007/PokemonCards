import type { CardModel } from '@/models/card.model';
import type { PokemonModel } from '@/models/pokemon.model';
import axios from 'axios';

export const API_BASE = 'http://localhost:7000/api'

const client = axios.create({
    baseURL: API_BASE,
    headers: {
        'Accept': 'application/json',
        'X-Name': 'MyApp',
    },
});

export class DataService {
    static async getPokemons() {
        return client.get<PokemonModel[]>('/pokemon')
    }

    static async getPokemonsByRarity(rarity: string) {
        return client.get<PokemonModel[]>(`/pokemon/rarity/${rarity}`)
    }

    static async getPokemonById(id: number) {
        return client.get<PokemonModel>(`/pokemon/${id}`)
    }

    static async getCards() {
        return await client.get('/card')
    }

    static async getCardById(id: number) {
        return await client.get(`/card/${id}`)
    }

    static async getCardByPokemonId(pokemonId: number) {
        return await client.get(`/card/pokemon/${pokemonId}`)
    }

    static async createCard(card: any) {
        return await client.request({
            url: `/card`,
            method: 'POST',
            data: card
        })
    }

    static async updateCard(id: number, card: CardModel) {
        return await client.request({
            url: `/card/${id}`,
            method: 'PUT',
            data: card
        })
    }
}