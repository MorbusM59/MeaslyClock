package com.measlyclock.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.measlyclock.data.SetType
import androidx.compose.ui.Alignment
import java.util.Locale

private fun SetType.displayName(): String =
    name.lowercase(Locale.getDefault()).replaceFirstChar { it.titlecase(Locale.getDefault()) }

private val PRESET_COLORS = listOf(
    Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFFE91E63),
    Color(0xFFFF9800), Color(0xFF9C27B0), Color(0xFF00BCD4),
    Color(0xFFF44336), Color(0xFF009688), Color(0xFFFFEB3B),
    Color(0xFF607D8B)
)

@Composable
fun AddAlarmSetDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, color: Color, type: SetType) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(PRESET_COLORS.first()) }
    var selectedType by remember { mutableStateOf(SetType.STANDALONE) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Alarm Set") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Color", style = MaterialTheme.typography.labelMedium)
                ColorPicker(
                    colors = PRESET_COLORS,
                    selectedColor = selectedColor,
                    onColorSelected = { selectedColor = it }
                )

                Text("Type", style = MaterialTheme.typography.labelMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SetType.entries.forEach { type ->
                        FilterChip(
                            selected = selectedType == type,
                            onClick = { selectedType = type },
                            label = { Text(type.displayName()) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank()) onConfirm(name.trim(), selectedColor, selectedType) },
                enabled = name.isNotBlank()
            ) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun ColorPicker(
    colors: List<Color>,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val rowSize = 5
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        colors.chunked(rowSize).forEach { rowColors ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowColors.forEach { color ->
                    val isSelected = color == selectedColor
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(color, CircleShape)
                            .then(
                                if (isSelected) Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                                else Modifier
                            )
                            .clickable { onColorSelected(color) }
                    )
                }
            }
        }
    }
}
