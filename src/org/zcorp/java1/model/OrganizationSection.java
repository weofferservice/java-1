package org.zcorp.java1.model;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class OrganizationSection extends Section {
    private List<Organization> orgs;

    public OrganizationSection(SectionType type, Organization... orgs) {
        super(type);
        Objects.requireNonNull(orgs, "'orgs' must not be null");
        this.orgs = new ArrayList<>(Arrays.asList(orgs));
    }

    public List<Organization> getOrgs() {
        return orgs;
    }

    public void add(Organization org) {
        orgs.add(org);
    }

    public void remove(Organization org) {
        orgs.remove(org);
    }

    public void remove(int index) {
        orgs.remove(index);
    }

    public void set(int index, Organization org) {
        orgs.set(index, org);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getTitle());
        sb.append(System.lineSeparator());
        int i = 1;
        for (Organization org : orgs) {
            sb.append(i++);
            sb.append(") ");
            sb.append(org);
        }
        return sb.toString();
    }
}
