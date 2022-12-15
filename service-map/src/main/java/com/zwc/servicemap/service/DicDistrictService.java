package com.zwc.servicemap.service;

import com.zwc.internalcommon.constant.AmapConfigConstants;
import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.dto.DicDistrict;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.servicemap.mapper.DicDistrictMapper;
import com.zwc.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    @Value("${amap.key}")
    private String amapKey;

    public ResponseResult initDicDistrict(String keywords){
        //请求地图
        String dicDistrictResult = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrictResult);
        //解析结果
        JSONObject dicDistrictJsonObject = JSONObject.fromObject(dicDistrictResult);
        int status = dicDistrictJsonObject.getInt(AmapConfigConstants.STATUS);
        if (status!=1){
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(),CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }
        JSONArray countryJSONArray = dicDistrictJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
        for (int i = 0;i < countryJSONArray.size();i++){
            //国家
            JSONObject countryJsonObject = countryJSONArray.getJSONObject(i);
            String countryAddressCode   = countryJsonObject.getString(AmapConfigConstants.ADCODE);
            String countryAddressName = countryJsonObject.getString(AmapConfigConstants.NAME);
            String countryParentAddressCode = "0";
            String countryLevel = countryJsonObject.getString(AmapConfigConstants.LEVEL);

            insertDicDistrict(countryAddressCode ,countryAddressName,countryLevel,countryParentAddressCode);

            //省
            JSONArray proviceJsonArray = countryJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
            for (int p = 0; p <proviceJsonArray.size() ; p++) {
                JSONObject proviceJsonObject = proviceJsonArray.getJSONObject(p);
                String proviceAddressCode = proviceJsonObject.getString(AmapConfigConstants.ADCODE);
                String proviceAddressName = proviceJsonObject.getString(AmapConfigConstants.NAME);
                String proviceParentAddressCode = countryAddressCode ;
                String provicelevel = proviceJsonObject.getString(AmapConfigConstants.LEVEL);

                insertDicDistrict(proviceAddressCode,proviceAddressName,provicelevel,proviceParentAddressCode);
                //市区
                JSONArray cityJsonArray = proviceJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                for (int c = 0; c < cityJsonArray.size(); c++) {
                    JSONObject cityJsonObject = cityJsonArray.getJSONObject(c);
                    String cityAddressCode = cityJsonObject.getString(AmapConfigConstants.ADCODE);
                    String cityAddressName = cityJsonObject.getString(AmapConfigConstants.NAME);
                    String cityParentAddressCode = proviceAddressCode;
                    String citylevel = cityJsonObject.getString(AmapConfigConstants.LEVEL);



                    insertDicDistrict(cityAddressCode,cityAddressName,citylevel,cityParentAddressCode);

                    //地区
                    JSONArray disJsonArray = cityJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                    for (int d = 0; d < disJsonArray.size(); d++) {
                        JSONObject disJsonObject = disJsonArray.getJSONObject(d);
                        String disAddressCode = disJsonObject.getString(AmapConfigConstants.ADCODE);
                        String disAddressName = disJsonObject.getString(AmapConfigConstants.NAME);
                        String disParentAddressCode = cityAddressCode;
                        String dislevel = disJsonObject.getString(AmapConfigConstants.LEVEL);

                        if (dislevel.equals(AmapConfigConstants.STREET)){
                            continue;
                        }

                        insertDicDistrict(disAddressCode,disAddressName,dislevel,disParentAddressCode);
                    }
                }
            }
        }


        //插入数据库
        return ResponseResult.success();
    }
    public void insertDicDistrict(String addressCode, String addressName,String level ,String parentAddressCode){
        // 数据库对象
        DicDistrict district = new DicDistrict();
        district.setAddressCode(addressCode);
        district.setAddressName(addressName);
        int levelInt = generateLevel(level);
        district.setLevel(levelInt);

        district.setParentAddressCode(parentAddressCode);

        // 插入数据库
        dicDistrictMapper.insert(district);
    }

    public int generateLevel(String level){
        int levelInt = 0;
        if (level.trim().equals("country")){
            levelInt = 0;
        }else if(level.trim().equals("province")){
            levelInt = 1;
        }else if(level.trim().equals("city")){
            levelInt = 2;
        }else if(level.trim().equals("district")){
            levelInt = 3;
        }
        return levelInt;
    }
}
