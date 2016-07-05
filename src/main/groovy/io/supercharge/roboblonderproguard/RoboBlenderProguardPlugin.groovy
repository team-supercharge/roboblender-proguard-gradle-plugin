package io.supercharge.roboblonderproguard

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

public class RoboBlenderProguardPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def variants = null

        if (project.plugins.findPlugin("com.android.application") || project.plugins.findPlugin("android") ||
                project.plugins.findPlugin("com.android.test")) {
            variants = "applicationVariants"
        } else if (project.plugins.findPlugin("com.android.library") || project.plugins.findPlugin("android-library")) {
            variants = "libraryVariants"
        } else {
            throw new ProjectConfigurationException("The android or android-library plugin must be applied to the project", null)
        }

        def usesAndroidApt = project.plugins.findPlugin("com.neenbedankt.android-apt") || project.plugins.findPlugin("android-apt")

        def rulesFile = new File(project.buildDir, "generated/proguard/roboblender_proguard_keeps.pro")

        if (variants == "libraryVariants") {
            project.android.defaultConfig.consumerProguardFile(rulesFile)
        }

        project.afterEvaluate {
            project.android.buildTypes.all { buildType ->
                if (variants == "applicationVariants" && buildType.minifyEnabled) {
                    buildType.proguardFile(rulesFile)
                }
            }

            project.android[variants].all { variant ->
                if (variants == "applicationVariants" && !variant.buildType.minifyEnabled) {
                    return
                }

                def javaCompile = variant.hasProperty('javaCompiler') ? variant.javaCompiler : variant.javaCompile

                def aptOutputDir = usesAndroidApt ? project.file(new File(project.buildDir, "generated/source/apt"))
                        : project.file(new File(project.buildDir, "intermediates/classes"))

                def roboBlenderCompilerArg = javaCompile.options.compilerArgs.find { it.toString().startsWith("-AguiceAnnotationDatabasePackageName=") }
                def roboBlenderPackageName = roboBlenderCompilerArg ? roboBlenderCompilerArg.split("=")[1] : ""

                def aptOutput = new File(aptOutputDir, variant.dirName)
                def dbFile = new File(aptOutput, roboBlenderPackageName.replace('.', '/') + "/AnnotationDatabaseImpl.java")

                def task = project.tasks.create(name: "process${variant.name.capitalize()}RoboBlender", type: AnnotationDatabaseProcessorTask) {
                    database = dbFile
                    proguardFile = rulesFile
                }

                javaCompile.finalizedBy task
            }
        }
    }
}
