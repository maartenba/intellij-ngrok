package be.maartenballiauw.intellij.plugins.ngrok.configuration.application

import com.intellij.openapi.components.BaseState

class NGrokSettingsState : BaseState() {
    var executablePath by string()
    var authToken by string()
}