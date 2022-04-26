package be.maartenballiauw.intellij.plugins.ngrok.configuration

import com.intellij.openapi.components.BaseState

class NGrokSettingsState : BaseState() {
    var executablePath by string()
    var arguments by string(NGrokSettings.defaultArguments)
}