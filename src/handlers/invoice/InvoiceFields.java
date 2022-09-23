package handlers.invoice;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvoiceFields {

    public static final Pattern invoiceCodePattern = Pattern.compile("(\\d{9}\\.\\d{1} \\d{10}\\.\\d{1} \\d{10}\\.\\d{1} \\d \\d{14})");

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
            Arrays.asList(Beneficiary, Cnpj, Payer, Cpf, BeneficiaryCode, DueDate, OurNumber,
                    DocumentPrice, DocumentNumber, Addition, ChargedValue, DocumentDate, Discount,
                    Currency, OtherDeductions, Penalty);

    public static final List<InvoiceField> invoiceFieldsSantander =
            Arrays.asList(
                    new InvoiceField("Beneficiário:", Beneficiary, 0,10,270,10),
                    new InvoiceField("Beneficiário:", Cnpj, 34,20, 100,10),
                    new InvoiceField("Pagador:", Payer, 0,20,105,10),
                    new InvoiceField("Pagador:", Cpf, 134,20,100,10),
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
}
