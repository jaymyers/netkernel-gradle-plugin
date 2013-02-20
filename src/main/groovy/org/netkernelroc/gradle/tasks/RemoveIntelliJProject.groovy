package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

class RemoveIntelliJProject extends DefaultTask {

  @org.gradle.api.tasks.TaskAction
  void removeIntelliJProject() {
    project.fsHelper.removeDirectory(project.file('.idea'))
    project.fsHelper.netkernelModuleNames().each { moduleName ->
      println "Removing ${moduleName}/${moduleName}.iml"
      project.file("${moduleName}/${moduleName}.iml").delete()
    }
  }
}
