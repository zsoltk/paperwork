package hu.supercluster.paperwork.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PaperworkPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("paperwork", PaperworkPluginExtension, project)
        project.afterEvaluate({
            File file = getFile(project)

            def gitSha = project.paperwork.gitSha
            def buildTime = project.paperwork.buildTime
            def extras = project.paperwork.extras

            def paperwork = "{\"gitSha\":\"$gitSha\", \"buildTime\":\"$buildTime\", \"extras\":$extras}"
            file.write paperwork
        })
    }

    private File getFile(Project project) {
        def file = project.file(project.paperwork.filename)
        file.parentFile.mkdirs()
        file
    }
}