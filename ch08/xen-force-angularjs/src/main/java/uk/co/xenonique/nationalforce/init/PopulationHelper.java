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

package uk.co.xenonique.nationalforce.init;

import uk.co.xenonique.nationalforce.entity.PassportCountry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * The type PopulationHelper
 *
 * @author Peter Pilgrim
 */
@Stateless
public class PopulationHelper {

    private static String[] girls = new String[]{
            "Sophia",
            "Emma",
            "Olivia",
            "Ava",
            "Isabella",
            "Mia",
            "Zoe",
            "Lily",
            "Emily",
            "Madelyn",
            "Madison",
            "Chloe",
            "Charlotte",
            "Aubrey",
            "Avery",
            "Abigail",
            "Kaylee",
            "Layla",
            "Harper",
            "Ella",
            "Amelia",
            "Arianna",
            "Riley",
            "Aria",
            "Hailey",
            "Hannah",
            "Aaliyah",
            "Evelyn",
            "Addison",
            "Mackenzie",
            "Adalyn",
            "Ellie",
            "Brooklyn",
            "Nora",
            "Scarlett",
            "Grace",
            "Anna",
            "Isabelle",
            "Natalie",
            "Kaitlyn",
            "Lillian",
            "Sarah",
            "Audrey",
            "Elizabeth",
            "Leah",
            "Annabelle",
            "Kylie",
            "Mila",
            "Claire",
            "Victoria",
            "Maya",
            "Lila",
            "Elena",
            "Lucy",
            "Savannah",
            "Gabriella",
            "Sabrina",
            "Frances",
            "Laura",
            "Deborah",
            "Terri",
            "Zara"
    };

    private static String[] boys = new String[]{
            "Logan",
            "Jayden",
            "Elijah",
            "Jack",
            "Luke",
            "Michael",
            "Benjamin",
            "Alexander",
            "Peter",
            "James",
            "Jayce",
            "Caleb",
            "Connor",
            "William",
            "Carter",
            "Ryan",
            "Oliver",
            "Matthew",
            "Daniel",
            "Gabriel",
            "Henry",
            "Owen",
            "Grayson",
            "Dylan",
            "Landon",
            "Isaac",
            "Nicholas",
            "Wyatt",
            "Nathan",
            "Andrew",
            "Cameron",
            "Dominic",
            "Joshua",
            "Eli",
            "Sebastian",
            "Hunter",
            "Brayden",
            "David",
            "Samuel",
            "Evan",
            "Gavin",
            "Christian",
            "Max",
            "Anthony",
            "Joseph",
            "Julian",
            "John",
            "Colton",
            "Levi",
            "Muhammad",
            "Isaiah",
            "Aaron",
            "Frederick",
    };

    private static String surnames[] = new String[] {
            "Brown",
            "Smith",
            "Patel",
            "Jones",
            "Williams",
            "Johnson",
            "Taylor",
            "Thomas",
            "Roberts",
            "Khan",
            "Lewis",
            "Jackson",
            "Clarke",
            "James",
            "Phillips",
            "Wilson",
            "Ali",
            "Mason",
            "Mitchell",
            "Rose",
            "Davis",
            "Davies",
            "Rodriguez",
            "Cox",
            "Alexander",
            "Wilson",
            "Campbell",
            "Kelly",
            "Johnston",
            "Moore",
            "Thompson",
            "Smyth",
            "Samson",
            "Johnson",
            "Ferris",
            "Harris",
            "Hodgson",
            "Megson",
            "Tomkins",
            "Perkins",
            "Simpson",
            "Simkins",
            "Watkins",
            "Rooney",
            "Bingham",
            "Stewart",
            "MacArthur",
            "McGregor",
            "Neill",
            "Neville",
            "Newton",
            "Ramsey",
            "Braithwaite",
            "Samuels",
            "Cassidy",
            "Lloyd",
            "Bray",
            "Anderson",
            "Winder",
            "Hervogstein",
            "Weinbergs-Hols"
    };

    private  static List<PassportCountry> countries = new ArrayList<>();

    static {
        countries.add( new PassportCountry( "Afghanistan", "AFG" ) );
        countries.add( new PassportCountry( "Albania", "ALB" ) );
        countries.add( new PassportCountry( "Algeria", "DZA" ) );
        countries.add( new PassportCountry( "American Samoa", "ASM" ) );
        countries.add( new PassportCountry( "Andorra", "AND" ) );
        countries.add( new PassportCountry( "Angola", "AGO" ) );
        countries.add( new PassportCountry( "Anguilla", "AIA" ) );
        countries.add( new PassportCountry( "Antarctica", "ATA" ) );
        countries.add( new PassportCountry( "Antigua and Barbuda", "ATG" ) );
        countries.add( new PassportCountry( "Argentina", "ARG" ) );
        countries.add( new PassportCountry( "Armenia", "ARM" ) );
        countries.add( new PassportCountry( "Aruba", "ABW" ) );
        countries.add( new PassportCountry( "Australia", "AUS" ) );
        countries.add( new PassportCountry( "Austria", "AUT" ) );
        countries.add( new PassportCountry( "Azerbaijan", "AZE" ) );
        countries.add( new PassportCountry( "Bahamas", "BHS" ) );
        countries.add( new PassportCountry( "Bahrain", "BHR" ) );
        countries.add( new PassportCountry( "Bangladesh", "BGD" ) );
        countries.add( new PassportCountry( "Barbados", "BRB" ) );
        countries.add( new PassportCountry( "Belarus", "BLR" ) );
        countries.add( new PassportCountry( "Belgium", "BEL" ) );
        countries.add( new PassportCountry( "Belize", "BLZ" ) );
        countries.add( new PassportCountry( "Benin", "BEN" ) );
        countries.add( new PassportCountry( "Bermuda", "BMU" ) );
        countries.add( new PassportCountry( "Bhutan", "BTN" ) );
        countries.add( new PassportCountry( "Bolivia", "BOL" ) );
        countries.add( new PassportCountry( "Bosnia and Herzegovina", "BIH" ) );
        countries.add( new PassportCountry( "Botswana", "BWA" ) );
        countries.add( new PassportCountry( "Bouvet Island", "BVT" ) );
        countries.add( new PassportCountry( "Brazil", "BRA" ) );
        countries.add( new PassportCountry( "British Indian Ocean Territory", "IOT" ) );
        countries.add( new PassportCountry( "Brunei Darussalam", "BRN" ) );
        countries.add( new PassportCountry( "Bulgaria", "BGR" ) );
        countries.add( new PassportCountry( "Burkina Faso", "BFA" ) );
        countries.add( new PassportCountry( "Burundi", "BDI" ) );
        countries.add( new PassportCountry( "Cambodia", "KHM" ) );
        countries.add( new PassportCountry( "Cameroon", "CMR" ) );
        countries.add( new PassportCountry( "Canada", "CAN" ) );
        countries.add( new PassportCountry( "Cape Verde", "CPV" ) );
        countries.add( new PassportCountry( "Cayman Islands", "CYM" ) );
        countries.add( new PassportCountry( "Central African Republic", "CAF" ) );
        countries.add( new PassportCountry( "Chad", "TCD" ) );
        countries.add( new PassportCountry( "Chile", "CHL" ) );
        countries.add( new PassportCountry( "China", "CHN" ) );
        countries.add( new PassportCountry( "Christmas Island", "CXR" ) );
        countries.add( new PassportCountry( "Cocos (Keeling) Islands", "CCK" ) );
        countries.add( new PassportCountry( "Colombia", "COL" ) );
        countries.add( new PassportCountry( "Comoros", "COM" ) );
        countries.add( new PassportCountry( "Congo", "COG" ) );
        countries.add( new PassportCountry( "Cook Islands", "COK" ) );
        countries.add( new PassportCountry( "Costa Rica", "CRI" ) );
        countries.add( new PassportCountry( "Côte d'Ivoire", "CIV" ) );
        countries.add( new PassportCountry( "Croatia", "HRV" ) );
        countries.add( new PassportCountry( "Cuba", "CUB" ) );
        countries.add( new PassportCountry( "Cyprus", "CYP" ) );
        countries.add( new PassportCountry( "Czech Republic", "CZE" ) );
        countries.add( new PassportCountry( "Democratic People's Republic of Korea", "PRK" ) );
        countries.add( new PassportCountry( "Democratic Republic of the Congo", "COD" ) );
        countries.add( new PassportCountry( "Denmark", "DNK" ) );
        countries.add( new PassportCountry( "Djibouti", "DJI" ) );
        countries.add( new PassportCountry( "Dominica", "DMA" ) );
        countries.add( new PassportCountry( "Dominican Republic", "DOM" ) );
        countries.add( new PassportCountry( "East Timor", "TMP" ) );
        countries.add( new PassportCountry( "Ecuador", "ECU" ) );
        countries.add( new PassportCountry( "Egypt", "EGY" ) );
        countries.add( new PassportCountry( "El Salvador", "SLV" ) );
        countries.add( new PassportCountry( "Equatorial Guinea", "GNQ" ) );
        countries.add( new PassportCountry( "Eritrea", "ERI" ) );
        countries.add( new PassportCountry( "Estonia", "EST" ) );
        countries.add( new PassportCountry( "Ethiopia", "ETH" ) );
        countries.add( new PassportCountry( "Falkland Islands (Malvinas)", "FLK" ) );
        countries.add( new PassportCountry( "Faeroe Islands", "FRO" ) );
        countries.add( new PassportCountry( "Fiji", "FJI" ) );
        countries.add( new PassportCountry( "Finland", "FIN" ) );
        countries.add( new PassportCountry( "France", "FRA" ) );
        countries.add( new PassportCountry( "France, Metropolitan", "FXX" ) );
        countries.add( new PassportCountry( "French Guiana", "GUF" ) );
        countries.add( new PassportCountry( "French Polynesia", "PYF" ) );
        countries.add( new PassportCountry( "Gabon", "GAB" ) );
        countries.add( new PassportCountry( "Gambia", "GMB" ) );
        countries.add( new PassportCountry( "Georgia", "GEO" ) );
        countries.add( new PassportCountry( "Germany", "D" ) );
        countries.add( new PassportCountry( "Ghana", "GHA" ) );
        countries.add( new PassportCountry( "Gibraltar", "GIB" ) );
        countries.add( new PassportCountry( "Greece", "GRC" ) );
        countries.add( new PassportCountry( "Greenland", "GRL" ) );
        countries.add( new PassportCountry( "Grenada", "GRD" ) );
        countries.add( new PassportCountry( "Guadeloupe", "GLP" ) );
        countries.add( new PassportCountry( "Guam", "GUM" ) );
        countries.add( new PassportCountry( "Guatemala", "GTM" ) );
        countries.add( new PassportCountry( "Guinea", "GIN" ) );
        countries.add( new PassportCountry( "Guinea-Bissau", "GNB" ) );
        countries.add( new PassportCountry( "Guyana", "GUY" ) );
        countries.add( new PassportCountry( "Haiti", "HTI" ) );
        countries.add( new PassportCountry( "Heard and McDonald Islands", "HMD" ) );
        countries.add( new PassportCountry( "Holy See (Vatican City State)", "VAT" ) );
        countries.add( new PassportCountry( "Honduras", "HND" ) );
        countries.add( new PassportCountry( "Hong Kong", "HKG" ) );
        countries.add( new PassportCountry( "Hungary", "HUN" ) );
        countries.add( new PassportCountry( "Iceland", "ISL" ) );
        countries.add( new PassportCountry( "India", "IND" ) );
        countries.add( new PassportCountry( "Indonesia", "IDN" ) );
        countries.add( new PassportCountry( "Iran, Islamic Republic of", "IRN" ) );
        countries.add( new PassportCountry( "Iraq", "IRQ" ) );
        countries.add( new PassportCountry( "Ireland", "IRL" ) );
        countries.add( new PassportCountry( "Israel", "ISR" ) );
        countries.add( new PassportCountry( "Italy", "ITA" ) );
        countries.add( new PassportCountry( "Jamaica", "JAM" ) );
        countries.add( new PassportCountry( "Japan", "JPN" ) );
        countries.add( new PassportCountry( "Jordan", "JOR" ) );
        countries.add( new PassportCountry( "Kazakhstan", "KAZ" ) );
        countries.add( new PassportCountry( "Kenya", "KEN" ) );
        countries.add( new PassportCountry( "Kiribati", "KIR" ) );
        countries.add( new PassportCountry( "Kuwait", "KWT" ) );
        countries.add( new PassportCountry( "Kyrgyzstan", "KGZ" ) );
        countries.add( new PassportCountry( "Lao People's Democratic Republic", "LAO" ) );
        countries.add( new PassportCountry( "Latvia", "LVA" ) );
        countries.add( new PassportCountry( "Lebanon", "LBN" ) );
        countries.add( new PassportCountry( "Lesotho", "LSO" ) );
        countries.add( new PassportCountry( "Liberia", "LBR" ) );
        countries.add( new PassportCountry( "Libyan Arab Jamahiriya", "LBY" ) );
        countries.add( new PassportCountry( "Liechtenstein", "LIE" ) );
        countries.add( new PassportCountry( "Lithuania", "LTU" ) );
        countries.add( new PassportCountry( "Luxembourg", "LUX" ) );
        countries.add( new PassportCountry( "Madagascar", "MDG" ) );
        countries.add( new PassportCountry( "Malawi", "MWI" ) );
        countries.add( new PassportCountry( "Malaysia", "MYS" ) );
        countries.add( new PassportCountry( "Maldives", "MDV" ) );
        countries.add( new PassportCountry( "Mali", "MLI" ) );
        countries.add( new PassportCountry( "Malta", "MLT" ) );
        countries.add( new PassportCountry( "Marshall Islands", "MHL" ) );
        countries.add( new PassportCountry( "Martinique", "MTQ" ) );
        countries.add( new PassportCountry( "Mauritania", "MRT" ) );
        countries.add( new PassportCountry( "Mauritius", "MUS" ) );
        countries.add( new PassportCountry( "Mayotte", "MYT" ) );
        countries.add( new PassportCountry( "Mexico", "MEX" ) );
        countries.add( new PassportCountry( "Micronesia, Federated States of", "FSM" ) );
        countries.add( new PassportCountry( "Monaco", "MCO" ) );
        countries.add( new PassportCountry( "Mongolia", "MNG" ) );
        countries.add( new PassportCountry( "Montserrat", "MSR" ) );
        countries.add( new PassportCountry( "Morocco", "MAR" ) );
        countries.add( new PassportCountry( "Mozambique", "MOZ" ) );
        countries.add( new PassportCountry( "Myanmar", "MMR" ) );
        countries.add( new PassportCountry( "Namibia", "NAM" ) );
        countries.add( new PassportCountry( "Nauru", "NRU" ) );
        countries.add( new PassportCountry( "Nepal", "NPL" ) );
        countries.add( new PassportCountry( "Netherlands, Kingdom of the", "NLD" ) );
        countries.add( new PassportCountry( "Netherlands Antilles", "ANT" ) );
        countries.add( new PassportCountry( "Neutral Zone", "NTZ" ) );
        countries.add( new PassportCountry( "New Caledonia", "NCL" ) );
        countries.add( new PassportCountry( "New Zealand", "NZL" ) );
        countries.add( new PassportCountry( "Nicaragua", "NIC" ) );
        countries.add( new PassportCountry( "Niger", "NER" ) );
        countries.add( new PassportCountry( "Nigeria", "NGA" ) );
        countries.add( new PassportCountry( "Niue", "NIU" ) );
        countries.add( new PassportCountry( "Norfolk Island", "NFK" ) );
        countries.add( new PassportCountry( "Northern Mariana Islands", "MNP" ) );
        countries.add( new PassportCountry( "Norway", "NOR" ) );
        countries.add( new PassportCountry( "Oman", "OMN" ) );
        countries.add( new PassportCountry( "Pakistan", "PAK" ) );
        countries.add( new PassportCountry( "Palau", "PLW" ) );
        countries.add( new PassportCountry( "Panama", "PAN" ) );
        countries.add( new PassportCountry( "Papua New Guinea", "PNG" ) );
        countries.add( new PassportCountry( "Paraguay", "PRY" ) );
        countries.add( new PassportCountry( "Peru", "PER" ) );
        countries.add( new PassportCountry( "Philippines", "PHL" ) );
        countries.add( new PassportCountry( "Pitcairn", "PCN" ) );
        countries.add( new PassportCountry( "Poland", "POL" ) );
        countries.add( new PassportCountry( "Portugal", "PRT" ) );
        countries.add( new PassportCountry( "Puerto Rico", "PRI" ) );
        countries.add( new PassportCountry( "Qatar", "QAT" ) );
        countries.add( new PassportCountry( "Republic of Korea", "KOR" ) );
        countries.add( new PassportCountry( "Republic of Moldova", "MDA" ) );
        countries.add( new PassportCountry( "Réunion", "REU" ) );
        countries.add( new PassportCountry( "Romania", "ROM" ) );
        countries.add( new PassportCountry( "Russian Federation", "RUS" ) );
        countries.add( new PassportCountry( "Rwanda", "RWA" ) );
        countries.add( new PassportCountry( "Saint Helena", "SHN" ) );
        countries.add( new PassportCountry( "Saint Kitts and Nevis", "KNA" ) );
        countries.add( new PassportCountry( "Saint Lucia", "LCA" ) );
        countries.add( new PassportCountry( "Saint Pierre and Miquelon", "SPM" ) );
        countries.add( new PassportCountry( "Saint Vincent and the Grenadines", "VCT" ) );
        countries.add( new PassportCountry( "Samoa", "WSM" ) );
        countries.add( new PassportCountry( "San Marino", "SMR" ) );
        countries.add( new PassportCountry( "Sao Tome and Principe", "STP" ) );
        countries.add( new PassportCountry( "Saudi Arabia", "SAU" ) );
        countries.add( new PassportCountry( "Senegal", "SEN" ) );
        countries.add( new PassportCountry( "Seychelles", "SYC" ) );
        countries.add( new PassportCountry( "Sierra Leone", "SLE" ) );
        countries.add( new PassportCountry( "Singapore", "SGP" ) );
        countries.add( new PassportCountry( "Slovakia", "SVK" ) );
        countries.add( new PassportCountry( "Slovenia", "SVN" ) );
        countries.add( new PassportCountry( "Solomon Islands", "SLB" ) );
        countries.add( new PassportCountry( "Somalia", "SOM" ) );
        countries.add( new PassportCountry( "South Africa", "ZAF" ) );
        countries.add( new PassportCountry( "South Georgia and the South Sandwich Island", "SGS" ) );
        countries.add( new PassportCountry( "Spain", "ESP" ) );
        countries.add( new PassportCountry( "Sri Lanka", "LKA" ) );
        countries.add( new PassportCountry( "Sudan", "SDN" ) );
        countries.add( new PassportCountry( "Suriname", "SUR" ) );
        countries.add( new PassportCountry( "Svalbard and Jan Mayen Islands", "SJM" ) );
        countries.add( new PassportCountry( "Swaziland", "SWZ" ) );
        countries.add( new PassportCountry( "Sweden", "SWE" ) );
        countries.add( new PassportCountry( "Switzerland", "CHE" ) );
        countries.add( new PassportCountry( "Syrian Arab Republic", "SYR" ) );
        countries.add( new PassportCountry( "Taiwan Province of China", "TWN" ) );
        countries.add( new PassportCountry( "Tajikistan", "TJK" ) );
        countries.add( new PassportCountry( "Thailand", "THA" ) );
        countries.add( new PassportCountry( "The former Yugoslav Republic of Macedonia", "MKD" ) );
        countries.add( new PassportCountry( "Togo", "TGO" ) );
        countries.add( new PassportCountry( "Tokelau", "TKL" ) );
        countries.add( new PassportCountry( "Tonga", "TON" ) );
        countries.add( new PassportCountry( "Trinidad and Tobago", "TTO" ) );
        countries.add( new PassportCountry( "Tunisia", "TUN" ) );
        countries.add( new PassportCountry( "Turkey", "TUR" ) );
        countries.add( new PassportCountry( "Turkmenistan", "TKM" ) );
        countries.add( new PassportCountry( "Turks and Caicos Islands", "TCA" ) );
        countries.add( new PassportCountry( "Tuvalu", "TUV" ) );
        countries.add( new PassportCountry( "Uganda", "UGA" ) );
        countries.add( new PassportCountry( "Ukraine", "UKR" ) );
        countries.add( new PassportCountry( "United Arab Emirates", "ARE" ) );
        countries.add( new PassportCountry( "United Kingdom -- Citizen", "GBR" ) );
        countries.add( new PassportCountry( "United Kingdom -- Dependent territories citizen", "GBD" ) );
        countries.add( new PassportCountry( "United Kingdom -- National (overseas)", "GBN" ) );
        countries.add( new PassportCountry( "United Kingdom -- Overseas citizen", "GBO" ) );
        countries.add( new PassportCountry( "United Kingdom -- Protected Person", "GBP" ) );
        countries.add( new PassportCountry( "United Kingdom -- Subject", "GBS" ) );
        countries.add( new PassportCountry( "United Republic of Tanzania", "TZA" ) );
        countries.add( new PassportCountry( "United States of America", "USA" ) );
        countries.add( new PassportCountry( "United States of America Minor Outlying Islands", "UMI" ) );
        countries.add( new PassportCountry( "Uruguay", "URY" ) );
        countries.add( new PassportCountry( "Uzbekistan", "UZB" ) );
        countries.add( new PassportCountry( "Vanuatu", "VUT" ) );
        countries.add( new PassportCountry( "Venezuela", "VEN" ) );
        countries.add( new PassportCountry( "Viet Nam", "VNM" ) );
        countries.add( new PassportCountry( "Virgin Islands (Great Britian)", "VGB" ) );
        countries.add( new PassportCountry( "Virgin Islands (United States)", "VIR" ) );
        countries.add( new PassportCountry( "Wallis and Futuna Islands", "WLF" ) );
        countries.add( new PassportCountry( "Western Sahara", "ESH" ) );
        countries.add( new PassportCountry( "Yemen", "YEM" ) );
        countries.add( new PassportCountry( "Zaire", "ZAR" ) );
        countries.add( new PassportCountry( "Zambia", "ZMB" ) );
        countries.add( new PassportCountry( "Zimbabwe", "ZWE" ) );
        countries.add( new PassportCountry( "indicates an UN offical)", "UNO" ) );
        countries.add( new PassportCountry( "specialized agency official", "UNA" ) );
        countries.add( new PassportCountry( "Stateless (per Article 1 of 1954 convention)", "XXA" ) );
        countries.add( new PassportCountry( "amended by 1967 protocol)", "XXB" ) );
        countries.add( new PassportCountry( "Refugee (non-convention)", "XXC" ) );
        countries.add( new PassportCountry( "Unspecified / Unknown", "XXX" ) );
    }

    public String getRandomGirlsName()
    {
        return girls[ (int)(Math.random() * girls.length) ];
    }

    public String getRandomBoysName()
    {
        return boys[ (int)(Math.random() * boys.length) ];
    }

    public String getRandomSurname()
    {
        return surnames[ (int)(Math.random() * surnames.length) ];
    }

    public PassportCountry getRandomPassportCountry()
    {
        return countries.get( (int)(Math.random() * countries.size()) );
    }

    @PostConstruct
    public void initialise() {
        System.out.printf("%s.initialise()\n", this.getClass().getSimpleName());
    }

    public List<PassportCountry> buildCountries() {
        return countries;
    }

    @PersistenceContext(unitName = "XenNationalForce")
    private EntityManager entityManager;

    public void saveCountries() {
        buildCountries().stream().forEach(country -> entityManager.persist(country));
    }
}
