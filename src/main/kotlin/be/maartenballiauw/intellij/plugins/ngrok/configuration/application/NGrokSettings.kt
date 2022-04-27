package be.maartenballiauw.intellij.plugins.ngrok.configuration.application

import com.intellij.openapi.components.*
import com.intellij.util.application

@State(name = "NGrokSettings", storages = [Storage("ngrok.xml")])
class NGrokSettings : SimplePersistentStateComponent<NGrokSettingsState>(NGrokSettingsState()) {

    companion object {
        //const val defaultArguments = "http https://localhost:5001"

        @JvmStatic
        fun getInstance(): NGrokSettings = service()
    }

    var executablePath
        get() = state.executablePath
        set(value) { state.executablePath = value }

    var authToken
        get() = state.authToken
        set(value) { state.authToken = value }

    override fun noStateLoaded() {
        super.noStateLoaded()
        loadState(NGrokSettingsState())
    }
}