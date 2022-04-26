package be.maartenballiauw.intellij.plugins.ngrok.configuration

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager


@State(name = "NGrokSettings", storages = [Storage("ngrok.xml")])
class NGrokSettings : SimplePersistentStateComponent<NGrokSettingsState>(NGrokSettingsState()) {

    companion object {
        const val defaultArguments = "http https://localhost:5001"

        @JvmStatic
        fun getInstance(project: Project): NGrokSettings = project.service()

        fun getInstanceForDefaultProject(): NGrokSettings {
            return ProjectManager.getInstance().defaultProject.service()
        }
    }

    var executablePath
        get() = state.executablePath
        set(value) { state.executablePath = value }

    var arguments
        get() = state.arguments
        set(value) { state.arguments = value }

    override fun noStateLoaded() {
        super.noStateLoaded()
        loadState(NGrokSettingsState())
    }
}