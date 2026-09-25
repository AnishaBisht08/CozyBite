package com.example.cozybite.ui.screens


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cozybite.R
import com.example.cozybite.ui.theme.DarkBrown
import com.example.cozybite.ui.theme.SecondaryText
import com.example.cozybite.ui.theme.SoftYellow
import com.example.cozybite.ui.theme.Surface
import com.example.cozybite.viewmodel.AuthViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onLoginClick: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val credentialManager = remember { CredentialManager.create(context = context) }
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {

        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = DarkBrown
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Create your account",
            color = DarkBrown,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Join CozyBite and discover your next favorite recipe.",
            color = SecondaryText
        )

        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text("Full Name")
            },
            shape = RoundedCornerShape(8.dp),
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLeadingIconColor = MaterialTheme.colorScheme.secondary,
                unfocusedLeadingIconColor = SecondaryText

            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text("Email address")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLeadingIconColor = MaterialTheme.colorScheme.secondary,
                unfocusedLeadingIconColor = SecondaryText

            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text("Password")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        imageVector =
                            if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation =
                if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLeadingIconColor = MaterialTheme.colorScheme.secondary,
                unfocusedLeadingIconColor = SecondaryText

            )
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                }
                if (password.length > 6) {
                    Toast.makeText(
                        context,
                        "Password should be at least 6 characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                viewModel.registerUser(
                    username = name,
                    email = email,
                    password = password
                ) { success, error ->
                    if (success) {
                        onSignUpClick()
                        Toast.makeText(context, "Sign up successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            context,
                            error ?: "Sign up failed, try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(26.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            } else {
                Text("Create Account")
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = SoftYellow
            )

            Text(
                text = "  or  ",
                color = SecondaryText
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = SoftYellow
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                scope.launch {
                    googleSignUp(
                        context = context,
                        credentialManager = credentialManager,
                        viewModel = viewModel
                    ) { success ->
                        if (success) {
                            onGoogleClick()
                        } else {
                            Toast.makeText(
                                context,
                                "Login failed, Please try again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Surface,
                contentColor = DarkBrown
            )
        ) {

            Icon(
                painter = painterResource(R.drawable.google),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Continue with Google")
        }



        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Already have an account? ",
                color = SecondaryText
            )

            androidx.compose.material3.TextButton(
                onClick = onLoginClick
            ) {
                Text(
                    text = "Log In",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}


suspend fun googleSignUp(
    context: Context,
    credentialManager: CredentialManager,
    viewModel: AuthViewModel,
    onResult: (Boolean) -> Unit
) {

    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId("911578035030-i1aok0nbu23kghmde7uat4uklq48g61o.apps.googleusercontent.com")
        .build()


    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {

        val result = credentialManager.getCredential(
            context = context,
            request = request
        )

        val credential = result.credential

        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

            val googleCredential = GoogleIdTokenCredential.createFrom(credential.data)

            viewModel.signInWithGoogle(
                idToken = googleCredential.idToken
            ) { success ->

                onResult(success)
            }
        }
    } catch (e: Exception) {
        e.message
    }

}