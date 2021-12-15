package com.example.moneyco.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DropDownMenuText() {

    val suggestions = listOf("Item1", "Item2", "Item3")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(" ") }

    val icon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown


    Column(modifier = Modifier.clickable {
        expanded = !expanded
    }) {
        OutlinedTextField(

            value = selectedText,
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                Icon(icon, "contentDescription", Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        expanded = false
                    }) {
                    Text(text = label)
                }
            }
        }

    }
}

@Preview
@Composable
fun DropDownMenuTextPrev() {
    DropDownMenuText()
}