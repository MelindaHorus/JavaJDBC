/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;

/**
 *
 * @author melin
 */

import java.sql.*;
import java.io.File;

public class ConexaoSQLite {
    private static final String URL = "jdbc:sqlite:database/aulajava.db";
    
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite não encontrado: " + e.getMessage());
        }
    }
    
    public static Connection getConnection() {
        try {
            Connection conexao = DriverManager.getConnection(URL);
            System.out.println("✅ Conexão com SQLite estabelecida!");
            return conexao;
            
        } catch (SQLException e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
            return null;
        }
    }
}