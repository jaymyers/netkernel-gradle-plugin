package groovy.org.netkernelroc.gradle.tasks

import org.apache.tools.ant.BuildEvent
import org.apache.tools.ant.BuildListener
import org.gradle.api.DefaultTask
/**
 * Downloads a copy of NKSE and stores it in .gradle/downloads. It will only
 * be downloaded once as long as the file stays intact.
 *
 */
class DownloadNKSE extends DefaultTask {

    @org.gradle.api.tasks.TaskAction
    void dlNKSE() {

        project.fsHelper.createGradleHomeDirectory "downloads"

        def dest = "${project.fsHelper.gradleHomeDir()}/downloads"

        println "Downloading NKSE to $dest"

        ant.project.buildListeners.toList().each {
            ant.project.removeBuildListener(it)
        }
        ant.project.addBuildListener(new BuildListener() {
            void buildStarted(BuildEvent event) {}
            void buildFinished(BuildEvent event) {}
            void targetStarted(BuildEvent event) {}
            void targetFinished(BuildEvent event) {}
            void taskStarted(BuildEvent event) {}
            void taskFinished(BuildEvent event) {}

            void messageLogged(BuildEvent event) {
                DownloadNKSE.this.logger.quiet event.message
            }
        })

        ant.get(src: 'http://apposite.netkernel.org/dist/1060-NetKernel-SE/1060-NetKernel-SE-5.1.1.jar',
                dest: dest,
                verbose : true,
                httpusecaches : true,
                usetimestamp : true)
    }
}
