package com.ohgiraffers.menu_table;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class categoryDelete {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 카테고리 코드번호 : ");
        int categoryCode = sc.nextInt();

        Connection con = getConnection();
        PreparedStatement pstm = null;
        int result = 0;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/category-query.xml"));
            String query = prop.getProperty("deleteCategory");

            pstm = con.prepareStatement(query);
            pstm.setInt(1, categoryCode);

            result = pstm.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstm);
            close(con);
        }
        if (result > 0) {
            System.out.print("메뉴 삭제 성공!");
        }else {
            System.out.print("메뉴 삭제 실패ㅠㅠ");
        }
    }
}
