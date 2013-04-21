package com.eventpool.common;

import javax.annotation.Resource;

import org.junit.Test;

import com.eventpool.common.repositories.OrderRepository;

public class EventTest extends BaseTest{
	
	@Resource
	OrderRepository orderRepository;
	 
    @Test
    public void addEvent() {
    System.out.println("Testing..");
//    orderRepository.findAll();
    }
    

}
