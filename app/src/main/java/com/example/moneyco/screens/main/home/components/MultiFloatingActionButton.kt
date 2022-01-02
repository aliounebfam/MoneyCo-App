package com.example.moneyco.screens.main.home.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyco.model.FabIcon
import com.example.moneyco.model.fab.FabOption
import com.example.moneyco.model.fab.MultiFabItem
import com.example.moneyco.model.fab.MultiFabState
import com.example.moneyco.model.fab.rememberMultiFabState

@ExperimentalAnimationApi
@Composable
fun MultiFloatingActionButton(
    modifier: Modifier = Modifier,
    items: List<MultiFabItem>,
    fabState: MutableState<MultiFabState> = rememberMultiFabState(),
    fabIcon: FabIcon,
    fabOption: FabOption = FabOption(),
    onFabItemClicked: (fabItem: MultiFabItem) -> Unit,
    stateChanged: (fabState: MultiFabState) -> Unit = {}
) {
    val rotation by animateFloatAsState(
        if (fabState.value == MultiFabState.Expand) {
            fabIcon.iconRotate ?: 0f
        } else {
            0f
        }
    )

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = fabState.value.isExpanded(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(items.size) { index ->
                    MiniFabItem(
                        item = items[index],
                        fabOption = fabOption,
                        onFabItemClicked = onFabItemClicked
                    )
                }

                item {}
            }
        }

        FloatingActionButton(
            onClick = {
                fabState.value = fabState.value.toggleValue()
                stateChanged(fabState.value)
            },
            backgroundColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            Icon(
                imageVector = fabIcon.iconRes,
                contentDescription = "FAB",
                modifier = Modifier.rotate(rotation),
                tint = fabOption.iconTint
            )
        }
    }
}

@Composable
fun MiniFabItem(
    item: MultiFabItem,
    fabOption: FabOption,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (fabOption.showLabel) {
            Text(
                text = item.label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                onFabItemClicked(item)
            },
            modifier = Modifier.size(48.5.dp),
            backgroundColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            Icon(
                imageVector = item.iconRes,
                contentDescription = "FAB Icon",
                tint = fabOption.iconTint
            )
        }
    }
}