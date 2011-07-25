package org.otto.web.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.otto.web.form.SourceForm;
import org.otto.web.util.FlashScope;
import org.otto.web.util.IntervalUtils;
import org.otto.web.util.MongoDbHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mongodb.DBCollection;

@Controller
public class SourcesController {
    
    @Inject
    private MongoDbHelper mongoDbHelper;
    
    @Inject
    private FlashScope flashScope;

    @RequestMapping({"/sources/{name}"})
    public String source(@PathVariable String name, Model model) {
    	model.addAttribute("navItem", "index");
    	
    	DBCollection collection = mongoDbHelper.getCollection(name);
    	
        model.addAttribute("count", collection.count());
        model.addAttribute("capped", collection.isCapped());
        model.addAttribute("lastWeekFrequency", mongoDbHelper.frequency(name, IntervalUtils.lastWeek()));
        model.addAttribute("yesterdayFrequency", mongoDbHelper.frequency(name, IntervalUtils.yesterday()));
        model.addAttribute("todayFrequency", mongoDbHelper.frequency(name, IntervalUtils.today()));
        
        return "sources/source";
    }

    @RequestMapping({"/sources/form"})
    public String form(Model model) {
        model.addAttribute("form", new SourceForm());

        return "sources/source_form";
    }

    @RequestMapping(value = "/sources", method = RequestMethod.POST)
    public String createSource(@Valid @ModelAttribute("form") SourceForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "sources/source_form";
        }

        mongoDbHelper.createCollection(form);
        
        flashScope.message("source " + form.getName() + " has just been created");

        return "redirect:/sources";
    }

    @RequestMapping(value = "/sources/{name}", method = RequestMethod.DELETE)
    public String dropSource(@PathVariable String name) {
    	mongoDbHelper.getCollection(name).drop();
    	
    	flashScope.message("source " + name + " has just been deleted");

        return "redirect:/sources";
    }
}
