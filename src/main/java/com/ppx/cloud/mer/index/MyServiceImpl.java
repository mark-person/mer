package com.ppx.cloud.mer.index;

import org.springframework.stereotype.Service;

import com.ppx.cloud.common.jdbc.MyDaoSupport;

@Service
public class MyServiceImpl extends MyDaoSupport {
	
	public int test() {
	    return 1;
	}

}
