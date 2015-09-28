/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package uk.co.xenonique.nationalforce;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.*;

/**
 * The type GeneratorTest
 *
 * @author Peter Pilgrim
 */
public class GeneratorTest {

    private Map<String, String> mapCodeToCountries = new TreeMap<>();
    private Map<String, String> mapCountriesToCode = new TreeMap<>();


    @Test
    public void blank() {
    }

    @Test
    @Ignore
    public void createEntity() throws IOException {
        final StringReader stringReader = new StringReader(data);
        final LineNumberReader reader = new LineNumberReader(stringReader);

        String line;
        while ((line = reader.readLine()) != null) {
            StringTokenizer stk = new StringTokenizer(line, "\t");
            if (stk.hasMoreTokens()) {
                final String country = stk.nextToken().trim();
                if (stk.hasMoreTokens()) {
                    final String code = stk.nextToken().trim();
                    mapCodeToCountries.put(code, country);
                    mapCountriesToCode.put(country, code);
                }
            }
        }

        System.out.println("List<PassportCountry> countries = new ArrayList<>();");
        mapCountriesToCode.entrySet().stream().forEach(e ->
                System.out.printf("countries.add( new PassportCountry( \"%s\", \"%s\" ) );\n", e.getKey(), e.getValue()));

        System.out.println("\n\n");

        System.out.println("var countryToCodeArrayMap = [");
        mapCountriesToCode.entrySet().stream().forEach(e ->
                System.out.printf("    { \"country\": \"%s\", \"code\":  \"%s\" },\n", e.getKey(), e.getValue()));
        System.out.println("];");

        System.out.println("\n\n");

        System.out.println("var codeToCountryArrayMap = [");
        mapCodeToCountries.entrySet().stream().forEach(e ->
                System.out.printf("    { \"code\": \"%s\", \"country\":  \"%s\" },\n", e.getKey(), e.getValue()));
        System.out.println("];");
    }

    static final String data = "Afghanistan\tAFG\n" +
            "Albania\tALB\n" +
            "Algeria\tDZA\n" +
            "American Samoa\tASM\n" +
            "Andorra\tAND\n" +
            "Angola\tAGO\n" +
            "Anguilla\tAIA\n" +
            "Antarctica\tATA\n" +
            "Antigua and Barbuda\tATG\n" +
            "Argentina\tARG\n" +
            "Armenia\tARM\n" +
            "Aruba\tABW\n" +
            "Australia\tAUS\n" +
            "Austria\tAUT\n" +
            "Azerbaijan\tAZE\n" +
            "Bahamas\tBHS\n" +
            "Bahrain\tBHR\n" +
            "Bangladesh\tBGD\n" +
            "Barbados\tBRB\n" +
            "Belarus\tBLR\n" +
            "Belgium\tBEL\n" +
            "Belize\tBLZ\n" +
            "Benin\tBEN\n" +
            "Bermuda\tBMU\n" +
            "Bhutan\tBTN\n" +
            "Bolivia\tBOL\n" +
            "Bosnia and Herzegovina\tBIH\n" +
            "Botswana\tBWA\n" +
            "Bouvet Island\tBVT\n" +
            "Brazil\tBRA\n" +
            "British Indian Ocean Territory\tIOT\n" +
            "Brunei Darussalam\tBRN\n" +
            "Bulgaria\tBGR\n" +
            "Burkina Faso\tBFA\n" +
            "Burundi\tBDI\n" +
            "Cambodia\tKHM\n" +
            "Cameroon\tCMR\n" +
            "Canada\tCAN\n" +
            "Cape Verde\tCPV\n" +
            "Cayman Islands\tCYM\n" +
            "Central African Republic\tCAF\n" +
            "Chad\tTCD\n" +
            "Chile\tCHL\n" +
            "China\tCHN\n" +
            "Christmas Island\tCXR\n" +
            "Cocos (Keeling) Islands\tCCK\n" +
            "Colombia\tCOL\n" +
            "Comoros\tCOM\n" +
            "Congo\tCOG\n" +
            "Cook Islands\tCOK\n" +
            "Costa Rica\tCRI\n" +
            "Côte d'Ivoire\tCIV\n" +
            "Croatia\tHRV\n" +
            "Cuba\tCUB\n" +
            "Cyprus\tCYP\n" +
            "Czech Republic\tCZE\n" +
            "Democratic People's Republic of Korea\tPRK\n" +
            "Democratic Republic of the Congo\tCOD\n" +
            "Denmark\tDNK\n" +
            "Djibouti\tDJI\n" +
            "Dominica\tDMA\n" +
            "Dominican Republic\tDOM\n" +
            "East Timor\tTMP\n" +
            "Ecuador\tECU\n" +
            "Egypt\tEGY\n" +
            "El Salvador\tSLV\n" +
            "Equatorial Guinea\tGNQ\n" +
            "Eritrea\tERI\n" +
            "Estonia\tEST\n" +
            "Ethiopia\tETH\n" +
            "Falkland Islands (Malvinas)\tFLK\n" +
            "Faeroe Islands\tFRO\n" +
            "Fiji\tFJI\n" +
            "Finland\tFIN\n" +
            "France\tFRA\n" +
            "France, Metropolitan\tFXX\n" +
            "French Guiana\tGUF\n" +
            "French Polynesia\tPYF\n" +
            "Gabon\tGAB\n" +
            "Gambia\tGMB\n" +
            "Georgia\tGEO\n" +
            "Germany\tD\n" +
            "Ghana\tGHA\n" +
            "Gibraltar\tGIB\n" +
            "Greece\tGRC\n" +
            "Greenland\tGRL\n" +
            "Grenada\tGRD\n" +
            "Guadeloupe\tGLP\n" +
            "Guam\tGUM\n" +
            "Guatemala\tGTM\n" +
            "Guinea\tGIN\n" +
            "Guinea-Bissau\tGNB\n" +
            "Guyana\tGUY\n" +
            "Haiti\tHTI\n" +
            "Heard and McDonald Islands\tHMD\n" +
            "Holy See (Vatican City State)\tVAT\n" +
            "Honduras\tHND\n" +
            "Hong Kong\tHKG\n" +
            "Hungary\tHUN\n" +
            "Iceland\tISL\n" +
            "India\tIND\n" +
            "Indonesia\tIDN\n" +
            "Iran, Islamic Republic of\tIRN\n" +
            "Iraq\tIRQ\n" +
            "Ireland\tIRL\n" +
            "Israel\tISR\n" +
            "Italy\tITA\n" +
            "Jamaica\tJAM\n" +
            "Japan\tJPN\n" +
            "Jordan\tJOR\n" +
            "Kazakhstan\tKAZ\n" +
            "Kenya\tKEN\n" +
            "Kiribati\tKIR\n" +
            "Kuwait\tKWT\n" +
            "Kyrgyzstan\tKGZ\n" +
            "Lao People's Democratic Republic\tLAO\n" +
            "Latvia\tLVA\n" +
            "Lebanon\tLBN\n" +
            "Lesotho\tLSO\n" +
            "Liberia\tLBR\n" +
            "Libyan Arab Jamahiriya\tLBY\n" +
            "Liechtenstein\tLIE\n" +
            "Lithuania\tLTU\n" +
            "Luxembourg\tLUX\n" +
            "Madagascar\tMDG\n" +
            "Malawi\tMWI\n" +
            "Malaysia\tMYS\n" +
            "Maldives\tMDV\n" +
            "Mali\tMLI\n" +
            "Malta\tMLT\n" +
            "Marshall Islands\tMHL\n" +
            "Martinique\tMTQ\n" +
            "Mauritania\tMRT\n" +
            "Mauritius\tMUS\n" +
            "Mayotte\tMYT\n" +
            "Mexico\tMEX\n" +
            "Micronesia, Federated States of\tFSM\n" +
            "Monaco\tMCO\n" +
            "Mongolia\tMNG\n" +
            "Montserrat\tMSR\n" +
            "Morocco\tMAR\n" +
            "Mozambique\tMOZ\n" +
            "Myanmar\tMMR\n" +
            "Namibia\tNAM\n" +
            "Nauru\tNRU\n" +
            "Nepal\tNPL\n" +
            "Netherlands, Kingdom of the\tNLD\n" +
            "Netherlands Antilles\tANT\n" +
            "Neutral Zone\tNTZ\n" +
            "New Caledonia\tNCL\n" +
            "New Zealand\tNZL\n" +
            "Nicaragua\tNIC\n" +
            "Niger\tNER\n" +
            "Nigeria\tNGA\n" +
            "Niue\tNIU\n" +
            "Norfolk Island\tNFK\n" +
            "Northern Mariana Islands\tMNP\n" +
            "Norway\tNOR\n" +
            "Oman\tOMN\n" +
            "Pakistan\tPAK\n" +
            "Palau\tPLW\n" +
            "Panama\tPAN\n" +
            "Papua New Guinea\tPNG\n" +
            "Paraguay\tPRY\n" +
            "Peru\tPER\n" +
            "Philippines\tPHL\n" +
            "Pitcairn\tPCN\n" +
            "Poland\tPOL\n" +
            "Portugal\tPRT\n" +
            "Puerto Rico\tPRI\n" +
            "Qatar\tQAT\n" +
            "Republic of Korea\tKOR\n" +
            "Republic of Moldova\tMDA\n" +
            "Réunion\tREU\n" +
            "Romania\tROM\n" +
            "Russian Federation\tRUS\n" +
            "Rwanda\tRWA\n" +
            "Saint Helena\tSHN\n" +
            "Saint Kitts and Nevis\tKNA\n" +
            "Saint Lucia\tLCA\n" +
            "Saint Pierre and Miquelon\tSPM\n" +
            "Saint Vincent and the Grenadines\tVCT\n" +
            "Samoa\tWSM\n" +
            "San Marino\tSMR\n" +
            "Sao Tome and Principe\tSTP\n" +
            "Saudi Arabia\tSAU\n" +
            "Senegal\tSEN\n" +
            "Seychelles\tSYC\n" +
            "Sierra Leone\tSLE\n" +
            "Singapore\tSGP\n" +
            "Slovakia\tSVK\n" +
            "Slovenia\tSVN\n" +
            "Solomon Islands\tSLB\n" +
            "Somalia\tSOM\n" +
            "South Africa\tZAF\n" +
            "South Georgia and the South Sandwich Island\tSGS\n" +
            "Spain\tESP\n" +
            "Sri Lanka\tLKA\n" +
            "Sudan\tSDN\n" +
            "Suriname\tSUR\n" +
            "Svalbard and Jan Mayen Islands\tSJM\n" +
            "Swaziland\tSWZ\n" +
            "Sweden\tSWE\n" +
            "Switzerland\tCHE\n" +
            "Syrian Arab Republic\tSYR\n" +
            "Taiwan Province of China\tTWN\n" +
            "Tajikistan\tTJK\n" +
            "Thailand\tTHA\n" +
            "The former Yugoslav Republic of Macedonia\tMKD\n" +
            "Togo\tTGO\n" +
            "Tokelau\tTKL\n" +
            "Tonga\tTON\n" +
            "Trinidad and Tobago\tTTO\n" +
            "Tunisia\tTUN\n" +
            "Turkey\tTUR\n" +
            "Turkmenistan\tTKM\n" +
            "Turks and Caicos Islands\tTCA\n" +
            "Tuvalu\tTUV\n" +
            "Uganda\tUGA\n" +
            "Ukraine\tUKR\n" +
            "United Arab Emirates\tARE\n" +
            "United Kingdom of Great Britain and Northern Ireland\t\n" +
            "United Kingdom - Citizen\tGBR\n" +
            "United Kingdom - Dependent territories citizen\tGBD\n" +
            "United Kingdom - National (overseas)\tGBN\n" +
            "United Kingdom - Overseas citizen\tGBO\n" +
            "United Kingdom - Protected Person\tGBP\n" +
            "United Kingdom - Subject\tGBS\n" +
            "United Republic of Tanzania\tTZA\n" +
            "United States of America\tUSA\n" +
            "United States of America Minor Outlying Islands\tUMI\n" +
            "Uruguay\tURY\n" +
            "Uzbekistan\tUZB\n" +
            "Vanuatu\tVUT\n" +
            "Venezuela\tVEN\n" +
            "Viet Nam\tVNM\n" +
            "Virgin Islands (Great Britian)\tVGB\n" +
            "Virgin Islands (United States)\tVIR\n" +
            "Wallis and Futuna Islands\tWLF\n" +
            "Western Sahara\tESH\n" +
            "Yemen\tYEM\n" +
            "Zaire\tZAR\n" +
            "Zambia\tZMB\n" +
            "Zimbabwe\tZWE\n" +
            "United Nations Organization\n" +
            "  (If indicating nationality,\n" +
            "indicates an UN offical)\tUNO\n" +
            "United Nations\n" +
            "  specialized agency official\tUNA\n" +
            "Stateless (per Article 1 of 1954 convention)\tXXA\n" +
            "Refugee\n" +
            "(per Article 1 of 1951 convention,\n" +
            "amended by 1967 protocol)\tXXB\n" +
            "Refugee (non-convention)\tXXC\n" +
            "Unspecified / Unknown\tXXX\n";
}
