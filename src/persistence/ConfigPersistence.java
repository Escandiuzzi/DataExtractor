package persistence;

import dtos.ConfigDto;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigPersistence {

    public static String Invoice = "Boleto";
    public static String IR = "IR";

    private static String filePath;

    private Gson gson;

    private ConfigDto config;

    public ConfigPersistence() {
        this(Paths.get("").toAbsolutePath().toString() + "/config.json");
    }

    public ConfigPersistence(String filePath)
    {
        gson = new Gson();
        config = new ConfigDto();

        this.filePath = filePath;

        try {
            File configFile = new File(this.filePath);
            configFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("Could not create config file");
        }

        load();
    }

    public void save()
    {
        try {
            Files.writeString(Path.of(filePath), gson.toJson(config), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.print("Invalid Path");
        }
    }

    public void load() {

        try {
            Gson gson = new Gson();

            Reader reader = Files.newBufferedReader(Paths.get(filePath));

            config = gson.fromJson(reader, ConfigDto.class);
            reader.close();

            if (config == null)
                updateFileWithEmptyConfig();

            this.setFileType(config.fileTypeSelected);
            this.setFrequency(config.frequency);
            this.setInputFolderPath(config.inputFolderPath);
            this.setOutputFolderPath(config.outputFolderPath);
            this.setErrorFolderPath(config.errorFolderPath);
            this.setBeneficiaryConfig(config.invoiceConfigDto.beneficiary);
            this.setCnpjConfig(config.invoiceConfigDto.cnpj);
            this.setPayerConfig(config.invoiceConfigDto.payer);
            this.setCpfConfig(config.invoiceConfigDto.cpf);
            this.setBeneficiaryCodeConfig(config.invoiceConfigDto.beneficiaryCode);
            this.setDueDateConfig(config.invoiceConfigDto.dueDate);
            this.setOurNumberConfig(config.invoiceConfigDto.ourNumber);
            this.setDocumentPriceConfig(config.invoiceConfigDto.documentPrice);
            this.setDocumentNumberConfig(config.invoiceConfigDto.documentNumber);
            this.setDocumentNumberConfig(config.invoiceConfigDto.documentNumber);
            this.setAdditionConfig(config.invoiceConfigDto.addition);
            this.setChargedValueConfig(config.invoiceConfigDto.chargedValue);
            this.setDocumentDateConfig(config.invoiceConfigDto.documentDate);
            this.setDiscountConfig(config.invoiceConfigDto.discount);
            this.setCurrencyConfig(config.invoiceConfigDto.currency);
            this.setOtherDeductionsConfig(config.invoiceConfigDto.otherDeductions);
            this.setPenaltyConfig(config.invoiceConfigDto.penalty);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setFileType(String fileTypeSelected) {
        config.fileTypeSelected = fileTypeSelected;
    }

    public String getFileType() {
        return config.fileTypeSelected;
    }

    public void setFrequency(String frequency) {
        config.frequency = frequency;
    }

    public String getFrequency() {
        return config.frequency;
    }

    public void setInputFolderPath(String inputFolderPath) {
        config.inputFolderPath = inputFolderPath;
    }

    public String getInputFolderPath() {
        return config.inputFolderPath;
    }

    public void setOutputFolderPath(String outputFolderPath) {
        config.outputFolderPath = outputFolderPath;
    }

    public String getOutputFolderPath() {
        return config.outputFolderPath;
    }

    public void setErrorFolderPath(String errorFolderPath) {
        config.errorFolderPath = errorFolderPath;
    }

    public String getErrorFolderPath() {

        return config.errorFolderPath;
    }

    public boolean getBeneficiaryConfig() { return config.invoiceConfigDto.beneficiary; }

    public void setBeneficiaryConfig(boolean exportBeneficiary) { config.invoiceConfigDto.beneficiary = exportBeneficiary; }

    public boolean getCnpjConfig() { return config.invoiceConfigDto.cnpj; }

    public void setCnpjConfig(boolean exportCnpj) { config.invoiceConfigDto.cnpj = exportCnpj; }

    public boolean getPayerConfig() { return config.invoiceConfigDto.payer; }

    public void setPayerConfig(boolean exportPayer) { config.invoiceConfigDto.payer = exportPayer; }

    public boolean getCpfConfig() { return config.invoiceConfigDto.cpf; }

    public void setCpfConfig(boolean exportCpf) { config.invoiceConfigDto.cpf = exportCpf; }

    public boolean getBeneficiaryCodeConfig() { return config.invoiceConfigDto.beneficiaryCode; }

    public void setBeneficiaryCodeConfig(boolean exportBeneficiaryCode) { config.invoiceConfigDto.beneficiaryCode = exportBeneficiaryCode; }

    public boolean getDueDateConfig() { return config.invoiceConfigDto.dueDate; }

    public void setDueDateConfig(boolean exportDueDate) { config.invoiceConfigDto.dueDate = exportDueDate; }

    public boolean getOurNumberConfig() { return config.invoiceConfigDto.ourNumber; }

    public void setOurNumberConfig(boolean exportOurNumber) { config.invoiceConfigDto.ourNumber = exportOurNumber; }

    public boolean getDocumentPriceConfig() { return config.invoiceConfigDto.documentPrice; }

    public void setDocumentPriceConfig(boolean exportDocumentPrice) { config.invoiceConfigDto.documentPrice = exportDocumentPrice; }

    public boolean getDocumentNumberConfig() { return config.invoiceConfigDto.documentNumber; }

    public void setDocumentNumberConfig(boolean exportDocumentNumber) { config.invoiceConfigDto.documentNumber = exportDocumentNumber; }

    public boolean getAdditionConfig() { return config.invoiceConfigDto.addition; }

    public void setAdditionConfig(boolean exportAddition) { config.invoiceConfigDto.addition = exportAddition; }

    public boolean getChargedValueConfig() { return config.invoiceConfigDto.chargedValue; }

    public void setChargedValueConfig(boolean exportChargedValue) { config.invoiceConfigDto.chargedValue = exportChargedValue; }

    public boolean getDocumentDateConfig() { return config.invoiceConfigDto.documentDate; }

    public void setDocumentDateConfig(boolean exportDocumentDate) { config.invoiceConfigDto.documentDate = exportDocumentDate; }

    public boolean getDiscountConfig() { return config.invoiceConfigDto.discount; }

    public void setDiscountConfig(boolean exportDiscount) { config.invoiceConfigDto.discount = exportDiscount; }

    public boolean getCurrencyConfig() { return config.invoiceConfigDto.currency; }

    public void setCurrencyConfig(boolean exportCurrency) { config.invoiceConfigDto.currency = exportCurrency; }

    public boolean getOtherDeductionsConfig() { return config.invoiceConfigDto.otherDeductions; }

    public void setOtherDeductionsConfig(boolean exportOtherDeductions) { config.invoiceConfigDto.otherDeductions = exportOtherDeductions; }

    public boolean getPenaltyConfig() { return config.invoiceConfigDto.penalty; }

    public void setPenaltyConfig(boolean exportPenalty) { config.invoiceConfigDto.penalty = exportPenalty; }

    private void updateFileWithEmptyConfig() {
        config = new ConfigDto();
        save();
    }
}
