package com.ohgiraffers.menu_table;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class categoryOneColumn {
    public static void main(String[] args) {
        /* 1. Connection 생성
         * DB와 연결하려면 먼저 Connection과 연결시켜주기 */
        Connection con = getConnection();
        /* PreparedStatement 선언  */
        PreparedStatement pstm = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회하려는 카테고리의 코드를 입력해주세요");
        System.out.print("=> ");
        String categoryCode = sc.nextLine();

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/category-query.xml"));
            String query = prop.getProperty("selectCategoryCode");

            pstm = con.prepareStatement(query);

            pstm.setString(1,categoryCode);

            rset = pstm.executeQuery();

            if (rset.next()) {
                System.out.printf(rset.getString("category_code") + " 번 코드 : " + rset.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidPropertiesFormatException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(pstm);
            close(rset);
            close(con);
        }
    }
}
