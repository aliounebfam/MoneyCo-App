package com.example.moneyco.screens.main.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.moneyco.ui.theme.Nunito
import java.util.*
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun TransactionItem(
    categorie: String,
    sousCategorie: String,
    description: String,
    date: Date
) {
    val cornerRadius: Dp = 15.dp
    val squareSize = 60.dp
    val swipeAbleState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 1)

    Surface(
        elevation = 3.dp,
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Box(
            modifier = Modifier
                .swipeable(
                    state = swipeAbleState,
                    anchors = anchors,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.3f)
                    },
                    orientation = Orientation.Horizontal
                )
                .clip(RoundedCornerShape(cornerRadius))
                .fillMaxWidth()
                .clickable {

                }
        ) {

            Box(
                modifier = Modifier.align(
                    Alignment.CenterEnd
                )
            ) {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFF44336),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            swipeAbleState.offset.value.roundToInt(), 0
                        )
                    }
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(Color(0xffECEFF1))
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Text(
                        text = "Catégorie: $categorie",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Nunito
                    )
                    if (sousCategorie != " ") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Sous catégorie: $sousCategorie",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = Nunito
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "$date",
                        style = MaterialTheme.typography.caption,
                        color = Color(0xff424242),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = Nunito
                    )
                }
            }
        }
    }
}
