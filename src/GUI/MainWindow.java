package GUI;

import Persistence.ConfigPersistence;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
    JPanel panel, contentPanel, bottomPanel;
    JRadioButton jRadioButton1, jRadioButton2;
    ButtonGroup documentTypeGroup;
    JLabel header, title, documentTypeLabel, frequencyLabel, inputDirLabel, outputDirLabel, errorDirLabel,
            frequency, fileLocation, inputFile, outputFile, errorFile;
    JTextField frequencyField;
    JButton inputDirButton, outputDirButton, errorDirButton, cancelButton, saveButton;
    JFileChooser inputFileChooser, outputFileChooser, errorFileChooser;

    ConfigPersistence configPersistence;

    public MainWindow() {
        super("Data Extractor");
        this.setSize(600,400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        configPersistence = new ConfigPersistence();

        createHeader();
        createContentPanel();
        createBottomPanel();

        panel = new JPanel(new BorderLayout());
        panel.add(header, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        this.getContentPane().add(panel);

        this.setVisible(true);
    }

    private void createHeader() {
        header = new JLabel("Data Extract");
        header.setBounds(0, 0, 600,30);
        header.setFont(new Font("Sans Serif", Font.BOLD, 18));

        header.setBackground(Color.lightGray);
        header.setBorder(new EmptyBorder(0,5,0, 0));//top,left,bottom,right
        header.setOpaque(true);
    }

    private void createContentPanel() {

        contentPanel = new JPanel(new BorderLayout());

        createTitle();
        createDocumentTypeField();
        createFrequencyField();
        createDirectoriesField();

        addComponentsToPanel();
    }

    private void createTitle() {
        title = new JLabel("Configurar exportação: ");
        title.setBounds(10, 0, 180, 50);
        title.setFont(new Font("Sans Serif", Font.BOLD, 14));
    }

    private void createDocumentTypeField() {
        documentTypeLabel = new JLabel("Selecione o tipo de arquivo: ");
        documentTypeLabel.setBounds(20, 50, 180, 40);

        jRadioButton1 = new JRadioButton();
        jRadioButton2 = new JRadioButton();

        jRadioButton1.setActionCommand("Boleto");
        jRadioButton2.setActionCommand("IR");

        jRadioButton1.setText("Boleto");
        jRadioButton2.setText("Imposto de renda");

        jRadioButton1.setBounds(200, 50, 80, 40);
        jRadioButton2.setBounds(280, 50, 250, 40);

        documentTypeGroup = new ButtonGroup();
        documentTypeGroup.add(jRadioButton1);
        documentTypeGroup.add(jRadioButton2);
    }

    private void createFrequencyField() {
        frequencyLabel = new JLabel("Informe a periodicidade: ");
        frequencyLabel.setBounds(20, 60, 180, 40);

        frequencyField = new JTextField(5);
        frequencyField.setBounds(180, 70, 60, 23);

        frequency = new JLabel("horas");
        frequency.setBounds(240, 62, 50, 40);
    }

    private void createDirectoriesField() {
        fileLocation = new JLabel("Local do Arquivo: ");
        fileLocation.setBounds(20, 80, 120, 40);

        inputDirLabel = new JLabel("Entrada: ");
        inputDirLabel.setBounds(40, 100, 70, 40);

        outputDirLabel = new JLabel("Saída: ");
        outputDirLabel.setBounds(40, 120, 50, 40);

        errorDirLabel = new JLabel("Erro: ");
        errorDirLabel.setBounds(40, 140, 50, 40);

        inputDirButton = new JButton("Informe a pasta de entrada");
        outputDirButton = new JButton("Informe a pasta de saída");
        errorDirButton = new JButton("Informe a pasta de erros");

        inputDirButton.setBounds(100, 110, 200, 23);
        outputDirButton.setBounds(100, 130, 200, 23);
        errorDirButton.setBounds(100, 150, 200, 23);

        inputFile = new JLabel("");
        outputFile = new JLabel("");
        errorFile = new JLabel("");

        inputFile.setBounds(305, 110, 250, 23);
        outputFile.setBounds(305, 130, 250, 23);
        errorFile.setBounds(305, 150, 250, 23);

        inputDirButton.addActionListener(this);
        outputDirButton.addActionListener(this);
        errorDirButton.addActionListener(this);

        inputFileChooser = new JFileChooser();
        outputFileChooser = new JFileChooser();
        errorFileChooser = new JFileChooser();

        inputFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        outputFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        errorFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        inputFileChooser.setAcceptAllFileFilterUsed(false);
        outputFileChooser.setAcceptAllFileFilterUsed(false);
        errorFileChooser.setAcceptAllFileFilterUsed(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == inputDirButton) {
            int result = inputFileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION){
                inputFile.setText(inputFileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(inputFileChooser.getSelectedFile().getAbsolutePath());
            }

        } else {
            if (e.getSource() == outputDirButton) {
                int result = outputFileChooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION){
                    outputFile.setText(outputFileChooser.getSelectedFile().getAbsolutePath());
                    System.out.println(outputFileChooser.getSelectedFile().getAbsolutePath());
                }

            } else {
                if (e.getSource() == errorDirButton) {
                    int result = errorFileChooser.showSaveDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION){
                        errorFile.setText(errorFileChooser.getSelectedFile().getAbsolutePath());
                        System.out.println(errorFileChooser.getSelectedFile().getAbsolutePath());
                    }
                }
            }
        }
    }

    private void addComponentsToPanel() {
        contentPanel.add(title);
        contentPanel.add(documentTypeLabel);
        contentPanel.add(frequencyLabel);
        contentPanel.add(frequencyField);
        contentPanel.add(frequency);

        contentPanel.add(fileLocation);
        contentPanel.add(inputDirLabel);
        contentPanel.add(outputDirLabel);
        contentPanel.add(errorDirLabel);

        contentPanel.add(inputDirButton);
        contentPanel.add(outputDirButton);
        contentPanel.add(errorDirButton);

        contentPanel.add(inputFile);
        contentPanel.add(outputFile);
        contentPanel.add(errorFile);

        contentPanel.add(jRadioButton1);
        contentPanel.add(jRadioButton2);

        this.add(jRadioButton1);
        this.add(jRadioButton2);
        this.add(documentTypeLabel);
    }

    private void createBottomPanel() {
        bottomPanel = new JPanel(new BorderLayout());
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.white);
        cancelButton.setFont(new Font("Sans Serif", Font.BOLD, 12));

        saveButton = new JButton("Save");
        saveButton.setBackground(Color.white);
        saveButton.setFont(new Font("Sans Serif", Font.BOLD, 12));

        bottomPanel.add(cancelButton, BorderLayout.WEST);
        bottomPanel.add(saveButton, BorderLayout.EAST);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                configPersistence.setFileType(documentTypeGroup.getSelection().getActionCommand());
                configPersistence.setFrequency(frequencyField.getText());
                configPersistence.setInputFolderPath(inputFile.getText());
                configPersistence.setOutputFolderPath(outputFile.getText());
                configPersistence.setErrorFolderPath(errorFile.getText());

                configPersistence.save();
            }
        });
    }
}
