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
import com.example.moneyco.data.database.entities.SubCategoryEntity

@Composable
fun SubCategorySelector(
    subCategories: List<SubCategoryEntity>,
    selectedSubCategoryId: Long?,
    onSubCategorySelected: (SubCategoryEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Sous-catégories",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        if (subCategories.isEmpty()) {
            Text(
                text = "Veuillez d'abord sélectionner une catégorie",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(subCategories) { subCategory ->
                    SubCategoryItem(
                        subCategory = subCategory,
                        isSelected = subCategory.id == selectedSubCategoryId,
                        onClick = { onSubCategorySelected(subCategory) }
                    )
                }
            }
        }
    }
}

@Composable
fun SubCategoryItem(
    subCategory: SubCategoryEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = if (isSelected) 4.dp else 1.dp,
        backgroundColor = if (isSelected) 
            MaterialTheme.colors.secondary
        else 
            MaterialTheme.colors.surface
    ) {
        Text(
            text = subCategory.name,
            color = if (isSelected) 
                MaterialTheme.colors.onSecondary
            else 
                MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}