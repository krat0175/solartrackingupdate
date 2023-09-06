package com.sysco.solar;

import com.sysco.solar.utils.DBUtils;
import com.sysco.solar.utils.PostgresqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class SolarTrackingUpdate {
    static PostgresqlHelper devhelper = new PostgresqlHelper("jdbc:postgresql://solar-np-dev-cluster.cluster-cnsfuhv24ujy.us-east-1.rds.amazonaws.com:5432/edwp", "Administrator", "RDSAdmin999");
    static PostgresqlHelper prodHelper = new PostgresqlHelper("jdbc:postgresql://solar-cluster.cluster-caan1opklydy.us-east-1.rds.amazonaws.com:5432/edwp", "solarwrite", "PG#Spring@2020");

    public static void main(String[] args) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(dateFormatter);
        String query = "Select * from eisgen.solar_load_tracking  where src_arrival_dttm BETWEEN  '" + formattedDate + " 00:00:00.000' and '" + formattedDate + " 23:59:59.999'";
        ResultSet prodDataForToday = prodHelper.executeQuery(query);
        List<Map<String, Object>> prodData = DBUtils.getResultSetData(prodDataForToday);
        ResultSet devDataForToday = devhelper.executeQuery(query);
        List<Map<String, Object>> devData = DBUtils.getResultSetData(devDataForToday);
        System.out.println("Prod Data Size: " + prodData.size());
        List<Map<String, Object>> prodNotAvailableDataFilterd = DBUtils.filterNotAvailableData(prodData, devData);
        List<Map<String, Object>> opcoFilter = DBUtils.filterDataByOpcoList(prodNotAvailableDataFilterd, args[0]);
       // DBUtils.insertDataToPostgresql(opcoFilter, devhelper);
        prodHelper.closeConnection();
    }
}
