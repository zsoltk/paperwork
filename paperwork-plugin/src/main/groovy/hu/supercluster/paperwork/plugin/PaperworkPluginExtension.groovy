package hu.supercluster.paperwork.plugin

class PaperworkPluginExtension {
    def String filename = "src/main/assets/paperwork.json"
    def String gitSha
    def String buildTime = new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"))
    def env = []
    def String extras = '{}'

    PaperworkPluginExtension(project) {
        gitSha = 'git rev-parse --short HEAD'.execute([], project.rootDir).text.trim()
    }
}