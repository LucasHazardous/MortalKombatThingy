package com.github.lucashazardous.mortalkombatthingy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.github.lucashazardous.mortalkombatthingy.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var characters = ArrayList<MortalKombatCharacter>()
    var previousCharacters = ArrayList<MortalKombatCharacter>()
    var characterCounter = HashMap<String, Int>()

    private val charactersToChoose = arrayOf(MortalKombatCharacter("Sub-Zero", R.drawable.subzero, 0),
        MortalKombatCharacter("Shang Tsung", R.drawable.shangtsung, 0),
        MortalKombatCharacter("Mileena", R.drawable.mileena, 0),
        MortalKombatCharacter("Scorpion", R.drawable.scorpion, 0),
        MortalKombatCharacter("Big Chungus", R.drawable.bigchungus, 0))

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = MortalKombatAdapter(characters)
        binding.recyclerView.adapter = adapter

        fun updateCharactersAdapter() {
            adapter.setItems(characters)
            adapter.notifyDataSetChanged()
        }

        fun savePreviousCharacters() {
            previousCharacters = characters.clone() as ArrayList<MortalKombatCharacter>
        }

        fun removeCharacterAt(index: Int) {
            val removed = characters.removeAt(index)

            if (characterCounter[removed.text]!! == removed.count) {
                characterCounter[removed.text] = characterCounter[removed.text]!! - 1
            }
        }

        binding.columnNumber.doAfterTextChanged {
            try {
                val value = binding.columnNumber.text.toString().toInt()
                if(value in 1..8) {
                    layoutManager.spanCount = value
                }
            } catch (_: Exception) { }
        }

        binding.btn1.setOnClickListener {
            savePreviousCharacters()
            val character = charactersToChoose[Random.nextInt(0, charactersToChoose.size)]
            characterCounter[character.text] = characterCounter.getOrDefault(character.text, 0) + 1
            characters.add(MortalKombatCharacter(character.text, character.img, characterCounter[character.text]!!))
            updateCharactersAdapter()
        }

        binding.btn2.setOnClickListener {
            savePreviousCharacters()
            characterCounter = HashMap()
            characters = ArrayList()
            updateCharactersAdapter()
        }

        binding.btn3.setOnClickListener {
            if(characters.size > 0) {
                savePreviousCharacters()
                removeCharacterAt(0)
                updateCharactersAdapter()
            }
        }

        binding.btn4.setOnClickListener {
            if(characters.size > 0){
                savePreviousCharacters()
                removeCharacterAt(characters.size-1)
                updateCharactersAdapter()
            }
        }

        binding.btn5.setOnClickListener {
            if(characters.size > 0){
                savePreviousCharacters()
                removeCharacterAt(Random.nextInt(0, characters.size))
                updateCharactersAdapter()
            }
        }

        binding.btn6.setOnClickListener {
            characters = previousCharacters
            characterCounter = HashMap()
            for(character in characters) {
                characterCounter[character.text] =
                    characterCounter.getOrDefault(character.text, 0).coerceAtLeast(character.count)
            }
            updateCharactersAdapter()
        }
    }
}