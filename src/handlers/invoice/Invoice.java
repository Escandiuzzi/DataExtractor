package handlers.invoice;

import java.util.Arrays;
import java.util.List;

public class Invoice {
    public static final String Santander = "Santander";
    public static final String NuBank = "Nu";

    public static final String Banrisul = "Banrisul";
    public static final String Sicredi = "Sicredi";

    public static final String InvoiceCode = "InvoiceCode";
    public static final String Beneficiary = "Beneficiary";
    public static final String Cnpj = "Cnpj";
    public static final String Payer = "Payer";
    public static final String Cpf = "Cpf";
    public static final String BeneficiaryCode = "BeneficiaryCode";
    public static final String DueDate = "DueDate";
    public static final String OurNumber = "OurNumber";
    public static final String DocumentPrice = "DocumentPrice";
    public static final String DocumentNumber = "DocumentNumber";
    public static final String Addition = "Addition";
    public static final String ChargedValue = "ChargedValue";
    public static final String DocumentDate = "DocumentDate";
    public static final String Discount = "Discount";
    public static final String Currency = "Currency";
    public static final String OtherDeductions = "OtherDeductions";
    public static final String Penalty = "Penalty";

    public static final List<String> invoiceFieldsKeys =
            Arrays.asList(InvoiceCode, Beneficiary, Cnpj, Payer, Cpf, BeneficiaryCode, DueDate, OurNumber,
                    DocumentPrice, DocumentNumber, Addition, ChargedValue, DocumentDate, Discount,
                    Currency, OtherDeductions, Penalty);

    public static final List<InvoiceField> invoiceFieldsSantander =
            Arrays.asList(
                    new InvoiceField("Local de Pagamento", InvoiceCode, 210,-10,350,10),
                    new InvoiceField("Beneficiário:", Beneficiary, 0,10,270,10),
                    new InvoiceField("CNPJ:", Cnpj, 25,0, 100,10),
                    new InvoiceField("Pagador:", Payer, 0,20,105,10),
                    new InvoiceField("Cpf:", Cpf, 20,0,100,10),
                    new InvoiceField("Agência/Código Beneficiário:", BeneficiaryCode, 0,3,120,10),
                    new InvoiceField("Vencimento:", DueDate, 0,3,130,10),
                    new InvoiceField("Nosso Número:", OurNumber,  0,3,120,10),
                    new InvoiceField("Valor do Documento:", DocumentPrice, 0,3,130,10),
                    new InvoiceField("Número Documento:", DocumentNumber, 0,3,120,10),
                    new InvoiceField("Acréscimos:", Addition, 0,3,130,10),
                    new InvoiceField("Valor Cobrado:", ChargedValue, 0,3,130,10),
                    new InvoiceField("Data do Documento", DocumentDate, 0,10,100,10),
                    new InvoiceField("Desconto/Abatimento", Discount, 0,10,150,10),
                    new InvoiceField("Moeda", Currency, 0,10,100,10),
                    new InvoiceField("Outras Deduções", OtherDeductions, 0,10,150,10),
                    new InvoiceField("Mora/Multa", Penalty, 0,10,150,10)
            );

    public static final List<InvoiceField> invoiceFieldsNuBank =
            Arrays.asList(
                    new InvoiceField("260-7", InvoiceCode, 40,0,400,10),
                    new InvoiceField("Beneficiário", Beneficiary, 60,0,270,10),
                    new InvoiceField("CNPJ/CPF", Cnpj, 0,10, 100,10),
                    new InvoiceField("Pagador", Payer, 30,0,105,10),
                    new InvoiceField("Pagador", Cpf, 134,0,100,10),
                    new InvoiceField("Agência / Código do Beneficiário", BeneficiaryCode, 0,5,120,10),
                    new InvoiceField("Vencimento", DueDate, -10,7,130,10),
                    new InvoiceField("Nosso Número", OurNumber, -10,5,200,10),
                    new InvoiceField("Valor do Documento", DocumentPrice, 0,3,200,20),
                    new InvoiceField("Nº do Documento", DocumentNumber, 0,5,90,10),
                    new InvoiceField("Outros Acréscimos", Addition, 0,5,200,10),
                    new InvoiceField("Valor Cobrado", ChargedValue, 0,7,300,20),
                    new InvoiceField("Data do Documento", DocumentDate, -5,10,100,10),
                    new InvoiceField("Desconto / Abatimento", Discount, 0,10,150,10),
                    new InvoiceField("Moeda", Currency, -20,5,100,10),
                    new InvoiceField("Outras Deduções", OtherDeductions, 0,10,150,10),
                    new InvoiceField("Mora / Multa", Penalty, 0,10,150,10)
            );

    public static final List<InvoiceField> invoiceFieldsSicredi =
            Arrays.asList(
                    new InvoiceField("748-X", InvoiceCode, 70,0,400,10),
                    new InvoiceField("Beneficiário", Beneficiary, 0,5,270,10),
                    new InvoiceField("CNPJ/CPF", Cnpj, 40,0, 100,5),
                    new InvoiceField("Pagador:", Payer, 25,0,50,10),
                    new InvoiceField("Pagador:", Cpf, 75,0,100,10),
                    new InvoiceField("Agência / Código Beneficiário", BeneficiaryCode, 0,5,120,10),
                    new InvoiceField("Vencimento", DueDate, -10,7,130,10),
                    new InvoiceField("Nosso Número", OurNumber, -10,5,200,10),
                    new InvoiceField("Valor do Documento", DocumentPrice, 0,3,200,20),
                    new InvoiceField("No. do documento", DocumentNumber, 0,5,120,10),
                    new InvoiceField("Outros Acréscimos", Addition, 0,5,200,10),
                    new InvoiceField("Valor Cobrado", ChargedValue, 0,7,300,20),
                    new InvoiceField("Data Emissão:", DocumentDate, 0,2,200,10),
                    new InvoiceField("Descontos / Abatimento", Discount, 0,3,150,10),
                    new InvoiceField("Carteira", Currency, 40,5,100,10),
                    new InvoiceField("Outras Deduções", OtherDeductions, 0,3,150,10),
                    new InvoiceField("Mora / Multa", Penalty, 0,3,150,10)
            );

    public static final List<InvoiceField> invoiceFieldsBanrisul =
            Arrays.asList(
                    new InvoiceField("SAC BANRISUL", InvoiceCode, 140,80,400,10),
                    new InvoiceField("Nome do Beneficiário", Beneficiary, 0,5,200,10),
                    new InvoiceField("Nome do Beneficiário", Cnpj, 200,5, 200,10),
                    new InvoiceField("Pagador:", Payer, 30,0,150,10),
                    new InvoiceField("Pagador:", Cpf, 180,0,100,10),
                    new InvoiceField("Agência / Código Beneficiário", BeneficiaryCode, 0,5,120,10),
                    new InvoiceField("Vencimento", DueDate, -10,7,130,10),
                    new InvoiceField("Nosso Número", OurNumber, -10,5,200,10),
                    new InvoiceField("Valor Documento", DocumentPrice, 0,3,200,10),
                    new InvoiceField("Número do Documento", DocumentNumber, 0,5,120,10),
                    new InvoiceField("Outros Acréscimos", Addition, 0,5,200,10),
                    new InvoiceField("Valor Cobrado", ChargedValue, 0,7,300,20),
                    new InvoiceField("Data Documento", DocumentDate, 0,2,200,10),
                    new InvoiceField("Desconto/Abatimento", Discount, 0,3,150,10),
                    new InvoiceField("Número Do Documento", Currency, 40,20,100,10),
                    new InvoiceField("Outras Deduções", OtherDeductions, 0,3,150,10),
                    new InvoiceField("Mora e Multa", Penalty, 0,3,150,10)
            );
}
