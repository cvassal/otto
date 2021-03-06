/*
 * Copyright 2011 Damien Bourdette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dbourdette.otto.source.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.dbourdette.otto.data.DataTablePeriod;
import com.github.dbourdette.otto.data.SimpleDataTable;
import com.github.dbourdette.otto.service.mail.Mail;
import com.github.dbourdette.otto.source.OldSource;
import com.github.dbourdette.otto.source.reports.OldReportConfig;
import com.github.dbourdette.otto.source.reports.OldSourceReports;
import com.github.dbourdette.otto.web.util.Pair;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

/**
 * Send mail schedule operation for a {@link com.github.dbourdette.otto.source.OldSource}
 *
 * @author damien bourdette
 */
public class OldMailSchedule {
    private String id;

    @NotNull
    private String report;

    @NotNull
    private DataTablePeriod period;

    private String cronExpression;

    @NotEmpty
    private String to;

    @NotEmpty
    private String title;

    public static List<OldMailSchedule> readAll(DBCursor cursor) {
        List<OldMailSchedule> result = new ArrayList<OldMailSchedule>();

        while (cursor.hasNext()) {
            result.add(read((BasicDBObject) cursor.next()));
        }

        return result;
    }

    public static OldMailSchedule read(BasicDBObject object) {
        OldMailSchedule schedule = new OldMailSchedule();

        schedule.setId(((ObjectId) object.get("_id")).toString());
        schedule.setReport(object.getString("report"));
        schedule.setPeriod(DataTablePeriod.valueOf(object.getString("period")));
        schedule.setTitle(object.getString("title"));
        schedule.setTo(object.getString("to"));
        schedule.setCronExpression(object.getString("cronExpression"));

        return schedule;
    }

    public BasicDBObject toDBObject() {
        BasicDBObject object = new BasicDBObject();

        if (StringUtils.isNotEmpty(id)) {
            object.put("_id", new ObjectId(id));
        }

        object.put("report", report);
        object.put("period", period.name());
        object.put("title", title);
        object.put("to", to);
        object.put("cronExpression", cronExpression);

        return object;
    }

    public Mail buildMail(OldSource source) {
        Mail mail = new Mail();

        mail.setTo(to);
        mail.setSubject(title + " sent at " + new Date() + " for period " + period);
        mail.setHtml(buildHtml(source));

        return mail;
    }

    public String buildHtml(OldSource source) {
        String html = "";

        OldReportConfig config = OldSourceReports.forSource(source).getReportConfigByTitle(report);

        if (config == null) {
            config = new OldReportConfig();
        }

        SimpleDataTable report = source.buildTable(config, period);

        html += "<div style=\"font-family: Helvetica Neue, Helvetica, Arial, sans-serif;\">";

        html += "<h1 style=\"font-size: 24px;line-height: 36px;font-weight: bold;display: block;\">" + config.getTitle() + "</h1>";

        html += "<table style=\"font-size: 13px;line-height:18px;border: 1px solid #DDD;border-collapse: separate;border-radius: 4px;width: 100%;margin-bottom: 18px;border-spacing: 0;\">";

        int count = 0;

        for (Pair pair : report.getSums()) {
            count += pair.getValue();
        }

        html += "<thead style=\"font-weight:bold\"><tr>";
        html += "<td style=\"border-top: 0;border-left: 1px solid #DDD;padding: 4px;line-height: 18px;text-align: left;vertical-align: top;\">total count</td>";
        html += "<td style=\"border-top: 0;border-left: 1px solid #DDD;padding: 4px;line-height: 18px;text-align: left;vertical-align: top;\">";
        html += count;
        html += "</td>";
        html += "</tr></thead>";

        for (Pair pair : report.getSums()) {
            html += "<tbody><tr>";
            html += "<td style=\"border-radius: 0 0 0 4px;border-left: 1px solid #DDD;padding: 4px;line-height: 18px;text-align: left;vertical-align: top;border-top: 1px solid #DDD;\">";
            html += pair.getName();
            html += "</td>";
            html += "<td style=\"border-radius: 0 0 4px 0;border-left: 1px solid #DDD;padding: 4px;line-height: 18px;text-align: left;vertical-align: top;border-top: 1px solid #DDD;\">";
            html += pair.getValue();
            html += "</td>";
            html += "</tr></tbody>";
        }

        html += "</table>";

        html += "</div>";

        return html;
    }

    public DataTablePeriod[] getPeriods() {
        return DataTablePeriod.values();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataTablePeriod getPeriod() {
        return period;
    }

    public void setPeriod(DataTablePeriod period) {
        this.period = period;
    }


}
