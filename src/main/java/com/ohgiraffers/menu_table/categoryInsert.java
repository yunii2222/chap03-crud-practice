package com.ohgiraffers.menu_table;

import com.ohgiraffers.model.dto.CategoryDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class categoryInsert {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("카테고리 이름 입력 : ");
        String categoryName = sc.nextLine();
        System.out.print("카테고리 참조코드 입력 : ");
        int refCategoryCode = sc.nextInt();

        CategoryDTO newCategory = new CategoryDTO();

        newCategory.setCategoryName(categoryName);
        newCategory.setRefCategoryCode(refCategoryCode);

        Connection con = getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/category-query.xml"));
            String query = prop.getProperty("insertCategory");

            pstm = con.prepareStatement(query);
            pstm.setString(1,newCategory.getCategoryName());
            pstm.setInt(2,newCategory.getRefCategoryCode());

            result = pstm.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstm);
            close(con);
        }

        if (result > 0) {
            System.out.print("카테고리 등록 완료!");
        }else {
            System.out.print("카테고리 등록 실패ㅠㅠ");
        }
    }
}
