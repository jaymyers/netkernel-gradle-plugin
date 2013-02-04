package groovy.org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 2/1/13
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
class CheckNetKernelInstallation extends DefaultTask {

    @org.gradle.api.tasks.TaskAction
    def checkInstallation() {
        if(project.nkHelper.isNetKernelInstalled()) {
            // I know about THESE NetKernel instances
        } else {
            println "NetKernel is not installed."
        }
    }
}
