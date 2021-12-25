package com.example.moneyco.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun EditableTextField(
    content: String,
    label: String,
    icon: ImageVector,
    field: String,
    descriptionIcon: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {

    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser
    val docRef = db.collection("users").document(currentUser!!.uid)

    var text by remember { mutableStateOf(content) }
    val value by remember { mutableStateOf(content) }
    var enable by remember { mutableStateOf(false) }


    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = label) },
                placeholder = { Text(text = "") },
                leadingIcon = { Icon(icon, descriptionIcon) },
                trailingIcon = {
                    if (text.isNotEmpty() && enable) {
                        Icon(Icons.Filled.Clear, "clear", modifier = Modifier.clickable {
                            text = ""
                        })
                    }
                },
                enabled = enable,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    leadingIconColor = MaterialTheme.colors.secondaryVariant,
                    disabledBorderColor = MaterialTheme.colors.secondaryVariant,
                    disabledLabelColor = MaterialTheme.colors.secondaryVariant,
                    disabledLeadingIconColor = MaterialTheme.colors.secondaryVariant,
                    unfocusedLabelColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier.weight(0.9f)
            )
            if (!enable) {
                IconButton(
                    onClick = {
                        enable = !enable
                    },
                    modifier = Modifier.weight(0.1f)
                ) {
                    Icon(
                        Icons.Outlined.Edit,
                        "edit",
                        tint = MaterialTheme.colors.secondaryVariant
                    )
                }
            }
        }

        if (enable) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    docRef.update(field, text)
                    enable = !enable
                }) {
                    Icon(Icons.Rounded.Done, "done", tint = Color(0xFF388E3C))
                }
                if (text != value) {
                    IconButton(onClick = { text = value }) {
                        Icon(Icons.Rounded.Clear, "clear", tint = Color.Red)
                    }
                }
            }
        }

    }
}