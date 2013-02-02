package groovy.org.netkernelroc.gradle.util

/**
 * Helper for FileSystem-related activities.
 */
class FileSystemHelper {

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

        if(!retValue) {
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
}
