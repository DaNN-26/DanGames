package com.example.dangames.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.unit.dp
import com.example.dangames.design.theme.DanGamesTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            disabledIndicatorColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            disabledTextColor = DanGamesTheme.colors.hint,
            disabledLeadingIconColor = DanGamesTheme.colors.hint,
            focusedContainerColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            unfocusedContainerColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            unfocusedIndicatorColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            focusedIndicatorColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            focusedTextColor = DanGamesTheme.colors.opposite,
            unfocusedTextColor = DanGamesTheme.colors.opposite,
            focusedLeadingIconColor = DanGamesTheme.colors.opposite,
            unfocusedLeadingIconColor = DanGamesTheme.colors.opposite,
            disabledPlaceholderColor = DanGamesTheme.colors.opposite,
            focusedPlaceholderColor = DanGamesTheme.colors.opposite,
            unfocusedPlaceholderColor = DanGamesTheme.colors.opposite,
        ),
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        )
    )
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            disabledIndicatorColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            disabledTextColor = DanGamesTheme.colors.hint,
            disabledLeadingIconColor = DanGamesTheme.colors.hint,
            focusedContainerColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            unfocusedContainerColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            unfocusedIndicatorColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            focusedIndicatorColor = DanGamesTheme.colors.singleAltColor.copy(alpha = 0.5f),
            focusedTextColor = DanGamesTheme.colors.opposite,
            unfocusedTextColor = DanGamesTheme.colors.opposite,
            focusedLeadingIconColor = DanGamesTheme.colors.opposite,
            unfocusedLeadingIconColor = DanGamesTheme.colors.opposite,
            disabledPlaceholderColor = DanGamesTheme.colors.opposite,
            focusedPlaceholderColor = DanGamesTheme.colors.opposite,
            unfocusedPlaceholderColor = DanGamesTheme.colors.opposite,
        ),
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        keyboardActions = KeyboardActions(
            onDone = { keyboard?.hide() }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        )
    )
}