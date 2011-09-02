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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.dbourdette.otto.graph.Graph;
import com.github.dbourdette.otto.source.DBSource;
import com.github.dbourdette.otto.source.Sources;
import com.github.dbourdette.otto.web.form.GraphForm;

/**
 * @author damien bourdette
 * @version \$Revision$
 */
@Controller
public class GraphController {

    @Inject
    private Sources sources;

    @RequestMapping({"/sources/{name}/graph"})
    public String graph(@PathVariable String name, @Valid GraphForm form, BindingResult result, Model model, HttpServletRequest request) {
        DBSource source = sources.getSource(name);

        form.fillWithDefault(source.getDefaultGraphParameters(), request);

        model.addAttribute("navItem", "graph");
        model.addAttribute("form", form);

        Long t1 = System.currentTimeMillis();

        Graph graph = form.buildGraph(source);
        graph.top(20);

        Long t2 = System.currentTimeMillis();

        String html =  graph.toGoogleHtml(1080, 750);

        Long t3 = System.currentTimeMillis();

        List<String> times = new ArrayList<String>();
        times.add("Gathered graph data in " + (t2 -t1) + "ms");
        times.add("Build html " + (t3 -t2) + "ms");

        model.addAttribute("times", times);
        model.addAttribute("graph", html);

        return "sources/graph";
    }

    @RequestMapping({"/sources/{name}/graph.csv"})
    public void csv(@PathVariable String name, GraphForm form, HttpServletResponse response) throws IOException {
        response.setContentType("application/csv");

        response.getWriter().write(form.buildGraph(sources.getSource(name)).toCsv());
    }

    @RequestMapping({"/sources/{name}/graph/table"})
    public String table(@PathVariable String name, GraphForm form, Model model) throws IOException {
        model.addAttribute("table", form.buildGraph(sources.getSource(name)).toHtmlTable());

        return "sources/graph_table";
    }
}
