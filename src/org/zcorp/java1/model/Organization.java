package org.zcorp.java1.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String name;
    private String url;
    private List<JobDescription> jobDescriptions;

    public Organization(String name, JobDescription... jobDescriptions) {
        Objects.requireNonNull(name, "'name' must not be null");
        Objects.requireNonNull(jobDescriptions, "'jobDescriptions' must not be null");
        this.name = name;
        this.jobDescriptions = new ArrayList<>(Arrays.asList(jobDescriptions));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return jobDescriptions.equals(that.jobDescriptions);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + jobDescriptions.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<JobDescription> getJobDescriptions() {
        return jobDescriptions;
    }

    public void addJobDescription(JobDescription jobDescription) {
        jobDescriptions.add(jobDescription);
    }

    public void addJobDescription(int index, JobDescription jobDescription) {
        jobDescriptions.add(index, jobDescription);
    }

    public void setJobDescription(int index, JobDescription jobDescription) {
        jobDescriptions.set(index, jobDescription);
    }

    public void removeJobDescription(int index) {
        jobDescriptions.remove(index);
    }

    public void removeJobDescription(JobDescription jobDescription) {
        jobDescriptions.remove(jobDescription);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(url != null ? "<a href=\"" + url + "\">" : "");
        sb.append(name);
        sb.append(url != null ? "</a>" : "");
        sb.append(System.lineSeparator());

        for (JobDescription jobDescription : jobDescriptions) {
            sb.append(jobDescription);
        }

        return sb.toString();
    }
}