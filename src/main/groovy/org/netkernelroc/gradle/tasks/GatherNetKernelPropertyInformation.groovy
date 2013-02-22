package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask


/**
 * Asks NetKernel for its configuration information via a request to netkernel:/config/
 * and saves that information into ??project extension information??
 *
 */
class GatherNetKernelPropertyInformation extends DefaultTask {

  @org.gradle.api.tasks.TaskAction
     def gatherNetKernelPropertyInformation() {

         if(project.nkHelper.isNetKernelRunning()) {
           // clear out current information cache

           // request JSON representation of config information

           // build local information cache

         } else {
             println "NetKernel is not running or not available on port 1060."
         }
     }
}
