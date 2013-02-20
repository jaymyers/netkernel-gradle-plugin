package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

class RemoveAllModules extends DefaultTask {


  @org.gradle.api.tasks.TaskAction void removeAllNetKernelModules() {

    // Get the directory where this script was executed
    def projectDirectory = System.getProperty("user.dir")
    // Get the name of the parent directory to use as the name of the modules.xml file in the modules.d directory
    def projectName = projectDirectory.split("/").last()

    def String installLocation = project.nkHelper.whereIsNetKernelInstalled()
    def String moduleExtensionDirectory = project.nkHelper.whereIsModuleExtensionDirectory()
    def netkernelRunning = true

    if (netkernelRunning) {
      File modulesD = new File(installLocation + moduleExtensionDirectory)
      modulesD.listFiles().each { File file ->
        if (!file.isDirectory()){
        println "Removing modules.d control file ${file.name}"
        file.delete()
        }
      }
    }
  }
}
