package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.ExtraPropertiesExtension

class ReportAndExperiment extends DefaultTask {

  @org.gradle.api.tasks.TaskAction
  void reportAndExperiment() {


    ExtensionContainer e = project.getExtensions()
    ExtraPropertiesExtension ep = e.getExtraProperties()
    println ep.has('nkM')



//    println project.properties.each{ key, value ->
//      println "key [${key}], value [${value}]"
//    }

//    println project.fsHelper.gradleHomeDirectoryExists("netkernelroc/templates")
//
//    if (!project.fsHelper.gradleHomeDirectoryExists("netkernelroc/templates")) {
//      project.fsHelper.createGradleHomeDirectory("netkernelroc/templates")
//    }
//
//    println project.fsHelper.gradleHomeDirectoryExists("netkernelroc/templates")


//    println "experimenting with GitHub fetching"
//
//    def http = new HttpURLClient(url: 'https://github.com');
//    http.setFollowRedirects(true)
//
//    def resp = http.request(path: '/netkernelroc/xunit-assertions/raw/master/foo.bar')
//    println resp.getStatus()
//    println resp.data.text
//
//    try {
//    resp = http.request(path: '/netkernelroc/xunit-assertions/raw/master/README.md')
//    }
//    catch(Exception e) {
//      println e
//    }
//    println resp.getStatus()
//    println resp.data.text


  }


}
