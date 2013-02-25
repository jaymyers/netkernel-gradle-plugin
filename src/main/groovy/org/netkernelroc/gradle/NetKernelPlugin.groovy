package org.netkernelroc.gradle
import org.gradle.api.Plugin
import org.gradle.api.Project
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
    p.tasks.add(name: 'statusNetKernel', group: 'NetKernel Management', type: NetKernelStatus, description: "Reports on the status of the currently running NetKernel.")
    p.tasks.add(name: 'installNetKernelModules', group: 'NetKernel Management', type: InstallModules, description: "Installs project modules in the currently running NetKernel.")
    p.tasks.add(name: 'installNetKernelModuleExtensionDirectory', group: 'NetKernel Management', type: InstallModuleExtensionDirectory, description: "Ensures that the currently running NetKernel has the proper module extension property set.")
    p.tasks.add(name: 'removeNetKernelModules', group: 'NetKernel Management', type: RemoveModules, description: "Removes project modules from the currently running NetKernel.")
    p.tasks.add(name: 'removeAllNetKernelModules', group:  'NetKernel Management', type: RemoveAllModules, description: "Removes all modules from any source from the currently running NetKernel.")
    p.tasks.add(name: 'createNetKernelModules', group:  'Module Creation', type: CreateNetKernelModules, description: "Create one or more NetKernel modules for this project from templates.")
    p.tasks.add(name: 'listTemplates', group:  'Module Creation', type: ListTemplates, description: "List available templates in a library.")
    p.tasks.add(name: 'listTemplateLibraries', group:  'Module Creation', type: ListTemplateLibraries, description: "List available templates libraries.")
    p.tasks.add(name: 'createIntelliJProject', group: 'IntelliJ', type: CreateIntelliJProject, description: "Create an IntelliJ project configuration (.idea directory and module *.iml files) for this project.")
    p.tasks.add(name: 'removeIntelliJProject', group:  'IntelliJ', type: RemoveIntelliJProject, description: "Remove all IntelliJ files (.idea directory and *.iml files).")

    p.tasks.add(name: 'report', group: 'Debug', type: ReportAndExperiment, description: "Experimenting with Gradle.")

    // These are tasks that others automatically depend on. They don't show up when you ask for a list of tasks
    p.tasks.add(name: 'installDefaultTemplates', type: InstallDefaultTemplates, description: "Install default NetKernel module templates.")
    p.tasks.createNetKernelModules.dependsOn "installDefaultTemplates"

//    p.tasks.add(name: 'installNetKernelJar', group:  'Utility', type: InstallNKSEJar, description: '') {
//      onlyIf { !project.fsHelper.gradleHomeDirectoryExists("/netkernel/install") }
//    }
//    p.tasks.add(name: 'downloadNetKernel', type: DownloadNKSE)
//    p.tasks.installNetKernelJar.dependsOn "downloadNetKernel"



//        p.tasks.add(name: 'checkNetKernelInstallation', type: CheckNetKernelInstallation)

//        p.tasks.installNetKernelJar.dependsOn "downloadNetKernel"

//    p.tasks.add(name: 'runNetKernelJar', group:  'Utility', type: RunNKSEJar, description:  '') {
//      workingDir "${project.fsHelper.gradleHomeDir()}/netkernel"
//      commandLine '/usr/bin/java', '-jar', "${project.fsHelper.gradleHomeDir()}/downloads/1060-NetKernel-SE-5.1.1.jar"
//      onlyIf { !project.nkHelper.isNetKernelRunning() }
//    }
//
//
//
//    p.getGradle().getTaskGraph().addTaskExecutionListener(new TaskExecutionListener() {
//      @Override void beforeExecute(Task task) {
//        if (task.name == 'installNetKernelJar') {
//          /* def es = Executors.newSingleThreadExecutor()
//           def f = es.submit({ p.tasks.runNetKernelJar.execute()} as Callable)
//           println f.get()
//           println "HUZZAH" */
//        }
//      }
//
//      @Override void afterExecute(Task task, TaskState taskState) {
//        // println task.name
//      }
//    })


  }
}
