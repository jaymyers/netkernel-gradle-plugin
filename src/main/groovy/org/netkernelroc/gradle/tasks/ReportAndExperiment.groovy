package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.ExtraPropertiesExtension

class ReportAndExperiment extends DefaultTask {

  @org.gradle.api.tasks.TaskAction
  void reportAndExperiment() {


    ExtensionContainer e = project.getExtensions()
    ExtraPropertiesExtension ep = e.getExtraProperties()

    ep.getProperties().each {key, value  ->
      println "${key}/${value}"
    }
    println "experimenting with Files and Directories"

    def sourceDirectory = ep.getProperties()['directory']

    def dir = new File(sourceDirectory)
    def files = []
    dir.eachFileRecurse {files += it }
    files.each {file ->
      println file.path
      println file.name
    }

//    println "There is an extension property uri - ${ep.has('uri')}. It's value is ${project.uri}"
//    println "There is an extension property template - ${ep.has('template')}. It's value is ${project.template}"




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
