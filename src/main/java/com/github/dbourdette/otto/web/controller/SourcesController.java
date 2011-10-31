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

package com.github.dbourdette.otto.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.dbourdette.otto.graph.GraphPeriod;
import com.github.dbourdette.otto.source.config.AggregationConfig;
import com.github.dbourdette.otto.source.DBSource;
import com.github.dbourdette.otto.source.config.DefaultGraphParameters;
import com.github.dbourdette.otto.source.config.MailReportConfig;
import com.github.dbourdette.otto.source.MailReports;
import com.github.dbourdette.otto.source.Sources;
import com.github.dbourdette.otto.source.TimeFrame;
import com.github.dbourdette.otto.web.form.CappingForm;
import com.github.dbourdette.otto.web.form.IndexForm;
import com.github.dbourdette.otto.web.form.SourceForm;
import com.github.dbourdette.otto.web.util.FlashScope;
import com.github.dbourdette.otto.web.util.IntervalUtils;

/**
 * @author damien bourdette
 * @version \$Revision$
 */
@Controller
public class SourcesController {

    @Inject
    private Sources sources;

    @Inject
    private FlashScope flashScope;

    @Inject
    private MailReports mailReports;

    @RequestMapping({"/sources/{name}"})
    public String source(@PathVariable String name, Model model) {
        model.addAttribute("navItem", "index");

        DBSource source = sources.getSource(name);

        model.addAttribute("source", source);
        model.addAttribute("aggregation", source.getAggregationConfig());
        model.addAttribute("lastWeekFrequency", source.findEventsFrequency(IntervalUtils.lastWeek()));
        model.addAttribute("yesterdayFrequency", source.findEventsFrequency(IntervalUtils.yesterday()));
        model.addAttribute("todayFrequency", source.findEventsFrequency(IntervalUtils.today()));
        model.addAttribute("defaultGraphParameters", source.getDefaultGraphParameters());
        model.addAttribute("mailReports", source.getMailReports());
        model.addAttribute("indexes", source.getIndexes());

        return "sources/source";
    }

    @RequestMapping("/sources/form")
    public String form(Model model) {
        model.addAttribute("form", new SourceForm());

        return "sources/source_form";
    }

    @RequestMapping(value = "/sources", method = RequestMethod.POST)
    public String createSource(@Valid @ModelAttribute("form") SourceForm form, BindingResult result) {
        if (StringUtils.isNotEmpty(form.getSize())) {
            try {
                form.getSizeInBytes();
            } catch (Exception e) {
                result.rejectValue("size", "size.invalidPattern");
            }
        }

        if (result.hasErrors()) {
            return "sources/source_form";
        }

        sources.createSource(form);

        flashScope.message("source " + form.getName() + " has just been created");

        return "redirect:/sources";
    }

    @RequestMapping("/sources/{name}/delete")
    public String dropSourceForm(@PathVariable String name, Model model) {
        model.addAttribute("navItem", "index");

        return "sources/source_delete_form";
    }

    @RequestMapping(value = "/sources/{name}", method = RequestMethod.DELETE)
    public String dropSource(@PathVariable String name) {
        sources.dropSource(name);

        flashScope.message("source " + name + " has just been deleted");

        return "redirect:/sources";
    }

    @RequestMapping("/sources/{name}/aggregation/form")
    public String aggregation(@PathVariable String name, Model model) {
        model.addAttribute("form", sources.getSource(name).getAggregationConfig());
        model.addAttribute("timeFrames", TimeFrame.values());

        return "sources/aggregation_form";
    }

    @RequestMapping(value = "/sources/{name}/aggregation", method = RequestMethod.POST)
    public String saveAggregation(@PathVariable String name, @Valid @ModelAttribute("form") AggregationConfig form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("timeFrames", TimeFrame.values());

            return "sources/aggregation_form";
        }

        sources.getSource(name).saveAggregation(form);

        return "redirect:/sources/{name}";
    }

    @RequestMapping("/sources/{name}/default-graph-params/form")
    public String defaultGraphParameters(@PathVariable String name, Model model) {
        model.addAttribute("form", sources.getSource(name).getDefaultGraphParameters());
        model.addAttribute("periods", GraphPeriod.values());

        return "sources/default_graph_params_form";
    }

    @RequestMapping(value = "/sources/{name}/default-graph-params", method = RequestMethod.POST)
    public String saveDefaultGraphParameters(@PathVariable String name, @Valid @ModelAttribute("form") DefaultGraphParameters form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("periods", GraphPeriod.values());

            return "sources/default_graph_params_form";
        }

        sources.getSource(name).setDefaultGraphParameters(form);

        return "redirect:/sources/{name}";
    }

    @RequestMapping("/sources/{name}/capping/form")
    public String capping(@PathVariable String name, Model model) {
        model.addAttribute("form", new CappingForm());

        return "sources/capping_form";
    }

    @RequestMapping(value = "/sources/{name}/capping", method = RequestMethod.POST)
    public String saveCapping(@PathVariable String name, @Valid @ModelAttribute("form") CappingForm form, BindingResult result, Model model) {
        if (StringUtils.isNotEmpty(form.getSize())) {
            try {
                form.getSizeInBytes();
            } catch (Exception e) {
                result.rejectValue("size", "size.invalidPattern");
            }
        }

        if (result.hasErrors()) {
            return "sources/capping_form";
        }

        sources.getSource(name).cap(form);

        return "redirect:/sources/{name}";
    }

    @RequestMapping("/sources/{name}/report")
    public String report(@PathVariable String name, Model model) {
        model.addAttribute("form", new MailReportConfig());

        return "sources/report_form";
    }

    @RequestMapping(value = "/sources/{name}/report", method = RequestMethod.POST)
    public String report(@PathVariable String name, @Valid @ModelAttribute("form") MailReportConfig form, BindingResult result, Model model) throws SchedulerException, ParseException {
        if (result.hasErrors()) {
            return "sources/report_form";
        }

        sources.getSource(name).saveMailReport(form);

        mailReports.onReportChange(form);

        return "redirect:/sources/{name}";
    }

    @RequestMapping("/sources/{name}/report/{id}")
    public String report(@PathVariable String name, @PathVariable String id, Model model) {
        model.addAttribute("form", sources.getSource(name).getMailReport(id));

        return "sources/report_form";
    }

    @RequestMapping("/sources/{name}/report/{id}/delete")
    public String delete(@PathVariable String name, @PathVariable String id, Model model) throws SchedulerException {
        MailReportConfig mailReportConfig = sources.getSource(name).deleteMailReport(id);

        if (mailReportConfig != null) {
            mailReports.onReportDeleted(mailReportConfig);
        }

        return "redirect:/sources/{name}";
    }

    @RequestMapping("/sources/{name}/report/{id}/send")
    public String sendReport(@PathVariable String name, @PathVariable String id, Model model) throws MessagingException, UnsupportedEncodingException {
        DBSource source = sources.getSource(name);

        MailReportConfig mailReport = source.getMailReport(id);

        mailReports.sendReport(mailReport);

        flashScope.message("your email report has been sent");

        return "redirect:/sources/{name}";
    }

    @RequestMapping("/sources/{name}/indexes/form")
    public String indexes(@PathVariable String name, Model model) {
        model.addAttribute("form", new IndexForm());

        return "sources/index_form";
    }

    @RequestMapping(value = "/sources/{name}/indexes", method = RequestMethod.POST)
    public String indexes(@PathVariable String name, @Valid @ModelAttribute("form") IndexForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sources/index_form";
        }

        DBSource source = sources.getSource(name);

        source.createIndex(form);

        return "redirect:/sources/{name}";
    }

    @RequestMapping("/sources/{name}/indexes/{index}/drop")
    public String dropIndex(@PathVariable String name, @PathVariable String index) {
        DBSource source = sources.getSource(name);

        source.dropIndex(index);

        return "redirect:/sources/{name}";
    }


}
