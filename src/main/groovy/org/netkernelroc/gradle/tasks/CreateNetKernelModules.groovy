package org.netkernelroc.gradle.tasks
import org.gradle.api.DefaultTask

class CreateNetKernelModules extends DefaultTask {

  @org.gradle.api.tasks.TaskAction
  void createNetKernelModules() {

    if (project.getExtensions().getExtraProperties().has('modules')) {
      println "We have modules!!"
    }
    else  {
      println "Creating the simplest NetKernel module"
      String moduleURI = "urn:org:netkernelroc:simple"
      String moduleDirectory = moduleURI.replaceAll(":", ".")
      String packageName = moduleDirectory.substring(4)
      String packagePath = packageName.replaceAll("\\.", "/")
      project.fsHelper.createDirectory(project.fsHelper.convertURNtoPath(moduleURI))
      String moduleTemplate = new File(project.fsHelper.gradleHomeDir()+'/netkernelroc/templates/default/simple-module.xml').text
      moduleTemplate = moduleTemplate.replaceAll('MODULE_URI', moduleURI)
      moduleTemplate = moduleTemplate.replaceAll('MODULE_CLASSPATH', packagePath)

      // Create stuff
      project.fsHelper.createDirectory(project.fsHelper.convertURNtoPath(moduleURI))
      project.file("${moduleDirectory}/module.xml").write(moduleTemplate)
    }

//    project.ext.nkM.each { moduleURI, map ->
//
//
//      if (!fsh.existsDir(fsh.convertURNtoPath(moduleURI)) && map.containsKey('template')) {
//        println "Creating a directory for ${fsh.convertURNtoPath(moduleURI)} as a ${map['template']}"
//
//        fsh.createDirectory(fsh.convertURNtoPath(moduleURI))
//        String template = map['template']
//
//        println template
//
////        URL url = this.getClass().getClassLoader().getResource('templates/a.xml')
//        println "this is lame"
//        String foo = ClassLoader.getResourceAsStream('templates/a.xml').text
//
//        println foo
//
////        String textOfModule = url.openStream().getText()
////
////        println textOfModule
//
//        def moduleDef = foo
//
//        String moduleDirectory = moduleURI.replaceAll(":", ".")
//        String packageName = moduleDirectory.substring(4)
//        String packagePath = packageName.replaceAll("\\.", "/")
//
//        moduleDef = moduleDef.replaceAll('MODULE_URI', moduleURI)
//        moduleDef = moduleDef.replaceAll('MODULE_CLASSPATH',packagePath)
//        project.file("${moduleDirectory}/module.xml").write(moduleDef)
//
//        println moduleDef
//
//        //Node projectXML = new XmlParser(false,true).parse(url.openStream())
//
//      }
//
//      //map.each { key, value -> println "  ${key} ${value}" }
//    }

  }
}

