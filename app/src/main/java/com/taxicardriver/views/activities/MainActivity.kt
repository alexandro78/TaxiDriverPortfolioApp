package com.taxicardriver.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.taxicardriver.network.connectbuilders.socketbuilders.websocketclients.HomeScreenWebSocketClien
import com.taxicardriver.ui.theme.TAXICARDRIVERTheme
import com.taxicardriver.views.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TAXICARDRIVERTheme {
                Navigation()
            }
        }
    }
}



//Базовий приклад організації web socket з'єднання///////////////////////////
////////////////////////////////////////////////////////////////////////////
@Composable
fun HomeScreenT() {
    var message by remember { mutableStateOf("Awaiting messages...") }
    var connectionStatus by remember { mutableStateOf("Connecting...") }
    var isDisconnected by remember { mutableStateOf(false) }

    val homeScreenWebSocketClien = remember {
        HomeScreenWebSocketClien(
            "ws://192.168.0.187:8080/websocket",
            onMessageReceived = { incomingMessage ->
                message = incomingMessage  // Status update when a message is received
            },
            onStatusChanged = { status ->
                connectionStatus = status
                if (status.contains("Failure") || status.contains("Closing")) {
                    isDisconnected = true
                } else if (status.contains("Connected")) {
                    isDisconnected = false
                }
            // Updating the connection status
            }
        ) { unexpected ->
            isDisconnected = unexpected
        }
    }

    Column {
        Button(onClick = { homeScreenWebSocketClien.connect() }) {
            Text("Connect to Server")
        }
        Button(onClick = { homeScreenWebSocketClien.disconnect() }) {
            Text("Disconnect")
        }
        Text(text = "Connection status: $connectionStatus")
        Text(text = "Server response: $message")
        if (!isDisconnected) {
            Text(text = "Warning: Lost connection to the server", color = Color.Red)
        }
        Button(onClick = {
            homeScreenWebSocketClien.sendMessage("{\"content1\":\"content1\", \"content2\":\"content2\"}")
        }) {
            Text("Send Message")
        }
    }
}
///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////