package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.web.forms.Dropdown;


@Controller
@RequestMapping("/dropdown")
public class DropdownController {
	
	List<Dropdown>  categories  = null;
	
	List<Dropdown>  subcatgories = null;
	
	@RequestMapping("categories")
    public @ResponseBody List<Dropdown> getCategories() {
		
		System.out.println("Getting categories...");
        if(categories == null){
        	categories = new ArrayList<Dropdown>();
        	setCategories();
        }
        
        return categories;
    }
	
	@RequestMapping("subcategories")
    public @ResponseBody List<Dropdown> getSubCategories(@RequestParam String categoryid) {
		
		System.out.println("Getting subcategories..."+categoryid);
        if(subcatgories == null){
        	subcatgories = new ArrayList<Dropdown>();
        	setSubCategories();
        }
        
        return categories;
    }
	
	
	
	private void setCategories(){
		Dropdown dto1 = new Dropdown();
		dto1.setValue("1");
		dto1.setKey("Training");
		
		Dropdown dto2 = new Dropdown();
		dto2.setValue("2");
		dto2.setKey("Books");
		
		Dropdown dto3 = new Dropdown();
		dto3.setValue("3");
		dto3.setKey("Test");
		
		categories.add(dto1);
		categories.add(dto2);
		categories.add(dto3);
		
	}
	
	private void setSubCategories(){
		Dropdown dto1 = new Dropdown();
		dto1.setValue("1");
		dto1.setKey("Training");
		
		Dropdown dto2 = new Dropdown();
		dto2.setValue("2");
		dto2.setKey("Books");
		
		Dropdown dto3 = new Dropdown();
		dto3.setValue("3");
		dto3.setKey("Test");
		
		subcatgories.add(dto1);
		subcatgories.add(dto2);
		subcatgories.add(dto3);
		
	}


}

