package com.kynchen.dao.base;

import com.kynchen.domain.base.Standard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * @author kynchen
 *
 * @date 2018/7/26 16:38
 *
 * @version idea
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class StandardRepositoryTest {

    @Autowired
    private StandardRepository standardRepository;
    @Test
    public void findByName() {
        List<Standard> standards=standardRepository.findByName("10-20公斤");
        System.out.println(standards);
    }
    @Test
    public void queryName(){
        List<Standard> standards = standardRepository.queryName("10-20公斤");
        System.out.println(standards);
    }
    @Test
    public void updateStandard(){
        standardRepository.updateMinLength(1,15);
    }
}