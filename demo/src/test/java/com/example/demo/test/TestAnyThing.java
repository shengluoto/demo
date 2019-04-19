package com.example.demo.test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.example.entity.AccessDDToken;
import com.example.entity.BoardFormModel;
import com.example.enums.ModuleTypeEnum;
import com.example.exception.ResultException;
import com.example.util.ChkUtil;
import com.example.util.OptionUtil;
import com.example.util.SelectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
	
	@Test
	public void test4(){
		List<String> existFormIdAndElementIdList = new ArrayList<>();
		Map<String, String> existSelectFieldMap = new HashMap<>();
		existFormIdAndElementIdList.contains("dfa");
		existSelectFieldMap.get("af");
	}
	
	@Test
	public void test5(){
		System.out.println(copyName("哈哈-副本1"));
	}
	
	private String copyName(String oldName) {
		if(oldName.lastIndexOf("-副本")!=-1){
			String subString0 = oldName.substring(0, oldName.lastIndexOf("-副本"));
			String subString1 = oldName.substring(oldName.lastIndexOf("-副本"));
			if(!ChkUtil.isEmpty(subString1)){
				String num = subString1.substring(subString1.lastIndexOf("-副本")+3);
				//num+1
				if(!ChkUtil.isEmpty(num) && ChkUtil.isInteger(num)){
					int numInt = Integer.valueOf(num);
					numInt = numInt+1;
					oldName = subString0 +"-副本"+numInt;
				}else{
					oldName = subString0+"-副本1";
				}
			}else{
				oldName = subString0+"-副本1";
			}
		}else{
			oldName = oldName+"-副本1";
		}
		return oldName;
	}
	
	@Test
	public void test6(){
		List<String> elementIdList = new ArrayList<>();
		System.out.println(elementIdList.size());
	}
	
	@Test
	public void test7(){
		String alias = "alias2_id_";
		System.out.println(alias.substring(0,alias.length()-3));
	}
	
	@Test
	public void test8(){
		Set<String> stringSet = new HashSet<>();
		stringSet.add("");
		stringSet.add(null);
		stringSet.add("家具啊");
		System.out.println(stringSet.toString());
	}
	@Test
	public void test9(){
//		System.out.println(new BigDecimal("1.2").equals(new BigDecimal("1.20")));  //输出false
//		System.out.println(new BigDecimal("1.2").compareTo(new BigDecimal("1.20")) == 0); //输出true
//		        
//		System.out.println(new BigDecimal(1.2).equals(new BigDecimal("1.20"))); //输出是?
//		System.out.println(new BigDecimal(1.2).compareTo(new BigDecimal("1.20")) == 0); //输出是?
//		   
//		System.out.println(new BigDecimal(1.2).equals(new BigDecimal(1.20))); //输出是?
//		System.out.println(new BigDecimal(1.2).compareTo(new BigDecimal(1.20)) == 0);//
		System.out.println(new BigDecimal("0").compareTo(new BigDecimal("0")));//
	}
	@Test
	public void test10(){
		Map<String,List<String>> needToSortSaveOrderMap = new HashMap<>();
		needToSortSaveOrderMap.put("8a8aba7265b225a00165b22bdac100a3", Arrays.asList("8a8aba7265b225a00165b22bda7d0091"));
		needToSortSaveOrderMap.put("8a8aba7265b225a00165b22bdb0b00b8", Arrays.asList());
		needToSortSaveOrderMap.put("8a8aba7265b225a00165b22bda1e0082", Arrays.asList("8a8aba7265b225a00165b22bd95e0075"));
		needToSortSaveOrderMap.put("8a8aba7265b225a00165b22bdb5900ca", Arrays.asList("8a8aba7265b225a00165b22bdb0b00b8","8a8aba7265b225a00165b22bdac100a3"));
		needToSortSaveOrderMap.put("8a8aba7265b225a00165b22bd95e0075", Arrays.asList());
		needToSortSaveOrderMap.put("8a8aba7265b225a00165b22bda7d0091", Arrays.asList("8a8aba7265b225a00165b22bda1e0082"));
		List<String> sortList = ChkUtil.doSortSaveOrder(needToSortSaveOrderMap);
		System.out.println(sortList);
	}
	
	@Test
	public void test11(){
		Map<String,List<String>> needToSortSaveOrderMap = new HashMap<>();
		needToSortSaveOrderMap.put("福娃一", Arrays.asList());
		needToSortSaveOrderMap.put("玩个屁go二", Arrays.asList("福娃一"));
		needToSortSaveOrderMap.put("我是三", Arrays.asList("玩个屁go二"));
		needToSortSaveOrderMap.put("我是四", Arrays.asList("我是三"));
		needToSortSaveOrderMap.put("我是五", Arrays.asList());
		needToSortSaveOrderMap.put("我是六", Arrays.asList("我是五"));
		needToSortSaveOrderMap.put("我是七", Arrays.asList("我是六"));
		needToSortSaveOrderMap.put("我是十", Arrays.asList("我是七", "我是六"));
		List<String> sortList = ChkUtil.doSortSaveOrder(needToSortSaveOrderMap);
		System.out.println(sortList);
	}
	
	@Test
	public void test12(){
	    java.util.Date date = new java.util.Date();
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        System.out.println("Date = " + date);
        System.out.println("LocalDateTime = " + localDateTime);
        System.out.println(ChkUtil.toCron(localDateTime));
	}
	@Test
	public void test13(){
		System.out.println(AccessDDToken.class.getName());
		System.out.println(AccessDDToken.class.getCanonicalName());
		System.out.println(AccessDDToken.class.getSimpleName());
		System.out.println(AccessDDToken.class.getTypeName());
	}
	@Test
	public void test14(){
		
//		String str = "{\"startItemName\":\"\",\"majorStartItemId\":\"\",\"groupId\":\"\",\"icon\":\"departTaskReport\",\"dataRight\":\"0\",\"schemeId\":\"8a8a9ca4681baab001681ced60971728\",\"isSaveShare\":\"1\",\"formEnName\":\"SGRZ-copy2\",\"majorEndItemId\":\"\",\"isImportant\":\"1\",\"endItemId\":\"\",\"isCalendar\":\"1\",\"formId\":\"8a8a9ca4689758690168983319e800b9\",\"max\":39,\"titleItemName\":\"\",\"layout\":\"1\",\"isBaseData\":\"1\",\"endItemName\":\"\",\"iconColor\":\"#2D8CF0\",\"describe\":\"\",\"titleItemId\":\"\",\"items\":[{\"defaultType\":\"0\",\"describe\":\"流水号38\",\"elementCnName\":\"单据编号\",\"elementEnName\":\"_17item38\",\"formId\":\"8a8a9ca4689758690168983319e800b9\",\"id\":\"8a8a9ca4689758690168983ce9800100\",\"isAddShow\":\"0\",\"isAppVisible\":\"0\",\"isRequired\":\"0\",\"isUnique\":\"0\",\"itemId\":\"17_item_38\",\"itemSort\":\"0\",\"layout\":\"0\",\"serialNumberList\":[{\"fixedValue\":\"NJDB\",\"type\":\"0\",\"weight\":0},{\"coverSymbol\":\"0\",\"initValue\":\"1\",\"length\":\"4\",\"stepLength\":\"1\",\"type\":\"2\",\"weight\":1}],\"type\":\"17\",\"weight\":0},{\"bizExePoint\":\"1\",\"dataType\":\"1\",\"defaultType\":\"3\",\"describe\":\"\",\"elementCnName\":\"项目名称\",\"elementEnName\":\"_20item1\",\"formId\":\"8a8a9ca4689758690168983319e800b9\",\"id\":\"8a8a9ca468975869016898331a4700bf\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isHref\":\"0\",\"isMultiple\":\"1\",\"isRequired\":\"0\",\"isUnique\":\"1\",\"isVisible\":\"0\",\"itemId\":\"20_item_1\",\"itemSort\":\"1\",\"layout\":\"0\",\"linkFormElementId\":\"8a8a9ca4681baab001681cf79305174c\",\"linkFormElementName\":\"项目名称\",\"linkFormId\":\"8a8a9ca4681baab001681cf792f7174a\",\"linkFormName\":\"项目信息\",\"type\":\"20\",\"weight\":0},{\"defaultType\":\"0\",\"defaultValue\":\"\",\"describe\":\"\",\"elementCnName\":\"项目简称\",\"elementEnName\":\"_0item29\",\"formId\":\"8a8a9ca4689758690168983319e800b9\",\"format\":\"0\",\"id\":\"8a8a9ca468975869016898331a4700c0\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isScan\":\"1\",\"isTextReco\":\"1\",\"isUnique\":\"1\",\"isUpdateScan\":\"0\",\"isVisible\":\"0\",\"isVoiceReco\":\"1\",\"itemId\":\"0_item_29\",\"itemSort\":\"2\",\"layout\":\"0\",\"length\":\"26\",\"type\":\"0\",\"weight\":0},{\"childFormId\":\"8a8a9ca46897586901689838207500ee\",\"describe\":\"子表单33\",\"elementCnName\":\"桩明细\",\"elementEnName\":\"_12item33\",\"formId\":\"8a8a9ca4689758690168983319e800b9\",\"id\":\"8a8a9ca46897586901689838207100ed\",\"isRequired\":\"0\",\"isSubFormInner\":\"1\",\"itemId\":\"12_item_33\",\"itemSort\":\"3\",\"items\":[{\"bizExePoint\":\"1\",\"dataType\":\"1\",\"defaultType\":\"3\",\"describe\":\"\",\"elementCnName\":\"桩类型\",\"elementEnName\":\"_8subItemundefined\",\"formId\":\"8a8a9ca46897586901689838207500ee\",\"id\":\"8a8a9ca46897586901689838207600ef\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isEmptyOption\":\"0\",\"isHref\":\"0\",\"isRequired\":\"1\",\"isUnique\":\"1\",\"isVisible\":\"0\",\"itemId\":\"8_subItem_undefined\",\"itemSort\":\"0\",\"layout\":\"0\",\"linkFormElementId\":\"8a8a9ca4687013b90168748b90bf039c\",\"linkFormElementName\":\"名称\",\"linkFormId\":\"8a8a9ca4687013b90168748b90bb0397\",\"linkFormName\":\"材料\",\"type\":\"8\",\"weight\":0},{\"defaultType\":\"0\",\"defaultValue\":\"\",\"describe\":\"\",\"elementCnName\":\"总量\",\"elementEnName\":\"_2subItem34\",\"endRange\":\"\",\"formId\":\"8a8a9ca46897586901689838207500ee\",\"format\":\"0\",\"id\":\"8a8a9ca4689758690168983bea6200fb\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isRequired\":\"1\",\"isVisible\":\"0\",\"itemId\":\"2_subItem_34\",\"itemSort\":\"1\",\"layout\":\"0\",\"length\":\"18\",\"startRange\":\"\",\"type\":\"2\",\"weight\":0},{\"defaultType\":\"0\",\"defaultValue\":\"\",\"describe\":\"\",\"elementCnName\":\"累计完成量\",\"elementEnName\":\"_2subItem35\",\"endRange\":\"\",\"formId\":\"8a8a9ca46897586901689838207500ee\",\"format\":\"0\",\"id\":\"8a8a9ca4689758690168983bea6300fc\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isRequired\":\"1\",\"isVisible\":\"0\",\"itemId\":\"2_subItem_35\",\"itemSort\":\"2\",\"layout\":\"0\",\"length\":\"18\",\"startRange\":\"\",\"type\":\"2\",\"weight\":0},{\"defaultType\":\"1\",\"describe\":\"\",\"elementCnName\":\"剩余量\",\"elementEnName\":\"_2subItem36\",\"endRange\":\"\",\"expression\":[{\"elementCnName\":\"桩明细-总量\",\"itemId\":\"2_subItem_34\",\"subItemId\":\"12_item_33\",\"type\":\"2\",\"weight\":0},{\"elementCnName\":\"-\",\"expId\":\"00001\",\"type\":\"symbol\",\"weight\":0},{\"elementCnName\":\"桩明细-累计完成量\",\"itemId\":\"2_subItem_35\",\"subItemId\":\"12_item_33\",\"type\":\"2\",\"weight\":0}],\"formId\":\"8a8a9ca46897586901689838207500ee\",\"format\":\"0\",\"id\":\"8a8a9ca4689758690168983bea6300fd\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isRequired\":\"1\",\"isVisible\":\"0\",\"itemId\":\"2_subItem_36\",\"itemSort\":\"3\",\"layout\":\"0\",\"length\":\"18\",\"startRange\":\"\",\"type\":\"2\",\"weight\":0},{\"defaultType\":\"1\",\"describe\":\"数字37\",\"elementCnName\":\"形象进度（%）\",\"elementEnName\":\"_2subItem37\",\"endRange\":\"\",\"expression\":[{\"elementCnName\":\"桩明细-累计完成量\",\"itemId\":\"2_subItem_35\",\"subItemId\":\"12_item_33\",\"type\":\"2\",\"weight\":0},{\"elementCnName\":\"/\",\"expId\":\"00003\",\"type\":\"symbol\",\"weight\":0},{\"elementCnName\":\"桩明细-总量\",\"itemId\":\"2_subItem_34\",\"subItemId\":\"12_item_33\",\"type\":\"2\",\"weight\":0},{\"elementCnName\":\"*\",\"expId\":\"00002\",\"type\":\"symbol\",\"weight\":0},{\"elementCnName\":\"100\",\"type\":\"static\",\"weight\":0}],\"formId\":\"8a8a9ca46897586901689838207500ee\",\"format\":\"0\",\"id\":\"8a8a9ca4689758690168983bea6300fe\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isRequired\":\"1\",\"isVisible\":\"0\",\"itemId\":\"2_subItem_37\",\"itemSort\":\"4\",\"layout\":\"0\",\"length\":\"18\",\"startRange\":\"\",\"type\":\"2\",\"weight\":0}],\"type\":\"12\",\"weight\":0},{\"defaultType\":\"0\",\"defaultValue\":\"\",\"describe\":\"\",\"elementCnName\":\"总形象进度\",\"elementEnName\":\"_2item31\",\"endRange\":\"\",\"formId\":\"8a8a9ca4689758690168983319e800b9\",\"format\":\"0\",\"id\":\"8a8a9ca468975869016898331a4c00cd\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isRequired\":\"1\",\"isVisible\":\"0\",\"itemId\":\"2_item_31\",\"itemSort\":\"4\",\"layout\":\"0\",\"length\":\"18\",\"startRange\":\"\",\"type\":\"2\",\"weight\":0},{\"defaultType\":\"6\",\"describe\":\"\",\"elementCnName\":\"填报人\",\"elementEnName\":\"_0item14\",\"formId\":\"8a8a9ca4689758690168983319e800b9\",\"format\":\"0\",\"id\":\"8a8a9ca468975869016898331a4c00ce\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isRequired\":\"1\",\"isScan\":\"1\",\"isTextReco\":\"1\",\"isUnique\":\"1\",\"isUpdateScan\":\"0\",\"isVisible\":\"1\",\"isVoiceReco\":\"1\",\"itemId\":\"0_item_14\",\"itemSort\":\"5\",\"layout\":\"1\",\"length\":\"26\",\"type\":\"0\",\"userField\":\"0\",\"weight\":0},{\"defaultType\":\"0\",\"defaultValue\":\"0\",\"describe\":\"\",\"elementCnName\":\"填报日期\",\"elementEnName\":\"_3item15\",\"formId\":\"8a8a9ca4689758690168983319e800b9\",\"format\":\"0\",\"id\":\"8a8a9ca468975869016898331a4d00cf\",\"isAppVisible\":\"0\",\"isEdit\":\"0\",\"isRequired\":\"1\",\"isVisible\":\"0\",\"itemId\":\"3_item_15\",\"itemSort\":\"6\",\"layout\":\"1\",\"type\":\"3\",\"weight\":0}],\"showItemId\":\"\",\"formCnName\":\"新进度一览表\",\"startItemId\":\"\"}";
//		String str = "{itemId:\"0_item_1\",aaa:\"23_item_2\",enName:\"16_subItem_16\",enName:\"_16_subItem_16\",enName:\"8subItemundefined\"}";
		String str = "{crfvgerbvevrbebvcfwsfvwervfergvergvberbvgerbvgerbgvrevge}";
		String chged = str.replaceAll("_item_","item").replaceAll("_subItem_", "subItem");
		System.out.println(chged);
		
//		Pattern pattern = Pattern.compile("\"(?<!_)[0-9]{1,5}(item|subItem)[0-9]{1,5}");
		Pattern pattern = Pattern.compile("\"(?<!_)[0-9]{1,5}(item|subItem)");
        Matcher matcher = pattern.matcher(chged);
        // find向前迭代
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
        	System.out.println(matcher.group());
        	matcher.appendReplacement(sb, new StringBuffer(matcher.group()).insert(1, "_").toString());
        }
        matcher.appendTail(sb);
		System.out.println(sb);
	}
	@Test
	public void test16(){
		
		String str = "{\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"max\":24,\"groupId\":\"\",\"icon\":\"fa-file\",\"dataRight\":\"0\",\"schemeId\":\"8a8a9ca464a19ec80164a1a734aa00f4\",\"layout\":\"1\",\"isSaveShare\":\"1\",\"isBaseData\":\"0\",\"formEnName\":\"newForm\",\"describe\":\"\",\"items\":[{\"defaultType\":\"0\",\"describe\":\"#333333\",\"elementCnName\":\"请假申请单\",\"elementEnName\":\"title1\",\"format\":\"center\",\"isEdit\":\"1\",\"isRequired\":\"0\",\"itemId\":\"title_1\",\"itemSort\":\"0\",\"layout\":\"0\",\"length\":\"20\",\"type\":\"18\",\"weight\":0},{\"defaultType\":\"6\",\"describe\":\"单行文本2\",\"elementCnName\":\"单位名称\",\"elementEnName\":\"text2\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a2277738004c\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isScan\":\"1\",\"isTextReco\":\"1\",\"isUnique\":\"1\",\"isUpdateScan\":\"0\",\"isVisible\":\"0\",\"isVoiceReco\":\"1\",\"itemId\":\"text_2\",\"itemSort\":\"1\",\"layout\":\"0\",\"type\":\"0\",\"userField\":\"8\",\"weight\":0},{\"defaultType\":\"0\",\"describe\":\"流水号4\",\"elementCnName\":\"单据编号\",\"elementEnName\":\"serial4\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"id\":\"8a8a9ca464a21ed30164a227776a004f\",\"isAddShow\":\"0\",\"isRequired\":\"0\",\"itemId\":\"serial_4\",\"itemSort\":\"2\",\"layout\":\"1\",\"serialNumberList\":[{\"fixedValue\":\"QJSQ\",\"type\":\"0\",\"weight\":0},{\"coverSymbol\":\"0\",\"initValue\":\"1\",\"length\":\"4\",\"stepLength\":\"1\",\"type\":\"2\",\"weight\":1}],\"type\":\"17\",\"weight\":0},{\"defaultType\":\"0\",\"defaultValue\":\"0\",\"describe\":\"日期控件5\",\"elementCnName\":\"申请日期\",\"elementEnName\":\"datetime5\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a227778e0054\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isVisible\":\"0\",\"itemId\":\"datetime_5\",\"itemSort\":\"3\",\"layout\":\"1\",\"type\":\"3\",\"weight\":0},{\"defaultType\":\"6\",\"describe\":\"单行文本6\",\"elementCnName\":\"姓名\",\"elementEnName\":\"text6\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a22777af0057\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isScan\":\"1\",\"isTextReco\":\"1\",\"isUnique\":\"1\",\"isUpdateScan\":\"0\",\"isVisible\":\"0\",\"isVoiceReco\":\"1\",\"itemId\":\"text_6\",\"itemSort\":\"4\",\"layout\":\"1\",\"type\":\"0\",\"userField\":\"0\",\"weight\":0},{\"bizExePoint\":\"1\",\"dataType\":\"0\",\"defaultType\":\"2\",\"describe\":\"搜索下拉21\",\"elementCnName\":\"工号\",\"elementEnName\":\"searchselector21\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"id\":\"8a8a9ca464a21ed30164a22778760068\",\"isEdit\":\"0\",\"isEmptyOption\":\"0\",\"isHref\":\"0\",\"isRequired\":\"0\",\"isUnique\":\"1\",\"isVisible\":\"0\",\"itemId\":\"searchselector_21\",\"itemSort\":\"5\",\"layout\":\"1\",\"linkFormElementId\":\"8a8a9ca464a21ed30164a2277e850116\",\"linkFormElementName\":\"工号\",\"linkFormId\":\"8a8a9ca464a21ed30164a2277e5d010a\",\"linkFormName\":\"员工假期统计表\",\"relateShowList\":[{\"elementId\":\"8a8a9ca464a21ed30164a22778760068\",\"itemId\":\"searchselector_21\",\"relateElementId\":\"8a8a9ca464a21ed30164a22778020060\",\"relateElementItemId\":\"number_10\",\"relateFormElementId\":\"8a8a9ca464a21ed30164a2277f480122\",\"relateFormElementName\":\"本年可用年假\"},{\"elementId\":\"8a8a9ca464a21ed30164a22778760068\",\"itemId\":\"searchselector_21\",\"relateElementId\":\"8a8a9ca464a21ed30164a22778280063\",\"relateElementItemId\":\"text_11\",\"relateFormElementId\":\"8a8a9ca464a21ed30164a2277fc9012b\",\"relateFormElementName\":\"本年可用调休\"}],\"type\":\"8\",\"weight\":0},{\"defaultType\":\"6\",\"describe\":\"单行文本8\",\"elementCnName\":\"部门\",\"elementEnName\":\"text8\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a22777ca005a\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isScan\":\"1\",\"isTextReco\":\"1\",\"isUnique\":\"1\",\"isUpdateScan\":\"0\",\"isVisible\":\"0\",\"isVoiceReco\":\"1\",\"itemId\":\"text_8\",\"itemSort\":\"6\",\"layout\":\"1\",\"type\":\"0\",\"userField\":\"6\",\"weight\":0},{\"defaultType\":\"6\",\"describe\":\"单行文本9\",\"elementCnName\":\"职务\",\"elementEnName\":\"text9\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a22777e1005d\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isScan\":\"1\",\"isTextReco\":\"1\",\"isUnique\":\"1\",\"isUpdateScan\":\"0\",\"isVisible\":\"0\",\"isVoiceReco\":\"1\",\"itemId\":\"text_9\",\"itemSort\":\"7\",\"layout\":\"1\",\"type\":\"0\",\"userField\":\"7\",\"weight\":0},{\"alt\":\"0\",\"defaultType\":\"0\",\"describe\":\"数字10\",\"elementCnName\":\"本年可用年假（小时）\",\"elementEnName\":\"number10\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a22778020060\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isVisible\":\"0\",\"itemId\":\"number_10\",\"itemSort\":\"8\",\"layout\":\"1\",\"length\":\"18\",\"type\":\"2\",\"weight\":0},{\"alt\":\"0\",\"defaultType\":\"0\",\"describe\":\"单行文本11\",\"elementCnName\":\"本年可用调休（小时）\",\"elementEnName\":\"text11\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a22778280063\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isScan\":\"1\",\"isTextReco\":\"1\",\"isUnique\":\"1\",\"isUpdateScan\":\"0\",\"isVisible\":\"0\",\"isVoiceReco\":\"1\",\"itemId\":\"text_11\",\"itemSort\":\"9\",\"layout\":\"1\",\"type\":\"0\",\"weight\":0},{\"defaultType\":\"0\",\"describe\":\"搜索下拉22\",\"elementCnName\":\"请假类型\",\"elementEnName\":\"searchselector22\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"id\":\"8a8a9ca464a21ed30164a2277890006e\",\"isEdit\":\"0\",\"isEmptyOption\":\"0\",\"isRequired\":\"1\",\"isUnique\":\"1\",\"isVisible\":\"0\",\"itemId\":\"searchselector_22\",\"itemSort\":\"10\",\"layout\":\"0\",\"optionValue\":[{\"deliverValue\":\"0\",\"isChecked\":\"1\",\"showValue\":\"年假\",\"weight\":0},{\"deliverValue\":\"1\",\"isChecked\":\"1\",\"showValue\":\"调休\",\"weight\":1},{\"deliverValue\":\"2\",\"isChecked\":\"1\",\"showValue\":\"事假\",\"weight\":2},{\"deliverValue\":\"3\",\"isChecked\":\"1\",\"showValue\":\"病假\",\"weight\":3},{\"deliverValue\":\"4\",\"isChecked\":\"1\",\"showValue\":\"婚假\",\"weight\":4},{\"deliverValue\":\"5\",\"isChecked\":\"1\",\"showValue\":\"产假\",\"weight\":5},{\"deliverValue\":\"6\",\"isChecked\":\"1\",\"showValue\":\"产前假\",\"weight\":6},{\"deliverValue\":\"7\",\"isChecked\":\"1\",\"showValue\":\"看护假\",\"weight\":7},{\"deliverValue\":\"8\",\"isChecked\":\"1\",\"showValue\":\"哺乳假\",\"weight\":8},{\"deliverValue\":\"9\",\"isChecked\":\"1\",\"showValue\":\"节育假\",\"weight\":9},{\"deliverValue\":\"10\",\"isChecked\":\"1\",\"showValue\":\"工伤假\",\"weight\":10},{\"deliverValue\":\"11\",\"isChecked\":\"1\",\"showValue\":\"丧假\",\"weight\":11}],\"type\":\"8\",\"weight\":0},{\"childFormId\":\"8a8a9ca464a21ed30164a22778f2007c\",\"describe\":\"子表单12\",\"elementCnName\":\"请假详情\",\"elementEnName\":\"subform12\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"id\":\"8a8a9ca464a21ed30164a22778460066\",\"isRequired\":\"0\",\"itemId\":\"subform_12\",\"itemSort\":\"11\",\"items\":[{\"defaultType\":\"0\",\"defaultValue\":\"0\",\"describe\":\"日期控件13\",\"elementCnName\":\"开始时间\",\"elementEnName\":\"datetime13\",\"formId\":\"8a8a9ca464a21ed30164a22778f2007c\",\"format\":\"1\",\"id\":\"8a8a9ca464a21ed30164a2277900007d\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isVisible\":\"0\",\"itemId\":\"datetime_13\",\"itemSort\":\"0\",\"layout\":\"0\",\"type\":\"3\",\"weight\":0},{\"defaultType\":\"0\",\"defaultValue\":\"0\",\"elementCnName\":\"结束时间\",\"elementEnName\":\"datetime14\",\"formId\":\"8a8a9ca464a21ed30164a22778f2007c\",\"format\":\"1\",\"id\":\"8a8a9ca464a21ed30164a2277924007e\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isVisible\":\"0\",\"itemId\":\"datetime_14\",\"itemSort\":\"1\",\"layout\":\"0\",\"type\":\"3\",\"weight\":0},{\"defaultType\":\"0\",\"describe\":\"单行文本15\",\"elementCnName\":\"请假事由\",\"elementEnName\":\"text15\",\"formId\":\"8a8a9ca464a21ed30164a22778f2007c\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a2277938007f\",\"isEdit\":\"0\",\"isRecordUnique\":\"1\",\"isRequired\":\"0\",\"isScan\":\"1\",\"isTextReco\":\"1\",\"isUnique\":\"1\",\"isUpdateScan\":\"0\",\"isVisible\":\"0\",\"isVoiceReco\":\"1\",\"itemId\":\"text_15\",\"itemSort\":\"2\",\"layout\":\"0\",\"type\":\"0\",\"weight\":0},{\"defaultType\":\"0\",\"describe\":\"数字16\",\"elementCnName\":\"合计（小时）\",\"elementEnName\":\"number16\",\"formId\":\"8a8a9ca464a21ed30164a22778f2007c\",\"format\":\"0\",\"id\":\"8a8a9ca464a21ed30164a227794e0080\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isVisible\":\"0\",\"itemId\":\"number_16\",\"itemSort\":\"3\",\"layout\":\"0\",\"length\":\"18\",\"type\":\"2\",\"weight\":0}],\"type\":\"12\",\"weight\":0},{\"defaultType\":\"1\",\"describe\":\"数字23\",\"elementCnName\":\"请假合计（小时）\",\"elementEnName\":\"number23\",\"expression\":[{\"elementCnName\":\"SUM\",\"expId\":\"01004\",\"type\":\"exp\",\"weight\":0},{\"elementCnName\":\"(\",\"expId\":\"00004\",\"type\":\"quot\",\"weight\":1},{\"elementCnName\":\"请假详情-合计（小时）\",\"itemId\":\"number_16\",\"subItemId\":\"subform_12\",\"type\":\"2\",\"weight\":2},{\"elementCnName\":\")\",\"expId\":\"00005\",\"type\":\"quot\",\"weight\":3}],\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"format\":\"0\",\"id\":\"8a8a9ca464a6fcb70164a7279f09053d\",\"isEdit\":\"0\",\"isRequired\":\"0\",\"isVisible\":\"0\",\"itemId\":\"number_23\",\"itemSort\":\"12\",\"layout\":\"0\",\"length\":\"18\",\"type\":\"2\",\"weight\":0},{\"defaultType\":\"0\",\"describe\":\"附件18\",\"elementCnName\":\"请上传附件\",\"elementEnName\":\"fileupload18\",\"fileNum\":\"10\",\"formId\":\"8a8a9ca464a21ed30164a22777240043\",\"id\":\"8a8a9ca464a21ed30164a22778610067\",\"isEdit\":\"0\",\"isRequired\":\"1\",\"isVisible\":\"0\",\"itemId\":\"fileupload_18\",\"itemSort\":\"13\",\"layout\":\"0\",\"type\":\"10\",\"weight\":0},{\"defaultType\":\"0\",\"describe\":\"#333333\",\"elementCnName\":\"备注：病假、婚假、丧假、产假、陪产假需要上传证明附件\",\"elementEnName\":\"title19\",\"format\":\"left\",\"isEdit\":\"1\",\"isRequired\":\"1\",\"itemId\":\"title_19\",\"itemSort\":\"14\",\"layout\":\"0\",\"length\":\"14\",\"type\":\"18\",\"weight\":0}],\"formCnName\":\"请假申请单\"}";
//		String str = "{itemId:\"0_item_1\",aaa:\"23_item_2\",enName:\"_10item1\"}";
		String chged = str.replaceAll("_","");
		System.out.println(chged);
	}
	
	@Test
	public void test15(){
		//生成Pattern对象并且编译一个简单的正则表达式"Kelvin" 
		Pattern p = Pattern.compile("Kelvin"); 
		//用Pattern类的matcher()方法生成一个Matcher对象
		String str = "Kelvin Li and Kelvin Chan are both working in Kelvin Chen's KelvinSoftShop company";
		System.out.println("调用m.appendTail(sb)后sb的最终内容是:"+str); 
		Matcher m = p.matcher(str); 
		StringBuffer sb = new StringBuffer(); 
		int i=0; 
		//使用find()方法查找第一个匹配的对象 
		boolean result = m.find(); 
		//使用循环将句子里所有的kelvin找出并替换再将内容加到sb里 
		while(result) { 
			i++; 
			m.appendReplacement(sb, "_"+m.group()); 
			System.out.println("第"+i+"次匹配后sb的内容是："+sb); 
			//继续查找下一个匹配对象 
			result = m.find(); 
		} 
		//最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里； 
		m.appendTail(sb); 
		System.out.println("调用m.appendTail(sb)后sb的最终内容是:"+ sb.toString()); 
	} 
	
	@Test
	public void test17(){
		BigDecimal ZERO_STR_BIGDECIMAL = new BigDecimal("0");
		System.out.println(new BigDecimal("-7").compareTo(ZERO_STR_BIGDECIMAL));
		System.out.println(new BigDecimal("0").compareTo(ZERO_STR_BIGDECIMAL));
		System.out.println(new BigDecimal("1").compareTo(ZERO_STR_BIGDECIMAL));
		System.out.println(new BigDecimal(0).compareTo(ZERO_STR_BIGDECIMAL));
	} 
	
	@Test
	public void test18(){
		String json = "{\"lastUpdateTime\":\"2019-03-08 11:21\",\"name\":\"项目看板\"}";
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		BoardFormModel boardFormModel = null;
		boardFormModel = gson.fromJson(json,new TypeToken<BoardFormModel>(){}.getType());
		System.out.println(boardFormModel.toString());
	} 
	
	@Test
	public void test19(){
		List<String> list = Arrays.asList("1","2","3");
//		System.out.println(SelectUtils.getSelectString(list, "id", "not"));
		System.out.println(SelectUtils.getCollectionSelectString(list, "id", ""));
	} 
}
