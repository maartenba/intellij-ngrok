package be.maartenballiauw.intellij.plugins.ngrok.configuration.project

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@State(name = "NGrokProjectSettings", storages = [Storage("ngrok.xml")])
class NGrokProjectSettings : SimplePersistentStateComponent<NGrokProjectSettingsState>(NGrokProjectSettingsState()) {

    companion object {
        @JvmStatic
        fun getInstance(project: Project): NGrokProjectSettings = project.service()
    }

    override fun noStateLoaded() {
        super.noStateLoaded()
        loadState(NGrokProjectSettingsState())
    }
}