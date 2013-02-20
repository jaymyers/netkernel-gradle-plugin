package org.netkernelroc.gradle.tasks

import org.gradle.api.DefaultTask

class CreateIntelliJProject extends DefaultTask {

  @org.gradle.api.tasks.TaskAction void createIntelliJProject() {


    if (!project.fsHelper.existsDir(".idea")) {
      project.fsHelper.createDir(".idea")

      def projectDirectory = System.getProperty("user.dir")
      def projectName = projectDirectory.split("/").last()

      println projectName
      println projectDirectory

      project.file("${projectDirectory}/.idea/.name").write("${projectName}")
      project.file("${projectDirectory}/.idea/ant.xml").write(antXML)
      project.file("${projectDirectory}/.idea/compiler.xml").write(compilerXML)
      project.file("${projectDirectory}/.idea/copyright").mkdirs()
      project.file("${projectDirectory}/.idea/copyright/profiles_settings.xml").write(profilesSettingsXML)
      project.file("${projectDirectory}/.idea/encodings.xml").write(encodingsXML)
      // TODO: This needs to be dynamically created
      project.file("${projectDirectory}/.idea/gradle.xml").write(gradleSettingsXML)
      project.file("${projectDirectory}/.idea/inspectionProfiles").mkdirs()
      project.file("${projectDirectory}/.idea/misc.xml").write(miscXML)
      project.file("${projectDirectory}/.idea/modules.xml").write(modulesXML)
      project.file("${projectDirectory}/.idea/scopes").mkdirs()
      project.file("${projectDirectory}/.idea/scopes/scope_settings.xml").write(scopeSettingsXML)
      project.file("${projectDirectory}/.idea/uiDesigner.xml").write(uiDesignerXML)
      project.file("${projectDirectory}/.idea/vcs.xml").write(vcsXML)

      project.fsHelper.netkernelModuleNames().each { moduleDirectory ->
        String packageName = moduleDirectory.substring(4)
        String imlFileName = "${moduleDirectory}.doc/${moduleDirectory}.doc.iml"
        println imlFileName

        File imlFile = new File(imlFileName)
        if (!imlFile.exists()) {

          // Add to the IntelliJ project meta information so that it knows about this module
          File moduleXML = new File(".idea/modules.xml")
          Node projectXML = new XmlParser(false, true).parse(moduleXML)

          if (project.file("${moduleDirectory}").exists() && !project.file("${moduleDirectory}/${moduleDirectory}.iml").exists()) {
            project.file("${moduleDirectory}/${moduleDirectory}.iml").write(moduleIMLXML)
            projectXML.find { it.name() == 'component' }.find { it.name() == 'modules' }.appendNode("module", ['fileurl': "file://\$PROJECT_DIR\$/${moduleDirectory}/${moduleDirectory}.iml", 'filepath': "\$PROJECT_DIR\$/${moduleDirectory}/${moduleDirectory}.iml"])
          }

          String outputXML = groovy.xml.XmlUtil.serialize(projectXML)
          project.file("${projectDirectory}/.idea/modules.xml").write(outputXML)
        } else {
          println "IntelliJ module for ${moduleDirectory} already exists."
        }
      }


    } else {
      println "IntelliJ project directory already exists."
    }
  }


  String vcsXML = """<?xml version="1.0" encoding="UTF-8"?>
  <project version="4">
    <component name="VcsDirectoryMappings">
      <mapping directory="" vcs="" />
    </component>
  </project>
  """


  String moduleIMLXML = """<?xml version="1.0" encoding="UTF-8"?>
  <module type="JAVA_MODULE" version="4">
    <component name="NewModuleRootManager" inherit-compiler-output="false">
      <output url="file://\$MODULE_DIR\$" />
      <output-test url="file://\$MODULE_DIR\$" />
      <content url="file://\$MODULE_DIR\$">
        <sourceFolder url="file://\$MODULE_DIR\$" isTestSource="false" />
      </content>
      <orderEntry type="inheritedJdk" />
      <orderEntry type="sourceFolder" forTests="false" />
    </component>
  </module>"""


  String scopeSettingsXML = """<component name="DependencyValidationManager">
  <state>
    <option name="SKIP_IMPORT_STATEMENTS" value="false" />
  </state>
</component>"""

  String uiDesignerXML = """<?xml version="1.0" encoding="UTF-8"?>
  <project version="4">
    <component name="Palette2">
      <group name="Swing">
        <item class="com.intellij.uiDesigner.HSpacer" tooltip-text="Horizontal Spacer" icon="/com/intellij/uiDesigner/icons/hspacer.png" removable="false" auto-create-binding="false" can-attach-label="false">
          <default-constraints vsize-policy="1" hsize-policy="6" anchor="0" fill="1" />
        </item>
        <item class="com.intellij.uiDesigner.VSpacer" tooltip-text="Vertical Spacer" icon="/com/intellij/uiDesigner/icons/vspacer.png" removable="false" auto-create-binding="false" can-attach-label="false">
          <default-constraints vsize-policy="6" hsize-policy="1" anchor="0" fill="2" />
        </item>
        <item class="javax.swing.JPanel" icon="/com/intellij/uiDesigner/icons/panel.png" removable="false" auto-create-binding="false" can-attach-label="false">
          <default-constraints vsize-policy="3" hsize-policy="3" anchor="0" fill="3" />
        </item>
        <item class="javax.swing.JScrollPane" icon="/com/intellij/uiDesigner/icons/scrollPane.png" removable="false" auto-create-binding="false" can-attach-label="true">
          <default-constraints vsize-policy="7" hsize-policy="7" anchor="0" fill="3" />
        </item>
        <item class="javax.swing.JButton" icon="/com/intellij/uiDesigner/icons/button.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="0" hsize-policy="3" anchor="0" fill="1" />
          <initial-values>
            <property name="text" value="Button" />
          </initial-values>
        </item>
        <item class="javax.swing.JRadioButton" icon="/com/intellij/uiDesigner/icons/radioButton.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="0" hsize-policy="3" anchor="8" fill="0" />
          <initial-values>
            <property name="text" value="RadioButton" />
          </initial-values>
        </item>
        <item class="javax.swing.JCheckBox" icon="/com/intellij/uiDesigner/icons/checkBox.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="0" hsize-policy="3" anchor="8" fill="0" />
          <initial-values>
            <property name="text" value="CheckBox" />
          </initial-values>
        </item>
        <item class="javax.swing.JLabel" icon="/com/intellij/uiDesigner/icons/label.png" removable="false" auto-create-binding="false" can-attach-label="false">
          <default-constraints vsize-policy="0" hsize-policy="0" anchor="8" fill="0" />
          <initial-values>
            <property name="text" value="Label" />
          </initial-values>
        </item>
        <item class="javax.swing.JTextField" icon="/com/intellij/uiDesigner/icons/textField.png" removable="false" auto-create-binding="true" can-attach-label="true">
          <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1">
            <preferred-size width="150" height="-1" />
          </default-constraints>
        </item>
        <item class="javax.swing.JPasswordField" icon="/com/intellij/uiDesigner/icons/passwordField.png" removable="false" auto-create-binding="true" can-attach-label="true">
          <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1">
            <preferred-size width="150" height="-1" />
          </default-constraints>
        </item>
        <item class="javax.swing.JFormattedTextField" icon="/com/intellij/uiDesigner/icons/formattedTextField.png" removable="false" auto-create-binding="true" can-attach-label="true">
          <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1">
            <preferred-size width="150" height="-1" />
          </default-constraints>
        </item>
        <item class="javax.swing.JTextArea" icon="/com/intellij/uiDesigner/icons/textArea.png" removable="false" auto-create-binding="true" can-attach-label="true">
          <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
            <preferred-size width="150" height="50" />
          </default-constraints>
        </item>
        <item class="javax.swing.JTextPane" icon="/com/intellij/uiDesigner/icons/textPane.png" removable="false" auto-create-binding="true" can-attach-label="true">
          <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
            <preferred-size width="150" height="50" />
          </default-constraints>
        </item>
        <item class="javax.swing.JEditorPane" icon="/com/intellij/uiDesigner/icons/editorPane.png" removable="false" auto-create-binding="true" can-attach-label="true">
          <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
            <preferred-size width="150" height="50" />
          </default-constraints>
        </item>
        <item class="javax.swing.JComboBox" icon="/com/intellij/uiDesigner/icons/comboBox.png" removable="false" auto-create-binding="true" can-attach-label="true">
          <default-constraints vsize-policy="0" hsize-policy="2" anchor="8" fill="1" />
        </item>
        <item class="javax.swing.JTable" icon="/com/intellij/uiDesigner/icons/table.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
            <preferred-size width="150" height="50" />
          </default-constraints>
        </item>
        <item class="javax.swing.JList" icon="/com/intellij/uiDesigner/icons/list.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="6" hsize-policy="2" anchor="0" fill="3">
            <preferred-size width="150" height="50" />
          </default-constraints>
        </item>
        <item class="javax.swing.JTree" icon="/com/intellij/uiDesigner/icons/tree.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
            <preferred-size width="150" height="50" />
          </default-constraints>
        </item>
        <item class="javax.swing.JTabbedPane" icon="/com/intellij/uiDesigner/icons/tabbedPane.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="3" hsize-policy="3" anchor="0" fill="3">
            <preferred-size width="200" height="200" />
          </default-constraints>
        </item>
        <item class="javax.swing.JSplitPane" icon="/com/intellij/uiDesigner/icons/splitPane.png" removable="false" auto-create-binding="false" can-attach-label="false">
          <default-constraints vsize-policy="3" hsize-policy="3" anchor="0" fill="3">
            <preferred-size width="200" height="200" />
          </default-constraints>
        </item>
        <item class="javax.swing.JSpinner" icon="/com/intellij/uiDesigner/icons/spinner.png" removable="false" auto-create-binding="true" can-attach-label="true">
          <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1" />
        </item>
        <item class="javax.swing.JSlider" icon="/com/intellij/uiDesigner/icons/slider.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1" />
        </item>
        <item class="javax.swing.JSeparator" icon="/com/intellij/uiDesigner/icons/separator.png" removable="false" auto-create-binding="false" can-attach-label="false">
          <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3" />
        </item>
        <item class="javax.swing.JProgressBar" icon="/com/intellij/uiDesigner/icons/progressbar.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="0" hsize-policy="6" anchor="0" fill="1" />
        </item>
        <item class="javax.swing.JToolBar" icon="/com/intellij/uiDesigner/icons/toolbar.png" removable="false" auto-create-binding="false" can-attach-label="false">
          <default-constraints vsize-policy="0" hsize-policy="6" anchor="0" fill="1">
            <preferred-size width="-1" height="20" />
          </default-constraints>
        </item>
        <item class="javax.swing.JToolBar\$Separator" icon="/com/intellij/uiDesigner/icons/toolbarSeparator.png" removable="false" auto-create-binding="false" can-attach-label="false">
          <default-constraints vsize-policy="0" hsize-policy="0" anchor="0" fill="1" />
        </item>
        <item class="javax.swing.JScrollBar" icon="/com/intellij/uiDesigner/icons/scrollbar.png" removable="false" auto-create-binding="true" can-attach-label="false">
          <default-constraints vsize-policy="6" hsize-policy="0" anchor="0" fill="2" />
        </item>
      </group>
    </component>
  </project>"""


  String miscXML = """<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="AnalysisProjectProfileManager">
    <option name="PROJECT_PROFILE" />
    <option name="USE_PROJECT_LEVEL_SETTINGS" value="false" />
    <list size="0" />
  </component>
  <component name="CheckstyleConfigurable">
    <option name="suppFilterFilename" value="" />
    <option name="suppCommentFilter" value="false" />
    <option name="offComment" value="CHECKSTYLE\\:OFF" />
    <option name="onComment" value="CHECKSTYLE\\:ON" />
    <option name="checkFormat" value=".*" />
    <option name="messageFormat" value="" />
    <option name="checkCPP" value="true" />
    <option name="checkC" value="true" />
    <option name="suppNearbyCommentFilter" value="false" />
    <option name="snCommentFormat" value="SUPPRESS CHECKSTYLE (\\w+)" />
    <option name="snCheckFormat" value="\$1" />
    <option name="snMessageFormat" value="" />
    <option name="snInfluenceFormat" value="0" />
    <option name="snCheckCPP" value="true" />
    <option name="snCheckC" value="true" />
  </component>
  <component name="EclipseCodeFormatter">
    <option name="defaultSettings" value="true" />
    <option name="id" value="1338504768765" />
    <option name="name" value="default" />
  </component>
  <component name="EntryPointsManager">
    <entry_points version="2.0" />
  </component>
  <component name="FindBugsConfigurable">
    <option name="make" value="true" />
    <option name="effort" value="default" />
    <option name="priority" value="Medium" />
  </component>
  <component name="IdProvider" IDEtalkID="415D46A9ABBAF2524B933430D6AAE534" />
  <component name="JavadocGenerationManager">
    <option name="OUTPUT_DIRECTORY" />
    <option name="OPTION_SCOPE" value="protected" />
    <option name="OPTION_HIERARCHY" value="true" />
    <option name="OPTION_NAVIGATOR" value="true" />
    <option name="OPTION_INDEX" value="true" />
    <option name="OPTION_SEPARATE_INDEX" value="true" />
    <option name="OPTION_DOCUMENT_TAG_USE" value="false" />
    <option name="OPTION_DOCUMENT_TAG_AUTHOR" value="false" />
    <option name="OPTION_DOCUMENT_TAG_VERSION" value="false" />
    <option name="OPTION_DOCUMENT_TAG_DEPRECATED" value="true" />
    <option name="OPTION_DEPRECATED_LIST" value="true" />
    <option name="OTHER_OPTIONS" value="" />
    <option name="HEAP_SIZE" />
    <option name="LOCALE" />
    <option name="OPEN_IN_BROWSER" value="true" />
  </component>
  <component name="ModuleEditorState">
    <option name="LAST_EDITED_MODULE_NAME" />
    <option name="LAST_EDITED_TAB_NAME" />
  </component>
  <component name="ProjectResources">
    <default-html-doctype>http://www.w3.org/1999/xhtml</default-html-doctype>
  </component>
  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_5" assert-keyword="true" jdk-15="true" project-jdk-name="1.6" project-jdk-type="JavaSDK">
    <output url="file://\$PROJECT_DIR\$/out" />
  </component>
  <component name="SuppressionsComponent">
    <option name="suppCommentEnabled" value="false" />
    <option name="suppComment" value="SUPPRESS CODING RULES" />
  </component>
  <component name="WebServicesPlugin" addRequiredLibraries="true" />
</project>
"""
  String antXML = """
  <?xml version="1.0" encoding="UTF-8"?>
  <project version="4">
    <component name="AntConfiguration">
      <defaultAnt bundledAnt="true" />
    </component>
  </project>
  """

  String compilerXML = """<?xml version="1.0" encoding="UTF-8"?>
  <project version="4">
    <component name="CompilerConfiguration">
      <option name="DEFAULT_COMPILER" value="Javac" />
      <resourceExtensions />
      <wildcardResourcePatterns>
        <entry name="?*.properties" />
        <entry name="?*.xml" />
        <entry name="?*.gif" />
        <entry name="?*.png" />
        <entry name="?*.jpeg" />
        <entry name="?*.jpg" />
        <entry name="?*.html" />
        <entry name="?*.dtd" />
        <entry name="?*.tld" />
        <entry name="?*.ftl" />
      </wildcardResourcePatterns>
      <annotationProcessing enabled="false" useClasspath="true" />
    </component>
  </project>
  """

  String profilesSettingsXML = """<component name="CopyrightManager">
   <settings default="">
     <module2copyright />
   </settings>
 </component>"""

  String encodingsXML = """<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="Encoding" useUTFGuessing="true" native2AsciiForPropertiesFiles="false" />
</project>

"""

  String gradleSettingsXML = """<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="GradleSettings">
    <option name="gradleHome" value="\$USER_HOME\$/bin/gradle-1.2" />
  </component>
</project>
"""

  String modulesXML = """<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="ProjectModuleManager">
    <modules>
    </modules>
  </component>
</project>"""


}
