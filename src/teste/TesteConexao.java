/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste;
import java.sql.*;
/**
 *
 * @author melin
 */
public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("🎯 INICIANDO TESTE DE CONEXÃO...");
        
        try {
            // Teste direto
            String url = "jdbc:sqlite:database/aulajava.db";
            System.out.println("🔗 Tentando conectar: " + url);
            
            Connection conn = DriverManager.getConnection(url);
            System.out.println("✅ CONEXÃO BEM SUCEDIDA!");
            
            // Verificar tabelas
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "tbfuncs", null);
            
            if (tables.next()) {
                System.out.println("✅ Tabela tbfuncs ENCONTRADA!");
            } else {
                System.out.println("❌ Tabela tbfuncs NÃO encontrada!");
            }
            
            // Verificar dados
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM tbfuncs");
            if (rs.next()) {
                System.out.println("📊 Total de funcionários: " + rs.getInt(1));
            }
            
            // Listar funcionários
            rs = stmt.executeQuery("SELECT * FROM tbfuncs");
            while (rs.next()) {
                System.out.println("👤 " + rs.getString("nome_func") + " - R$ " + rs.getDouble("sal_func"));
            }
            
            conn.close();
            
        } catch (SQLException e) {
            System.out.println("❌ ERRO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}