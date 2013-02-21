package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

class RemoveAllModules extends DefaultTask {


  @org.gradle.api.tasks.TaskAction
  void removeAllNetKernelModules() {
    if (project.nkHelper.isNetKernelRunning()) {
      def String installLocation = project.nkHelper.whereIsNetKernelInstalled()
      def String moduleExtensionDirectory = project.nkHelper.whereIsModuleExtensionDirectory()
      File modulesD = new File(installLocation + moduleExtensionDirectory)
      modulesD.listFiles().each { File file ->
        if (!file.isDirectory()) {
          println "Removing modules.d control file ${file.name}"
          file.delete()
        }
      }
    } else {
      println "NetKernel is not running. Please start NetKernel and run this command again."
    }
  }
}
