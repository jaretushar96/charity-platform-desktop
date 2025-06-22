package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import javax.swing.*;




public class homepage {
    public static void main(String[] args) {
        // Create main frame
        UIManager.put("Button.focus", Color.white); // Disable focus color
        UIManager.put("Button.select", Color.white); // Disable focus color on selection
        UIManager.put("Button.focusWidth", 0);
        JFrame frame = new JFrame("Charity Platform");
        frame.setSize(1900, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        frame.setLayout(new BorderLayout());
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE); // Change header color to white
        headerPanel.setPreferredSize(new Dimension(800, 60)); // Header height

        // Logo panel
        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon("/home/tushar/swing_project/images/wesite logo.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        logoLabel.setPreferredSize(new Dimension(60, 60)); // Adjust logo size
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 100, 0)); // Add 100px space to the right of the logo
        logoPanel.setOpaque(false);
        logoPanel.add(logoLabel);
        headerPanel.add(logoPanel, BorderLayout.WEST);

        // Buttons panel (left-aligned near the logo)
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Adjust spacing between buttons
        buttonsPanel.setOpaque(false); // Make it blend with the header background

        // Navigation buttons
        JButton homeButton = new JButton("Home");
        JButton aboutUsButton = new JButton("About Us");
        JButton contactUsButton = new JButton("Contact Us");
        JButton donateButton = new JButton("Donate Now");

        // Add buttons to panel
        
        JButton[] leftButtons = {homeButton, aboutUsButton, contactUsButton, donateButton};
        ButtonGroup buttonGroup = new ButtonGroup();
        for (JButton button : leftButtons) {
            button.setFocusPainted(false);
            button.setFocusable(false);
            button.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size
            button.setBackground(Color.WHITE); // Set background to white
            button.setForeground(Color.BLACK); // Text color black by default
            button.setBorder(null); // Remove borders
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setPreferredSize(new Dimension(120, 60)); // Set uniform button size
            button.setVerticalAlignment(SwingConstants.CENTER);
        
            button.addActionListener(e -> {
                // Reset all buttons' text color to black
                for (JButton b : leftButtons) {
                    b.setForeground(Color.BLACK);
                }
                // Change the clicked button's text color to orange
                button.setForeground(new Color(255, 69, 0));
            });
        
            buttonGroup.add(button);
            buttonsPanel.add(button);
        }
        
        JPanel buttonsWrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 150, 0)); // Add 150px space from the logo
        buttonsWrapperPanel.setOpaque(false);
        buttonsWrapperPanel.add(buttonsPanel);
        headerPanel.add(buttonsWrapperPanel, BorderLayout.CENTER);
        
        // Login button panel (right-aligned)
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 200, 0)); // Add 200px space from left
        loginPanel.setOpaque(false);

        JLabel loginLogoLabel = new JLabel(new ImageIcon(new ImageIcon("/home/tushar/swing_project/images/login logo.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        loginPanel.add(loginLogoLabel);
        headerPanel.add(loginPanel, BorderLayout.EAST);

        frame.add(headerPanel, BorderLayout.NORTH);


        loginLogoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


                Loginpage login = new Loginpage();
                login.showPage();

               
        
            }
        });
        

        // Main content panel with sections for About Us and Contact Us
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Blank space for animations and images
        
       // Panel for Slideshow
JPanel blankSpacePanel = new JPanel(new CardLayout());
blankSpacePanel.setPreferredSize(new Dimension(1900, 700));

// Add images to the slideshow
ArrayList<String> imagePaths = new ArrayList<>();
imagePaths.add("/home/tushar/swing_project/images/a1.jpg");
imagePaths.add("/home/tushar/swing_project/images/a7.jpg");
imagePaths.add("/home/tushar/swing_project/images/a6.jpg");
imagePaths.add("/home/tushar/swing_project/images/a4.jpg");
imagePaths.add("/home/tushar/swing_project/images/a5.jpeg");
imagePaths.add("/home/tushar/swing_project/images/a13.jpg");
imagePaths.add("/home/tushar/swing_project/images/a8.jpg");
imagePaths.add("/home/tushar/swing_project/images/a9.jpg");
imagePaths.add("/home/tushar/swing_project/images/a12.jpg");
imagePaths.add("/home/tushar/swing_project/images/a10.jpg");
imagePaths.add("/home/tushar/swing_project/images/a11.jpg");


for (String path : imagePaths) {
    JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(1900, 700, Image.SCALE_SMOOTH)));
    blankSpacePanel.add(imageLabel);
}

// Overlay panel to center the text
JPanel overlayPanel = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);
    }
};
overlayPanel.setLayout(new GridBagLayout()); // Center-align content
overlayPanel.setPreferredSize(new Dimension(1900, 700));

// Custom JLabel for hover effect (color change and zoom effect)
class HoverableLabel extends JLabel {
    private float defaultFontSize;
    private float hoverFontSize;

    public HoverableLabel(String text, float fontSize) {
        super(text);
        this.defaultFontSize = fontSize;
        this.hoverFontSize = fontSize + 10f;
        setFont(new Font("Arial", Font.BOLD, (int) defaultFontSize));
        setForeground(Color.WHITE); // Default text color is white
        setHorizontalAlignment(SwingConstants.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // Apply hover effect only for the heading
    public void applyHoverEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(getFont().deriveFont(hoverFontSize)); // Zoom in
                setForeground(Color.BLACK); // Change color to black on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(getFont().deriveFont(defaultFontSize)); // Zoom out
                setForeground(Color.WHITE); // Revert color to white
            }
        });
    }
}

// Create heading label
HoverableLabel heading = new HoverableLabel("Charity Platform", 55.0f); // Default heading size

// Create quote label
JLabel quote = new JLabel("\"Charity brings to life again those who are spiritually dead.\"");
quote.setFont(new Font("Arial", Font.PLAIN, 24)); // Quote font size
quote.setForeground(Color.WHITE);
quote.setHorizontalAlignment(SwingConstants.CENTER);

// Create additional text below the quote in horizontal manner, at the bottom of the screen

JLabel additionalText = new JLabel("\"It's easy to run to others. It's so hard to stand on one's own record. You can fake virtue for an audience. You can't fake it in your own eyes.Your ego is your strictest judge. They run from it. They spend their lives running. It's easier to donate a few thousand to charity and think oneself noble than to base self-respect on personal standards of personal achievement. It's simple to seek substitutes for competence--such easy substitutes: love, charm, kindness, charity. But there is no substitute for competence.\"");
additionalText.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font size for additional text
additionalText.setForeground(Color.WHITE); // White text color
additionalText.setHorizontalAlignment(SwingConstants.RIGHT); // Align text horizontally

// Set bounds for additional text to keep it horizontal with proper padding
additionalText.setBounds(150, 600, 1000, 100); // Positioned at the bottom with 150px margin from left, right, and bottom

// Add heading, quote, and additional text to the overlay panel
GridBagConstraints gbc = new GridBagConstraints();
gbc.gridx = 0;
gbc.gridy = 0;
gbc.insets = new Insets(0, 0, 20, 0); // Space between heading and quote
overlayPanel.add(heading, gbc);

gbc.gridy = 1;
overlayPanel.add(quote, gbc);

// Position additional text at the bottom of the screen horizontally, without wrapping
gbc.gridy = 2;
gbc.insets = new Insets(0, 150, 100, 150); // Add 150px margin from left, right, and bottom
overlayPanel.add(additionalText, gbc); // Add additional text horizontally at the bottom

// Apply hover effect to the heading
heading.applyHoverEffect();

// Add both the slideshow and the overlay text to a layered pane
JLayeredPane layeredPane = new JLayeredPane();
layeredPane.setPreferredSize(new Dimension(1900, 700));
blankSpacePanel.setBounds(0, 0, 1900, 700);
overlayPanel.setBounds(0, 0, 1900, 700);

layeredPane.add(blankSpacePanel, Integer.valueOf(0));
layeredPane.add(overlayPanel, Integer.valueOf(1));

// Add the layered pane to your main panel
mainPanel.add(layeredPane);

// Start slideshow
Timer timer = new Timer(3000, e -> {
    CardLayout cl = (CardLayout) blankSpacePanel.getLayout();
    cl.next(blankSpacePanel);
});
timer.start();

 // About Us Section
JPanel aboutUsPanel = new JPanel();
aboutUsPanel.setBackground(new Color(242, 242, 242)); // Light gray background
aboutUsPanel.setLayout(new BoxLayout(aboutUsPanel, BoxLayout.Y_AXIS));
aboutUsPanel.setBorder(BorderFactory.createEmptyBorder(80, 80, 40, 80)); // Padding (top, left, bottom, right)

// Heading with margin & padding
JLabel headingLabel = new JLabel("About Us");
headingLabel.setFont(new Font("Arial", Font.BOLD, 34)); // Large and bold font
headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
headingLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 40, 0)); // Top & bottom spacing
aboutUsPanel.add(headingLabel);

// Text Description with margin & padding
JLabel paragraphLabel = new JLabel("<html><div style='text-align: justify;'>"
        + "Our charity platform bridges the gap between donors and beneficiaries, ensuring efficient and direct contributions. "
        + "We prioritize transparency, making sure every donation reaches the right hands. "
        + "With an easy-to-use interface, donors can browse and support individuals in need seamlessly. "
        + "Your generosity helps uplift communities and bring meaningful change. "
        + "Trust, efficiency, and integrity define our platform. "
        + "Every contribution, big or small, makes a real difference. "
        + "Join us in spreading hope and making a lasting impact on lives."
        + "</div></html>");
paragraphLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Large and readable font
paragraphLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
paragraphLabel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40)); // Margin & Padding (top, left, bottom, right)
aboutUsPanel.add(paragraphLabel);

// Panel for Images and Names (Directors)
JPanel imagesPanel = new JPanel();
imagesPanel.setLayout(new GridLayout(1, 3, 10, 0)); // Reduced extra vertical gap
imagesPanel.setBackground(new Color(242, 242, 242));
imagesPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0)); // Top margin for images

// Define images and names
String[] imagePaths1 = {
    "/home/tushar/swing_project/images/aboutsec6.jpg",
    "/home/tushar/swing_project/images/aboutsec5.jpg",
    "/home/tushar/swing_project/images/aboutsection.jpg"
};
String[] names = {"John Doe", "Jane Smith", "Emily Brown"};

for (int i = 0; i < 3; i++) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(242, 242, 242));
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Image label (Bigger images with padding)
    JLabel imageLabel = new JLabel(new ImageIcon(
        new ImageIcon(imagePaths1[i]).getImage().getScaledInstance(360, 360, Image.SCALE_SMOOTH))); // Increased size
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Padding around the image

    // Name label (No extra space)
    JLabel nameLabel = new JLabel(names[i]);
    nameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Readable font
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    nameLabel.setOpaque(true);
    nameLabel.setBackground(new Color(220, 220, 220)); // Light gray background
    nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Small padding
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    nameLabel.setMaximumSize(new Dimension(360, 30)); // Match width of image

    panel.add(imageLabel);
    panel.add(nameLabel);

    imagesPanel.add(panel);


    
}

// Add imagesPanel to main panel
aboutUsPanel.add(imagesPanel);

// Set proper dimensions
aboutUsPanel.setPreferredSize(new Dimension(1000, 1000)); // Adjust height

// Add to main panel
mainPanel.add(aboutUsPanel);

// Use the existing headerPanel if it already exists, otherwise rename


// Contact Us Header
JPanel contactHeaderPanel = new JPanel();
contactHeaderPanel.setBackground(new Color(80, 0, 255)); // Vibrant blue
contactHeaderPanel.setPreferredSize(new Dimension(900, 120));

JLabel headerLabel = new JLabel("Get in Touch With Us", SwingConstants.CENTER);
headerLabel.setForeground(Color.WHITE);
headerLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Increased font size
contactHeaderPanel.setLayout(new BorderLayout());
contactHeaderPanel.add(headerLabel, BorderLayout.CENTER);

// Contact Us Section
JPanel contactUsPanel = new JPanel();
contactUsPanel.setBackground(new Color(15, 0, 40)); // Navy blue
contactUsPanel.setLayout(new BorderLayout());
contactUsPanel.setPreferredSize(new Dimension(900, 500));

// Main Content Panel (Inside Navy Blue Section)
JPanel infoPanel = new JPanel(new GridBagLayout());
infoPanel.setBackground(new Color(15, 0, 40));
infoPanel.setPreferredSize(new Dimension(900, 380));

GridBagConstraints gbcContact = new GridBagConstraints();  // 🔹 FIXED variable name
gbcContact.insets = new Insets(10, 20, 10, 20);
gbcContact.fill = GridBagConstraints.HORIZONTAL;

// Charity Platform Heading
JLabel charityHeading = new JLabel("<html><b style='color:white; font-size:22px;'>CHARITY PLATFORM</b></html>");
charityHeading.setHorizontalAlignment(SwingConstants.CENTER);
gbcContact.gridx = 0;
gbcContact.gridy = 0;
gbcContact.gridwidth = 3;
infoPanel.add(charityHeading, gbcContact);
gbcContact.gridwidth = 1;

// Live Map (Left Side)
ImageIcon mapIcon = new ImageIcon("/home/tushar/swing_project/images/map.png");
JLabel mapLabel = new JLabel(new ImageIcon(mapIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH)));
mapLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
mapLabel.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://maps.app.goo.gl/CKjskabUWVc8gQ1G7"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
});

gbcContact.gridx = 0;
gbcContact.gridy = 1;
gbcContact.gridheight = 3;gbcContact.insets = new Insets(5, 30, 0, 300); // Added padding and margin
infoPanel.add(mapLabel, gbcContact);
gbcContact.gridheight = 1;

// Follow Us Section (Middle)
JLabel followUsLabel = new JLabel("<html><b style='color:white;'>Follow us on:</b></html>");
gbcContact.gridx = 1;
gbcContact.gridy = 1;
infoPanel.add(followUsLabel, gbcContact);

ImageIcon fbIcon = new ImageIcon("/home/tushar/swing_project/images/loginfacebook.png");
ImageIcon instaIcon = new ImageIcon("/home/tushar/swing_project/images/logininsta.jpeg");
ImageIcon youtubeIcon = new ImageIcon("/home/tushar/swing_project/images/youtube12.png");
ImageIcon twitterIcon = new ImageIcon("/home/tushar/swing_project/images/twitter.png");

JLabel fbLabel = new JLabel(new ImageIcon(fbIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
JLabel instaLabel = new JLabel(new ImageIcon(instaIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
JLabel youtubeLabel = new JLabel(new ImageIcon(youtubeIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
JLabel twitterLabel = new JLabel(new ImageIcon(twitterIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

JPanel socialPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
socialPanel.setBackground(new Color(15, 0, 40));
socialPanel.add(fbLabel);
socialPanel.add(instaLabel);
socialPanel.add(youtubeLabel);
socialPanel.add(twitterLabel);

gbcContact.gridy = 2;
 
infoPanel.add(socialPanel, gbcContact);

// Address and Contact Info (Right Side)
JLabel addressLabel = new JLabel("<html><b style='color:white;'>Address:</b><br>123 Charity Lane<br>Hope City, USA<br>Building No. 5, Block A</html>");
JLabel phoneLabel = new JLabel("<html><b style='color:white;'>Phone:</b> +1-800-CHARITY</html>");
JLabel mobileLabel = new JLabel("<html><b style='color:white;'>Mobile:</b> +91-9876543210</html>");
JLabel emailLabel = new JLabel("<html><b style='color:white;'>Email:</b> <span style='color:yellow;'>contact@charityplatform.org</span></html>");


gbcContact.gridx = 2;
gbcContact.gridy = 1;
infoPanel.add(addressLabel, gbcContact);
gbcContact.gridy = 2;
infoPanel.add(phoneLabel, gbcContact);
gbcContact.gridy = 3;
infoPanel.add(mobileLabel, gbcContact);gbcContact.insets = new Insets(5, 170, 10, 30); // Increased margin

// Adding Everything to Contact Us Panel
contactUsPanel.add(contactHeaderPanel, BorderLayout.NORTH);
contactUsPanel.add(infoPanel, BorderLayout.CENTER);

// Add to Main Panel
mainPanel.add(contactUsPanel);
frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        
        // Footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.LIGHT_GRAY);
        footerPanel.add(new JLabel("\u00A9 2025 Charity Platform - All Rights Reserved"));
        frame.add(footerPanel, BorderLayout.SOUTH);

        // Donate now page
        donateButton.addActionListener(e -> {

            DonateNowPage donateNow = new DonateNowPage();
            donateNow.showPage(); 


            
        });
        

        homeButton.addActionListener(e -> blankSpacePanel.scrollRectToVisible(blankSpacePanel.getBounds()));
        aboutUsButton.addActionListener(e -> aboutUsPanel.scrollRectToVisible(aboutUsPanel.getBounds()));
        contactUsButton.addActionListener(e -> contactUsPanel.scrollRectToVisible(contactUsPanel.getBounds()));

        // Display the frame
        frame.setVisible(true);
    }
}































































