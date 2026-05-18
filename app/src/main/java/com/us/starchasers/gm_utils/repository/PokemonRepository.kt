package com.us.starchasers.gm_utils.repository

import com.us.starchasers.gm_utils.data.remote.PokeApi
import com.us.starchasers.gm_utils.data.remote.responses.Pokemon
import com.us.starchasers.gm_utils.data.remote.responses.PokemonList
import com.us.starchasers.gm_utils.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error(message = "An error occurred: ${e.message}")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonDetail(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonDetail(name)
        } catch (e: Exception) {
            return Resource.Error(message = "An error occurred: ${e.message}")
        }
        return Resource.Success(response)
    }

}