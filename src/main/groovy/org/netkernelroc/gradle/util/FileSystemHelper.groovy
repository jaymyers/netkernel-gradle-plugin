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
   * Given an absolute path for a file, switch the source and destination portions
   * and return the new absolute path
   *
   * @param file
   * @param oldPath
   * @param newPath
   * @return
   */
  String switchFilePath(String file, String oldPath, String newPath) {
    return newPath + file.substring(oldPath.length(),file.length())
  }



  /**
   * Get the Gradle Home directory for this user.
   * @return the current user's Gradle home dir
   */
  def gradleHomeDir() {
    return "${System.properties['user.home']}/.gradle"
  }

  boolean removeDirectory(File directory) {

    if (directory == null) {
      return false
    }
    if (!directory.exists()) {
      return true
    }
    if (!directory.isDirectory()) {
      return false
    }

    String[] list = directory.list();

    // Some JVMs return null for File.list() when the
    // directory is empty.
    if (list != null) {
      for (int i = 0; i < list.length; i++) {
        File entry = new File(directory, list[i]);

        //        System.out.println("\tremoving entry " + entry);

        if (entry.isDirectory()) {
          if (!removeDirectory(entry)) {
            return false
          }
        } else {
          if (!entry.delete()) {
            return false
          }
        }
      }
    }

    return directory.delete();
  }

  /**
   * Refactor this!
   *
   * @param dirName
   * @return
   */
  def createDir(String dirName) {
    createDirectory(dirName)
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
