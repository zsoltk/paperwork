package hu.supercluster.paperwork.plugin

class PaperworkPluginExtension {
    private final Object project
    def String filename = "src/main/assets/paperwork.json"
    def Map set = [:]

    PaperworkPluginExtension(project) {
        this.project = project
    }

    public String gitSha() {
        'git rev-parse --short HEAD'.execute([], project.rootDir).text.trim()
    }
}