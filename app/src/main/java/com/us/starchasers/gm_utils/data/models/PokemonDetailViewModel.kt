package com.us.starchasers.gm_utils.data.models

import androidx.lifecycle.ViewModel
import com.us.starchasers.gm_utils.data.remote.responses.Pokemon
import com.us.starchasers.gm_utils.repository.PokemonRepository
import com.us.starchasers.gm_utils.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonDetail(pokemonName)
    }
}