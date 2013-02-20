package org.netkernelroc.gradle.tasks

import groovy.io.FileType
import org.gradle.api.DefaultTask

/**
 * This scans the project directory for sub-directories starting with urn: and
 * creates an appropriate modules extension directory XML file that will include
 * all discovered modules.
 *
 * The name of the xml file placed into NetKernel matches the directory name given for
 * this project.
 *
 * TODO: override via some property file to not include all modules.
 * TODO: override the XML file name with a property setting
 *
 */
class InstallModules extends DefaultTask {

  @org.gradle.api.tasks.TaskAction void installNetKernelModules() {

    // Get the directory where this script was executed
    def projectDirectory = System.getProperty("user.dir")
    // Get the name of the parent directory to use as the name of the modules.xml file in the modules.d directory
    def projectName = projectDirectory.split("/").last()

    // Create the base for the module.xml file we'll use for attachment
    def modules = new XmlParser().parseText("<modules> </modules>")

    def p = ".*"
    def dir = new File(projectDirectory)
    dir.traverse(type: FileType.DIRECTORIES,
        maxDepth: 0) { directory ->
      if (directory.name.startsWith("urn")) {
        println "Adding ${projectDirectory}/${directory.name}"
        def node = new XmlParser().parseText("<module runlevel='7'>" + projectDirectory + "/" + directory.name + "/</module>")
        modules.append(node)
      }
    }
    def String installLocation = project.nkHelper.whereIsNetKernelInstalled()
    println installLocation
    def String moduleExtensionDirectory = project.nkHelper.whereIsModuleExtensionDirectory()
    println moduleExtensionDirectory
    def netkernelRunning = true

    if (netkernelRunning) {
      String moduleXMLFileName = installLocation + moduleExtensionDirectory + "/" + projectName + '.xml'
      // Test if the modules.d file is already present (pre-condition is that it must not be)
      println installLocation + moduleExtensionDirectory + "/" + projectName + ".xml"
      File file1 = new File(moduleXMLFileName)

      if (!file1.exists()) {
        // Write the new modules.xml file into the NetKernel installation
        PrintWriter writer = new PrintWriter(moduleXMLFileName)

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

