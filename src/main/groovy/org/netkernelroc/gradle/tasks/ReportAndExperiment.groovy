package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

class ReportAndExperiment extends DefaultTask {

  @org.gradle.api.tasks.TaskAction void reportAndExperiment() {
    project.nkHelper.setNetKernelModulesExtensionDirectory()
  }
}

