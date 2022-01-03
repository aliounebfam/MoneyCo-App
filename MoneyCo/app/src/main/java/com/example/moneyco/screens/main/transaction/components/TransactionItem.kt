package com.example.moneyco.screens.main.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
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
import com.example.moneyco.data.GetMesTransactions
import com.example.moneyco.screens.main.profil.components.AlertDialogTransaction
import com.example.moneyco.ui.theme.Nunito
import com.example.moneyco.ui.theme.noir
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun TransactionItem(
    transaction: GetMesTransactions,
    afficher: Boolean = false,
    onDelete: () -> Unit
) {
    val cornerRadius: Dp = 15.dp
    val squareSize = 60.dp
    val swipeAbleState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 1)
    var boolean by remember { mutableStateOf(false) }

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

                .background(Color(0xFFEF5350))
        ) {
            if (boolean) {
                AlertDialogTransaction(transaction)
            }

            Box(
                modifier = Modifier.align(
                    Alignment.CenterEnd
                )
            ) {
                IconButton(
                    onClick = {
                        onDelete()
                    }
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
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
                    .clickable {
                        boolean = !boolean
                    }
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    if (afficher) {
                        Text(
                            text = "Type: ${transaction.type}",
                            style = MaterialTheme.typography.body1,
                            color = noir,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Nunito
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Text(
                        text = "Catégorie: ${transaction.categorie}",
                        style = MaterialTheme.typography.body1,
                        color = noir,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = Nunito
                    )
                    if (transaction.sousCategorie != " ") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Sous catégorie: ${transaction.sousCategorie}",
                            style = MaterialTheme.typography.body1,
                            color = noir,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = Nunito
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Montant: ${transaction.montant}",
                        style = MaterialTheme.typography.body1,
                        color = noir,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = Nunito
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = transaction.date,
                        fontSize = MaterialTheme.typography.button.fontSize,
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
