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
            def env = getEnv(project)

            def paperwork = "{\"gitSha\":\"$gitSha\", \"buildTime\":\"$buildTime\", \"env\":{$env}, \"extras\":$extras}"
            file.write paperwork
        })
    }

    private File getFile(Project project) {
        def file = project.file(project.paperwork.filename)
        file.parentFile.mkdirs()
        file
    }

    private String getEnv(Project project) {
        def env = ''

        project.paperwork.env.each { var ->
            def value = System.getenv(var)
            env = env + "\"${var}\":\"${value}\","
        }

        if (env.length() > 0) {
            env = env.substring(0, env.length() - 1)
        }

        env
    }
}