package com.taxicardriver.network.connectbuilders.socketbuilders.websocketclients

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.taxicardriver.network.connectbuilders.socketbuilders.AddJwtToWebSocketConnectionClients
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class HomeScreenWebSocketClien(
    private val url: String,
    private val onMessageReceived: (String) -> Unit,
    private val onStatusChanged: (String) -> Unit,
    private val onDisconnected: (Boolean) -> Unit
) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    ///reconnection///
    private var isManualDisconnect = false
    private var isConnected = false
    private val reconnectHandler = Handler(Looper.getMainLooper())
    private val reconnectRunnable = Runnable { connect() }

    ///ping checker///
    private val heartbeatHandler = Handler(Looper.getMainLooper())
    private val heartbeatRunnable = Runnable { sendHeartbeat() }
    private val heartbeatInterval: Long = 10000


    ////////////////////////////////////////////////////////////////
    private var pingReceived = false
    private val pingCheckHandler = Handler(Looper.getMainLooper())
    private val pingCheckRunnable = Runnable { checkPingResponse() }

    fun connect() {
        val request = AddJwtToWebSocketConnectionClients.createRequest(url)
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                isManualDisconnect = false
                isConnected = true
                onStatusChanged("Connected to: ${response.request.url} with status code: ${response.code}")
                reconnectHandler.removeCallbacks(reconnectRunnable) // Delete scheduled reconnection attempts
                onDisconnected(false) //Перекидаємо статус в змінну isUnexpectedDisconnected в HomeScreenViewModel, щоб не показувати попередження про розриз socket з'єдняння

                startHeartbeat() // Запуск heartbeat при успішному підключенні
                startPingCheck()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessageReceived(text)
                Log.d("homewebsocket", "Полученное Received message: $text") // Логування отриманого повідомлення
                pingReceived = true
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) { //Викликається завжди при закритті з'єднання, як ручного так і непередбачуваного
                //Дублюємо isConnected = false на випадок, якщо з'єднання непередбачуваного втрачене.
                isConnected = false
                onStatusChanged("Closing: $code $reason")

                stopHeartbeat() // Зупинка heartbeat при будь-якому завершенні з'єднання
                stopPingCheck()
                Log.d(
                    "homewebsocket",
                    "Спрацював onClosing() метод Connection closing: $code $reason"
                )
                if (!isManualDisconnect) {
                    scheduleReconnect()
                    onDisconnected(true) // Notify about unexpected disconnection ////Перекидаємо статус true в змінну isUnexpectedDisconnected в HomeScreenViewModel для відображення попередження про непередбачувану втрату сокет зв'язку
                } else {
                    onDisconnected(false) // Notify about manual disconnection ////Перекидаємо статус false в змінну isUnexpectedDisconnected в HomeScreenViewModel, щоб не відображати попередження про непередбачувану втрату сокет зв'язку
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                isConnected = false
                Log.d("homewebsocket", "Розірваний WIFI: крок 1 виклик onFailure() методу, змінна isConnected = false")
                onStatusChanged("Failure: ${t.message}")
                stopHeartbeat() // Зупинка heartbeat при непередбачуваній втраті сокет зв'язку
                stopPingCheck()
                if (!isManualDisconnect) { //Якщо isManualDisconnect = false тобто розрив через непередбачувані причини
                    scheduleReconnect()
                    onDisconnected(true) // Повідомляємо про непередбачену втрату сокету. ////Перекидаємо статус true в змінну isUnexpectedDisconnected в HomeScreenViewModel для відображення попередження про непередбачувану втрату сокет зв'язку
                    Log.d("homewebsocket", "Метод onFailure() гілка if з'являється повідомлення про про непередбачену втрату сокету")
                } else { // Спрацьовує на ручне завершення сокет з'єднання.
                    onDisconnected(false) //// Перекидаємо статус false в змінну isUnexpectedDisconnected в HomeScreenViewModel, щоб не з'явилось повідомлення про непередбачувану втрату сокет зв'язку
                    Log.d("homewebsocket", "Метод onFailure() гілка else запобігаємо появі повідомлення про непередбачену втрату сокету")
                    Log.d("homewebsocket", "Connection failed: ${t.message}")
                }
            }
        })
    }


    fun sendMessage(message: String) {
        if (isConnected) {
            webSocket?.send(message)
            Log.d("homewebsocket", "Sent message: $message")
        } else {
            onStatusChanged("Message not sent. Connection is not established.")
            Log.d("homewebsocket", "Message not sent. Connection is not established.")
        }
    }

    fun disconnect() { //Контрольоване закриття сокет з'єднання
        isManualDisconnect = true //Задаємо isManualDisconnect = true, щоб працювали умовні конструкції, якщо розрив зв'язку контрольований то запобігти повторному перепідключенню до сокет в onFailure() методі
        onDisconnected(false)
        isConnected = false //Задаемо стан змінної false, щоб зупинити спроби відправки повідомлень на сервер в методі sendMessage()
        // а також, щоб зупинити відправку ping кожні 10 сек через sendHeartbeat()

        webSocket?.close(1000, "Client closed the connection")
        onStatusChanged("Disconnected by client")

        //Зупиняємо авто перепідключення до сокету кожні 7 сек.
        reconnectHandler.removeCallbacks(reconnectRunnable)

        //Зупиняємо heartbeat ping через кожні 10 сек.
        stopHeartbeat()
    }


    private fun scheduleReconnect() { //Викликаємо в onFailure() методі при непередбачуваному разриві сокету з сервером,
        // а також викликається в sendHeartbeat() методі при умові, що змінна isConnected = false щоб відновити сокет з'єднання через 7 сек.
        reconnectHandler.postDelayed(reconnectRunnable, 7000)
        Log.d("homewebsocket", "Втрачено WIFI, крок 3 викликався scheduleReconnect()")
    }


    private fun startHeartbeat() { //Викликається всередині connect() метода,
        // для запуску механізму регулярної відправки ping повідомлень через метод sendHeartbeat() кожні 10 сек.
        // що призводить до виклику sendHeartbeat() кожні 10 сек.
        heartbeatHandler.postDelayed(heartbeatRunnable, heartbeatInterval)
        Log.d("homewebsocket", "Викликався в scheduleReconnect() відправка heartbeat кожні 10 сек")
    }


    private fun stopHeartbeat() {
        heartbeatHandler.removeCallbacks(heartbeatRunnable)
        Log.d("homewebsocket", "Якщо WIFI зв'язок розірвано крок 2")
    }


    private fun sendHeartbeat() { //Виклик sendHeartbeat() метода кожні 10 сек. після виклика метода startHeartbeat()
        if (isConnected) {
            val pingRequestMessage = "{\"ping\":true}"
            webSocket?.send(pingRequestMessage)
            heartbeatHandler.postDelayed(heartbeatRunnable, heartbeatInterval)
            Log.d("homewebsocket", "fun sendHeartbeat() гілка if (isConnected)")
        } else {
            scheduleReconnect()
            Log.d("homewebsocket", "fun sendHeartbeat() гілка else")
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////
    private fun startPingCheck() {
        pingCheckHandler.postDelayed(pingCheckRunnable, 15000)
        Log.d("homewebsocket", "Started ping check with interval: 10000")
    }

    private fun stopPingCheck() {
        pingCheckHandler.removeCallbacks(pingCheckRunnable)
        Log.d("homewebsocket", "Stopped ping check")
    }

    private fun checkPingResponse() {
        if (!pingReceived) {
            isConnected = false
            onStatusChanged("Ping timeout. No response received for ping.")
            onDisconnected(true)
            stopHeartbeat()
            scheduleReconnect()
            Log.d("homewebsocket", "checkPingResponse() ветка if")
        } else {
            Log.d("homewebsocket", "checkPingResponse() ветка else")
            pingReceived = false // Скидаємо значення для наступної перевірки
            startPingCheck() // Запускаємо перевірку знову
        }
    }
}












