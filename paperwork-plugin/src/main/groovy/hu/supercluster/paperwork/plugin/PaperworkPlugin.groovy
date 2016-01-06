package hu.supercluster.paperwork.plugin

import groovy.json.JsonOutput
import org.gradle.api.Plugin
import org.gradle.api.Project

class PaperworkPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("paperwork", PaperworkPluginExtension, project)
        project.afterEvaluate({
            File file = getFile(project)

            def set = project.paperwork.set
            def paperwork = JsonOutput.toJson(set)

            file.write paperwork
        })
    }

    private File getFile(Project project) {
        def file = project.file(project.paperwork.filename)
        file.parentFile.mkdirs()
        file
    }
}