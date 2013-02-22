NetKernel Gradle plugin
=======================

A Gradle plugin providing tasks that support rapid development of NetKernel modules.

Each Gradle project can support one or more NetKernel modules.
Following NetKernel conventions, each NetKernel module is identified
by a URN, such as urn:org:netkernelroc:sample. Because operating systems
have trouble with colons in file names, it is customary to substitute
a period for the colons and therefore this plugin supports directory
names such as urn.org.netkernelroc.sample.

NetKernel development usually requires multiple modules and this plugin will support
any number of module directories. The use of 'urn' as the beginning of
each module directory name is significant for some tasks, such as the
installNetKernelModule task.



Tasks
-----

createNetKernelModules (cNKM)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This task creates a new NetKernel module based on a template module directory.

The parameters used by this task are:

==================    ================================  ===========
Parameter             Default                           Description
==================    ================================  ===========
moduleURI             urn:org:netkernelroc:sample       The URI identifier for the module
moduleTemplate        simple                            The particular template in the specified library
templateLibrary       default                           The name of the template library within the directory
templateDirectory     ~/.gradle/netkernelroc/templates  The location of the template library (Must be absolute in this release)
==================    ================================  ===========

Gradle will load parameter information from a variety of sources.
For example, the file ~/.gradle/gradle.properties could have the following entries::

  templateDirectory=/Users/randy/development/source/templates
  templateLibrary=databliss

which would allow a user to use the same set of templates and provide only the URI and specific template
throught he command line::

  gradle cNKM -PmoduleURI=urn:org:netkernelroc:sample:sample2 -PmoduleTemplate=complete



installNetKernelModules (iNKM)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



removeNetKernelModules (rNKM)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


removeAllNetKernelModules (rANKM)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~





Get the Plugin
--------------

- Install Gradle
- Get a copy of this GitHub repo (using the normal means)

From the repo issue this command to install the plugin::

  gradle install

The current version number is 0.0.6-SNAPSHOT

Using the Plugin
----------------

Once you have things installed, create a directory for your NetKernel modules. In that directory
create a build.gradle file with the following contents::


  apply plugin: 'netkernel'

  buildscript {
    repositories {
      mavenLocal()
      }
    dependencies {
      classpath group: 'org.netkernelroc.gradle', name: 'netkernel-gradle-plugin', version: '0.0.5-SNAPSHOT'
      }
  }



Install the module template files::

  gradle installTemplates


Now, in the project directory:

To create a sample NetKernel module from a template::

  gradle createNetKernelModules

This will create the directory urn.org.netkernelroc.sample in your project directory. The module.xml for the
NetKernel module will be based on a simple example template. This is a ready-to-use
NetKernel module.

With NetKernel running, issue the following::

  gradle installNetKernelModules

to install the module in NetKernel. (This uses the modules.d extension feature, which must be enabled.
(More documentation on this will be coming later).

Once this completed, you request the URL http://localhost:8080/hello to run the endpoint in the sample module.

IntelliJ Support
----------------

The task createIntelliJProject and removeIntelliJProject will create an IntelliJ project structure within your project.
This task creates a hidden subdirectory .idea and adds an *.iml file in each module.


NB: Remember to add .idea/ and *.iml to .gitignore to prevent IntelliJ project files from being added to
the project repository.

Authors
-------
Randolph Kahle
Brian Sletten
