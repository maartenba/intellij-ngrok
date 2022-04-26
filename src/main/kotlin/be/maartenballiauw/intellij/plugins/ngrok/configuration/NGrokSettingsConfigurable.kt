package be.maartenballiauw.intellij.plugins.ngrok.configuration

import com.intellij.ide.actions.ShowSettingsUtilImpl
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import be.maartenballiauw.intellij.plugins.ngrok.NGrokBundle

class NGrokSettingsConfigurable(private val project: Project): BoundSearchableConfigurable(
    NGrokBundle.message("ngrok.settings.name"),
    NGrokBundle.message("ngrok.settings.name"),
    _id = ID
) {

    companion object {
        const val ID = "Settings.Plugins.NGrok"

        fun showSettings(project: Project) {
            ShowSettingsUtilImpl.showSettingsDialog(project, ID, "")
        }
    }

    private val settings
        get() = NGrokSettings.getInstance(project)

    override fun createPanel(): DialogPanel {
        return panel {
            row(NGrokBundle.message("ngrok.settings.executablePath")) {
                textFieldWithBrowseButton(
                    browseDialogTitle = NGrokBundle.message("ngrok.settings.executablePath.dialogTitle"),
                    fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor()
                ).bindText(
                    { settings.executablePath ?: "" },
                    { settings.executablePath = it }
                ).horizontalAlign(HorizontalAlign.FILL)
            }

            row(NGrokBundle.message("ngrok.settings.arguments")) {
                textField()
                    .bindText(
                        { settings.arguments ?: NGrokSettings.defaultArguments },
                        { settings.arguments = it }
                    ).horizontalAlign(HorizontalAlign.FILL)
            }
        }
    }
}