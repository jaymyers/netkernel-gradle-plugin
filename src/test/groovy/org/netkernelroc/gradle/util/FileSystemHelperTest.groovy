package groovy.org.netkernelroc.gradle.util

import org.junit.Before
import org.junit.Test

class FileSystemHelperTest {
    def FileSystemHelper helper

    @Before
    void setUp() {
        helper = new FileSystemHelper()
    }

    @Test
    void getGradleHomeDirectory() {
        def gradleHomeDir = helper.gradleHomeDir()
        assert gradleHomeDir.equals("${System.properties["user.home"]}/.gradle")
    }

    @Test
    void createDirectoryIfItDoesNotExist() {
        def dirName = "${System.properties["user.dir"]}/${generateTemporaryName()}"
        def f = new File(dirName)
        assert !f.exists()
        assert helper.createDirectory(dirName)
        assert f.exists()
        f.delete()
    }

    @Test
    void createGradleHomeDirIfItDoesNotExist() {
        def gradleHomeDir = helper.gradleHomeDir()
        def tempName = generateTemporaryName()
        def dirName = "$gradleHomeDir/$tempName"
        def f = new File(dirName)
        assert !helper.gradleHomeDirectoryExists(tempName)
        assert helper.createGradleHomeDirectory(tempName)
        assert helper.gradleHomeDirectoryExists(tempName)
        f.delete()
    }

    @Test
    void createFileIfItDoesNotExist() {
        def dirName = "${System.properties["user.dir"]}/${generateTemporaryName()}"
        assert helper.createDirectory(dirName)
        def fileName = "${dirName}/${generateTemporaryName()}"
        def f = new File(fileName)
        assert !f.exists()
        assert helper.createFile(fileName)
        assert f.exists()
        f.delete()
    }

    def generateTemporaryName() {
        System.currentTimeMillis().toString()
    }
}
