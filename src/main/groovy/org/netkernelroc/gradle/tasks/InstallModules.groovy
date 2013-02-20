package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask
/**
 * This scans the project directory for sub-directories starting with urn and
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

  @org.gradle.api.tasks.TaskAction
  void installNetKernelModules() {

    def projectDirectory = System.getProperty("user.dir")
    def projectName = projectDirectory.split("/").last()

    // Create the base for the module.xml file we'll use for attachment
    def modules = new XmlParser().parseText("<modules> </modules>")

    project.fsHelper.netkernelModuleNames().each { name ->
      def node = new XmlParser().parseText("<module runlevel='7'>${projectDirectory}/${name}/</module>")
      modules.append(node)
    }
    def String installLocation = project.nkHelper.whereIsNetKernelInstalled()
    def String moduleExtensionDirectory = project.nkHelper.whereIsModuleExtensionDirectory()

    if (project.nkHelper.isNetKernelRunning()) {
      String moduleXMLFileName = installLocation + moduleExtensionDirectory + '/' + projectName + '.xml'

      File file1 = new File(moduleXMLFileName)
      if (!file1.exists()) {
        PrintWriter writer = new PrintWriter(moduleXMLFileName)
        def xmlWriter = new XmlNodePrinter(writer)
        xmlWriter.setPreserveWhitespace(true)
        xmlWriter.setQuote("'")
        xmlWriter.print(modules)
        println "Adding modules.d control file ${projectName}.xml"
      } else {
        println "The modules.d control file ${projectName}.xml already exists."
      }
    }
    else {
      println "NetKernel is not running. Please start NetKernel and run this command again."
    }
  }
}

