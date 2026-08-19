import type { PokemonModel } from "./pokemon.model"

export interface CardModel {
    id: number
    pokemon: PokemonModel
    count: number
    firstTimeObtained: string
}