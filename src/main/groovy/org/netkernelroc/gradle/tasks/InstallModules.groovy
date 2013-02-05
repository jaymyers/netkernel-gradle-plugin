package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask


/**
 * Determines which modules the developer wants to have installed into the running
 * NetKernel instance and creates the appropriate modules extension directory XML file.
 *
 * TODO: Decide if this command should replace an existing XML file in the modules extension directory or fail and report as it does now.
 */
class InstallModules extends DefaultTask {

    @org.gradle.api.tasks.TaskAction
    void installNetKernelModules() {

        // Get the directory where this script was executed
        def projectDirectory = System.getProperty("user.dir")
        // Get the name of the parent directory to use as the name of the modules.xml file in the modules.d directory
        def projectName = projectDirectory.split("/").last()

        // Create the base for the module.xml file we'll use for attachment
        def modules = new XmlParser().parseText("<modules> </modules>")

        project.ext.installModules.each { moduleURI ->
            String moduleDirectory = moduleURI.replaceAll(":", ".")
            def node = new XmlParser().parseText("<module runlevel='7'>" + projectDirectory + "/" + moduleDirectory + "/</module>")
            modules.append(node)
        }

        def String installLocation = project.nkHelper.whereIsNetKernelInstalled()
        def String moduleExtensionDirectory = project.nkHelper.whereIsModuleExtensionDirectory()
        def netkernelRunning = true

        if (netkernelRunning) {
            // Test if the modules.d file is already present (pre-condition is that it must not be)
            File file1 = new File(installLocation + moduleExtensionDirectory + projectName + ".xml")

            if (!file1.exists()) {
                // Write the new modules.xml file into the NetKernel installation
                PrintWriter writer = new PrintWriter(installLocation + moduleExtensionDirectory + projectName + ".xml")

                def xmlWriter = new XmlNodePrinter(writer)
                xmlWriter.setPreserveWhitespace(true)
                xmlWriter.setQuote("'")
                xmlWriter.print(modules)

            } else {
                println "The modules.d control file already exists"
            }
        }
    }

}
