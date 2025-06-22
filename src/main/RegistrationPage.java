package main;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RegistrationPage extends JFrame{
    private JFrame frame;
private JTextField nameField, mobileField, emailField, addressField, problemField;
private JPasswordField passwordField;
private JTextArea  descriptionArea; // ✅ Added descriptionArea
private JRadioButton male, female, other;
private JLabel profilePicLabel, upiQrLabel;
private File profilePicFile, upiQrFile;
private JButton uploadProfilePic, uploadUpiQr, submitButton, cancelButton;

    
    public RegistrationPage() {
     
        frame = new JFrame("Registration Form");
        frame.setSize(900, 1150);  // Fixed size to match form
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(240, 240, 250));  

        JLabel heading = new JLabel("Register New User", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setOpaque(true);  // ✅ Makes background visible
        heading.setBackground(new Color(100, 230, 25));  // Light blue
        heading.setForeground(Color.BLACK);
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); 
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(850, 1100));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // JLabel heading = new JLabel("Register New User", SwingConstants.CENTER);
        // heading.setFont(new Font("Arial", Font.BOLD, 22));
        // heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        
        nameField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(nameField, gbc);
        
        JLabel mobileLabel = new JLabel("Mobile No:");
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(mobileLabel, gbc);
        
        mobileField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(mobileField, gbc);
        
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(emailLabel, gbc);
        
        emailField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(emailField, gbc);
        
        JLabel addressLabel = new JLabel("Address:");
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(addressLabel, gbc);
        
        addressField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(addressField, gbc);
        
        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(genderLabel, gbc);
        
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        other = new JRadioButton("Other");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(other);
        JPanel genderPanel = new JPanel();
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.add(other);
        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(genderPanel, gbc);

        // Problem Label

JLabel problemLabel = new JLabel("Problem:");
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(problemLabel, gbc);
        
        problemField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(problemField, gbc);

// Description Label


gbc.gridx = 0; gbc.gridy = 6; formPanel.add(new JLabel("Description:"), gbc);
descriptionArea = new JTextArea(5, 30);
descriptionArea.setLineWrap(true);
descriptionArea.setWrapStyleWord(true);
JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
gbc.gridx = 1; formPanel.add(descriptionScrollPane, gbc);

// Password Label
JLabel passwordLabel = new JLabel("Password:");
gbc.gridx = 0; gbc.gridy = 7;  // Shifted down one row
formPanel.add(passwordLabel, gbc);

passwordField = new JPasswordField(30);
gbc.gridx = 1; gbc.gridy = 7;
formPanel.add(passwordField, gbc);

        
        
        
        uploadProfilePic = new JButton("Upload Profile Picture");
        gbc.gridx = 0; gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(uploadProfilePic, gbc);
        
        uploadUpiQr = new JButton("Upload UPI QR Code");
        gbc.gridx = 1; gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(uploadUpiQr, gbc);

        profilePicLabel = new JLabel();
        profilePicLabel.setPreferredSize(new Dimension(100, 100));
        profilePicLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       gbc.gridx = 0; gbc.gridy = 10;
        formPanel.add(profilePicLabel, gbc);

upiQrLabel = new JLabel();
upiQrLabel.setPreferredSize(new Dimension(100, 100));
upiQrLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
gbc.gridx = 2; gbc.gridy = 10;
formPanel.add(upiQrLabel, gbc);

uploadProfilePic.addActionListener(e -> uploadImage(true));
uploadUpiQr.addActionListener(e -> uploadImage(false));
        
submitButton = new JButton("Submit"); 
submitButton.setBackground(Color.GREEN);
submitButton.addActionListener(e -> {
    if (saveToDatabase()) { // Save data and check if successful
        JOptionPane.showMessageDialog(frame, "Registration Successful! Redirecting to Login Page.");
        frame.dispose(); // Close RegistrationPage
        
    } else {
        JOptionPane.showMessageDialog(frame, "Registration Failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
});
gbc.gridx = 0; gbc.gridy = 12;
formPanel.add(submitButton, gbc);

cancelButton = new JButton("Cancel");
cancelButton.setBackground(Color.RED);
cancelButton.addActionListener(e -> {
    frame.dispose(); // Close RegistrationPage
    
});
gbc.gridx = 1; gbc.gridy = 12;
formPanel.add(cancelButton, gbc);


        
        frame.add(formPanel);
        frame.setVisible(true);
    }


  
    

    private void uploadImage(boolean isProfilePic) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
    
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath(); // Get the full file path
            
            // Resize and display image on the UI
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
    
            if (isProfilePic) {
                profilePicLabel.setIcon(imageIcon);
                profilePicFile = selectedFile;  // Store the file path for saving in DB
            } else {
                upiQrLabel.setIcon(imageIcon);
                upiQrFile = selectedFile;  // Store the file path for saving in DB
            }
    
            System.out.println("Selected File Path: " + imagePath); // Debug print
        }
    }




    public void showPage() {
        frame.setVisible(true);
    }
    

   
    
   
    private boolean saveToDatabase() {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO users (name, mobile_number, email, address, gender, problem, description, hashed_password, profile_pic_path, upi_qr_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
    
            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, mobileField.getText());
            pstmt.setString(3, emailField.getText());
            pstmt.setString(4, addressField.getText());
            pstmt.setString(5, male.isSelected() ? "Male" : female.isSelected() ? "Female" : "Other");
            pstmt.setString(6, problemField.getText());
            pstmt.setString(7, descriptionArea.getText());
            pstmt.setString(8, new String(passwordField.getPassword())); // Store hashed password if applicable
    
            // Store file paths instead of binary data
            pstmt.setString(9, profilePicFile != null ? profilePicFile.getAbsolutePath() : null);
            pstmt.setString(10, upiQrFile != null ? upiQrFile.getAbsolutePath() : null);
            // System.out.println("Profile Pic File: " + (profilePicFile != null ? profilePicFile.getAbsolutePath() : "NULL"));
            // System.out.println("UPI QR File: " + (upiQrFile != null ? upiQrFile.getAbsolutePath() : "NULL"));

    
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Registration Successful!");
                return true;  // ✅ Success
            } else {
                JOptionPane.showMessageDialog(frame, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // ❌ Failure
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // ❌ Exception occurred
        }
    }
    
    public static void main(String[] args) {
        new RegistrationPage();
    }
}
