package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.service.WechatTokenService;
import com.example.task.MessageRemindJobTask;
import com.example.util.QuartzFactoryUtil;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

	//@Test
	public void contextLoads() {
	}
	@Autowired
    private WechatTokenService wechatTokenService;

    private static class GetAlias {
		public static AtomicInteger start = new AtomicInteger(0);;
		
		public GetAlias() {
		}
		
		public String getNextAlias(){
			int next = getStart().incrementAndGet();
			StringBuilder sb = new StringBuilder("alias");
			sb.append(next).append("_");
			return sb.toString();
		}

		public AtomicInteger getStart() {
			return start;
		}
	}
    //@Test
    public void test3() {
        List<Person> persons = Arrays.asList(
                new Person("e1", "l1"),
                new Person("e2", "l1"),
                new Person("e3", "l2"),
                new Person("e4", "l2")
        );
        for(int i=0; i<5; i++) {
        	Thread t = new Thread(()->{
					GetAlias alias = new GetAlias();
					List<Employee> employees = persons.stream()
							.map(p ->new Employee(p.getName(), p.getLastName(),alias.getNextAlias(), 1000))
							.collect(Collectors.toList());
					employees.forEach(System.out::println);
				});
        	t.start();
        }
    }
    
    @Test
    public void test6() {
    	for(int i=0; i<200; i++) {
    		Thread t = new Thread(()->{
    			log.info("{}",wechatTokenService.getAccessToken(null, null, null));
			});
    		t.start();
    	}
    	
    }
    

class Person {

    private String name;
    private String lastName;

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

	class Employee {

		private double salary;
		private String name;
		private String lastName;
		private String alias;

		public Employee(String name, String lastName, String alias, double salary) {
			this.name = name;
			this.lastName = lastName;
			this.salary = salary;
			this.alias = alias;
		}

		public double getSalary() {
			return salary;
		}

		public void setSalary(double salary) {
			this.salary = salary;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public Employee() {
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		@Override
		public String toString() {
			return "Employee [salary=" + salary + ", name=" + name + ", lastName=" + lastName + ", alias=" + alias
					+ "]";
		}
	}
	
//	@Test
    public void test4() {
		String sendOnceOfCron = "35 59 21 13 * ?";
		QuartzFactoryUtil.getInstance().addJobByCron(
				"my_injtest", "my_injtest", MessageRemindJobTask.class, sendOnceOfCron, null);
		
	}
	
}
