package com.example.demo.test;

import java.util.Arrays;

import org.junit.Test;

import io.rong.util.CodeUtil;

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
}
