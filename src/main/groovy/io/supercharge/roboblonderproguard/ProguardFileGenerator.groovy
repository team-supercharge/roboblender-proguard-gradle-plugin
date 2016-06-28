package io.supercharge.roboblonderproguard;

import org.gradle.api.Project;

import java.io.File;

public class ProguardFileGenerator {

    void generateProguardFile(Project project, List<String> referencedClassNames) {
        def dir = new File(project.buildDir, "generated/proguard")

        if (!dir.exists()) {
            dir.mkdirs()
        }

        def proguardFile = new File(dir, "roboblender_proguard_keeps.pro")

        if (!proguardFile.exists()) {
            proguardFile.createNewFile()
        }

        proguardFile.text = ""

        referencedClassNames.forEach() { referencedClassName ->
            proguardFile << "-keep class " + referencedClassName + "\n"
        }
    }
}
