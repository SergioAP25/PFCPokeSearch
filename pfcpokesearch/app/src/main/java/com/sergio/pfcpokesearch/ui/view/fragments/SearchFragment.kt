package com.sergio.pfcpokesearch.ui.view.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sergio.pfcpokesearch.databinding.FragmentSearchBinding
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import com.sergio.pfcpokesearch.ui.view.DetailActivity
import com.sergio.pfcpokesearch.ui.view.recyclerview.PokemonAdapter
import com.sergio.pfcpokesearch.ui.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    // Instancia del pokemonViewModel
    private lateinit var pokemonViewModel: PokemonViewModel
    // Instancia del adapter
    private lateinit var adapter: PokemonAdapter
    // Lista donde se almacenarán los tipos por los que ordenar
    private var typeList: MutableList<String> = mutableListOf()
    // Lista de botones
    private lateinit var buttonList: List<View>
    // Lista de última query que almacenará la última query de la barra de búsqueda
    private var lastQuery: String = ""
    // String que indicará el tipo de ordenado A-Z o Z-A
    private var ordering: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        // Asigna a la variable el viewmodel correspondiente
        pokemonViewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        // Inicialización de la lista de botones con cada una de sus views
        buttonList = listOf(binding.normal, binding.fire, binding.water, binding.grass,
            binding.electric, binding.ice, binding.ground, binding.flying,
            binding.poison, binding.fighting, binding.psychic, binding.dark,
            binding.rock, binding.bug, binding.ghost, binding.steel, binding.dragon, binding.fairy)

        return binding.root
    }

    // Función que inicializa los observer del viewmodel
    private fun observer(query: String){
        // Asigna el observer a isLoading
        pokemonViewModel.isLoading.observe(viewLifecycleOwner, Observer {isLoading ->
            // Gestiona cuando mostrar la barra de carga en función de si isLoading
            // es true o false además de asignar una lista vacía al recyclerview
            // si está cargando
            if(isLoading){
                adapter.setData(emptyList())
                binding.bar.setVisibility(View.VISIBLE)
            }
            else{
                binding.bar.setVisibility(View.GONE)
            }
        })

        // Si el hilo del viewmodel no es null lo cancela, esto se debe a que
        // por cada búsqueda se generara un hilo, y no sabes cuando van a terminar
        // dando resultados incorrectos y sobrecargando la aplicación
        if(pokemonViewModel.scope!=null){
            pokemonViewModel.scope!!.cancel()
        }

        // Llama a la función pokemonSearch para buscar los pokemons
        pokemonViewModel.pokemonSearch(query, ordering, typeList)
        // // Asigna el observer a pokemonModel
        pokemonViewModel.pokemonModel.observe(viewLifecycleOwner, Observer {pokemon ->
            // Cuando la lista cambia en pokemonModel asigna los nuevos pokemon al recyclerview
            adapter.setData(pokemon)
            manageVisibility(pokemon)
        })
    }

    // Se encarga de controlar la visibilidad del recyclerview
    private fun manageVisibility(pokemon: List<FilteredPokemon>) {
        if (pokemon.isEmpty()){
            // Si la lista está vacía quita el recyclerview
            binding.rvPokemon.setVisibility(View.GONE)
            // y hace visible not found que es un texto
            binding.notFound.setVisibility(View.VISIBLE)
        }
        else{
            // En el caso contrario es al revés
            binding.rvPokemon.setVisibility(View.VISIBLE)
            binding.notFound.setVisibility(View.GONE)
        }
    }

    // Función que inicializa los listeners de los botones de ordenado A-Z y Z-A
    private fun initOrderingButtons(){
        binding.az.setOnClickListener {
            if(!binding.az.isSelected){
                // Si el botón no está seleccionado
                if (!binding.za.isSelected){
                    // Y el otro botón no está seleccionado, marca como seleccionado el botón
                    binding.az.isSelected = true
                    // Le asigna el color de fondo correspondiente
                    binding.az.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    // Actualiza el String que indica el tipo de ordenado al correspondiente
                    ordering = "az"
                    // Invoca a la función observer con la última query insertada en la barra
                    // de búsqueda para actualizar la lista de pokemons
                    observer(lastQuery)
                }
            }
            else{
                // Si el botón ya estaba previamentente seleccionado lom deselecciona
                binding.az.isSelected = false
                // Le retira el fondo
                binding.az.background = null
                // Actualiza el String de ordenado a un String vacío indicando que no hay
                // filtrado por ordenado
                ordering = ""
                // Invoca a la función observer con la última query insertada en la barra
                // de búsqueda para actualizar la lista de pokemons
                observer(lastQuery)
            }
        }

        binding.za.setOnClickListener {
            if(!binding.za.isSelected){
                if (!binding.az.isSelected){
                    binding.za.isSelected = true
                    binding.za.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    ordering = "za"
                    observer(lastQuery)
                }
            }
            else{
                binding.za.isSelected = false
                binding.za.background = null
                ordering = ""
                observer(lastQuery)
            }
        }
    }

    // Función que inicializa los listeners de los botones de tipos
    private fun initButtons(){
        binding.normal.setOnClickListener {
            if(!binding.normal.isSelected){
                // Si no está seleccionado cuenta el número de botones seleccionados
                if (countSelectedButtons()<2){
                    // Si es menor que 2 lo selecciona
                    binding.normal.isSelected = true
                    // Le da el color correspondiente
                    binding.normal.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    // Añade un tipo a la lista de tipos
                    typeList.add("normal")
                    // Invoca a la función observer con la última query insertada en la barra
                    // de búsqueda para actualizar la lista de pokemons
                    observer(lastQuery)
                }
            }
            else{
                // Si hay 2 o más botones deselecciona el bottón
                binding.normal.isSelected = false
                // Le quita el fondo
                binding.normal.background = null
                // Quita el tipo de la lista de tipos
                typeList.remove("normal")
                // Invoca a la función observer con la última query insertada en la barra
                // de búsqueda para actualizar la lista de pokemons
                observer(lastQuery)
            }
        }

        binding.fire.setOnClickListener {
            if(!binding.fire.isSelected){
                if (countSelectedButtons()<2){
                    binding.fire.isSelected = true
                    binding.fire.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("fire")
                    observer(lastQuery)
                }
            }
            else{
                binding.fire.isSelected = false
                binding.fire.background = null
                typeList.remove("fire")
                observer(lastQuery)
            }
        }

        binding.water.setOnClickListener {
            if(!binding.water.isSelected){
                if (countSelectedButtons()<2){
                    binding.water.isSelected = true
                    binding.water.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("water")
                    observer(lastQuery)
                }
            }
            else{
                binding.water.isSelected = false
                binding.water.background = null
                typeList.remove("water")
                observer(lastQuery)
            }
        }

        binding.grass.setOnClickListener {
            if(!binding.grass.isSelected){
                if (countSelectedButtons()<2){
                    binding.grass.isSelected = true
                    binding.grass.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("grass")
                    observer(lastQuery)
                }
            }
            else{
                binding.grass.isSelected = false
                binding.grass.background = null
                typeList.remove("grass")
                observer(lastQuery)
            }
        }

        binding.electric.setOnClickListener {
            if(!binding.electric.isSelected){
                if (countSelectedButtons()<2){
                    binding.electric.isSelected = true
                    binding.electric.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("electric")
                    observer(lastQuery)
                }
            }
            else{
                binding.electric.isSelected = false
                binding.electric.background = null
                typeList.remove("electric")
                observer(lastQuery)
            }
        }

        binding.ice.setOnClickListener {
            if(!binding.ice.isSelected){
                if (countSelectedButtons()<2){
                    binding.ice.isSelected = true
                    binding.ice.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("ice")
                    observer(lastQuery)
                }
            }
            else{
                binding.ice.isSelected = false
                binding.ice.background = null
                typeList.remove("ice")
                observer(lastQuery)
            }
        }

        binding.ground.setOnClickListener {
            if(!binding.ground.isSelected){
                if (countSelectedButtons()<2){
                    binding.ground.isSelected = true
                    binding.ground.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("ground")
                    observer(lastQuery)
                }
            }
            else{
                binding.ground.isSelected = false
                binding.ground.background = null
                typeList.remove("ground")
                observer(lastQuery)
            }
        }

        binding.flying.setOnClickListener {
            if(!binding.flying.isSelected){
                if (countSelectedButtons()<2){
                    binding.flying.isSelected = true
                    binding.flying.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("flying")
                    observer(lastQuery)
                }
            }
            else{
                binding.flying.isSelected = false
                binding.flying.background = null
                typeList.remove("flying")
                observer(lastQuery)
            }
        }

        binding.poison.setOnClickListener {
            if(!binding.poison.isSelected){
                if (countSelectedButtons()<2){
                    binding.poison.isSelected = true
                    binding.poison.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("poison")
                    observer(lastQuery)
                }
            }
            else{
                binding.poison.isSelected = false
                binding.poison.background = null
                typeList.remove("poison")
                observer(lastQuery)
            }
        }

        binding.fighting.setOnClickListener {
            if(!binding.fighting.isSelected){
                if (countSelectedButtons()<2){
                    binding.fighting.isSelected = true
                    binding.fighting.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("fighting")
                    observer(lastQuery)
                }
            }
            else{
                binding.fighting.isSelected = false
                binding.fighting.background = null
                typeList.remove("fighting")
                observer(lastQuery)
            }
        }

        binding.psychic.setOnClickListener {
            if(!binding.psychic.isSelected){
                if (countSelectedButtons()<2){
                    binding.psychic.isSelected = true
                    binding.psychic.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("psychic")
                    observer(lastQuery)
                }
            }
            else{
                binding.psychic.isSelected = false
                binding.psychic.background = null
                typeList.remove("psychic")
                observer(lastQuery)
            }
        }

        binding.dark.setOnClickListener {
            if(!binding.dark.isSelected){
                if (countSelectedButtons()<2){
                    binding.dark.isSelected = true
                    binding.dark.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("dark")
                    observer(lastQuery)
                }
            }
            else{
                binding.dark.isSelected = false
                binding.dark.background = null
                typeList.remove("dark")
                observer(lastQuery)
            }
        }

        binding.rock.setOnClickListener {
            if(!binding.rock.isSelected){
                if (countSelectedButtons()<2){
                    binding.rock.isSelected = true
                    binding.rock.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("rock")
                    observer(lastQuery)
                }
            }
            else{
                binding.rock.isSelected = false
                binding.rock.background = null
                typeList.remove("rock")
                observer(lastQuery)
            }
        }

        binding.bug.setOnClickListener {
            if(!binding.bug.isSelected){
                if (countSelectedButtons()<2){
                    binding.bug.isSelected = true
                    binding.bug.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("bug")
                    observer(lastQuery)
                }
            }
            else{
                binding.bug.isSelected = false
                binding.bug.background = null
                typeList.remove("bug")
                observer(lastQuery)
            }
        }

        binding.ghost.setOnClickListener {
            if(!binding.ghost.isSelected){
                if (countSelectedButtons()<2){
                    binding.ghost.isSelected = true
                    binding.ghost.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("ghost")
                    observer(lastQuery)
                }
            }
            else{
                binding.ghost.isSelected = false
                binding.ghost.background = null
                typeList.remove("ghost")
                observer(lastQuery)
            }
        }

        binding.steel.setOnClickListener {
            if(!binding.steel.isSelected){
                if (countSelectedButtons()<2){
                    binding.steel.isSelected = true
                    binding.steel.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("steel")
                    observer(lastQuery)
                }
            }
            else{
                binding.steel.isSelected = false
                binding.steel.background = null
                typeList.remove("steel")
                observer(lastQuery)
            }
        }

        binding.dragon.setOnClickListener {
            if(!binding.dragon.isSelected){
                if (countSelectedButtons()<2){
                    binding.dragon.isSelected = true
                    binding.dragon.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("dragon")
                    observer(lastQuery)
                }
            }
            else{
                binding.dragon.isSelected = false
                binding.dragon.background = null
                typeList.remove("dragon")
                observer(lastQuery)
            }
        }

        binding.fairy.setOnClickListener {
            if(!binding.fairy.isSelected){
                if (countSelectedButtons()<2){
                    binding.fairy.isSelected = true
                    binding.fairy.setBackgroundColor(Color.parseColor("#DAD3D3"))
                    typeList.add("fairy")
                    observer(lastQuery)
                }
            }
            else{
                binding.fairy.isSelected = false
                binding.fairy.background = null
                typeList.remove("fairy")
                observer(lastQuery)
            }
        }
    }

    // Función que se encarga de cargar el estado de los botones pasando por la lista
    // de tipos
    private fun loadButtonState(){
        typeList.forEach {
            when(it){
                "normal" -> {
                    // Lo coloca a true
                    binding.normal.isSelected = true
                    // Le da el color correspondiente
                    binding.normal.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "fire" -> {
                    binding.fire.isSelected = true
                    binding.fire.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "water" -> {
                    binding.water.isSelected = true
                    binding.water.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "grass" -> {
                    binding.grass.isSelected = true
                    binding.grass.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "electric" -> {
                    binding.electric.isSelected = true
                    binding.electric.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "ice" -> {
                    binding.ice.isSelected = true
                    binding.ice.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "ground" -> {
                    binding.ground.isSelected = true
                    binding.ground.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "flying" -> {
                    binding.flying.isSelected = true
                    binding.flying.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "poison" -> {
                    binding.poison.isSelected = true
                    binding.poison.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "fighting" -> {
                    binding.fighting.isSelected = true
                    binding.fighting.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "psychic" -> {
                    binding.psychic.isSelected = true
                    binding.psychic.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "dark" -> {
                    binding.dark.isSelected = true
                    binding.dark.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "rock" -> {
                    binding.rock.isSelected = true
                    binding.rock.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "bug" -> {
                    binding.bug.isSelected = true
                    binding.bug.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "ghost" -> {
                    binding.ghost.isSelected = true
                    binding.ghost.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "steel" -> {
                    binding.steel.isSelected = true
                    binding.steel.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "dragon" -> {
                    binding.dragon.isSelected =true
                    binding.dragon.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
                "fairy" -> {
                    binding.fairy.isSelected =true
                    binding.fairy.setBackgroundColor(Color.parseColor("#DAD3D3"))
                }
            }
        }

        when(ordering){
            "az" -> {
                binding.az.isSelected =true
                binding.az.setBackgroundColor(Color.parseColor("#DAD3D3"))
            }

            "za" -> {
                binding.za.isSelected =true
                binding.za.setBackgroundColor(Color.parseColor("#DAD3D3"))
            }
        }
    }

    // Función que pasa por la lista de botones y cuenta los seleccionados
    private fun countSelectedButtons(): Int{
        var number = 0
        for (i in 0 until buttonList.size){
            if (buttonList[i].isSelected){
                number++
            }
        }
        return number
    }

    // Función que gestiona el cambio de actividad a detalle
    private fun navigatetoDetail(name:String){
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_NAME, name)
        startActivity(intent)
    }

    // Función que llama al view model para usar la función addFavorite
    private suspend fun addFavorite(name: String){
        pokemonViewModel.addFavoritePokemon(name)
    }
    // Función que llama al view model para usar la función removeFavorite
    private suspend fun removeFavorite(name: String){
        pokemonViewModel.removeFavoritePokemon(name)
    }

    // Función que llama al view model para usar la función isFavorite
    private suspend fun isFavorite(name: String): Boolean{
        return pokemonViewModel.isFavoritePokemon(name)
    }
}