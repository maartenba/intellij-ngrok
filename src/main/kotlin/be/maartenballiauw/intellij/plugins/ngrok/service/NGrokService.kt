@file:Suppress("UnstableApiUsage")

package be.maartenballiauw.intellij.plugins.ngrok.service

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ColoredProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.execution.services.ServiceEventListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.Key
import com.intellij.util.io.BaseOutputReader

class NGrokService : Disposable {

    private val logger = Logger.getInstance(NGrokService::class.java)

    var processHandler: ColoredProcessHandler? = null
        private set

    val isRunning: Boolean
        get() {
            val currentHandler = processHandler
            return currentHandler != null && !currentHandler.isProcessTerminated
        }

    fun start(commandLine: GeneralCommandLine) {
        if (processHandler != null) {
            logger.warn("start() was called while processHandler was not null. The caller should verify if an existing session is running, before calling start()")
            return
        }

        processHandler = object : ColoredProcessHandler(commandLine) {
            // If it's a long-running mostly idle daemon process, 'BaseOutputReader.Options.forMostlySilentProcess()' helps to reduce CPU usage.
            override fun readerOptions(): BaseOutputReader.Options = BaseOutputReader.Options.forMostlySilentProcess()
        }
        processHandler?.let {
            it.addProcessListener(object : ProcessListener {
                override fun onTextAvailable(p0: ProcessEvent, p1: Key<*>) { }

                override fun processTerminated(e: ProcessEvent) {
                    processHandler = null
                }

                override fun startNotified(e: ProcessEvent) { }
            })
            it.startNotify()
            syncServices()
        }
    }

    fun stop() = stopInternal {
        syncServices()
    }

    private fun stopInternal(runAfter: (() -> Unit)? = null) {
        if (processHandler == null) return

        processHandler?.let {
            try {
                it.destroyProcess()
            } finally {
                if (!it.isProcessTerminating && !it.isProcessTerminated) {
                    it.killProcess()
                }
                runAfter?.invoke()
            }
        }
    }

    override fun dispose() = stopInternal()

    private fun syncServices() =
        ApplicationManager.getApplication().messageBus.syncPublisher(ServiceEventListener.TOPIC)
            .handle(ServiceEventListener.ServiceEvent.createResetEvent(NGrokServiceViewContributor.NGrokSession::class.java))
}