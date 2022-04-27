package be.maartenballiauw.intellij.plugins.ngrok.agent.api

import be.maartenballiauw.intellij.plugins.ngrok.Constants
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

val gsonConverter = GsonConverterFactory
    .create(
        GsonBuilder()
        .setLenient()
        .disableHtmlEscaping()
        .create())

val ngrokAgentService = Retrofit.Builder()
    .baseUrl(Constants.NGROK_AGENT_API_BASE)
    .addConverterFactory(gsonConverter)
    .build()
    .create(NGrokAgentService::class.java)

interface NGrokAgentService {
    @Headers("Content-Type: application/json")
    @GET("tunnels")
    fun listTunnels() : Call<ListTunnelsResponse>
}

data class ListTunnelsResponse(
    @SerializedName("tunnels") var tunnels: MutableList<Tunnel>
)

data class Tunnel(
    @SerializedName("name") var name: String,
    @SerializedName("uri") var uri: String,
    @SerializedName("public_url") var publicUrl: String,
    @SerializedName("proto") var protocol: String,
    /* TODO
"config": {
    "addr": "localhost:80",
    "inspect": true,
},
     */
    @SerializedName("metrics") var metrics: TunnelMetrics,
)

data class TunnelMetrics(
    @SerializedName("conns") var connections: TunnelMetricsValues?,
    @SerializedName("http") var http: TunnelMetricsValues?
)

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