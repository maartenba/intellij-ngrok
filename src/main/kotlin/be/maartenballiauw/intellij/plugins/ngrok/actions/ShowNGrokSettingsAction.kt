package be.maartenballiauw.intellij.plugins.ngrok.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import be.maartenballiauw.intellij.plugins.ngrok.NGrokBundle
import be.maartenballiauw.intellij.plugins.ngrok.configuration.NGrokSettingsConfigurable

class ShowNGrokSettingsAction
    : AnAction(
    NGrokBundle.message("action.ngrok.show_settings.name"),
    NGrokBundle.message("action.ngrok.show_settings.description"),
    AllIcons.Actions.EditSource
) {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        NGrokSettingsConfigurable.showSettings(project)
    }
}