package com.example.nourishlens.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.R
import com.example.nourishlens.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: (String, String) -> Unit,
    onGuestLogin: () -> Unit
) {
    var emailOrBadgeId by remember { mutableStateOf("kartikey.singh@nourishlens.gov.in") }
    var password by remember { mutableStateOf("inspector2026") }
    var selectedStation by remember { mutableStateOf("New Delhi Region Station #04") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarmOat),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 500.dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // App Logo
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "NourishLens Logo",
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, ForestPrimary, RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "NourishLens",
                style = MaterialTheme.typography.headlineLarge,
                color = ForestPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )

            Text(
                text = "Legal Metrology & FSSAI Enforcement Portal",
                style = MaterialTheme.typography.bodyMedium,
                color = CharcoalVariant,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Auth Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, ForestPrimary.copy(alpha = 0.2f), RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = CardMilledLinen)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "INSPECTOR AUTHENTICATION",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )

                    // Email / Badge Input
                    OutlinedTextField(
                        value = emailOrBadgeId,
                        onValueChange = { emailOrBadgeId = it },
                        label = { Text("Officer Email / Badge ID") },
                        leadingIcon = {
                            Icon(Icons.Default.Badge, contentDescription = null, tint = ForestPrimary)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceWarmOat,
                            unfocusedContainerColor = SurfaceWarmOat,
                            focusedBorderColor = ForestPrimary,
                            unfocusedBorderColor = OutlineHairline
                        ),
                        singleLine = true
                    )

                    // Password Input
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = ForestPrimary)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle Password Visibility",
                                    tint = CharcoalVariant
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceWarmOat,
                            unfocusedContainerColor = SurfaceWarmOat,
                            focusedBorderColor = ForestPrimary,
                            unfocusedBorderColor = OutlineHairline
                        ),
                        singleLine = true
                    )

                    // Station Selection Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = ForestPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(
                                    text = "ASSIGNED REGIONAL STATION",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CharcoalVariant,
                                    fontSize = 9.sp
                                )
                                Text(
                                    text = selectedStation,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CharcoalEspresso,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Sign In Button
                    Button(
                        onClick = {
                            if (emailOrBadgeId.isNotBlank() && password.isNotBlank()) {
                                onLoginSuccess("Kartikey Singh (Senior Inspector)", selectedStation)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ForestPrimary),
                        shape = RoundedCornerShape(24.dp),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Login,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sign In to Enforcement Portal",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Guest Access Option
                    OutlinedButton(
                        onClick = onGuestLogin,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ForestPrimary),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text(
                            text = "Continue as Guest Inspector",
                            style = MaterialTheme.typography.labelMedium,
                            color = ForestPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Footer Note
            Text(
                text = "Authorized under Section 36 of Legal Metrology Act 2009 & FSSAI Regulations 2020. Unauthorised access is prohibited.",
                style = MaterialTheme.typography.bodySmall,
                color = CharcoalVariant,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
