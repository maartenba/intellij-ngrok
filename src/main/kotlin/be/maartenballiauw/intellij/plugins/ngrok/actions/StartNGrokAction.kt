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
import com.intellij.util.execution.ParametersListUtil
import be.maartenballiauw.intellij.plugins.ngrok.NGrokBundle
import be.maartenballiauw.intellij.plugins.ngrok.configuration.NGrokSettings
import be.maartenballiauw.intellij.plugins.ngrok.configuration.NGrokSettingsConfigurable
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

        val settings = NGrokSettings.getInstance(project)
        e.presentation.isEnabled = !settings.executablePath.isNullOrEmpty()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        if (ngrokService.isRunning) {
            return
        }

        val settings = NGrokSettings.getInstance(project)
        if (settings.executablePath.isNullOrEmpty()) {
            NGrokSettingsConfigurable.showSettings(project)
            return
        }

        logger.info("ngrok executable: ${settings.executablePath}")
        logger.info("ngrok arguments: ${settings.arguments}")

        val application = ApplicationManager.getApplication()

        ProgressManager.getInstance().run(object : Task.Backgroundable(project, NGrokBundle.message("service.ngrok.starting"), true, PerformInBackgroundOption.DEAF) {
            override fun run(indicator: ProgressIndicator) {

                indicator.text = NGrokBundle.message("service.ngrok.starting")
                application.invokeLaterOnWriteThread {
                    application.runWriteAction {
                        val commandLine = GeneralCommandLine(settings.executablePath)

                        val argumentsList = ParametersListUtil.parse(settings.arguments ?: "")
                        commandLine.parametersList.addAll(argumentsList)

                        if (!argumentsList.contains("--log")) {
                            commandLine.parametersList.add("--log")
                            commandLine.parametersList.add("stdout")
                        }

                        commandLine.environment.putAll(EnvironmentUtil.getEnvironmentMap())

                        if (indicator.isCanceled) return@runWriteAction

                        ngrokService.start(commandLine)
                    }
                }
            }
        })
    }
}