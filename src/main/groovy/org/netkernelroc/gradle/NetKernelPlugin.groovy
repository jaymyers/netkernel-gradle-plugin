package org.netkernelroc.gradle

import org.netkernelroc.gradle.tasks.CreateNetKernelModules
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.tasks.TaskState
import org.netkernelroc.gradle.tasks.*
import org.netkernelroc.gradle.util.FileSystemHelper
import org.netkernelroc.gradle.util.NetKernelHelper

/**
 * NetKernel Plugin
 */
class NetKernelPlugin implements Plugin<Project> {

  void apply(Project p) {
    println "You are applying $p.name"

    p.extensions.create("fsHelper", FileSystemHelper)
    p.extensions.create("nkHelper", NetKernelHelper)

    p.tasks.add(name: 'downloadNetKernel', type: DownloadNKSE)
    p.tasks.add(name: 'statusNetKernel', type: NetKernelStatus, description: "Reports on the status of the currently running NetKernel.")
    p.tasks.add(name: 'installNetKernelModules', type: InstallModules, description: "Installs modules in the currently running NetKernel.")
    p.tasks.add(name: 'removeNetKernelModules', type: RemoveModules, description: "Removes modules from the currently running NetKernel.")
    p.tasks.add(name: 'removeAllNetKernelModules', type: RemoveAllModules, description: "Removes all modules from currently running NetKernel.")
    p.tasks.add(name: 'installTemplates', type: InstallTemplates, description: "Install NetKernel module templates.")
    p.tasks.add(name: 'createNetKernelModules', type: CreateNetKernelModules, description: "Create NetKernel modules from templates.")

    p.tasks.add(name: 'report', type: ReportAndExperiment, description: "Experimenting with Gradle.")

    p.tasks.add(name: 'runNetKernelJar', type: RunNKSEJar) {
      workingDir "${project.fsHelper.gradleHomeDir()}/netkernel"
      commandLine '/usr/bin/java', '-jar', "${project.fsHelper.gradleHomeDir()}/downloads/1060-NetKernel-SE-5.1.1.jar"
      onlyIf { !project.nkHelper.isNetKernelRunning() }
    }

    p.getGradle().getTaskGraph().addTaskExecutionListener(new TaskExecutionListener() {
      @Override void beforeExecute(Task task) {
        if (task.name == 'installNetKernelJar') {
          /* def es = Executors.newSingleThreadExecutor()
           def f = es.submit({ p.tasks.runNetKernelJar.execute()} as Callable)
           println f.get()
           println "HUZZAH" */
        }
      }

      @Override void afterExecute(Task task, TaskState taskState) {
        // println task.name
      }
    })

    p.tasks.add(name: 'installNetKernelJar', type: InstallNKSEJar) {
      onlyIf { !project.fsHelper.gradleHomeDirectoryExists("/netkernel/install") }
    }

    p.tasks.installNetKernelJar.dependsOn "downloadNetKernel"
    p.tasks.createNetKernelModules.dependsOn "installTemplates"
  }
}
