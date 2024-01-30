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

public class categoryUpdate {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("변경할 카테고리 이름 입력 : ");
        String categoryName = sc.nextLine();
        System.out.print("변경할 카테고리 코드 입력 : ");
        int categoryCode = sc.nextInt();
        sc.nextLine();

        CategoryDTO changeCategory = new CategoryDTO();

        changeCategory.setCategoryName(categoryName);
        changeCategory.setCategoryCode(categoryCode);

        Connection con = getConnection();
        PreparedStatement pstm = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/category-query.xml"));
            String query = prop.getProperty("updateCategory");

            pstm = con.prepareStatement(query);
            pstm.setString(1,changeCategory.getCategoryName());
            pstm.setInt(2,changeCategory.getCategoryCode());

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
            System.out.print("카테고리 변경 완료!");
        }else {
            System.out.print("카테고리 변경 실패ㅠㅠ");
        }
    }
}
