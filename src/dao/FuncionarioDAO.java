/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexao.ConexaoSQLite;
import modelo.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author melin
 */



public class FuncionarioDAO {
    
    public List<Funcionario> pesquisarPorNome(String nome) {
        List<Funcionario> funcionarios = new ArrayList<>();
        
        String sql = "SELECT f.cod_func, f.nome_func, f.sal_func, c.ds_cargo " +
                    "FROM tbfuncs f " +
                    "INNER JOIN tbcargos c ON f.cod_cargo = c.cd_cargo " +
                    "WHERE f.nome_func LIKE ?";
        
        System.out.println("🎯 Executando SQL: " + sql);
        System.out.println("🎯 Parâmetro LIKE: " + nome);
        
        try (Connection conn = ConexaoSQLite.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nome + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Funcionario func = new Funcionario();
                func.setCodigo(rs.getInt("cod_func"));
                func.setNome(rs.getString("nome_func"));
                func.setSalario(rs.getDouble("sal_func"));
                func.setCargo(rs.getString("ds_cargo"));
                funcionarios.add(func);
                
                System.out.println("✅ Encontrado: " + func.getNome() + " - " + func.getCargo());
            }
            
            System.out.println("📊 Total do SELECT: " + funcionarios.size() + " registros");
            
        } catch (SQLException e) {
            System.out.println("❌ Erro no SELECT: " + e.getMessage());
        }
        
        return funcionarios;
    }
}