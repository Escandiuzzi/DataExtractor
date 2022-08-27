package Handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandler {
    final Pattern cnpjPattern = Pattern.compile("([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\n");
    final Pattern agencyCodePattern = Pattern.compile("(([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([+-]?\\d+))?( ([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([+-]?\\d+))?)+)\n");
    final Pattern documentCodePattern = Pattern.compile("(\\d{9}\\.\\d{1} \\d{10}\\.\\d{1} \\d{10}\\.\\d{1} \\d \\d{14})");



    public void ShowData(String text) {

        Matcher cnpjMatcher = cnpjPattern.matcher(text);
        cnpjMatcher.find();

        Matcher agencyCodePatternMatcher = agencyCodePattern.matcher(text);
        agencyCodePatternMatcher.find();

        Matcher documentCodeMatcher = documentCodePattern.matcher(text);
        documentCodeMatcher.find();

        System.out.println("CNPJ: " + cnpjMatcher.group(0));
        System.out.println("Código Beneficiario: " + agencyCodePatternMatcher.group(0));
        System.out.println("Código Boleto: " + documentCodeMatcher.group(0));
    }


}
