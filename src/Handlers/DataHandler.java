package Handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandler {
    final Pattern cnpjPattern = Pattern.compile("([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\n");
    final Pattern agencyCodePattern = Pattern.compile("\\d{4}(\\.\\d{1}) \\d{7}");
    final Pattern documentCodePattern = Pattern.compile("(\\d{9}\\.\\d{1} \\d{10}\\.\\d{1} \\d{10}\\.\\d{1} \\d \\d{14})");
    final Pattern cpfPattern = Pattern.compile("\\d{3}.\\d{3}.\\d{3}-?\\d{2}" );
    final Pattern datePattern = Pattern.compile("([0-9]{2})/([0-9]{2})/([0-9]{4})");
    final Pattern moneyPattern = Pattern.compile("\\d{1,4}(\\.\\d{3})*,\\d{2}");

    public void ShowData(String text) {

        Matcher cnpjMatcher = cnpjPattern.matcher(text);
        cnpjMatcher.find();

        Matcher agencyCodePatternMatcher = agencyCodePattern.matcher(text);
        agencyCodePatternMatcher.find();

        Matcher documentCodeMatcher = documentCodePattern.matcher(text);
        documentCodeMatcher.find();

        Matcher cpfMatcher = cpfPattern.matcher(text);
        cpfMatcher.find();

        Matcher dateMatcher = datePattern.matcher(text);
        dateMatcher.find();

        Matcher moneyMatcher = moneyPattern.matcher(text);
        moneyMatcher.find();

        PrintIfValid("CNPJ: ", cnpjMatcher);
        PrintIfValid("Código Beneficiario: ", agencyCodePatternMatcher);
        PrintIfValid("Código Boleto: ", documentCodeMatcher);
        PrintIfValid("CPF: ", cpfMatcher);
        PrintIfValid("Data de vencimento: ", dateMatcher);
        PrintIfValid("Valor: ", moneyMatcher);
    }

    private void PrintIfValid(String prefix, Matcher matcher) {
        try {
            System.out.println(prefix + matcher.group(0));
        } catch (IllegalStateException ex) {
            System.out.println("Could not find " + prefix);
        }
    }
}
