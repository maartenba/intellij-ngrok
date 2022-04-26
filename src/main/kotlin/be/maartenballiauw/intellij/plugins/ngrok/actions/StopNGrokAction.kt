package be.maartenballiauw.intellij.plugins.ngrok.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import be.maartenballiauw.intellij.plugins.ngrok.NGrokBundle
import be.maartenballiauw.intellij.plugins.ngrok.service.NGrokService

class StopNGrokAction
    : AnAction(
    NGrokBundle.message("action.ngrok.stop.name"),
    NGrokBundle.message("action.ngrok.stop.description"),
    AllIcons.Actions.Suspend
) {

    private val ngrokService = service<NGrokService>()

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = ngrokService.isRunning
    }

    override fun actionPerformed(e: AnActionEvent) {
        if (ngrokService.isRunning) {
            ngrokService.stop()
        }
    }
}


