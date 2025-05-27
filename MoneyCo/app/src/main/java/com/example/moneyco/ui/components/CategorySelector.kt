package com.example.moneyco.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moneyco.data.database.entities.CategoryEntity

@Composable
fun CategorySelector(
    categories: List<CategoryEntity>,
    selectedCategoryId: Long?,
    onCategorySelected: (CategoryEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Catégories",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    isSelected = category.id == selectedCategoryId,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoryEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = if (isSelected) 4.dp else 1.dp,
        backgroundColor = if (isSelected) 
            MaterialTheme.colors.primary
        else 
            MaterialTheme.colors.surface
    ) {
        Text(
            text = category.name,
            color = if (isSelected) 
                MaterialTheme.colors.onPrimary
            else 
                MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}