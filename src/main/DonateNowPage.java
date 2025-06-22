package main;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DonateNowPage {
    private JFrame frame;
    private JPanel profilePanel;

    public DonateNowPage() {
        frame = new JFrame("Donate Now");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel heading = new JLabel("\"Giving is not just about making a donation, it’s about making a difference.\"", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(heading, BorderLayout.NORTH);

        profilePanel = new JPanel();
        profilePanel.setLayout(new GridLayout(0, 3, 20, 20)); // 3 profiles per row
        JScrollPane scrollPane = new JScrollPane(profilePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);

        loadProfiles();
        
        frame.setVisible(true);
    }
    public void loadProfiles() {
        profilePanel.removeAll(); // Clear existing profiles
        String query = "SELECT name, problem, profile_pic_path, mobile_number FROM users WHERE in_donate_now = true";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                String problem = rs.getString("problem");
                String profilePicPath = rs.getString("profile_pic_path");
                String mobileNumber = rs.getString("mobile_number");
                
                JPanel profileCard = createProfileCard(name, problem, profilePicPath, mobileNumber);
                profilePanel.add(profileCard);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        profilePanel.revalidate();
        profilePanel.repaint();
    }



    private ImageIcon getCircularImage(String imagePath, int size) {
    try {
        // Read the image from the file path
        BufferedImage originalImage = ImageIO.read(new File(imagePath));

        // Create a new image with transparency
        BufferedImage circleImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleImage.createGraphics();

        // Enable smooth rendering
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a circular clip
        g2.setClip(new Ellipse2D.Float(0, 0, size, size));

        // Draw the image within the clipped circle
        g2.drawImage(originalImage, 0, 0, size, size, null);
        g2.dispose();

        return new ImageIcon(circleImage);
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

private JPanel createProfileCard(String name, String problem, String profilePicPath, String mobileNumber) {
    JPanel card = new JPanel();
    card.setLayout(new BorderLayout(10, 5)); // Reduced gap
    card.setPreferredSize(new Dimension(250, 320));
    card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
    card.setBackground(new Color(240, 248, 255)); // Light pastel blue background (adjustable)

    // Load and display profile picture in a circular shape
    JLabel profilePic = new JLabel();
    ImageIcon circularIcon = getCircularImage(profilePicPath, 175);
    if (circularIcon != null) {
        profilePic.setIcon(circularIcon);
    }
    profilePic.setHorizontalAlignment(SwingConstants.CENTER);

    // Name label
    JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
    nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
    nameLabel.setForeground(Color.DARK_GRAY);

    // Problem label (Reduced gap)
    JLabel problemLabel = new JLabel("<html><center>\"" + problem + "\"</center></html>", SwingConstants.CENTER);
    problemLabel.setFont(new Font("Arial", Font.ITALIC, 13));
    problemLabel.setForeground(Color.GRAY);

    // "View Details" button
    JButton viewDetailsButton = new JButton("View Details");
    viewDetailsButton.setFont(new Font("Arial", Font.BOLD, 14));
    viewDetailsButton.setForeground(Color.WHITE);
    viewDetailsButton.setBackground(new Color(0, 123, 255)); // Professional blue color
    viewDetailsButton.setFocusPainted(false);
    viewDetailsButton.setBorderPainted(false);
    viewDetailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    viewDetailsButton.setPreferredSize(new Dimension(180, 35)); // Adjusted width

    // Add rounded corners
    viewDetailsButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 123, 255), 1, true), // Border with rounded corners
            BorderFactory.createEmptyBorder(5, 15, 5, 15) // Padding
    ));

    // On button click, show full user details
    viewDetailsButton.addActionListener(e -> showUserDetails(mobileNumber));

    // Panel for bottom section (problem & button)
    JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
    bottomPanel.setOpaque(false);
    bottomPanel.add(problemLabel, BorderLayout.NORTH);
    
    // Button panel for margin at bottom
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    buttonPanel.add(viewDetailsButton);
    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Add components
    card.add(profilePic, BorderLayout.NORTH);
    card.add(nameLabel, BorderLayout.CENTER);
    card.add(bottomPanel, BorderLayout.SOUTH);

    return card;
}


    
    

    // Function to load image from file path
private ImageIcon loadImage(String path, int width, int height) {
    File file = new File(path);
    if (file.exists()) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    } else {
        System.out.println("File not found: " + path);
        return new ImageIcon(); // Return empty icon if file not found
    }
}






    private void showUserDetails(String mobileNumber) {
        String query = "SELECT name, mobile_number, email, address, gender, description, profile_pic_path, upi_qr_path FROM users WHERE mobile_number = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, mobileNumber);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Fetch user details
                String name = rs.getString("name");
                String mobile = rs.getString("mobile_number");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String gender = rs.getString("gender");
                String description = rs.getString("description");
                String profilePicPath = rs.getString("profile_pic_path");  // Image path
                String qrCodePath = rs.getString("upi_qr_path");  // QR path

                // Load images from file paths
                ImageIcon profilePic = loadImage(profilePicPath, 100, 100);
                ImageIcon qrCode = loadImage(qrCodePath, 150, 150);

                // Create new frame to display details
                JFrame detailsFrame = new JFrame("User Details");
                detailsFrame.setSize(400, 600);
                detailsFrame.setLayout(new BorderLayout());

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Profile Picture
                JLabel profileLabel = new JLabel(profilePic);
                profileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // User Information Labels
                JLabel nameLabel = new JLabel("<html><b>Name:-  </b> " + name + "</html>");
                JLabel mobileLabel = new JLabel("<html><b>Mobile:-  </b> " + mobile + "</html>");
                JLabel emailLabel = new JLabel("<html><b>Email:-  </b> " + email + "</html>");
                JLabel addressLabel = new JLabel("<html><b>Address:-  </b> " + address + "</html>");
                JLabel genderLabel = new JLabel("<html><b>Gender:-  </b> " + gender + "</html>");
                JLabel descriptionLabel = new JLabel("<html><b>Description:-  </b> " + description + "</html>");
                JLabel qrCodeLabel = new JLabel("<html><b>UPI QR Code:-  </b></html>");

                // Align Labels
                nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                mobileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                genderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // QR Code Image
                JLabel qrLabel = new JLabel(qrCode);
                qrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Add components to panel
                panel.add(profileLabel);
                panel.add(Box.createVerticalStrut(10));
                panel.add(nameLabel);
                panel.add(mobileLabel);
                panel.add(emailLabel);
                panel.add(addressLabel);
                panel.add(genderLabel);
                panel.add(descriptionLabel);
                panel.add(Box.createVerticalStrut(10));
                panel.add(qrLabel);

                detailsFrame.add(panel, BorderLayout.CENTER);
                detailsFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showPage() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DonateNowPage::new);
    }
}
