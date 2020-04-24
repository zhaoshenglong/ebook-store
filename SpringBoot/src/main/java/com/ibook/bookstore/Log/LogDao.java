package com.ibook.bookstore.Log;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LogDao {
    private static Configuration conf;

    static{
        conf = HBaseConfiguration.create();
    }


    public static boolean isExist(String tableName){
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
            return admin.tableExists(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void createTable(String tableName,String... columnfamily){
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();

            if(isExist(tableName)){
                log.info("Table {} already exists", tableName);
            }else{
                HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
                for(String cf:columnfamily){
                    htd.addFamily(new HColumnDescriptor(cf));
                }
                admin.createTable(htd);
                log.info("Successfully created table {}", tableName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteTable(String tableName) {
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();

            if (!isExist(tableName)) {
                log.info("Delete failed. Table {} does not exists", tableName);
            } else {
                admin.disableTable(TableName.valueOf(tableName));
                admin.deleteTable(TableName.valueOf(tableName));
                log.info("Successfully deleted table {}", tableName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteAll(String tableName,String... rowkeys){
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table t = connection.getTable(TableName.valueOf(tableName));

            if (!isExist(tableName)) {
                log.info("Delete failed. Table {} does not exists", tableName);
            } else {
                List<Delete> list = new ArrayList<Delete>();
                for (String row:rowkeys){
                    Delete d = new Delete(Bytes.toBytes(row));
                    list.add(d);
                }
                t.delete(list);
                log.info("Successfully deleted all rows");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addRow(String tableName,String rowkey,String cf,String column,String value){
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table t = connection.getTable(TableName.valueOf(tableName));
            if (!isExist(tableName)) {
                log.info("Add row failed. Table {} does not exist", tableName);
            } else {
                Put p = new Put(Bytes.toBytes(rowkey));
                p.addColumn(Bytes.toBytes(cf),Bytes.toBytes(column),Bytes.toBytes(value));
                t.put(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void scanRows(String tableName) {
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table t = connection.getTable(TableName.valueOf(tableName));

            Scan s = new Scan();
            ResultScanner rs = t.getScanner(s);

            for (Result r:rs){
                Cell[] cells = r.rawCells();
                for (Cell c : cells){
                    System.out.print("行键为："+Bytes.toString(CellUtil.cloneRow(c))+"  ");
                    System.out.print("列族为："+Bytes.toString(CellUtil.cloneFamily(c))+"  ");
                    System.out.print("列名为："+Bytes.toString(CellUtil.cloneQualifier(c))+"  ");
                    System.out.println("值为："+Bytes.toString(CellUtil.cloneValue(c)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int countRows(String tableName){
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Table t = connection.getTable(TableName.valueOf(tableName));
            int res = 0;

            Scan s = new Scan();
            ResultScanner rs = t.getScanner(s);

            for (Result ignored :rs){
                res += 1;
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
