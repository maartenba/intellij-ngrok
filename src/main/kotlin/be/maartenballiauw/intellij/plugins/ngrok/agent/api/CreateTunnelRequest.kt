package be.maartenballiauw.intellij.plugins.ngrok.agent.api

import com.google.gson.annotations.SerializedName

data class CreateTunnelRequest(
    @SerializedName("name") val name: String?,
    @SerializedName("addr") val address: String,
    @SerializedName("proto") val protocol: TunnelProtocol
)