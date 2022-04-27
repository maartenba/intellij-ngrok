package be.maartenballiauw.intellij.plugins.ngrok.agent.api

import com.google.gson.annotations.SerializedName

data class TunnelMetricsValues(
    @SerializedName("count") var count: Double,
    @SerializedName("gauge") var gauge: Double,
    @SerializedName("rate1") var rate1: Double,
    @SerializedName("rate5") var rate5: Double,
    @SerializedName("rate15") var rate15: Double,
    @SerializedName("p50") var p50: Double,
    @SerializedName("p90") var p90: Double,
    @SerializedName("p95") var p95: Double,
    @SerializedName("p99") var p99: Double
)