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
    p.extensions.create("fsHelper", FileSystemHelper)
    p.extensions.create("nkHelper", NetKernelHelper)

    // High level tasks
    p.tasks.add(name: 'statusNetKernel', type: NetKernelStatus, description: "Reports on the status of the currently running NetKernel.")
    p.tasks.add(name: 'installNetKernelModules', type: InstallModules, description: "Installs modules in the currently running NetKernel.")
    p.tasks.add(name: 'removeNetKernelModules', type: RemoveModules, description: "Removes modules from the currently running NetKernel.")
    p.tasks.add(name: 'removeAllNetKernelModules', type: RemoveAllModules, description: "Removes all modules from currently running NetKernel.")
    p.tasks.add(name: 'createNetKernelModules', type: CreateNetKernelModules, description: "Create NetKernel modules from templates.")
    p.tasks.add(name: 'createIntelliJProject', type: CreateIntelliJProject, description: "Create an IntelliJ project for these modules.")
    p.tasks.add(name: 'removeIntelliJProject', type: RemoveIntelliJProject, description: "Removes any IntelliJ project files.")

    //p.tasks.add(name: 'report', type: ReportAndExperiment, description: "Experimenting with Gradle.")

    // These are tasks that others automatically depend on. They don't show up when you ask for a list of tasks
    p.tasks.add(name: 'installDefaultTemplates', type: InstallDefaultTemplates, description: "Install default NetKernel module templates.")
    p.tasks.createNetKernelModules.dependsOn "installDefaultTemplates"

    p.tasks.add(name: 'installNetKernelJar', type: InstallNKSEJar) {
      onlyIf { !project.fsHelper.gradleHomeDirectoryExists("/netkernel/install") }
    }
    p.tasks.add(name: 'downloadNetKernel', type: DownloadNKSE)
    p.tasks.installNetKernelJar.dependsOn "downloadNetKernel"



        //p.tasks.add(name: 'checkNetKernelInstallation', type: CheckNetKernelInstallation)

        p.tasks.installNetKernelJar.dependsOn "downloadNetKernel"

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


  }
}
