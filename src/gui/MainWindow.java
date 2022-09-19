package gui;

import persistence.ConfigPersistence;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWindow extends JDialog implements ActionListener {
    JPanel panel, contentPanel, bottomPanel;
    JRadioButton jRadioInvoice, jRadioIR;
    ButtonGroup documentTypeGroup;
    JLabel header, title, documentTypeLabel, frequencyLabel, inputDirLabel, outputDirLabel, errorDirLabel,
            frequency, fileLocation, inputFile, outputFile, errorFile;
    JTextField frequencyField;
    JButton inputDirButton, outputDirButton, errorDirButton, cancelButton, saveButton;
    JFileChooser inputFileChooser, outputFileChooser, errorFileChooser;

    ConfigPersistence configPersistence;

    public MainWindow(ConfigPersistence configPersistence) {
        super((Window)null);
        setModal(true);
        this.setSize(600,400);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.configPersistence = configPersistence;

        createHeader();
        createContentPanel();
        createBottomPanel();

        panel = new JPanel(new BorderLayout());
        panel.add(header, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        loadSavedState();

        this.getContentPane().add(panel);
        this.setVisible(true);
    }

    private void loadSavedState() {
        try {
            if (configPersistence.getFileType().equals(ConfigPersistence.Invoice))
                jRadioInvoice.setSelected(true);
            else if (configPersistence.getFileType().equals(ConfigPersistence.IR))
                jRadioIR.setSelected(true);

            frequencyField.setText(configPersistence.getFrequency());

            inputFile.setText(configPersistence.getInputFolderPath());
            outputFile.setText(configPersistence.getOutputFolderPath());
            errorFile.setText(configPersistence.getErrorFolderPath());

            inputFileChooser.setSelectedFile(new File(configPersistence.getInputFolderPath()));
            outputFileChooser.setSelectedFile(new File(configPersistence.getOutputFolderPath()));
            errorFileChooser.setSelectedFile(new File(configPersistence.getErrorFolderPath()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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

        jRadioInvoice = new JRadioButton();
        jRadioIR = new JRadioButton();

        jRadioInvoice.setActionCommand(ConfigPersistence.Invoice);
        jRadioIR.setActionCommand(ConfigPersistence.IR);

        jRadioInvoice.setText("Boleto");
        jRadioIR.setText("Imposto de renda");

        jRadioInvoice.setBounds(200, 50, 80, 40);
        jRadioIR.setBounds(280, 50, 250, 40);

        documentTypeGroup = new ButtonGroup();
        documentTypeGroup.add(jRadioInvoice);
        documentTypeGroup.add(jRadioIR);
    }

    private void createFrequencyField() {
        frequencyLabel = new JLabel("Informe a periodicidade: ");
        frequencyLabel.setBounds(20, 60, 180, 40);

        frequencyField = new JTextField(5);
        frequencyField.setBounds(170, 70, 60, 23);

        frequency = new JLabel("horas");
        frequency.setBounds(240, 62, 50, 40);
    }

    private void createDirectoriesField() {
        fileLocation = new JLabel("Local do Arquivo: ");
        fileLocation.setBounds(20, 85, 110, 40);

        /// Input
        inputDirLabel = new JLabel("Entrada: ");
        inputDirLabel.setBounds(40, 110, 70, 40);

        inputDirButton = new JButton("Informe a pasta de entrada");
        inputDirButton.setBounds(100, 120, 200, 23);

        inputFile = new JLabel("");
        inputFile.setBounds(305, 120, 250, 23);

        /// Output
        outputDirLabel = new JLabel("Saída: ");
        outputDirLabel.setBounds(40, 140, 50, 40);
        outputDirButton = new JButton("Informe a pasta de saída");
        outputDirButton.setBounds(100, 150, 200, 23);
        outputFile = new JLabel("");
        outputFile.setBounds(305, 150, 250, 23);

        /// Error
        errorDirLabel = new JLabel("Erro: ");
        errorDirLabel.setBounds(40, 170, 50, 40);
        errorDirButton = new JButton("Informe a pasta de erros");
        errorDirButton.setBounds(100, 180, 200, 23);
        errorFile = new JLabel("");
        errorFile.setBounds(305, 180, 250, 23);

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

        contentPanel.add(jRadioInvoice);
        contentPanel.add(jRadioIR);

        this.add(jRadioInvoice);
        this.add(jRadioIR);
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

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

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
