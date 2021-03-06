package com.github.dbourdette.otto.service.config;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author damien bourdette
 * @version \$Revision$
 */
@Service
public class Config {
    public static final String MONITORING_SOURCE = "monitoringSource";

    @Inject
    private Datastore datastore;

    public void set(String key, String value) {
        datastore.delete(query(key));

        if (StringUtils.isEmpty(value)) {
            return;
        }

        ConfigEntry entry = new ConfigEntry(key, value);

        datastore.save(entry);
    }

    public String get(String key) {
        ConfigEntry entry = query(key).get();

        return entry == null ? null : entry.getValue();
    }

    private Query<ConfigEntry> query(String key) {
        return datastore.find(ConfigEntry.class).field("key").equal(key);
    }
}
