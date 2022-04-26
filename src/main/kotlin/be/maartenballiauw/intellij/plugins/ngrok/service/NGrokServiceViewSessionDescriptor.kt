@file:Suppress("UnstableApiUsage")

package be.maartenballiauw.intellij.plugins.ngrok.service

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.process.ColoredProcessHandler
import com.intellij.execution.services.SimpleServiceViewDescriptor
import com.intellij.execution.ui.ConsoleView
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.components.JBPanelWithEmptyText
import icons.NGrokIcons
import be.maartenballiauw.intellij.plugins.ngrok.NGrokBundle
import be.maartenballiauw.intellij.plugins.ngrok.actions.ShowNGrokSettingsAction
import be.maartenballiauw.intellij.plugins.ngrok.actions.StartNGrokAction
import be.maartenballiauw.intellij.plugins.ngrok.actions.StopNGrokAction
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JPanel

class NGrokServiceViewSessionDescriptor(private val project: Project)
    : SimpleServiceViewDescriptor(NGrokBundle.message("service.ngrok.name"), NGrokIcons.NGrok), Disposable {

    companion object {
        val defaultToolbarActions = DefaultActionGroup(
            StartNGrokAction(),
            StopNGrokAction(),
            ShowNGrokSettingsAction()
        )
    }

    private val ngrokService = service<NGrokService>()
    private var processHandler: ColoredProcessHandler? = null
    private var commandLine: String? = null

    private val consoleView: ConsoleView = TextConsoleBuilderFactory.getInstance()
        .createBuilder(project).apply { setViewer(true) }.console

    init {
        Disposer.register(project, this)
        Disposer.register(project, consoleView)
    }

    protected val panel = createEmptyComponent()

    override fun getToolbarActions() = defaultToolbarActions

    override fun getPresentation(): ItemPresentation {
        ensureConsoleView()

        val superPresentation = super.getPresentation()
        return object : ItemPresentation {
            override fun getLocationString(): String? = commandLine
            override fun getIcon(p: Boolean) = superPresentation.getIcon(p)
            override fun getPresentableText() = superPresentation.presentableText
        }
    }

    override fun getContentComponent(): JComponent? {
        ensureConsoleView()
        return panel
    }

    private fun ensureConsoleView() {
        ngrokService.processHandler?.let { activeProcessHandler ->

            if (processHandler != activeProcessHandler) {
                processHandler?.detachProcess()
                processHandler = activeProcessHandler
                commandLine = activeProcessHandler.commandLine

                consoleView.attachToProcess(activeProcessHandler)
            }
        }

        if (processHandler != null && panel.components.isEmpty()) {
            panel.add(consoleView.component, BorderLayout.CENTER)
        }
    }

    private fun createEmptyComponent(): JPanel {
        val panel: JPanel = JBPanelWithEmptyText(BorderLayout())
            .withEmptyText(NGrokBundle.message("service.ngrok.not_started"))
            .withBorder(BorderFactory.createEmptyBorder())
        panel.isFocusable = true
        return panel
    }

    override fun dispose() {
        Disposer.dispose(consoleView)
    }
}