package be.maartenballiauw.intellij.plugins.ngrok.configuration.project

import com.intellij.ide.actions.ShowSettingsUtilImpl
import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import be.maartenballiauw.intellij.plugins.ngrok.NGrokBundle

class NGrokProjectSettingsConfigurable(private val project: Project): BoundSearchableConfigurable(
    NGrokBundle.message("ngrok.settings.project.name"),
    NGrokBundle.message("ngrok.settings.project.name"),
    _id = ID
) {

    companion object {
        const val ID = "Settings.Plugins.NGrok.Project"

        fun showSettings(project: Project) {
            ShowSettingsUtilImpl.showSettingsDialog(project, ID, "")
        }
    }

    private val settings
        get() = NGrokProjectSettings.getInstance(project)

    override fun createPanel(): DialogPanel {
        return panel {
            row("TODO") {

            }
        }
    }
}