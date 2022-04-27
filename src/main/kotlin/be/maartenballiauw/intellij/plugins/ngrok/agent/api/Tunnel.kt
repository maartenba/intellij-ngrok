package be.maartenballiauw.intellij.plugins.ngrok.agent.api

import com.google.gson.annotations.SerializedName

data class Tunnel(
    @SerializedName("name") var name: String,
    @SerializedName("uri") var uri: String,
    @SerializedName("public_url") var publicUrl: String,
    @SerializedName("proto") var protocol: TunnelProtocol,
    /* TODO
"config": {
    "addr": "localhost:80",
    "inspect": true,
},
     */
    @SerializedName("metrics") var metrics: TunnelMetrics,
)