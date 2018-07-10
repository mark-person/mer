package com.ppx.cloud.demo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppx.cloud.common.jdbc.MyCriteria;
import com.ppx.cloud.common.jdbc.MyDaoSupport;
import com.ppx.cloud.common.page.Page;


/**
 * 如果方法需要被其它模块调用，则使用interface XXXService, 实时类应用的方法加上@Override
 * @author mark
 * @date 2018年7月2日
 */
@Service
public class TestServiceImpl extends MyDaoSupport {

    @Transactional
	public List<Test> list(Page page, Test bean) {
        
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(getJdbcTemplate());
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        List<Integer> inList = new ArrayList<Integer>();
        inList.add(34);
        inList.add(36);
        paramMap.put("testId", inList); 
        
        String prodSql = "select * from test where test_id in (:testId)";
        
        jdbc.queryForList(prodSql, paramMap);
        
        List<Object[]> paraList = new ArrayList<Object[]>();
        Object[] obj1 = {36};
        paraList.add(obj1);
        
        Object[] obj2 = {34};
        paraList.add(obj2);
        
        
        getJdbcTemplate().batchUpdate("update test set test_name = 'ee1' where test_id = ?", paraList);
	    
	    
	    
	    getJdbcTemplate().update("update test set test_name = 'ee1' where test_id = ?", 36);
	    
	    //getJdbcTemplate().execute("update test set test_name = 523 where test_id = 36");
	    
		// 必须存参数的SQL要显示写在sql,不能用MyCriteria拼接
		
		// 默认排序，后面加上需要从页面传过来的排序的，防止SQL注入
		page.addDefaultOrderName("test_id").addPermitOrderName("test_price").addUnique("test_id");
		
		// 分页排序查询，是不是允许左边加"%"?
		MyCriteria c = createCriteria("where").addAnd("test_name like ?", "%", bean.getTestName(), "%");
		
		// 分开两条sql，mysql在count还会执行子查询, 总数返回0将不执行下一句
		StringBuilder cSql = new StringBuilder("select count(*) from test").append(c);
		StringBuilder qSql = new StringBuilder("select test_id, test_name, test_price, test_date, created from test").append(c);
		
		List<Test> list = queryPage(Test.class, page, cSql, qSql, c.getParaList());
		return list;
	}
	
    public int insert(Test bean) {
        // 后面带不允许重名的字段（该字段需要建索引）
        return insertEntity(bean, "test_name");
    }
    
    public Test get(Integer id) {
        Test bean = getJdbcTemplate().queryForObject("select * from test where test_id = ?",
                BeanPropertyRowMapper.newInstance(Test.class), id);     
        return bean;
    }
    
    public int update(Test bean) {
        // 后面带不允许重名的字段（该字段需要建索引）
        return updateEntity(bean, "test_name");
    }
    
    public int delete(Integer id) {
        // 大部分需求是状态删除，用update语句
        return getJdbcTemplate().update("delete from test where test_id = ?", id);
    }
	
}
