package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    JPanel panel;

    JLabel header;
    JLabel title;

    JRadioButton jRadioButton1;
    JRadioButton jRadioButton2;

    ButtonGroup documentTypeGroup;
    JLabel documentTypeLabel;

    JButton cancelButton;
    JButton saveButton;

    public MainWindow() {
        super("Data Extractor");
        this.setSize(600,400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panel = new JPanel(new BorderLayout());

        header = new JLabel("Data Extract");
        header.setBounds(0, 0, 600,30);
        header.setFont(new Font("Sans Serif", Font.BOLD, 18));

        header.setBackground(Color.lightGray);
        header.setBorder(new EmptyBorder(0,5,0, 0));//top,left,bottom,right
        header.setOpaque(true);

        JPanel contentPanel = new JPanel(new BorderLayout());

        title = new JLabel("Configurar exportação: ");
        title.setBounds(10, 0, 180, 50);
        title.setFont(new Font("Sans Serif", Font.BOLD, 14));

        documentTypeLabel = new JLabel("Selecione o tipo de arquivo: ");
        documentTypeLabel.setBounds(20, 50, 180, 50);

        jRadioButton1 = new JRadioButton();
        jRadioButton2 = new JRadioButton();

        jRadioButton1.setText("Boleto");
        jRadioButton2.setText("Imposto de renda");

        jRadioButton1.setBounds(200, 50, 80, 50);
        jRadioButton2.setBounds(280, 50, 250, 50);

        contentPanel.add(title);
        contentPanel.add(documentTypeLabel);
        contentPanel.add(jRadioButton1);
        contentPanel.add(jRadioButton2);

        this.add(jRadioButton1);
        this.add(jRadioButton2);
        this.add(documentTypeLabel);

        documentTypeGroup = new ButtonGroup();

        documentTypeGroup.add(jRadioButton1);
        documentTypeGroup.add(jRadioButton2);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.white);
        cancelButton.setFont(new Font("Sans Serif", Font.BOLD, 12));

        saveButton = new JButton("Save");
        saveButton.setBackground(Color.white);
        saveButton.setFont(new Font("Sans Serif", Font.BOLD, 12));

        bottomPanel.add(cancelButton, BorderLayout.WEST);
        bottomPanel.add(saveButton, BorderLayout.EAST);

        panel.add(header, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        this.getContentPane().add(panel);

        this.setVisible(true);
    }
}
