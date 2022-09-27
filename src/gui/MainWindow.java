package gui;

import persistence.ConfigPersistence;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWindow extends JDialog implements ActionListener {
    JPanel panel, contentPanel, invoiceCheckboxPanel, bottomPanel;
    JRadioButton jRadioInvoice, jRadioIR;
    ButtonGroup documentTypeGroup;
    JLabel header, title, documentTypeLabel, frequencyLabel, inputDirLabel, outputDirLabel, errorDirLabel,
            frequency, fileLocation, inputFile, outputFile, errorFile;

    JCheckBox beneficiary, cnpj, payer, cpf, beneficiaryCode, dueDate, ourNumber, documentPrice, documentNumber,
            addition, chargedValue, documentDate, discount, currency, otherDeductions, penalty;
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
        createInvoiceFieldsPanel();

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

        jRadioInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoiceCheckboxPanel.setVisible(true);
            }
        });

        jRadioIR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoiceCheckboxPanel.setVisible(false);
            }
        });


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

    private void createInvoiceFieldsPanel() {
        invoiceCheckboxPanel = new JPanel(new GridLayout(6, 4));

        beneficiary = new JCheckBox("Beneficiario");
        cnpj = new JCheckBox("CNPJ");
        payer = new JCheckBox("Pagador");
        cpf = new JCheckBox("CPF");
        beneficiaryCode = new JCheckBox("Cod Beneficiario");
        dueDate = new JCheckBox("Vencimento");
        ourNumber = new JCheckBox("Nosso Nº");
        documentPrice = new JCheckBox("Valor");
        documentNumber = new JCheckBox("Nº Documento");
        addition = new JCheckBox("Acréscimos");
        chargedValue = new JCheckBox("Valor Cobrado");
        documentDate= new JCheckBox("Data Documento");
        discount = new JCheckBox("Desconto");
        currency = new JCheckBox("Moeda");
        otherDeductions = new JCheckBox("Deducoes");
        penalty = new JCheckBox("Multa");

        invoiceCheckboxPanel.add(beneficiary, 0, 0);
        invoiceCheckboxPanel.add(cnpj);
        invoiceCheckboxPanel.add(payer);
        invoiceCheckboxPanel.add(cpf);
        invoiceCheckboxPanel.add(beneficiaryCode);
        invoiceCheckboxPanel.add(dueDate);
        invoiceCheckboxPanel.add(ourNumber);
        invoiceCheckboxPanel.add(documentPrice);
        invoiceCheckboxPanel.add(documentNumber);
        invoiceCheckboxPanel.add(addition);
        invoiceCheckboxPanel.add(chargedValue);
        invoiceCheckboxPanel.add(documentDate);
        invoiceCheckboxPanel.add(discount);
        invoiceCheckboxPanel.add(currency);
        invoiceCheckboxPanel.add(otherDeductions);
        invoiceCheckboxPanel.add(penalty);

        invoiceCheckboxPanel.setBounds(20, 205, 550, 100);

        contentPanel.add(invoiceCheckboxPanel);

        initializeInvoiceCheckbox();
    }

    private void initializeInvoiceCheckbox() {
        beneficiary.setSelected(configPersistence.getBeneficiaryConfig());
        cnpj.setSelected(configPersistence.getCnpjConfig());
        payer.setSelected(configPersistence.getPayerConfig());
        cpf.setSelected(configPersistence.getCpfConfig());
        beneficiaryCode.setSelected(configPersistence.getBeneficiaryCodeConfig());
        dueDate.setSelected(configPersistence.getDueDateConfig());
        ourNumber.setSelected(configPersistence.getOurNumberConfig());
        documentPrice.setSelected(configPersistence.getDocumentPriceConfig());
        documentNumber.setSelected(configPersistence.getDocumentNumberConfig());
        addition.setSelected(configPersistence.getAdditionConfig());
        chargedValue.setSelected(configPersistence.getChargedValueConfig());
        documentDate.setSelected(configPersistence.getDocumentDateConfig());
        discount.setSelected(configPersistence.getDiscountConfig());
        currency.setSelected(configPersistence.getCurrencyConfig());
        otherDeductions.setSelected(configPersistence.getOtherDeductionsConfig());
        penalty.setSelected(configPersistence.getPenaltyConfig());
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

                updateConfigInvoiceFields();


                configPersistence.save();
            }

            private void updateConfigInvoiceFields() {
                configPersistence.setBeneficiaryConfig(beneficiary.isSelected());
                configPersistence.setCnpjConfig(cnpj.isSelected());
                configPersistence.setPayerConfig(payer.isSelected());
                configPersistence.setCpfConfig(cpf.isSelected());
                configPersistence.setBeneficiaryCodeConfig(beneficiaryCode.isSelected());
                configPersistence.setDueDateConfig(dueDate.isSelected());
                configPersistence.setOurNumberConfig(ourNumber.isSelected());
                configPersistence.setDocumentPriceConfig(documentPrice.isSelected());
                configPersistence.setDocumentNumberConfig(documentNumber.isSelected());
                configPersistence.setAdditionConfig(addition.isSelected());
                configPersistence.setChargedValueConfig(chargedValue.isSelected());
                configPersistence.setDocumentDateConfig(documentDate.isSelected());
                configPersistence.setDiscountConfig(discount.isSelected());
                configPersistence.setCurrencyConfig(currency.isSelected());
                configPersistence.setOtherDeductionsConfig(otherDeductions.isSelected());
                configPersistence.setPenaltyConfig(penalty.isSelected());
            }
        });
    }
}
