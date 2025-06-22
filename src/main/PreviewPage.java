package main;

import java.awt.*;
import java.io.File;
import java.sql.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class PreviewPage {
    private JFrame frame;
    private JTextField nameField, mobileField, emailField, addressField;
    private JTextArea problemArea, descriptionArea;
    private JRadioButton male, female, other;
    private JButton editButton, deleteButton, saveButton, donateButton;
    private JButton uploadProfilePic, uploadUpiQr;
    private JLabel profilePicLabel, upiQrLabel;
    private String mobileNumber;
    private File profilePicFile, upiQrFile;
    private boolean isEditing = false;

    public PreviewPage(String mobileNumber) {
        this.mobileNumber = mobileNumber;

        frame = new JFrame("Preview Form");
        frame.setSize(600, 700);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("Preview Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(12, 5, 4, 10));
        frame.add(panel, BorderLayout.CENTER);
//         JPanel panel = new JPanel(new GridBagLayout());
// GridBagConstraints gbc = new GridBagConstraints();
// gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between elements
// gbc.anchor = GridBagConstraints.WEST; // Align components to the left


        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Mobile:"));
        mobileField = new JTextField();
        mobileField.setEditable(false);
        panel.add(mobileField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Problem:"));
        problemArea = new JTextArea(2, 20);
        panel.add(problemArea);

        panel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(2, 20);
        panel.add(descriptionArea);

        panel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        other = new JRadioButton("Other");
        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);
        group.add(other);
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.add(other);
        panel.add(genderPanel);

        panel.add(new JLabel("Profile Picture:"));
        profilePicLabel = new JLabel();
        profilePicLabel.setPreferredSize(new Dimension(50, 30));
        profilePicLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(profilePicLabel);

        panel.add(new JLabel("UPI QR Code:"));
        upiQrLabel = new JLabel();
        upiQrLabel.setPreferredSize(new Dimension(20, 10));
        upiQrLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(upiQrLabel);

        uploadProfilePic = new JButton("Upload Profile Picture");
        uploadProfilePic.setEnabled(false);
        uploadProfilePic.addActionListener(e -> uploadImage(true));
        panel.add(uploadProfilePic);

        uploadUpiQr = new JButton("Upload UPI QR Code");
        uploadUpiQr.setEnabled(false);
        uploadUpiQr.addActionListener(e -> uploadImage(false));
        panel.add(uploadUpiQr);

        JPanel buttonPanel = new JPanel();
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");
        donateButton = new JButton("Add to Donate Now");

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(donateButton);

        editButton.setBackground(Color.BLUE);
editButton.setForeground(Color.WHITE);
saveButton.setBackground(Color.GREEN);
saveButton.setForeground(Color.WHITE);
deleteButton.setBackground(Color.RED);
deleteButton.setForeground(Color.WHITE);
donateButton.setBackground(Color.ORANGE);
donateButton.setForeground(Color.WHITE);


        frame.add(buttonPanel, BorderLayout.SOUTH);

        loadUserData();
        setFieldsEditable(false);

        editButton.addActionListener(e -> setFieldsEditable(true));
        deleteButton.addActionListener(e -> deleteUserData());
        saveButton.addActionListener(e -> saveUserData());
        donateButton.addActionListener(e -> addToDonateNow());

        // updateProfilePic.addActionListener(e -> updateImage("profile_pic", profilePicLabel));
        // updateUpiQr.addActionListener(e -> updateImage("upi_qr", upiQrLabel));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadUserData() {
        String query = "SELECT * FROM users WHERE mobile_number = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mobileNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                mobileField.setText(rs.getString("mobile_number"));
                emailField.setText(rs.getString("email"));
                addressField.setText(rs.getString("address"));
                problemArea.setText(rs.getString("problem"));
                descriptionArea.setText(rs.getString("description"));
                String gender = rs.getString("gender");
                male.setSelected("Male".equals(gender));
                female.setSelected("Female".equals(gender));
                other.setSelected("Other".equals(gender));

                loadProfilePic(rs.getString("profile_pic_path"));
                loadUpiQr(rs.getString("upi_qr_path"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadProfilePic(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            profilePicFile = new File(imagePath);
            profilePicLabel.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        }
    }

    private void loadUpiQr(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            upiQrFile = new File(imagePath);
            upiQrLabel.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        }
    }

    private void setFieldsEditable(boolean editable) {
        nameField.setEditable(editable);
        emailField.setEditable(editable);
        addressField.setEditable(editable);
        problemArea.setEditable(editable);
        descriptionArea.setEditable(editable);
        male.setEnabled(editable);
        female.setEnabled(editable);
        other.setEnabled(editable);
        uploadProfilePic.setEnabled(editable);
        uploadUpiQr.setEnabled(editable);

        saveButton.setEnabled(editable);
    }


    private void uploadImage(boolean isProfilePic) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            if (isProfilePic) {
                profilePicLabel.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                profilePicFile = selectedFile;
            } else {
                upiQrLabel.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                upiQrFile = selectedFile;
            }
        }
    }

    private void saveUserData() {
        String query = "UPDATE users SET name=?, email=?, address=?, problem=?, description=?, gender=?, profile_pic_path=?, upi_qr_path=? WHERE mobile_number=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nameField.getText());
            stmt.setString(2, emailField.getText());
            stmt.setString(3, addressField.getText());
            stmt.setString(4, problemArea.getText());
            stmt.setString(5, descriptionArea.getText());
            stmt.setString(6, male.isSelected() ? "Male" : female.isSelected() ? "Female" : "Other");
            stmt.setString(7, (profilePicFile != null) ? profilePicFile.getAbsolutePath() : null);
            stmt.setString(8, (upiQrFile != null) ? upiQrFile.getAbsolutePath() : null);
            stmt.setString(9, mobileNumber);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Data updated successfully!");
            setFieldsEditable(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUserData() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete your data?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = Database.getConnection()) {
                if (conn == null) {
                    throw new SQLException("Connection failed");
                }
                String query = "DELETE FROM users WHERE mobile_number=?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, mobileNumber);
                    int deletedRows = stmt.executeUpdate();
                    if (deletedRows > 0) {
                        JOptionPane.showMessageDialog(frame, "Data deleted successfully!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Delete failed. User may not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }





    public void showPage() {
        frame.setVisible(true);
    }
    
    private void addToDonateNow() {
        String query = "UPDATE users SET in_donate_now = TRUE WHERE mobile_number = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, mobileNumber);  // Use mobile number instead of user ID
            int rowsUpdated = stmt.executeUpdate();
    
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "User data added to Donate Now section!");
    
                // Refresh the DonateNowPage to show updated profiles
                DonateNowPage donateNowPage = new DonateNowPage();
                donateNowPage.loadProfiles(); // ✅ Ensure this method is public in DonateNowPage
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add user to Donate Now section.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    
    

    public static void main(String[] args) {
        new PreviewPage("1234567890");
    }
}
