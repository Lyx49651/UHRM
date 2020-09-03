package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.CollectTable;
import com.longwang.uhrm.mapper.CollectTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectTableDao {
    @Autowired
    CollectTableMapper collectTableMapper;

    public List<CollectTable> findAll(){ return collectTableMapper.findAll(); }
    public List<CollectTable> findAllPassed(){ return collectTableMapper.findAllPassed();}
    public List<CollectTable> findAllSaved(){ return collectTableMapper.findAllSaved();}

    public boolean changeStatusById(String status, int id){ return collectTableMapper.changeStatusById(status, id) == 1; }
    public boolean insertCollectTable(CollectTable collectTable) {return collectTableMapper.insertCollectTable(collectTable) == 1;}

}
