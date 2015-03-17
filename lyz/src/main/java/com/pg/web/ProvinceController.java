package com.pg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.service.ICommonService;
import com.pg.entity.Province;

@Controller
public class ProvinceController {

	@Autowired
	@Qualifier("CommonService")
	private ICommonService commonServiec;

	@RequestMapping(value = "/abs")
	public String list(Model model) {
		final Province province = commonServiec.get(Province.class, 26l);
		System.out.println(province);
		return "index";
	}

}
