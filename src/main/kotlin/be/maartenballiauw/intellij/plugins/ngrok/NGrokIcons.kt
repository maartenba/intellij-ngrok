@file:Suppress("PackageDirectoryMismatch")

package icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

@Suppress("SameParameterValue")
object NGrokIcons {
    @JvmField val NGrok = load("ngrok.svg")

    private fun load(path: String): Icon = IconLoader.getIcon("/icons/$path", this::class.java)
}