This is a plugin for NetKernel


An example build.gradle is:

apply plugin: 'netkernel'

buildscript {
  repositories {
    mavenLocal()
  }
  dependencies {
  classpath group: 'org.netkernelroc.gradle', name: 'netkernel-gradle-plugin', version: '0.0.1-SNAPSHOT'
  }
}

project.ext.netkernelModules = [
    "urn:com:bbyopen:metis:di:product", 
    "urn:com:bbyopen:metis:di:review"
  ]
project.ext.installModules = [
    "urn:com:bbyopen:metis:di:product"
  ]

Notice that project.ext.netkernelModules must list all modules under Gradle management
Notice that project.ext.installModules includes the set of all modules that are to be installed in a running instance of NetKernel.
