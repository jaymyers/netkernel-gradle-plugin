package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask


class CheckNetKernelInstallation extends DefaultTask {

  @org.gradle.api.tasks.TaskAction
  def checkInstallation() {
    if (project.nkHelper.isNetKernelInstalled()) {
      // I know about THESE NetKernel instances
    } else {
      println "NetKernel is not installed."
    }
  }
}
