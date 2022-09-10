package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWindow extends JFrame implements ActionListener {
    JPanel panel;

    JLabel header;
    JLabel title;

    JRadioButton jRadioButton1;
    JRadioButton jRadioButton2;

    ButtonGroup documentTypeGroup;
    JLabel documentTypeLabel, frequencyLabel, inputDirLabel, outputDirLabel, errorDirLabel;
    JTextField frequencyField;
    JLabel frequency;
    JButton inputDirButton, outputDirButton, errorDirButton;
    JFileChooser inputFileChooser, outputFileChooser, errorFileChooser;


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

        // TIPO DO ARQUIVO
        documentTypeLabel = new JLabel("Selecione o tipo de arquivo: ");
        documentTypeLabel.setBounds(20, 50, 180, 40);

        jRadioButton1 = new JRadioButton();
        jRadioButton2 = new JRadioButton();

        jRadioButton1.setText("Boleto");
        jRadioButton2.setText("Imposto de renda");

        jRadioButton1.setBounds(200, 50, 80, 40);
        jRadioButton2.setBounds(280, 50, 250, 40);

        // PERIODICIDADE
        frequencyLabel = new JLabel("Informe a periodicidade: ");
        frequencyLabel.setBounds(20, 60, 180, 40);

        frequencyField = new JTextField(5);
        frequencyField.setBounds(165, 70, 60, 23);

        frequency = new JLabel("horas");
        frequency.setBounds(230, 60, 50, 40);

        //DIRETORIOS
        inputDirLabel = new JLabel("Entrada: ");
        inputDirLabel.setBounds(40, 80, 50, 40);
        outputDirLabel = new JLabel("Saída: ");
        outputDirLabel.setBounds(40, 100, 50, 40);
        errorDirLabel = new JLabel("Erro: ");
        errorDirLabel.setBounds(40, 120, 50, 40);


        inputDirButton = new JButton("Informe a pasta de entrada");
        outputDirButton = new JButton("Informe a pasta de saída");
        errorDirButton = new JButton("Informe a pasta de erros");

        inputDirButton.setBounds(100, 90, 200, 23);
        outputDirButton.setBounds(100, 110, 200, 23);
        errorDirButton.setBounds(100, 130, 200, 23);

        inputDirButton.addActionListener(this);
        outputDirButton.addActionListener(this);
        errorDirButton.addActionListener(this);

        inputFileChooser = new JFileChooser();
        inputFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()){
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

        contentPanel.add(title);
        contentPanel.add(documentTypeLabel);
        contentPanel.add(frequencyLabel);
        contentPanel.add(frequencyField);
        contentPanel.add(frequency);

        contentPanel.add(inputDirLabel);
        contentPanel.add(outputDirLabel);
        contentPanel.add(errorDirLabel);

        contentPanel.add(inputDirButton);
        contentPanel.add(outputDirButton);
        contentPanel.add(errorDirButton);

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

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==inputDirButton) {
            inputFileChooser.showSaveDialog(null);
            System.out.println(inputFileChooser);
            System.out.println("CLICOU BOTAO INPUT");
        } else {
            if (e.getSource()==outputDirButton) {
                outputFileChooser.showSaveDialog(null);
                System.out.println(outputFileChooser);
                System.out.println("CLICOU BOTAO OUTPUT");
            } else {
                if (e.getSource()==errorDirButton) {
                    errorFileChooser.showSaveDialog(null);
                    System.out.println(errorFileChooser);
                    System.out.println("CLICOU BOTAO ERROR");
                }
            }
        }

    }
}
