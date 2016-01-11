package hu.supercluster.paperwork.integration.matcher;

public class GitShaMatcher extends RegexMatcher {
    public GitShaMatcher() {
        super("[0-9a-f]{7}");
    }
}
