package org.otto.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;

import org.codehaus.jackson.JsonParseException;
import org.otto.web.form.BatchForm;
import org.otto.web.form.BatchValuesType;
import org.otto.web.util.DBObjectParser;
import org.otto.web.util.FlashScope;
import org.otto.web.util.MongoDbHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author damien bourdette
 */
@Controller
public class BatchController {
	@Inject
	private MongoDbHelper mongoDbHelper;

	@Inject
	private FlashScope flashScope;

	@Inject
	private DBObjectParser parser;

	@RequestMapping("/types/{name}/events/batch")
	public String form(@PathVariable String name, Model model) {
		model.addAttribute("form", new BatchForm());

		return "types/batch_form";
	}

	@RequestMapping(value = "/types/{name}/events/batch", method = RequestMethod.POST)
	public String postEvent(@PathVariable String name, @Valid @ModelAttribute("form") BatchForm form,
			BindingResult bindingResult) throws JsonParseException, IOException {
		if (bindingResult.hasErrors()) {
			return "types/batch_form";
		}

		DBCollection collection = mongoDbHelper.getCollection(name);

		for (int i = 0; i < form.getCount(); i++) {
			DBObject object = null;

			if (form.getValuesType() == BatchValuesType.JSON) {
				object = parser.fromJson(form.getValues());
			} else {
				object = parser.fromKeyValues(form.getValues());
			}

			if (object.containsField("date") || (!(object.get("date") instanceof Date))) {
				object.put("date", form.getDateType().instanciateDate().toDate());
			}

			collection.insert(object);
		}

		flashScope.message(form.getCount() + " events inserted");

		return "redirect:/types/{name}/events/batch";
	}
}