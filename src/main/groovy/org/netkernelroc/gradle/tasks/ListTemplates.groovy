package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.ExtraPropertiesExtension

class ListTemplates extends DefaultTask {

  @org.gradle.api.tasks.TaskAction void createNetKernelModules() {

    ExtensionContainer e = project.getExtensions()
    ExtraPropertiesExtension ep = e.getExtraProperties()
    def properties = ep.getProperties()

    // Get user supplied module uri or use default
    String moduleURI = "urn:org:netkernelroc:sample"
    if (properties.containsKey('moduleURI')) {
      moduleURI = properties['moduleURI']
    }

    // Get user supplied module template or use default
    String moduleTemplate = "simple"
    if (properties.containsKey('moduleTemplate')) {
      moduleTemplate = properties['moduleTemplate']
    }

    // Get user supplied template library or use default
    String templateLibrary = "default"
    if (properties.containsKey('templateLibrary')) {
      templateLibrary = properties['templateLibrary']
    }

    // Get user supplied template directory or use default
    String templateDirectory = project.fsHelper.gradleHomeDir() + '/netkernelroc/templates'
    if (properties.containsKey('templateDirectory')) {
      templateDirectory = properties['templateDirectory']
    }

    if (!project.fsHelper.existsDir(templateDirectory)) {
      println "The specified template directory [${templateDirectory}] does not exist."
    }
    else {
      println ""
      println "Directory: ${templateDirectory}"
      String templateLibraryDirectory = templateDirectory + '/' + templateLibrary
      if (!project.fsHelper.existsDir(templateLibraryDirectory)){
        println "The template Libary [${templateLibrary}] is not found in the template diretory"
      }
      else {
        println "Library: ${templateLibrary}"
        println ""
        def tlDir = new File(templateLibraryDirectory)
        tlDir.eachDir { dir ->
          println "Template: ${dir.name}"
          println "-----------------------------"
          dir.eachFile { file ->
            if (file.name.toLowerCase().equals("readme")) {
              String readme = file.text
              println readme
              println  ""
            }
          }
        }
      }
    }
  }

}
