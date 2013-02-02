package org.netkernelroc.gradle

import groovy.org.netkernelroc.gradle.tasks.DownloadNKSE
import groovy.org.netkernelroc.gradle.tasks.InstallNKSEJar
import groovy.org.netkernelroc.gradle.tasks.RunNKSEJar

import groovy.org.netkernelroc.gradle.util.FileSystemHelper
import groovy.org.netkernelroc.gradle.util.NetKernelHelper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.tasks.TaskState

/**
 * NetKernel Plugin
 */
class NetKernelPlugin implements Plugin<Project> {

    void apply(Project p) {
        println "You are applying $p.name"

        p.extensions.create("fsHelper", FileSystemHelper)
        p.extensions.create("nkHelper", NetKernelHelper)

        p.tasks.add(name: 'downloadNetKernel', type: DownloadNKSE)

        p.tasks.add(name: 'runNetKernelJar', type: RunNKSEJar) {
            workingDir "${project.fsHelper.gradleHomeDir()}/netkernel"
            commandLine '/usr/bin/java', '-jar', "${project.fsHelper.gradleHomeDir()}/downloads/1060-NetKernel-SE-5.1.1.jar"

            onlyIf { !project.nkHelper.isNetKernelRunning() }
        }

        p.getGradle().getTaskGraph().addTaskExecutionListener( new TaskExecutionListener() {
            @Override
            void beforeExecute(Task task) {
                if(task.name == 'installNetKernelJar') {
                   /* def es = Executors.newSingleThreadExecutor()
                    def f = es.submit({ p.tasks.runNetKernelJar.execute()} as Callable)
                    println f.get()
                    println "HUZZAH" */
                }
            }

            @Override
            void afterExecute(Task task, TaskState taskState) {
               // println task.name
            }
        })

        p.tasks.add(name: 'installNetKernelJar', type: InstallNKSEJar) {
            onlyIf { !project.fsHelper.gradleHomeDirectoryExists("/netkernel/install") }
        }

        p.tasks.installNetKernelJar.dependsOn "downloadNetKernel"
    }
}
