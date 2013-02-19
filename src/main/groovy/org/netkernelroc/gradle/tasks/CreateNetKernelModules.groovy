package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask
import org.netkernelroc.gradle.util.FileSystemHelper


class CreateNetKernelModules extends DefaultTask {

  @org.gradle.api.tasks.TaskAction void createNetKernelModules() {
    def fsh = new FileSystemHelper()

    project.ext.nkM.each { moduleURI, map ->


      if (!fsh.existsDir(fsh.convertURNtoPath(moduleURI)) && map.containsKey('template')) {
        println "Creating a directory for ${fsh.convertURNtoPath(moduleURI)} as a ${map['template']}"

        fsh.createDirectory(fsh.convertURNtoPath(moduleURI))
        String template = map['template']

        URL url = CreateNetKernelModules.class.getClassLoader().getResource("templates/module.doc.xml")

        println url.toString()

        String textOfModule = url.openStream().getText()

        println textOfModule

        def moduleDef = textOfModule

        String moduleDirectory = moduleURI.replaceAll(":", ".")
        String packageName = moduleDirectory.substring(4)
        String packagePath = packageName.replaceAll("\\.", "/")

        moduleDef = moduleDef.replaceAll('MODULE_URI', moduleURI)
        moduleDef = moduleDef.replaceAll('MODULE_CLASSPATH',packagePath)
        project.file("${moduleDirectory}/module.xml").write(moduleDef)

        println moduleDef

        //Node projectXML = new XmlParser(false,true).parse(url.openStream())

      }

      //map.each { key, value -> println "  ${key} ${value}" }
    }

  }
}
