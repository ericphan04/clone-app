package com.clone.meetup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.clone.meetup.ui.components.MeetupPrimaryButton
import com.clone.meetup.ui.components.MeetupTextButton
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme

/**
 * Auth screen. The real app delegates to `base.auth.LoginWebActivity` / `SignUpWebActivity`
 * (hosted WebViews). Rebuilt native, same visual language: 32sp title, 4dp-radius inputs on
 * palette_text_entry_background, 48dp peach CTA, divider-with-label between the two paths.
 * Fields mirror the WebView form: email, password, plus the social row above.
 */
@Composable
fun AuthScreen(
    onBack: () -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface),
        contentPadding = PaddingValues(bottom = MeetupSpacing.xlarge),
    ) {
        item {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(MeetupSpacing.tiny),
            ) {
                Surface(
                    onClick = onBack,
                    shape = CircleShape,
                    color = colors.surface,
                    modifier = Modifier.size(40.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.textPrimary,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = MeetupSpacing.normal)) {
                Spacer(Modifier.height(MeetupSpacing.table))
                Text(
                    text = "Welcome back",
                    style = MaterialTheme.typography.displayLarge,
                    color = colors.textPrimary,
                )
                Spacer(Modifier.height(MeetupSpacing.tiny))
                Text(
                    text = "Sign in to see your events, groups, and messages.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.textSecondary,
                )
                Spacer(Modifier.height(MeetupSpacing.stripe))

                MeetupField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email address",
                    keyboardType = KeyboardType.Email,
                )
                Spacer(Modifier.height(MeetupSpacing.small))
                MeetupField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    showPassword = showPassword,
                    onTogglePassword = { showPassword = !showPassword },
                )

                Spacer(Modifier.height(MeetupSpacing.small))
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.viridian,
                )

                Spacer(Modifier.height(MeetupSpacing.stripe))
                MeetupPrimaryButton(
                    text = "Sign in",
                    onClick = onSignIn,
                    enabled = email.isNotBlank() && password.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(MeetupSpacing.stripe))
                DividerWithLabel("or")
                Spacer(Modifier.height(MeetupSpacing.stripe))

                SocialRow("Continue with Google")
                Spacer(Modifier.height(MeetupSpacing.tiny))
                SocialRow("Continue with Apple")
                Spacer(Modifier.height(MeetupSpacing.tiny))
                SocialRow("Continue with Facebook")

                Spacer(Modifier.height(MeetupSpacing.stripe))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "New to Meetup? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                    MeetupTextButton(text = "Create an account", onClick = { })
                }
            }
        }
    }
}

@Composable
private fun MeetupField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onTogglePassword: (() -> Unit)? = null,
) {
    val colors = MeetupTheme.colors
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label, style = MaterialTheme.typography.bodyMedium) },
        singleLine = true,
        shape = RoundedCornerShape(MeetupShape.textEntry),
        textStyle = MaterialTheme.typography.bodyLarge,
        visualTransformation = if (isPassword && !showPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next,
        ),
        trailingIcon = if (isPassword && onTogglePassword != null) {
            {
                Surface(onClick = onTogglePassword, color = colors.surface) {
                    Text(
                        text = if (showPassword) "Hide" else "Show",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.viridian,
                        modifier = Modifier.padding(horizontal = MeetupSpacing.tiny),
                    )
                }
            }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colors.textPrimary,
            unfocusedBorderColor = colors.grey5,
            focusedContainerColor = colors.surface,
            unfocusedContainerColor = colors.surface,
            cursorColor = colors.viridian,
        ),
    )
}

@Composable
private fun DividerWithLabel(label: String) {
    val colors = MeetupTheme.colors
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(colors.grey5),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = colors.textTertiary,
            modifier = Modifier.padding(horizontal = MeetupSpacing.small),
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(colors.grey5),
        )
    }
}

@Composable
private fun SocialRow(label: String) {
    val colors = MeetupTheme.colors
    Surface(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(MeetupShape.button),
        color = colors.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.grey5),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = colors.textPrimary,
            )
        }
    }
}
