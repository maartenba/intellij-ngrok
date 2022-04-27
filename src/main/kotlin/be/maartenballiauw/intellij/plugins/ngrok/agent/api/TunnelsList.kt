package be.maartenballiauw.intellij.plugins.ngrok.agent.api

import com.google.gson.annotations.SerializedName

data class TunnelsList(
    @SerializedName("tunnels") var tunnels: MutableList<Tunnel>
)