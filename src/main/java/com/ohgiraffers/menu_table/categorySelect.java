package com.ohgiraffers.menu_table;

import com.ohgiraffers.model.dto.CategoryDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class categorySelect {
    public static void main(String[] args) {
        /* 1. Connection 생성 */
        Connection con = getConnection();
        /* PreparedStatement 선언  */
        PreparedStatement pstm = null;
        ResultSet rset = null;
        /* 한 행의 정보를 담을 DTO */
        CategoryDTO selectCategory = null;
        /* 여러 DTO 타입의 객체를 담을 List */
        List<CategoryDTO> categoryList = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("검색하려는 단어에 포함되어 있는 카테고리 이름을 작성해주세요");
        System.out.print("=> ");
        String categoryName = sc.nextLine();

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/category-query.xml"));
            String query = prop.getProperty("selectCategoryName");

            pstm = con.prepareStatement(query);
            pstm.setString(1,categoryName);

            rset = pstm.executeQuery();

            categoryList = new ArrayList<>();

            while (rset.next()) {
                selectCategory = new CategoryDTO();

                selectCategory.setCategoryCode(rset.getInt("category_code"));
                selectCategory.setCategoryName(rset.getString("category_name"));
                selectCategory.setRefCategoryCode(rset.getInt("ref_category_code"));

                categoryList.add(selectCategory);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstm);
            close(con);
        }
        for (CategoryDTO categoryDTO : categoryList) {
            System.out.println(categoryDTO);
        }
    }
}
