package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.TagDao;
import org.gatex.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/codeTemplate")
public class CodeTemplateController {
	@Autowired
	private Environment env;

    @GetMapping("/{key}")
	public ResponseEntity<Map<String,String>> get(@PathVariable(name="key", required=true) String key){
    	Map<String,String> data=new HashMap();
		data.put("answer", env.getProperty(key+".answer"));
		data.put("unittest", env.getProperty(key+".unittest"));
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
