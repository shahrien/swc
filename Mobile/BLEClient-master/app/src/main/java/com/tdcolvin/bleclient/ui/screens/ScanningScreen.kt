package com.tdcolvin.bleclient.ui.screens

import android.bluetooth.BluetoothDevice
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdcolvin.bleclient.R
import com.tdcolvin.bleclient.ble.PERMISSION_BLUETOOTH_CONNECT
import com.tdcolvin.bleclient.ble.PERMISSION_BLUETOOTH_SCAN
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale

@Composable
@RequiresPermission(allOf = [PERMISSION_BLUETOOTH_SCAN, PERMISSION_BLUETOOTH_CONNECT])
fun ScanningScreen(
    isScanning: Boolean,
    foundDevices: List<BluetoothDevice>,
    startScanning: () -> Unit,
    stopScanning: () -> Unit,
    selectDevice: (BluetoothDevice) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.safe_walk),
            contentDescription = "Deskripsi Imej",
            modifier = Modifier
                .fillMaxWidth() // or any modifier you need
                .padding(bottom = 5.dp)
                .size(350.dp), // space between image and button
            contentScale = ContentScale.Fit
        )
        if (isScanning) {
            Text("Scanning...")

            Button(onClick = stopScanning) {
                Text("Stop Scanning")
            }
        }
        else {
            Button(
                onClick = startScanning
            ) {
                Text("Start Scanning")
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f) 
        ) {
            items(foundDevices) { device ->
                DeviceItem(
                    deviceName = device.name,
                    selectDevice = { selectDevice(device) }
                )
            }
        }
    }
}

@Composable
fun DeviceItem(deviceName: String?, selectDevice: () -> Unit) {
    if (deviceName != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = deviceName,
                    textAlign = TextAlign.Center,
                )
                Button(onClick = selectDevice) {
                    Text("Connect")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDeviceItem() {
    DeviceItem(deviceName = "A test BLE device", selectDevice = { })
}