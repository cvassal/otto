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

package com.github.dbourdette.otto.web.controller.api;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.dbourdette.otto.source.Source;
import com.github.dbourdette.otto.util.Page;
import com.github.dbourdette.otto.web.service.RemoteEventsFacade;
import com.mongodb.DBObject;

/**
 * Controller for external apis.
 *
 * @author damien bourdette
 * @version \$Revision$
 */
@Controller
public class ApiController {

    private static final int JSONAPI_PAGE_SIZE = 1000;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime();

    @Inject
    private RemoteEventsFacade remoteEventsFacade;

    private static final byte[] EMPTY_GIF;

    static {
        try {
            EMPTY_GIF = IOUtils.toByteArray(ApiController.class.getResourceAsStream("empty.gif"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load empty.gif");
        }
    }

    @RequestMapping(value = "/api/sources/{name}/events", method = RequestMethod.POST)
    public void post(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) {
        remoteEventsFacade.post(name, copyParams(request));

        response.setStatus(200);
    }

    @RequestMapping(value = "/jsonapi/sources/{name}/events", method = RequestMethod.GET, headers = "Accept=application/json")
    public void eventsJson(@PathVariable String name, @RequestParam(required = false) Integer page, HttpServletResponse response) throws IOException {
        Source source = Source.findByName(name);
        Page<DBObject> events = source.findEvents(page, JSONAPI_PAGE_SIZE);

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode root = mapper.createObjectNode();

        root.put("count", events.getTotalCount());
        root.put("page", Page.fixPage(page));

        ArrayNode eventsNode = root.putArray("events");

        for (DBObject event : events.getItems()) {
            ObjectNode eventNode = mapper.createObjectNode();

            for (String key : event.keySet()) {
                if (!"_id".equals(key)) {
                    eventNode.put(key, formatValue(event.get(key)));
                }
            }

            eventsNode.add(eventNode);
        }

        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), root);
    }

    @RequestMapping(value = "/imgapi/sources/{name}/event.gif")
    public void gifPost(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) throws IOException {
        remoteEventsFacade.post(name, copyParams(request));

        response.setHeader("Cache-Control", "private, no-cache");
        response.setHeader("Content-Type", "image/gif");
        response.setHeader("Pragma", "no-cache");
        response.setStatus(200);
        response.getOutputStream().write(EMPTY_GIF);
    }

    /**
     * Since we are using @Async in our RemoteEventsFacade we need to deep copy map of parameters from request.
     * At least tomcat reuse request objects and map would be empty when @Async code is invoked.
     */
    private Map<String, String> copyParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();

        Enumeration<String> names = request.getParameterNames();

        while (names.hasMoreElements()) {
            String name = names.nextElement();

            params.put(name, request.getParameter(name));
        }

        return params;
    }

    private String formatValue(Object value) {
        if (value instanceof Date) {
            return DATE_TIME_FORMATTER.print(new DateTime(value));
        } else {
            return value == null ? null : value.toString();
        }
    }

}
