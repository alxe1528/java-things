package t.javax.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import test.bean.User;

public class TestValid {
	
	@Test
	public  void testA(){
		 /* 创建效验工厂 */  
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();   
		Validator validator = validatorFactory.getValidator();   
		
		/* 信息封装 */  
		User user= new User();
		user.setId(232);
		
		/* 将类型装载效验 */  
		Set<ConstraintViolation<User>> set = validator.validate(user);   
		for (ConstraintViolation<User> constraintViolation : set)   
		System.out.println("错误：" + constraintViolation.getMessage());   

	}
	
	public static void main(String[] args) {
		/*
		Validator validator = Validation.byProvider(HibernateValidator.class )
		        .configure()
		        .failFast( true )
		        .buildValidatorFactory()
		        .getValidator();
		
		User user= new User();
		user.setId(232);
		
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		System.out.println(constraintViolations);*/
	}
}
