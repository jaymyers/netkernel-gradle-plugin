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

        // Get the directory where this script was executed
        def projectDirectory = System.getProperty("user.dir")
        // Get the name of the parent directory to use as the name of the modules.xml file in the modules.d directory
        def projectName = projectDirectory.split("/").last()

        // Check that NetKernel is running as we need that to learn where it is installed
        // This URL will return the the local absolute file URL location of the installation location of NetKernel
        def installLocationURL = 'http://localhost:1060/tools/scriptplaypen?action2=execute&type=gy&example&identifier&name&space&script=context%2EcreateResponseFrom%28context%2Esource%28%22netkernel%3A%2Fconfig%2Fnetkernel%2Einstall%2Epath%22%29%29'
        def installLocation
        def modulesExtensionLocation
        try {
            installLocation = new URL(installLocationURL).text
            println "NetKernel is running at ${installLocation.substring(5)}"

            File modulesD = new File(installLocation.substring(5) + 'etc/modules.d/')
            modulesD.listFiles().each { File file ->
                println "For ${file.name}"
                Node projectXML = new XmlParser(false, true).parse(file)
                projectXML.each { println "   ${it}" }
            }


        } catch (Exception e) {
            println 'NetKernel is not running or not available on port 1060'
        }

    }


}
