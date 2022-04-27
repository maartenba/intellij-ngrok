package be.maartenballiauw.intellij.plugins.ngrok.agent.api

import com.google.gson.annotations.SerializedName

data class TunnelMetrics(
    @SerializedName("conns") var connections: TunnelMetricsValues?,
    @SerializedName("http") var http: TunnelMetricsValues?
)