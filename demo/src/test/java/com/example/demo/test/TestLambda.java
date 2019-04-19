package com.example.demo.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.data.GetAlias;

public class TestLambda {

    public static void main(String[] args) {
//        List<Person> persons = Arrays.asList(
//                new Person("童", "老大"),
//                new Person("童1", "老二"),
//                new Person("张", "成"),
//                new Person("唐", "佳佳"),
//                new Person("周2", "周"),
//                new Person("周3", "周"),
//                new Person("周4", "周"),
//                new Person("周5", "周"),
//                new Person("周6", "周"),
//                new Person("周7", "周"),
//                new Person("周8", "周")
//        );
    	ArrayList<Person> persons = new ArrayList<>(10);
        persons.add(new Person("童", "老大"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        persons.add(new Person("童1", "老二"));
        System.out.println(persons.size());
        System.out.println(getArrayListCapacity(persons));
//      List<Person> persons = new ArrayList<>();
//        for(int i=0; i<5; i++) {
//        	Thread t = new Thread(new Runnable(){
//				@Override
//				public void run() {
//					GetAlias alias = new GetAlias();
//					List<Employee> employees = persons.stream()
//							.map(p ->new Employee(p.getName(), p.getLastName(),alias.getNextAlias(), 1000))
//							.collect(Collectors.toList());
//					employees.forEach(System.out::println);
//				}
//        	});
//        	t.start();
//        }
		        
//        List<Employee> employees2 = persons.stream()
//                .map(p ->new Employee(p.getName(), p.getLastName(),alias.getNextAlias(), 1000))
//                .collect(Collectors.toList());
//        employees2.forEach(System.out::println);
//        System.out.println("=========================");
//       String[] array =  StringUtils.split(String.valueOf("haha"), ";");
//       for (String string : array) {
//		System.out.println(string);
//       }
        GetAlias alias = new GetAlias("haha");
        long start1 = System.nanoTime();
//        List<Employee> emplList2 = persons.stream().map(p ->new Employee(p.getName(), p.getLastName(),alias.getNextAlias(), 1000)).collect(Collectors.toList());
        persons.stream().map(Person::getName).collect(Collectors.joining(""));
        System.out.println("lambda时间:"+(System.nanoTime()-start1)/1000000);
        Employee empl = null;
        long start2 = System.nanoTime();
        List<Employee> emplList = new ArrayList<>(persons.size());
        StringBuilder sb = new StringBuilder();
        for (Person p : persons) {
        	empl = new Employee(p.getName(), p.getLastName(),alias.getNextAlias(), 1000);
        	emplList.add(new Employee(p.getName(), p.getLastName(),alias.getNextAlias(), 1000));
        	emplList.add(empl);
        	sb.append(p.getName());
		}
        sb.toString();
        System.out.println("for循环时间:"+(System.nanoTime()-start2)/1000000);
        emplList.forEach(System.out::println);
        
//        stdByClass.forEach((k,v)->System.out.println("Key:"+k+"  "+ ",	Value:"+((List<Person>)v).stream().map(Person::toString).collect(Collectors.joining(","))));
//        long start3 = System.nanoTime();
//        Map<String,List<Person>> stdByClass = persons.stream().collect(Collectors.groupingBy(Person::getName));
//        System.out.println("mapsrstream时间:"+(System.nanoTime()-start3)/1000);
//        long start4 = System.nanoTime();
//        Map<String,Person> map = new HashMap<>();
//        for (Person p : persons) {
//        	map.put(p.getName(), p);
//		}
//        System.out.println("mapfor时间:"+(System.nanoTime()-start4)/1000);
    }
    
    public static int getArrayListCapacity(ArrayList<?> arrayList) {
        Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            Field field = arrayListClass.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] objects = (Object[])field.get(arrayList);
           return objects.length;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }
}

/**
 * 获取下个别名
 * @ClassName: GetAlias
 * @Description: 
 * @author 
 * 2018年6月28日  tck 创建
 */
//class GetAlias {
//	public static AtomicInteger start = new AtomicInteger(0);;
//	
//	public GetAlias() {
//	}
//	
//	public String getNextAlias(){
//		int next = getStart().incrementAndGet();
//		StringBuilder sb = new StringBuilder("alias");
//		sb.append(next).append("_");
//		return sb.toString();
//	}
//
//	public AtomicInteger getStart() {
//		return start;
//	}
//}

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

	@Override
	public String toString() {
		return "Person [" +name + lastName  +"]";
	}
}

class Employee  {

    private double salary;
    private String name;
    private String lastName;
    private String alias;

    public Employee(String name, String lastName, String alias, double salary) {
        this.name=name;
        this.lastName=lastName;
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
		return "Employee [salary=" + salary + ", name=" + name + ", lastName=" + lastName + ", alias=" + alias + "]";
	}
}

class Student {
    private String name;
    private int age;
    private String className;
    public Student(String name,String className,int age){
        this.name=name;
        this.age=age;
        this.className = className;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getClassName() {
        return className;
    }
} 
