package main;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;



public class Loginpage{

    private JFrame frame;
    
    public static void main(String[] args) {
     
        new Loginpage().showPage();
       
    }

  
    public Loginpage() {
        frame = new JFrame("Login Page");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(230, 240, 250), getWidth(), getHeight(), new Color(30, 144, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setPreferredSize(new Dimension(600, 800));
        backgroundPanel.setLayout(new GridBagLayout());
        frame.setContentPane(backgroundPanel);

        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(400, 500));
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10, true));
        
        backgroundPanel.add(formPanel);

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBounds(100, 20, 200, 40);
        formPanel.add(titleLabel);

        JLabel userLabel = new JLabel("Mobile Number:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setBounds(50, 75, 300, 20);
        formPanel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(50, 100, 300, 40);
        userField.setFont(new Font("Arial", Font.PLAIN, 16));
        userField.setForeground(Color.GRAY);
        userField.setOpaque(false);
        userField.setBorder(BorderFactory.createEmptyBorder());
        userField.setText("Enter mobile number *");
        userField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals("Enter mobile number *")) {
                    userField.setText("");
                    userField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (userField.getText().trim().isEmpty()) {
                    userField.setText("Enter mobile number *");
                    userField.setForeground(Color.GRAY);
                }
            }
        });
        formPanel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passLabel.setBounds(50, 155, 300, 20);
        formPanel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(50, 180, 300, 40);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.setForeground(Color.GRAY);
        passField.setOpaque(false);
        passField.setBorder(BorderFactory.createEmptyBorder());
        passField.setText(". . . . . .");
        passField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(passField.getPassword()).equals(". . . . . .")) {
                    passField.setText("");
                    passField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (new String(passField.getPassword()).trim().isEmpty()) {
                    passField.setText(". . . . . .");
                    passField.setForeground(Color.GRAY);
                }
            }
        });
        formPanel.add(passField);

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(50, 220, 300, 20);
        messageLabel.setForeground(Color.RED);
        formPanel.add(messageLabel);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(100, 250, 200, 40);
        loginButton.setBackground(new Color(0, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            String mobileNumber = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (authenticateUser(mobileNumber, password)) {
                System.out.println("✅ Login Successful! Redirecting to Preview Page...");
                
                // ✅ Pass mobileNumber to PreviewPage
                
                PreviewPage preview = new PreviewPage(mobileNumber);
                preview.showPage();
            
                frame.dispose();  // Close login page
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
            
        
        });
        

      
        
        

        JButton registerButton = new JButton("REGISTER");
        registerButton.setBounds(100, 310, 200, 40);
        registerButton.setBackground(new Color(0, 204, 102));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(registerButton);


        registerButton.addActionListener(e -> {
            try {
                RegistrationPage regPage = new RegistrationPage();
                regPage.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
       
        
        
        
       
        


        JLabel orLabel = new JLabel("Or register using", SwingConstants.CENTER);
        orLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        orLabel.setBounds(100, 360, 200, 20);
        formPanel.add(orLabel);

        ImageIcon googleIcon = new ImageIcon("/home/tushar/swing_project/images/logingoogle.png");
        ImageIcon instaIcon = new ImageIcon("/home/tushar/swing_project/images/logininsta.jpeg");
        ImageIcon fbIcon = new ImageIcon("/home/tushar/swing_project/images/loginfacebook.png");

        JLabel googleLabel = new JLabel(new ImageIcon(googleIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        JLabel instaLabel = new JLabel(new ImageIcon(instaIcon.getImage().getScaledInstance(49, 49, Image.SCALE_SMOOTH)));
        JLabel fbLabel = new JLabel(new ImageIcon(fbIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        
        googleLabel.setBounds(90, 415, 40, 40);
        instaLabel.setBounds(180, 415, 40, 40);
        fbLabel.setBounds(270, 415, 40, 40);
        
        formPanel.add(googleLabel);
        formPanel.add(instaLabel);
        formPanel.add(fbLabel);
        

        frame.setVisible(true);
    }


    

    private static boolean authenticateUser(String mobileNumber, String password) {
        String query = "SELECT * FROM users WHERE mobile_number = ? AND hashed_password = ?";
        
        try (Connection conn = Database.getConnection();  // ✅ Always get a fresh connection
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, mobileNumber);
            stmt.setString(2, password);
    
            try (ResultSet rs = stmt.executeQuery()) {
                boolean userExists = rs.next();
                System.out.println(userExists ? "✅ User found!" : "❌ No user found.");
                return userExists;
            }
    
        } catch (SQLException e) {
            System.out.println("❌ Authentication Error: " + e.getMessage());
            return false;
        }
    }
    



    public void showPage() {
        frame.setVisible(true);
    }
    


}
    

