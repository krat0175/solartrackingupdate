package com.sysco.solar.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DBUtils {

    //store Resultset data in array list
    public static List<Map<String, Object>> getResultSetData(ResultSet rs) throws SQLException {
        List<Map<String, Object>> rowData = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            record.put("data_group", rs.getString(1));
            record.put("src_arrival_dttm",  rs.getString(2));
            record.put("opco_nbr",  rs.getString(3));
            record.put("load_status",  rs.getString(4));
            rowData.add(record);
        }
        return rowData;
    }
    //filter not availble data between two list maps
    public static List<Map<String, Object>> filterNotAvailableData(List<Map<String, Object>> prodData, List<Map<String, Object>> devData) {
        List<Map<String, Object>> notAvailableData = new ArrayList<>();
        if(devData.size() == 0){
            return prodData;
        }
        else
        {
        for (Map<String, Object> prodRecord : prodData) {
            boolean isAvailable = false;
            for (Map<String, Object> devRecord : devData) {
                if (prodRecord.get("opco_nbr").equals(devRecord.get("opco_nbr"))) {
                    isAvailable = true;
                    break;
                }
            }
            if (!isAvailable) {
                notAvailableData.add(prodRecord);
            }
        }}
        return notAvailableData;
    }
    //filter data by opcolist
    public static List<Map<String, Object>> filterDataByOpcoList(List<Map<String, Object>> data, String opcos) {
        List<String> opcoList = new ArrayList<>(Arrays.asList(opcos.split(",")));
        List<Map<String, Object>> filteredData = new ArrayList<>();
        for (Map<String, Object> record : data) {
            if (opcoList.contains(record.get("opco_nbr"))) {
                System.out.println("Opco "+record.get("opco_nbr"));
                filteredData.add(record);
            }
        }
        return filteredData;
    }

    //insert data from listmap to postgresql
    public static void insertDataToPostgresql(List<Map<String, Object>> data, PostgresqlHelper postgresqlHelper) throws SQLException {
        if(data.size() == 0){
            System.out.println("No data to insert");
            return;
        }
        for (Map<String, Object> record : data) {
            String query = "INSERT INTO eisgen.solar_load_tracking (data_group, src_arrival_dttm, opco_nbr, load_status,curr_ind) VALUES ('" + record.get("data_group") + "', '" + record.get("src_arrival_dttm") + "', '" + record.get("opco_nbr") + "', 'P','Y')";
          postgresqlHelper.executeInsert(query);
        }
    }
}