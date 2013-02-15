package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

/**
 * Removes the module extension directory XML file and hence all modules in this project
 * from a running instance of NetKernel.
 */

class RemoveModules extends DefaultTask {

    @org.gradle.api.tasks.TaskAction
    void removeAllNetKernelModules() {

        // Get the directory where this script was executed
        def projectDirectory = System.getProperty("user.dir")
        // Get the name of the parent directory to use as the name of the modules.xml file in the modules.d directory
        def projectName = projectDirectory.split("/").last()

        def String installLocation = project.nkHelper.whereIsNetKernelInstalled()
        def String moduleExtensionDirectory = project.nkHelper.whereIsModuleExtensionDirectory()
        def netkernelRunning = true

        if (netkernelRunning) {
            // Test if the modules.d file is already present (pre-condition is that it must not be)
            File file1 = new File(installLocation + moduleExtensionDirectory + projectName + ".xml")

            if (file1.exists()) {
                file1.delete()
            } else {
                println "The modules.d control file for this project does not exist"
            }
        }

    }
}
