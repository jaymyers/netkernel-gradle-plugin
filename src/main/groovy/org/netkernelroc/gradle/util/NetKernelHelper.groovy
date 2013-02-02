package groovy.org.netkernelroc.gradle.util

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
        } catch(Throwable t) {
        }

        retValue
    }
}
