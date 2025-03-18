package buildteam.Model;

public enum EducationLevel {
    TENTH("10th"),
    TWELFTH("12th"),
    GRADUATION("Graduation"),
    POST_GRADUATION("Post Graduation");

    private final String displayName;

    EducationLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
