package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

class InstallModuleExtensionDirectory extends  DefaultTask{

  @org.gradle.api.tasks.TaskAction void checkAndSetNetKernelModuleExtensionDirectoryLocation() {

    project.nkHelper.setNetKernelModulesExtensionDirectory()
  }
}
