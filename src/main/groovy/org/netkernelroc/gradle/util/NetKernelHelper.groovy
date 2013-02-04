package org.netkernelroc.gradle.util

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class NetKernelHelper {
    def isNetKernelRunning() {
        def retValue = false

        try {
            def http = new HTTPBuilder('http://localhost:1060')
            http.uri.path = "/"

            http.request(Method.GET) {
                response.success = { resp, reader ->
                    assert resp.status == 200
                    retValue = true
                }
            }
        } catch (Throwable t) {
        }

        retValue
    }

    def whereIsNetKernelInstalled() throws Exception {

        // Get the directory where this script was executed
        def projectDirectory = System.getProperty("user.dir")

        // Check that NetKernel is running as we need that to learn where it is installed
        // This URL will return the the local absolute file URL location of the installation location of NetKernel
        def installLocationURL = 'http://localhost:1060/tools/scriptplaypen?action2=execute&type=gy&example&identifier&name&space&script=context%2EcreateResponseFrom%28context%2Esource%28%22netkernel%3A%2Fconfig%2Fnetkernel%2Einstall%2Epath%22%29%29'
        def installLocation
        def modulesExtensionLocation
        installLocation = new URL(installLocationURL).text.substring(5)

        return installLocation
    }
}
