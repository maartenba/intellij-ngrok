package be.maartenballiauw.intellij.plugins.ngrok.agent.api

import com.google.gson.annotations.SerializedName

enum class TunnelProtocol(val value: String) {
    @SerializedName("http")
    HTTP("http"),

    @SerializedName("tcp")
    TCP("tcp"),

    @SerializedName("tls")
    TLS("tls")
}