package hu.supercluster.paperwork.plugin

class PaperworkPluginExtension {
    private final Object project
    def String filename = "src/main/assets/paperwork.json"
    def Map set = [:]

    PaperworkPluginExtension(project) {
        this.project = project
    }

    public String shell(String cmd) {
        cmd.execute([], project.rootDir).text.trim()
    }

    public String gitSha() {
        shell('git rev-parse --short HEAD')
    }

    public String gitTag() {
        shell('git describe --tags --abbrev=0')
    }

    public String gitInfo() {
        shell('git describe --tags --always --dirty')
    }

    public String gitBranch() {
        ['sh', '-c', 'git branch | sed -n "/\\* /s///p"'].execute().text.trim()
    }

    public String buildTime() {
        new Date().getTime();
    }

    public String buildTime(String format) {
        new Date().format(format)
    }

    public String buildTime(String format, String timeZoneId) {
        new Date().format(format, TimeZone.getTimeZone(timeZoneId))
    }

    public String env(String var) {
        System.getenv(var)
    }
}