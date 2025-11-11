/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author melin
 */
package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {
    
    private JTextField display;
    private double num1, num2, resultado;
    private String operador;
    private boolean novoNumero;
    
    public Calculadora() {
        criarCalculadora();
    }
    
    private void criarCalculadora() {
        setTitle("Calculadora");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(new Color(240, 240, 240));
        
        criarDisplay();
        criarTeclado();
        
        num1 = num2 = resultado = 0;
        operador = "";
        novoNumero = true;
    }
    
    private void criarDisplay() {
        display = new JTextField();
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setForeground(new Color(50, 50, 50));
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        display.setText("0");
        
        add(display, BorderLayout.NORTH);
    }
    
    private void criarTeclado() {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 4, 8, 8));
        painelBotoes.setBackground(new Color(240, 240, 240));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[][] botoes = {
            {"C", "", "", "÷"},
            {"7", "8", "9", "×"},
            {"4", "5", "6", "−"},
            {"1", "2", "3", "+"},
            {"0", "", ".", "="}
        };
        
        for (String[] linha : botoes) {
            for (String texto : linha) {
                if (!texto.isEmpty()) {
                    JButton botao = criarBotao(texto);
                    painelBotoes.add(botao);
                } else {
                    painelBotoes.add(new JLabel(""));
                }
            }
        }
        
        add(painelBotoes, BorderLayout.CENTER);
    }
    
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 20));
        botao.setFocusPainted(false);
        botao.setBorderPainted(true);
        botao.setContentAreaFilled(true);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.addActionListener(this);
        
        Color corFundo;
        Color corTexto;
        
        if (texto.matches("[0-9]") || texto.equals(".")) {
            corFundo = Color.WHITE;
            corTexto = new Color(50, 50, 50);
        } else if (texto.equals("C")) {
            corFundo = new Color(255, 100, 100);
            corTexto = Color.WHITE;
        } else if (texto.equals("=")) {
            corFundo = new Color(70, 130, 255);
            corTexto = Color.WHITE;
        } else {
            corFundo = new Color(240, 240, 240);
            corTexto = new Color(70, 130, 255);
        }
        
        botao.setBackground(corFundo);
        botao.setForeground(corTexto);
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));
        
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (texto.matches("[0-9]") || texto.equals(".")) {
                    botao.setBackground(new Color(245, 245, 245));
                } else if (!texto.equals("C") && !texto.equals("=")) {
                    botao.setBackground(new Color(230, 230, 230));
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (texto.matches("[0-9]") || texto.equals(".")) {
                    botao.setBackground(Color.WHITE);
                } else if (!texto.equals("C") && !texto.equals("=")) {
                    botao.setBackground(new Color(240, 240, 240));
                }
            }
        });
        
        return botao;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        try {
            if (comando.matches("[0-9]")) {
                if (novoNumero || display.getText().equals("0")) {
                    display.setText(comando);
                    novoNumero = false;
                } else {
                    display.setText(display.getText() + comando);
                }
            } else if (comando.equals(".")) {
                if (novoNumero) {
                    display.setText("0.");
                    novoNumero = false;
                } else if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
            } else if (comando.equals("C")) {
                display.setText("0");
                num1 = num2 = resultado = 0;
                operador = "";
                novoNumero = true;
            } else if (comando.equals("+") || comando.equals("−") || comando.equals("×") || comando.equals("÷")) {
                if (!operador.isEmpty() && !novoNumero) {
                    calcularResultado();
                }
                num1 = Double.parseDouble(display.getText());
                operador = comando;
                novoNumero = true;
            } else if (comando.equals("=")) {
                if (!operador.isEmpty()) {
                    calcularResultado();
                    operador = "";
                }
            }
        } catch (NumberFormatException ex) {
            display.setText("Erro");
            novoNumero = true;
        } catch (ArithmeticException ex) {
            display.setText("Erro: " + ex.getMessage());
            novoNumero = true;
        } catch (Exception ex) {
            display.setText("Erro");
            novoNumero = true;
        } finally {
        }
    }
    
    private void calcularResultado() {
        num2 = Double.parseDouble(display.getText());
        
        switch (operador) {
            case "+":
                resultado = num1 + num2;
                break;
            case "−":
                resultado = num1 - num2;
                break;
            case "×":
                resultado = num1 * num2;
                break;
            case "÷":
                if (num2 == 0) {
                    throw new ArithmeticException("Divisão por zero");
                }
                resultado = num1 / num2;
                break;
        }
        
        if (Double.isInfinite(resultado) || Double.isNaN(resultado)) {
            display.setText("Erro");
        } else if (resultado == (int) resultado) {
            display.setText(String.valueOf((int) resultado));
        } else {
            display.setText(String.valueOf(resultado));
        }
        
        num1 = resultado;
        novoNumero = true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Calculadora().setVisible(true);
            }
        });
    }
}