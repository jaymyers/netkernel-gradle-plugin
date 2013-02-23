package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.ExtraPropertiesExtension

class CreateNetKernelModules extends DefaultTask {

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

    // Check to see if the module directory already exists and exit if it does
    if (project.fsHelper.existsDir(project.nkHelper.convertURNtoPath(moduleURI))) {
      println "The requested module directory [${project.nkHelper.convertURNtoPath(moduleURI)}] already exists."
    }

    println "Creating module [${moduleURI}] with the [${moduleTemplate}] template from the [${templateLibrary}] template library located at [${templateDirectory}]"

    def targetDir = project.getProjectDir().absolutePath + '/' + project.nkHelper.convertURNtoPath(moduleURI)
    def sourceDir = templateDirectory + '/' + templateLibrary + '/' + moduleTemplate

    String moduleDirectory = moduleURI.replaceAll(":", ".")
    String packageName = moduleDirectory.substring(4)
    String packagePath = packageName.replaceAll("\\.", "/")

    project.fsHelper.createDirectory(targetDir)

    def sDir = new File(sourceDir)
    sDir.eachFileRecurse { file ->
      if (file.isDirectory()) {
        project.fsHelper.createDirectory(project.fsHelper.switchFilePath(file.absolutePath, sourceDir, targetDir))
      } else {
        String template = file.text
        template = template.replaceAll('MODULE_URI_WITHOUT_URN', moduleURI.substring(4))
        template = template.replaceAll('MODULE_URI_CORE', moduleURI.substring(0,moduleURI.length()-5))
        template = template.replaceAll('MODULE_URI', moduleURI)
        template = template.replaceAll('MODULE_CLASSPATH', packagePath)
        project.file(project.fsHelper.switchFilePath(file.absolutePath, sourceDir, targetDir)).write(template)
      }
    }
  }
}

