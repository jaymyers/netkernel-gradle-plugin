package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

class InstallDefaultTemplates extends DefaultTask {

  /**
   * This code is going to start out pretty dumb...
   *
   * We'll simply copy from internal strings while we wait on a fix for the class loader resource loading issue
   *
   */

  @org.gradle.api.tasks.TaskAction
  void installTemplates() {
    if (!project.fsHelper.gradleHomeDirectoryExists('netkernelroc/templates/default')) {
      project.fsHelper.createGradleHomeDirectory('netkernelroc/templates/default')
      project.fsHelper.createGradleHomeDirectory('netkernelroc/templates/default/simple')
      project.file(project.fsHelper.gradleHomeDir() + '/netkernelroc/templates/default/simple/module.xml').write(simpleModuleXML)
    }
  }

String simpleModuleXML = """\
<module version="2.0">
  <meta>
    <identity>
      <uri>MODULE_URI</uri>
      <version>0.0.1</version>
    </identity>
    <info>
      <name>Simple Module</name>
      <description>Simple example module that provide HTTP delivered representations</description>
      <icon/>
    </info>
  </meta>

  <system>
    <dynamic />
  </system>

  <rootspace name="Simple Module" uri="MODULE_URI">

    <mapper>
      <config>
        <endpoint>
          <grammar>res:/hello</grammar>
          <request>
            <identifier>active:groovy</identifier>
            <argument name="operator">
              <literal type="string">
              context.createResponseFrom("Hello, from NetKernel!").setMimeType("text/plain")
              </literal>
            </argument>
          </request>
        </endpoint>
      </config>
      <space>
        <import>
          <uri>urn:org:netkernel:ext:system</uri>
        </import>
        <import>
          <uri>urn:org:netkernel:ext:layer1</uri>
        </import>
        <import>
          <uri>urn:org:netkernel:lang:groovy</uri>
        </import>
      </space>
    </mapper>

    <!-- This will map us into the HTTP FrontEnd Fulcrum -->
    <literal type="xml" uri="res:/etc/system/SimpleDynamicImportHook.xml">
      <connection>
        <type>HTTPFulcrum</type>
      </connection>
    </literal>

  </rootspace>

</module>
"""

}
