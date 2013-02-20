The NetKernelROC community plugin for NetKernel development


The simplest use requires the following build.gradle file:


apply plugin: 'netkernel'

buildscript {
  repositories {
    mavenLocal()
  }
  dependencies {
  classpath group: 'org.netkernelroc.gradle', name: 'netkernel-gradle-plugin', version: '0.0.4-SNAPSHOT'
  }
}

To create a sample NetKernel module:

gradle createNetKernelModules

This will install a standard set of module templates into ~/.gradle/netkernelroc/templates/standard and then
create a module in the directory:

urn.org.netkernelroc.sample

This module imports itself into the front end fulcrum and provides a resource at http://localhost:8080/hello


