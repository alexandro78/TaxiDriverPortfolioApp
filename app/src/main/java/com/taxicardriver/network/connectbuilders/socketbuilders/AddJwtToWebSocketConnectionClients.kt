package com.taxicardriver.network.connectbuilders.socketbuilders

import okhttp3.Request

object AddJwtToWebSocketConnectionClients {
    private var token: String = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJlbWFpbDJAY29tIiwicm9sZSI6IlVTRVIiLCJ1c2VySWQiOjMsImlhdCI6MTcxOTQ5MDMzMiwiZXhwIjoxNzIwMzU0MzMyfQ.379XFuV_doVnZ54Py-_i7npSlLFnyPjzYMM7VyW_7yxCmfpLuOFEd5t4QWIUd8CG"

    private fun getToken(): String {
        // Logic for obtaining or updating a token
        return token
    }

    fun createRequest(url: String): Request {
        return Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${getToken()}")
            .build()
    }
}