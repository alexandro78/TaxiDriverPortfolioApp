package com.taxicardriver.network.connectbuilders.socketbuilders.websocketclients

import com.taxicardriver.network.connectbuilders.socketbuilders.AddJwtToWebSocketConnectionClients
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener


class TestWebSocketClient(
    private val url: String,
    private val onMessageReceived: (String) -> Unit,
    private val onStatusChanged: (String) -> Unit
) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect() {
        val request = AddJwtToWebSocketConnectionClients.createRequest(url)
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                onStatusChanged("Connected to: ${response.request.url} with status code: ${response.code}")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessageReceived(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                onStatusChanged("Closing: $code $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                onStatusChanged("Failure: ${t.message}")
            }
        })
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    fun disconnect() {
        webSocket?.close(1000, "Client closed the connection")
        onStatusChanged("Disconnected by client")
    }
}
