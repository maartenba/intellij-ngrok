@file:Suppress("UnstableApiUsage")

package be.maartenballiauw.intellij.plugins.ngrok.service

import com.intellij.execution.services.ServiceViewContributor
import com.intellij.openapi.project.Project

class NGrokServiceViewContributor
    : ServiceViewContributor<NGrokServiceViewContributor.NGrokSession> {

    private val ngrokSessions = mutableListOf(NGrokSession())

    override fun getServices(project: Project) = ngrokSessions

    override fun getViewDescriptor(project: Project) = NGrokServiceViewSessionDescriptor(project)

    override fun getServiceDescriptor(project: Project, session: NGrokSession) =
        NGrokServiceViewSessionDescriptor(project)

    class NGrokSession {
        // NOTE: This class is a placeholder to be able to display an entry.
    }
}