package org.zcorp.java1.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations;

    private OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        this(new LinkedList<>(Arrays.asList(organizations)));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public boolean addOrganization(Organization org) {
        return organizations.add(org);
    }

    public boolean isEmpty() {
        return organizations.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}