package com.example.demo.test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

import com.example.enums.ModuleTypeEnum;
import com.example.exception.ResultException;
import com.example.util.OptionUtil;

import io.rong.util.CodeUtil;
import io.vavr.control.Try;

public class TestAnyThing {
	
	@Test
	public void test1(){
		String[] arr = new String[] {"weixincssiot", "1528703005", "V9Nv72Unxuw9bz7o","xikWhj95fcfbvRQgYJj0a0jcsupChYQEcCwDrYVdaxk/mGE+i5+RZgQ4bBLG07oTgUlSPPO+MSo1fszCzxUk5KdJlPfnxqCIuH1PabsJBmH2nZN6qq9fUyRuj4vFjajAbTtZDd6KK0lU4vplqY01+cZhiZdXjsuLTRkHEtavkdMJLhnQ+qKaYhLtbrc7L6BMHsKlyRYI3J87NoLWA3uXrCCFtHoIG/dOc6W5LmRjrCiwCKSgnVW3cy4xxVCfVn8ZRkKxKghItxgaAPS5eKCmw/ORSJzoPVagz3pjs5OPuB2yZkVtiP9aybl4Z2rgK6/7mZTQnp4zkBolRr+iBRmJvwnezb5mDw/qtk4SLe4YOKqO7QAINe6ETRN77ASNapI96N91lHvuUrUwBMpJs795SFpCzp6R9dXZDBjsoOgwkOE="};
		// 按字典排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		int attLength = arr.length;
		for (int i=0; i<attLength; i++) {
			content.append(arr[i]);
		}
		String sha1Str = CodeUtil.hexSHA1(content.toString());
		System.out.println(sha1Str);
	}
	
	@Test
	public void test2(){
		String moduleType = "6";
		switch (ModuleTypeEnum.getByValue(moduleType)) {
		case USER:
			System.out.println("用户");
			break;
		case TEAM:
			System.out.println("组织");
			break;
		case ROLE:
			System.out.println("角色");
			break;
		case DEPARTMENT:
			System.out.println("部门");
			break;
		case POSITION:
			System.out.println("职务");
			break;
		case PERSON:
			System.out.println("人员");
			break;
		case COMPANY:
			System.out.println("公司");
			break;
		}
	}
	
	@Test
	public void test3(){
		List<String> list = Arrays.asList("(","(","(",")");
		Stack<String> stack = new Stack<>();
		for (String m : list) {
			if ("(".equals(m)) {
				stack.push("(");
			} else if (")".equals(m)) {
				Try.of(()->stack.pop()).getOrElseThrow(()->new ResultException(-2,"关联条件中括号不闭合!",null,null,null));
			}
		}
		System.out.println(stack.size());
		OptionUtil.of(!stack.isEmpty()).getOrElseThrow(()->new ResultException(-2,"关联检验失败!",null,null,null));
	}
}
