package be.maartenballiauw.intellij.plugins.ngrok.configuration.application

import com.intellij.ide.actions.ShowSettingsUtilImpl
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import be.maartenballiauw.intellij.plugins.ngrok.NGrokBundle

class NGrokSettingsConfigurable(): BoundSearchableConfigurable(
    NGrokBundle.message("ngrok.settings.name"),
    NGrokBundle.message("ngrok.settings.name"),
    _id = ID
) {

    companion object {
        const val ID = "Settings.Plugins.NGrok"

        fun showSettings() {
            ShowSettingsUtilImpl.showSettingsDialog(null, ID, "")
        }
    }

    private val settings
        get() = NGrokSettings.getInstance()

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

            row(NGrokBundle.message("ngrok.settings.authToken")) {
                textField()
                    .bindText(
                        { settings.authToken ?: "" },
                        { settings.authToken = it }
                    ).horizontalAlign(HorizontalAlign.FILL)
            }
        }
    }
}