package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.Position;
import com.longwang.uhrm.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionDao {
    @Autowired
    PositionMapper positionMapper;

    //根据id获得position
    public Position getPosByID(int idPosition){
        return positionMapper.getPosById(idPosition);
    }

    //根据typename获得id
    public int getId(String typeName){
        return positionMapper.getId(typeName);
    }

    //根据idposition获得post表中的name
    public String getPostName(int idPosition){
        return positionMapper.getName(idPosition);
    }

}
