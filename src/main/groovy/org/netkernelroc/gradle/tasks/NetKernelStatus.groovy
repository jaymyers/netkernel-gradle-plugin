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
            def String installLocation = project.nkHelper.whereIsNetKernelInstalled()

            println "NetKernel is running and is located at:"
            println installLocation

            def String moduleExtensionDirectory = project.nkHelper.whereIsModuleExtensionDirectory()

            // test for failure!

            File modulesD = new File(installLocation + moduleExtensionDirectory)
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
