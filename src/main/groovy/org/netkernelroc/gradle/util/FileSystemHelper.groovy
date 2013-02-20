package org.netkernelroc.gradle.util

import groovy.io.FileType

/**
 * Helper for FileSystem-related activities.
 */
class FileSystemHelper {

  /**
   * Converts a string with a URN (with colons) to a directory path (with periods)
   */
  def convertURNtoPath(String urn) {
    return urn.replaceAll(":", ".")
  }

  boolean existsDir(String dir) {
    File fileDir = new File(dir)
    return fileDir.exists()
  }



  /**
   * Get the Gradle Home directory for this user.
   * @return the current user's Gradle home dir
   */
  def gradleHomeDir() {
    return "${System.properties['user.home']}/.gradle"
  }

  /**
   * Create a directory if it doesn't exist.
   * @param dirName an absolute filename
   * @return an indicator of success (true if it existed or is created)
   */
  def createDirectory(String dirName) {
    boolean retValue = false

    def f = new File(dirName)
    retValue = f.isDirectory() && f.exists()

    if (!retValue) {
      retValue = f.mkdirs()
    }

    retValue
  }

  /**
   * Create a directory in the Gradle Home Directory ("$user.dir"/.gradle)
   * @param dirName a relative filename
   */
  def createGradleHomeDirectory(String dirName) {
    def dir = "${gradleHomeDir()}/$dirName"
    return createDirectory(dir)
  }

  /**
   * Determine if a directory exists.
   */

  def gradleHomeDirectoryExists(String dirName) {
    def dir = "${gradleHomeDir()}/$dirName"
    new File(dir).exists()
  }

  /**
   * Create a file if it doesn't exist.
   * @param fileName an absolute filename
   * @return an indicator of success (true if it existed or is created)
   */

  def createFile(String fileName) {
    boolean retValue = false

    def f = new File(fileName)
    retValue = f.createNewFile()
    retValue
  }

  /**
   * Returns a list of directory names for the NetKernel modules
   *
   */
  def netkernelModuleNames() {
    def names = []
    def projectDirectory = System.getProperty("user.dir")
    def dir = new File(projectDirectory)
    dir.traverse(type: FileType.DIRECTORIES, maxDepth: 0) { directory ->
      if (directory.name.startsWith("urn")) {
        names.add(directory.name)
      }
    }
    names
  }

  /**
   * Returns the name of the project.
   *
   * By default, this is the directory name containing the project
   *
   */
  def projectName() {
    def projectDirectory = System.getProperty("user.dir")
    def projectName = projectDirectory.split("/").last()
    projectName
  }

}
