package hu.supercluster.paperwork.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PaperworkPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("paperwork", PaperworkPluginExtension)
        project.afterEvaluate({
            File file = getFile(project)

            def gitSha = 'git rev-parse --short HEAD'.execute([], project.rootDir).text.trim()
            def buildTime = new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"))
            def extra = project.paperwork.extra

            def paperwork = "{\"gitSha\":\"$gitSha\", \"buildTime\":\"$buildTime\", \"extra\":$extra}"
            file.write paperwork
        })
    }

    private File getFile(Project project) {
        def file = project.file(project.paperwork.filename)
        file.parentFile.mkdirs()
        file
    }
}