NetKernel Gradle plugin
=======================

This is the KernelROC community plugin for NetKernel development with Gradle.

Get the Plugin
--------------

- Install Gradle
- Get a copy of this GitHub repo (using the normal means)

From the repo issue this command to install the plugin:

``gradle install``

The current version number is 0.0.5-SNAPSHOT

Using the Plugin
----------------

Once you have things installed, create a directory for your NetKernel modules. In that directory
create a build.gradle file with the following contents:

```
apply plugin: 'netkernel'

buildscript {
  repositories {
    mavenLocal()
    }
  dependencies {
    classpath group: 'org.netkernelroc.gradle', name: 'netkernel-gradle-plugin', version: '0.0.5-SNAPSHOT'
    }
}
```

Install the module template files:

```gradle installTemplates```


Now, in the project directory:

To create a sample NetKernel module:

```gradle installTemplates```

Create a sample NetKernel module from a template:

```gradle createNetKernelModules```

This will create a directory urn.org.netkernelroc.sample and insert a module.xml file. This is a ready-to-use
NetKernel module.

With NetKernel running, issue the following:

```gradle installNetKernelModules```

to inform NetKernel about your modules.

Once this completed, you request the URL http://localhost:8080/hello to run the endpoint in the sample module.

IntelliJ Support
----------------

The task createIntelliJProject and removeIntelliJProject will create an .idea/ based project structure and
include each NetKernel module as an IntelliJ project.

NB: Remember to add .idea/ and *.iml to .gitignore to prevent IntelliJ project files from being added to
the project repository.


