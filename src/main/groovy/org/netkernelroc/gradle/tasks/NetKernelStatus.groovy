package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

/**
 * Given the location of NetKernel, it finds the module XML
 * files and reports back on the command line.
 *
 */


class NetKernelStatus extends DefaultTask {

    @org.gradle.api.tasks.TaskAction
    void checkNetKernelStatus() {

        try {
            installLocation = project.nkHelper.whereIsNetKernelInstalled()

            File modulesD = new File(installLocation + 'etc/modules.d/')
            modulesD.listFiles().each { File file ->
                println "For ${file.name}"
                Node projectXML = new XmlParser(false, true).parse(file)
                projectXML.each { println "   ${it}" }
            }
        }
        catch (Exception e) {
            println 'NetKernel is not running or not available on port 1060'
        }
    }

}
