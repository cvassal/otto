package com.github.dbourdette.otto.web.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.github.dbourdette.otto.source.Source;

/**
 * Used for jsp layout by group
 *
 * @author damien bourdette
 * @version \$Revision$
 */
public class SourceGroups {
    Map<String, SourceGroup> groups = new HashMap<String, SourceGroup>();

    public static SourceGroups findAll() {
        SourceGroups groups = new SourceGroups();

        for (Source source : Source.findAll()) {
            SourceGroup group = groups.ensureExists(source.getDisplayGroup());

            group.addSource(source);
        }

        return groups;
    }

    public Set<SourceGroup> getGroups() {
        Collection<SourceGroup> unsortedGroups = groups.values();

        TreeSet<SourceGroup> sortedGroups = new TreeSet<SourceGroup>(new Comparator<SourceGroup>() {
            @Override
            public int compare(SourceGroup sourceGroup, SourceGroup sourceGroup1) {
                return sourceGroup.getName().compareTo(sourceGroup1.getName());
            }
        });

        sortedGroups.addAll(unsortedGroups);

        return sortedGroups;
    }

    private SourceGroup ensureExists(String group) {
        if (group == null) {
            group = "";
        }

        SourceGroup sourceGroup = groups.get(group);

        if (sourceGroup == null) {
            sourceGroup = new SourceGroup(group);

            groups.put(group, sourceGroup);
        }

        return sourceGroup;
    }
}
