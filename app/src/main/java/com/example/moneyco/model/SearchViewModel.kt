package com.example.moneyco.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.moneyco.utils.SearchState

class SearchViewModel : ViewModel() {
    private val _searchState: MutableState<SearchState> = mutableStateOf(SearchState.CLOSED)
    val searchState: State<SearchState> = _searchState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchState(newVal: SearchState) {
        _searchState.value = newVal
    }


    fun updateSearchTextState(newVal: String) {
        _searchTextState.value = newVal
    }
}