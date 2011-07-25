package org.otto.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.otto.web.util.MongoDbHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodb.DB;

@Controller
public class IndexController {

    @Inject
    private DB mongoDb;

    @RequestMapping({"/", "/index", "/sources"})
    public String index(Model model) {
        List<String> sources = new ArrayList<String>();

        for (String name : mongoDb.getCollectionNames()) {
            if (name.startsWith(MongoDbHelper.EVENTS_PREFIX)) {
            	sources.add(name.substring(MongoDbHelper.EVENTS_PREFIX.length()));
            }
        }

        model.addAttribute("sources", sources);
        
        return "index";
    }
}
