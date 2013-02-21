package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

/**
 * Removes the module extension directory XML file and hence all modules in this project
 * from a running instance of NetKernel.
 */

class RemoveModules extends DefaultTask {

  @org.gradle.api.tasks.TaskAction
  void removeAllNetKernelModules() {
    def projectName = project.fsHelper.projectName()

    if (project.nkHelper.isNetKernelRunning()) {
      def String installLocation = project.nkHelper.whereIsNetKernelInstalled()
      def String moduleExtensionDirectory = project.nkHelper.whereIsModuleExtensionDirectory()
      String moduleXMLFileName = installLocation + moduleExtensionDirectory + "/" + projectName + '.xml'
      File file1 = new File(moduleXMLFileName)
      if (file1.exists()) {
        println "Removing modules.d control file ${projectName}.xml"
        file1.delete()
      } else {
        println "The modules.d control file for this project does not exist"
      }
    }
    else {
      println "NetKernel is not running. Please start NetKernel and run this command again."
    }
  }
}
