package be.maartenballiauw.intellij.plugins.ngrok.agent.api

import be.maartenballiauw.intellij.plugins.ngrok.Constants
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private val gsonConverter = GsonConverterFactory
    .create(
        GsonBuilder()
            .setLenient()
            .disableHtmlEscaping()
            .create())

interface NGrokAgentService {

    companion object {
        val INSTANCE = Retrofit.Builder()
            .baseUrl(Constants.NGROK_AGENT_API_BASE)
            .addConverterFactory(gsonConverter)
            .build()
            .create(NGrokAgentService::class.java)
    }

    @Headers("Accept: application/json")
    @GET("tunnels")
    fun listTunnels() : Call<TunnelsList>

    @Headers("Accept: application/json")
    @GET("tunnels/{name}")
    fun getTunnel(@Path("name") name: String) : Call<Tunnel>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json")
    @POST("tunnels")
    fun createTunnel(@Body request: CreateTunnelRequest) : Call<Tunnel>

    @DELETE("tunnels/{name}")
    fun stopTunnel(@Path("name") name: String) : Call<String?>
}