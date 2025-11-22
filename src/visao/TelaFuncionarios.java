/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visao;
import dao.FuncionarioDAO;
import modelo.Funcionario;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author melin
 * 
 * 
 * 
 * 
 * 1Elabore um programa em Java que apresente um frame semelhante ao que se segue:
 * 2Deverá criar um banco SQL Server chamado aulajava com as tabelas conforme esquema abaixo:
 * 3. Acrescente alguns registros, respeitando as chaves e o relacionamento; 
 * 4. Estabeleça a conexão utilizando o JDBC; 
 * 5. Ao clicar no botão Pesquisar, deverá ser efetuado o select (utilize like) para “preencher” um recordset e PreparedStatement para fazer o SQL. 6. Os botões Próximo e Anterior devem permitir a navegação pelo recordset até os limites inicial e final.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class TelaFuncionarios extends JFrame {
    private JTextField txtPesquisa, txtNome, txtSalario, txtCargo;
    private JButton btnPesquisar, btnAnterior, btnProximo;
    private List<Funcionario> funcionarios;
    private int indiceAtual;
    private FuncionarioDAO dao;
    
    public TelaFuncionarios() {
        dao = new FuncionarioDAO();
        initComponents();
        funcionarios = List.of();
        indiceAtual = -1;
    }
    
    private void initComponents() {
        setTitle("TRABALHO PRÁTICO 04");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("Nome:"));
        txtPesquisa = new JTextField(15);
        txtPesquisa.setText("marcelo");
        panelSuperior.add(txtPesquisa);
        
        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(e -> pesquisar());
        panelSuperior.add(btnPesquisar);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCentral.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        txtNome.setEditable(false);
        panelCentral.add(txtNome);
        
        panelCentral.add(new JLabel("Salário:"));
        txtSalario = new JTextField();
        txtSalario.setEditable(false);
        panelCentral.add(txtSalario);
        
        panelCentral.add(new JLabel("Cargo:"));
        txtCargo = new JTextField();
        txtCargo.setEditable(false);
        panelCentral.add(txtCargo);
        
        add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new FlowLayout());
        
        btnAnterior = new JButton("Anterior");
        btnAnterior.setEnabled(false);
        btnAnterior.addActionListener(e -> anterior());
        panelInferior.add(btnAnterior);
        
        btnProximo = new JButton("Próximo");
        btnProximo.setEnabled(false);
        btnProximo.addActionListener(e -> proximo());
        panelInferior.add(btnProximo);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    private void pesquisar() {
        String nome = txtPesquisa.getText().trim();
        
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome!");
            return;
        }
        
        System.out.println("🔍 Executando SELECT com LIKE no banco...");
        
        funcionarios = dao.pesquisarPorNome(nome);
        
        System.out.println("📊 Resultado do SELECT: " + funcionarios.size() + " funcionários");
        
        if (funcionarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum funcionário encontrado!");
            limparCampos();
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(false);
        } else {
            indiceAtual = 0;
            exibirFuncionarioAtual();
            atualizarBotoes();
        }
    }
    
    private void exibirFuncionarioAtual() {
        if (indiceAtual >= 0 && indiceAtual < funcionarios.size()) {
            Funcionario func = funcionarios.get(indiceAtual);
            txtNome.setText(func.getNome());
            txtSalario.setText(String.format("R$ %.2f", func.getSalario()));
            txtCargo.setText(func.getCargo());
            System.out.println("✅ Exibindo: " + func.getNome() + " (" + (indiceAtual + 1) + "/" + funcionarios.size() + ")");
        }
    }
    
    private void atualizarBotoes() {
        boolean podeAnterior = (indiceAtual > 0);
        boolean podeProximo = (indiceAtual < funcionarios.size() - 1);
        
        btnAnterior.setEnabled(podeAnterior);
        btnProximo.setEnabled(podeProximo);
        
        System.out.println("🔘 Botões - Anterior: " + podeAnterior + ", Próximo: " + podeProximo);
    }
    
    private void anterior() {
        if (indiceAtual > 0) {
            indiceAtual--;
            exibirFuncionarioAtual();
            atualizarBotoes();
        }
    }
    
    private void proximo() {
        if (indiceAtual < funcionarios.size() - 1) {
            indiceAtual++;
            exibirFuncionarioAtual();
            atualizarBotoes();
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtSalario.setText("");
        txtCargo.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaFuncionarios().setVisible(true);
        });
    }
}