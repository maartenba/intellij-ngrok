@file:Suppress("UnstableApiUsage")

package be.maartenballiauw.intellij.plugins.ngrok.actions

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.progress.PerformInBackgroundOption
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.util.EnvironmentUtil
import be.maartenballiauw.intellij.plugins.ngrok.NGrokBundle
import be.maartenballiauw.intellij.plugins.ngrok.configuration.application.NGrokSettings
import be.maartenballiauw.intellij.plugins.ngrok.configuration.application.NGrokSettingsConfigurable
import be.maartenballiauw.intellij.plugins.ngrok.service.NGrokService

class StartNGrokAction
    : AnAction(
    NGrokBundle.message("action.ngrok.start.name"),
    NGrokBundle.message("action.ngrok.start.description"),
    AllIcons.Actions.Execute) {

    companion object {
        private const val NGROK_PROCESS_TIMEOUT_MILLIS = 15000
    }

    private val logger = Logger.getInstance(StartNGrokAction::class.java)
    private val ngrokService = service<NGrokService>()

    override fun update(e: AnActionEvent) {
        val project = e.project ?: return

        if (ngrokService.isRunning) {
            e.presentation.isEnabled = false
            return
        }

        val settings = NGrokSettings.getInstance()
        e.presentation.isEnabled = !settings.executablePath.isNullOrEmpty()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        if (ngrokService.isRunning) {
            return
        }

        val settings = NGrokSettings.getInstance()
        if (settings.executablePath.isNullOrEmpty()) {
            NGrokSettingsConfigurable.showSettings()
            return
        }

        logger.info("ngrok executable: ${settings.executablePath}")

        val application = ApplicationManager.getApplication()

        ProgressManager.getInstance().run(object : Task.Backgroundable(project, NGrokBundle.message("service.ngrok.starting"), true, PerformInBackgroundOption.DEAF) {
            override fun run(indicator: ProgressIndicator) {

                indicator.text = NGrokBundle.message("service.ngrok.starting")
                application.invokeLaterOnWriteThread {
                    application.runWriteAction {
                        val commandLine = GeneralCommandLine(settings.executablePath)
                        commandLine.addParameters("start", "--none", "--log", "stdout")
                        commandLine.environment.putAll(EnvironmentUtil.getEnvironmentMap())
                        settings.authToken?.let {
                            commandLine.environment.put("NGROK_AUTHTOKEN", it)
                        }

                        if (indicator.isCanceled) return@runWriteAction

                        ngrokService.start(commandLine)
                    }
                }
            }
        })
    }
}