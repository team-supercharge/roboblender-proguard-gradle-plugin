package io.supercharge.roboblonderproguard

import com.github.javaparser.JavaParser
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

public class AnnotationDatabaseProcessorTask extends DefaultTask {

	@InputFile
	def File database

    @OutputFile
    def File proguardFile

	@TaskAction
	void execute(IncrementalTaskInputs inputs) {
		def stream = new FileInputStream(database)
		def compilationUnit = JavaParser.parse(stream);

		def adapter = new AnnotationDatabaseAdapter()
        adapter.visit(compilationUnit, null)

        def generator = new ProguardFileGenerator()
        generator.generateProguardFile(project, adapter.referencedClassNames)
	}
}
