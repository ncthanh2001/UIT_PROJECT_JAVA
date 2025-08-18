package org.smart_job.entity;


public enum JobStatus {
    WISH_LIST("Wish List"),
    APPLIED("Applied"),
    INTERVIEW("Interview"),
    OFFER("Offer"),
    REJECTED("Rejected"),
    ACCEPTED("Accepted"),
    ARCHIVED("Archived");

    private final String displayName;

    JobStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static JobStatus fromString(String status) {
        for (JobStatus jobStatus : JobStatus.values()) {
            if (jobStatus.name().equalsIgnoreCase(status.replace(" ", "_"))) {
                return jobStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}