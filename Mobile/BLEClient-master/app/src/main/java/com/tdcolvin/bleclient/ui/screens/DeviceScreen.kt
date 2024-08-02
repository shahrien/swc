package com.tdcolvin.bleclient.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tdcolvin.bleclient.ble.CTF_SERVICE_UUID
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import android.net.Uri
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScreen(
    unselectDevice: () -> Unit,
    isDeviceConnected: Boolean,
    discoveredCharacteristics: Map<String, List<String>>,
    password: String?,
    nameWrittenTimes: Int,
    connect: () -> Unit,
    discoverServices: () -> Unit,
    readPassword: () -> Unit,
    writeName: () -> Unit
) {
    val foundTargetService = discoveredCharacteristics.contains(CTF_SERVICE_UUID.toString())
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var phoneNumber by remember { mutableStateOf("") }

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = connect) {
                Text("Connect to the devices")
            }
            OutlinedButton(onClick = unselectDevice) {
                Text("Disconnect")
            }


        }

        Text("Device connected: $isDeviceConnected")

        if (isDeviceConnected) {
            Spacer(modifier = Modifier.height(25.dp))
            Text("Guardian phone number:" ,
                modifier = Modifier.fillMaxWidth(0.7f), // Fill the width to ensure alignment
                style = TextStyle(fontSize = 16.sp,
                    fontWeight = FontWeight.Bold, // Set text to bold
                    textAlign = TextAlign.Start))
            OutlinedTextField(
                enabled = isDeviceConnected,
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone
                ),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(top = 8.dp)
            )

            Button(onClick = {
                val message = "Hi, Welcome to SafeWalk" // Replace with your message
                val uri = Uri.parse("smsto:$phoneNumber")
                val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
                    putExtra("sms_body", message)
                    setPackage("com.whatsapp")
                }
                try {
                    ContextCompat.startActivity(context, intent, null)
                } catch (e: Exception) {
                    // Handle the exception if WhatsApp is not installed
                    e.printStackTrace()
                }
            }) {
                Text("Send WhatsApp Message")
            }
        }

    }
}
