package com.github.dbourdette.otto.integration.source;

import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.dbourdette.otto.Registry;
import com.github.dbourdette.otto.source.Source;
import com.github.dbourdette.otto.source.reports.ReportConfig;
import com.github.dbourdette.otto.source.reports.SourceReports;
import com.github.dbourdette.otto.web.exception.SourceAlreadyExists;
import com.github.dbourdette.otto.web.exception.SourceNotFound;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author damien bourdette
 * @version \$Revision$
 */
public class SourceTest {
    @Before
    public void clean() throws UnknownHostException, NoSuchFieldException {
        Mongo mongo = new Mongo();

        DB db = mongo.getDB("otto-integration");
        db.dropDatabase();

        Morphia morphia = new Morphia();
        morphia.map(Source.class);

        Registry.mongoDb = db;
        Registry.datastore = morphia.createDatastore(mongo, "otto-integration");
    }
    
    @Test
    public void create() {
        Source source = Source.create("test");

        assertThat(source.getName()).isEqualTo("test");
    }

    @Test
    public void createTwice() {
        Source.create("test");

        try {
            Source.create("test");
            
            fail("Source test already exists");
        } catch (SourceAlreadyExists e) {
            
        }
    }

    @Test
    public void exists() {
        Source.create("test");

        assertThat(Source.exists("test")).isTrue();
    }

    @Test
    public void findByName() {
        Source.create("test");
        
        Source source = Source.findByName("test");

        assertThat(source.getName()).isEqualTo("test");
    }

    @Test
    public void findByNameNotFound() {
        try {
            Source.findByName("test");

            fail("Source test does not exist");
        } catch (SourceNotFound e) {

        }
    }

    @Test
    public void findAll() {
        Source.create("test1");
        Source.create("test2");

        assertThat(Source.findAll().size()).isEqualTo(2);
        assertThat(Source.findAll()).contains(Source.findByName("test1"));
        assertThat(Source.findAll()).contains(Source.findByName("test2"));
    }

    @Test
    public void delete() {
        Source source = Source.create("test");

        source.delete();

        assertThat(Source.exists("test")).isFalse();
    }

    @Test
    public void reportConfigs() {
        Source source = Source.create("test");

        ReportConfig config = new ReportConfig();
        config.setTitle("hits");
        config.setValueAttribute("hits");

        config.setSourceName(source.getName());
        config.save();

        Assert.assertEquals(1, SourceReports.forSource(source).getReportConfigs().size());
        Assert.assertEquals("hits", SourceReports.forSource(source).getReportConfigs().get(0).getValueAttribute());

        config = SourceReports.forSource(source).getReportConfig(config.getId());

        Assert.assertEquals("hits", config.getValueAttribute());

        config.setValueAttribute("slug");
        config.save();

        Assert.assertEquals("slug", SourceReports.forSource(source).getReportConfigs().get(0).getValueAttribute());
    }
}
