package org.netkernelroc.gradle.tasks

import groovyx.net.http.RESTClient
import org.gradle.api.DefaultTask

/**
 *  Installs a copy of NetKernel in the user's .gradle directory
 *  and registers it w/ .gradle/netkernel/instances. This only
 *  happens if Gradle doesn't detect a running copy.
 */
class InstallNKSEJar extends DefaultTask {

    @org.gradle.api.tasks.TaskAction
    void instalNKSE() {

        while(!project.nkHelper.isNetKernelRunning()) {
            println "Waiting for NetKernel to run..."
            Thread.sleep(1000)
        }

        def installDir = "${project.fsHelper.gradleHomeDir()}/netkernel/install"
        project.fsHelper.createGradleHomeDirectory("/netkernel/install")
        println "NetKernel is now running..."
        println "Installing to $installDir"

        try {
            def client = new RESTClient('http://localhost:1060/')
            def resp = client.get( path: 'installer/',
                    query: [target : installDir,
                            expand : 'yes' ,
                            proxyHost : '',
                            proxyPort : ''])

            if(resp.status == 200) {
                println "Successfully installed NetKernel."
                println "Shutting NetKernel down..."

                resp = client.get( path: '/tools/shutdown',
                   query : [ confirm : '1',
                             action2 : 'force'])

                if(resp.status == 200) {
                    println "Installation complete."
                }

            } else {
                println "Error installing NetKernel to $installDir"
            }

        } catch(Throwable t) {
            t.printStackTrace()
        }
    }
}
