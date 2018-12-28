/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.monitoring;

/**
 *
 * @author olhshk
 */
public class CsvData {

    public static String CSV_ROUND_1 = "List;Order;Round;SNR;Condition;Length_list;Word;Cue_nonword;Word1;Word2;Word3;Word4;Word5;Word6;Position_target;Position_foil\n"
            + "A;0,1;0;0;Target-only;4 words;schaap;schuip;kelf;schuip_2;bientjel;jirker;;;2;0\n"
            + "A;0,2;0;-6;NoTarget;3 words;zak;zas;pieder;krel;ern;;;;0;0\n"
            + "A;0,3;0;-10;Target+Foil;5 words;hart;hats;traap;halgherm;spaas;hats_2;baphies;;4;2\n"
            + "A;1;1;0;Target-only;6 words;touw;tomp;werrens;knaf;regni;apveid;tomp_2;penk;5;0\n"
            + "A;2;1;-2;NoTarget;3 words;tang;mang;halktat;wieel;vreeuw;;;;0;0\n"
            + "A;3;1;-4;Target-only;5 words;kroon;broon;nijmsaard;wulen;pluif;broon_2;swi;;4;0\n"
            + "A;4;1;-6;Target+Foil;5 words;man;mas;kieg;guitel;marralk;mas_2;res;;4;3\n"
            + "A;5;1;-8;NoTarget;4 words;eend;oend;fraal;stoek;raf;noger;;;0;0\n"
            + "A;6;1;-10;Target+Foil;4 words;taart;taapt;taafmong;taapt_2;sloog;gies;;;2;1\n"
            + "A;7;1;-12;Target-only;3 words;kaars;kiers;slon;kiers_2;waga;;;;2;0\n"
            + "A;8;1;-14;NoTarget;5 words;gat;gar;famker;cigroon;snaan;vets;rirte;;0;0\n"
            + "A;9;1;-16;Target+Foil;3 words;bom;bog;bofreun;bog_2;zamp;;;;2;1\n"
            + "A;10;1;-18;Target-only;4 words;haai;hoei;viet;spunel;hoei_2;nielund;;;3;0\n"
            + "A;11;2;0;Target+Foil;6 words;band;baxt;wirums;hesk;bafruip;pras;baxt_2;grulon;5;3\n"
            + "A;12;2;-2;Target-only;6 words;kruis;fluis;doon;rades;fluis_2;eedond;wemp;goeling;3;0\n"
            + "A;13;2;-4;Target+Foil;5 words;hand;haps;harfbijg;dreflier;worzel;haps_2;keeg;;4;1\n"
            + "A;14;2;-6;NoTarget;4 words;zwaan;praan;veg;gebog;siekoed;fonguin;;;0;0\n"
            + "A;15;2;-8;Target-only;4 words;bloem;blijm;roor;anwoep;blijm_2;efschauk;;;3;0\n"
            + "A;16;2;-10;Target-only;3 words;smaak;smaaf;gog;smaaf_2;kleis;;;;2;0\n"
            + "A;17;2;-12;Target+Foil;4 words;hoed;hoem;hoegpaat;strieg;hoem_2;vleek;;;3;1\n"
            + "A;18;2;-14;Target-only;4 words;bier;biem;fots;olgeer;biem_2;tijnkel;;;3;0\n"
            + "A;19;2;-16;NoTarget;6 words;peen;peef;zek;kijs;miel;draxtor;redel;vrop;0;0\n"
            + "A;20;2;-18;Target+Foil;3 words;bord;bops;bolgwap;bops_2;lank;;;;2;1\n"
            + "A;21;3;0;NoTarget;3 words;loep;luip;baaf;mefdel;schuik;;;;0;0\n"
            + "A;22;3;-2;Target+Foil;6 words;broer;broen;launning;broefnak;pag;broen_2;akbuip;rornas;4;2\n"
            + "A;23;3;-4;NoTarget;4 words;doek;deek;oedbijp;jor;stunk;burbijn;;;0;0\n"
            + "A;24;3;-6;Target-only;5 words;muis;muin;pran;muin_2;barp;euzer;schekder;;2;0\n"
            + "A;25;3;-8;Target+Foil;5 words;hond;hogt;aafbag;holmdrins;hogt_2;toor;goe;;3;2\n"
            + "A;26;3;-10;NoTarget;5 words;beest;beept;luin;spom;zoeg;kraaf;roog;;0;0\n"
            + "A;27;3;-12;NoTarget;5 words;blaas;draas;antveg;wref;jomel;loor;proos;;0;0\n"
            + "A;28;3;-14;Target+Foil;4 words;brood;broog;dauk;broopkimp;broog_2;knekgel;;;3;2\n"
            + "A;29;3;-16;Target-only;4 words;pop;pos;woetaug;pos_2;snerm;goos;;;2;0\n"
            + "A;30;3;-18;NoTarget;6 words;klad;klag;vuitlil;laaps;hook;brelp;raben;otrong;0;0\n"
            + "B;0,1;0;0;Target-only;4 words;kers;hers;nevra;hers_2;zwesbag;smap;;;2;0\n"
            + "B;0,2;0;-6;NoTarget;3 words;hert;hets;sperf;mijk;lir;;;;0;0\n"
            + "B;0,3;0;-10;Target+Foil;5 words;zalf;zarp;momp;zanskolm;hog;zarp_2;garpes;;4;2\n"
            + "B;1;1;0;Target-only;6 words;fiets;fuits;ontseet;giemel;strap;melsoon;fuits_2;zoorbeelk;5;0\n"
            + "B;2;1;-2;NoTarget;3 words;veer;veep;gerf;eurwag;kraap;;;;0;0\n"
            + "B;3;1;-4;Target-only;5 words;spook;spuik;rool;vloest;klork;spuik_2;angraus;;4;0\n"
            + "B;4;1;-6;Target+Foil;5 words;roos;roop;bruif;geernan;rooglim;roop_2;klil;;4;3\n"
            + "B;5;1;-8;NoTarget;4 words;roem;loem;gribund;kang;anject;dreel;;;0;0\n"
            + "B;6;1;-10;Target+Foil;4 words;mouw;molp;moufdijn;molp_2;hamp;sinier;;;2;1\n"
            + "B;7;1;-12;Target-only;3 words;stift;stins;zandog;stins_2;taud;;;;2;0\n"
            + "B;8;1;-14;NoTarget;5 words;haas;haap;pals;meffie;scherg;wupel;abzui;;0;0\n"
            + "B;9;1;-16;Target+Foil;3 words;bus;buf;burzil;buf_2;tieg;;;;2;1\n"
            + "B;10;1;-18;Target-only;4 words;bijl;lijl;schilmpag;worbuin;lijl_2;gidoer;;;3;0\n"
            + "B;11;2;0;Target+Foil;6 words;sik;sir;filoet;has;simsel;ats;sir_2;lobut;5;3\n"
            + "B;12;2;-2;Target-only;6 words;moord;moops;fluik;bezwop;moops_2;aukpeis;vessen;pra;3;0\n"
            + "B;13;2;-4;Target+Foil;5 words;huis;huin;huimrief;lesk;gebeeg;huin_2;wamoel;;4;1\n"
            + "B;14;2;-6;NoTarget;4 words;uil;ool;salder;pir;kijn;garlon;;;0;0\n"
            + "B;15;2;-8;Target-only;4 words;been;bien;foestvag;murk;bien_2;hader;;;3;0\n"
            + "B;16;2;-10;Target-only;3 words;sjaal;vraal;gesoe;vraal_2;pruif;;;;2;0\n"
            + "B;17;2;-12;Target+Foil;4 words;zon;zol;zormerp;moen;zol_2;garker;;;3;1\n"
            + "B;18;2;-14;Target-only;4 words;rok;ror;schir;waapzan;ror_2;merbog;;;3;0\n"
            + "B;19;2;-16;NoTarget;6 words;bloed;zwoed;liem;megel;goon;ploots;kioel;faper;0;0\n"
            + "B;20;2;-18;Target+Foil;3 words;jas;jal;jafpes;jal_2;zwins;;;;2;1\n"
            + "B;21;3;0;NoTarget;3 words;zorg;zolg;naap;smoop;tetjel;;;;0;0\n"
            + "B;22;3;-2;Target+Foil;6 words;blaar;blaap;nos;blaafhijp;lides;blaap_2;vas;mig;4;2\n"
            + "B;23;3;-4;NoTarget;4 words;steeg;steem;hooltbijn;adbie;tamer;gir;;;0;0\n"
            + "B;24;3;-6;Target-only;5 words;baard;baaks;veepsel;baaks_2;nil;plamp;zates;;2;0\n"
            + "B;25;3;-8;Target+Foil;5 words;hoef;hoeg;raandog;hoeptijp;hoeg_2;zaam;womp;;3;2\n"
            + "B;26;3;-10;NoTarget;5 words;slak;slas;ciruur;halg;veurserm;woets;tan;;0;0\n"
            + "B;27;3;-12;NoTarget;5 words;kom;kor;duil;tikaat;aunpam;nocht;firp;;0;0\n"
            + "B;28;3;-14;Target+Foil;4 words;oog;ood;kloor;ooptaag;ood_2;merlie;;;3;2\n"
            + "B;29;3;-16;Target-only;4 words;spin;klin;vook;klin_2;treesfer;pozaas;;;2;0\n"
            + "B;30;3;-18;NoTarget;6 words;sop;sor;raber;knes;brots;vep;ledoer;meusel;0;0";

    public static String CSV_ROUND_2 = "List;Order;Round;SNR;Condition;Length_list;Non_word;Cue_word;Word1;Word2;Word3;Word4;Word5;Word6;Position_target;Position_foil\n"
            + "A;0,1;0;0;Target-only;4 words;schuip;schaap;keus;schaap_2;borstel;cirkel;;;2;0\n"
            + "A;0,2;0;-6;NoTarget;3 words;zas;zak;gieter;stel;erf;;;;0;0\n"
            + "A;0,3;0;-10;Target+Foil;5 words;hats;hart;troep;halsband;spies;hart_2;badhuis;;4;2\n"
            + "A;1;1;0;Target-only;6 words;tomp;touw;oordeel;staf;rente;arbeid;touw_2;wenk;5;0\n"
            + "A;2;1;-2;NoTarget;3 words;mang;tang;hangmat;riool;sneeuw;;;;0;0\n"
            + "A;3;1;-4;Target-only;5 words;broon;kroon;nijlpaard;regen;druif;kroon_2;ski;;4;0\n"
            + "A;4;1;-6;Target+Foil;5 words;mas;man;kier;beitel;mammoet;man_2;ren;;4;3\n"
            + "A;5;1;-8;NoTarget;4 words;oend;eend;kwaal;sjeik;ras;zomer;;;0;0\n"
            + "A;6;1;-10;Target+Foil;4 words;taapt;taart;taalfout;taart_2;sloot;nies;;;2;1\n"
            + "A;7;1;-12;Target-only;3 words;kiers;kaars;slot;kaars_2;lama;;;;2;0\n"
            + "A;8;1;-14;NoTarget;5 words;gar;gat;masker;citroen;traan;vent;hitte;;0;0\n"
            + "A;9;1;-16;Target+Foil;3 words;bog;bom;bosbes;bom_2;zand;;;;2;1\n"
            + "A;10;1;-18;Target-only;4 words;hoei;haai;riet;puzzel;haai_2;weiland;;;3;0\n"
            + "A;11;2;0;Target+Foil;6 words;baxt;band;minuut;heup;balpen;gras;band_2;sultan;5;3\n"
            + "A;12;2;-2;Target-only;6 words;fluis;kruis;loon;dadel;kruis_2;eiland;werk;voeding;3;0\n"
            + "A;13;2;-4;Target+Foil;5 words;haps;hand;hartslag;premier;wortel;hand_2;keer;;4;1\n"
            + "A;14;2;-6;NoTarget;4 words;praan;zwaan;vel;gebak;sieraad;fontein;;;0;0\n"
            + "A;15;2;-8;Target-only;4 words;blijm;bloem;koor;oproep;bloem_2;afspraak;;;3;0\n"
            + "A;16;2;-10;Target-only;3 words;smaaf;smaak;god;smaak_2;kluis;;;;2;0\n"
            + "A;17;2;-12;Target+Foil;4 words;hoem;hoed;hoefsmid;striem;hoed_2;bleek;;;3;1\n"
            + "A;18;2;-14;Target-only;4 words;biem;bier;fort;onweer;bier_2;vaarwel;;;3;0\n"
            + "A;19;2;-16;NoTarget;6 words;peef;peen;hek;wijs;ziel;tractor;ketel;stop;0;0\n"
            + "A;20;2;-18;Target+Foil;3 words;bops;bord;boksbal;bord_2;land;;;;2;1\n"
            + "A;21;3;0;NoTarget;3 words;luip;loep;baan;middel;struik;;;;0;0\n"
            + "A;22;3;-2;Target+Foil;6 words;broen;broer;leerling;broekzak;pad;broer_2;afloop;harnas;4;2\n"
            + "A;23;3;-4;NoTarget;4 words;deek;doek;eerbied;por;stank;buslijn;;;0;0\n"
            + "A;24;3;-6;Target-only;5 words;muin;muis;plan;muis_2;berg;emmer;schouder;;2;0\n"
            + "A;25;3;-8;Target+Foil;5 words;hogt;hond;aanbod;holster;hond_2;tooi;roe;;3;2\n"
            + "A;26;3;-10;NoTarget;5 words;beept;beest;luik;slof;voeg;kraag;room;;0;0\n"
            + "A;27;3;-12;NoTarget;5 words;draas;blaas;ontzag;chef;kogel;leer;prijs;;0;0\n"
            + "A;28;3;-14;Target+Foil;4 words;broog;brood;duik;broosheid;brood_2;vleugel;;;3;2\n"
            + "A;29;3;-16;Target-only;4 words;pos;pop;rijtuig;pop_2;zwerm;goot;;;2;0\n"
            + "A;30;3;-18;NoTarget;6 words;klag;klad;voetbal;laars;rook;breuk;haven;omgang;0;0\n"
            + "B;0,1;0;0;Target-only;4 words;hers;kers;zebra;kers_2;zwembad;stap;;;2;0\n"
            + "B;0,2;0;-6;NoTarget;3 words;hets;hert;speld;dijk;lid;;;;0;0\n"
            + "B;0,3;0;-10;Target+Foil;5 words;zarp;zalf;pomp;zandbak;hof;zalf_2;bosjes;;4;2\n"
            + "B;1;1;0;Target-only;6 words;fuits;fiets;ontbijt;bijbel;strop;persoon;fiets_2;voorbeeld;5;0\n"
            + "B;2;1;-2;NoTarget;3 words;veep;veer;geld;oorlog;slaap;;;;0;0\n"
            + "B;3;1;-4;Target-only;5 words;spuik;spook;ruil;vlecht;klomp;spook_2;applaus;;4;0\n"
            + "B;4;1;-6;Target+Foil;5 words;ries;roos;bries;veerman;roofdier;roos_2;klik;;4;3\n"
            + "B;5;1;-8;NoTarget;4 words;loem;roem;tulband;kalf;object;steel;;;0;0\n"
            + "B;6;1;-10;Target+Foil;4 words;molp;mouw;moutwijn;mouw_2;kamp;sigaar;;;2;1\n"
            + "B;7;1;-12;Target-only;3 words;stins;stift;zondag;stift_2;tijd;;;;2;0\n"
            + "B;8;1;-14;NoTarget;5 words;haap;haas;pand;koffie;scherf;regel;armoe;;0;0\n"
            + "B;9;1;-16;Target+Foil;3 words;buf;bus;burger;bus_2;wieg;;;;2;1\n"
            + "B;10;1;-18;Target-only;4 words;lijl;bijl;schildpad;fortuin;bijl_2;gitaar;;;3;0\n"
            + "B;11;2;0;Target+Foil;6 words;sir;sik;piloot;hal;signaal;ets;sik_2;robot;5;3\n"
            + "B;12;2;-2;Target-only;6 words;moops;moord;fluit;begrip;moord_2;aalmoes;veulen;sla;3;0\n"
            + "B;13;2;-4;Target+Foil;5 words;huin;huis;huidcel;vest;gebied;huis_2;kameel;;4;1\n"
            + "B;14;2;-6;NoTarget;4 words;ool;uil;folder;pil;lijn;ballon;;;0;0\n"
            + "B;15;2;-8;Target-only;4 words;bien;been;feestdag;merk;been_2;water;;;3;0\n"
            + "B;16;2;-10;Target-only;3 words;vraal;sjaal;gewei;sjaal_2;proef;;;;2;0\n"
            + "B;17;2;-12;Target+Foil;4 words;zol;zon;zolder;moed;zon_2;bakker;;;3;1\n"
            + "B;18;2;-14;Target-only;4 words;ror;rok;schip;waanzin;rok_2;hertog;;;3;0\n"
            + "B;19;2;-16;NoTarget;6 words;zwoed;bloed;lijm;kegel;zoon;plaats;viool;jager;0;0\n"
            + "B;20;2;-18;Target+Foil;3 words;jal;jas;jargon;jas_2;print;;;;2;1\n"
            + "B;21;3;0;NoTarget;3 words;zolg;zorg;naam;sloep;tempel;;;;0;0\n"
            + "B;22;3;-2;Target+Foil;6 words;blaap;blaar;non;blaaspijp;liter;blaar_2;tas;mis;4;2\n"
            + "B;23;3;-4;NoTarget;4 words;steem;steeg;hoofdpijn;actie;meter;gif;;;0;0\n"
            + "B;24;3;-6;Target-only;5 words;baaks;baard;voedsel;baard_2;gil;kramp;zadel;;2;0\n"
            + "B;25;3;-8;Target+Foil;5 words;hoeg;hoef;maandag;hoektand;hoef_2;zaal;worp;;3;2\n"
            + "B;26;3;-10;NoTarget;5 words;slas;slak;figuur;halm;voorkeur;toets;kan;;0;0\n"
            + "B;27;3;-12;NoTarget;5 words;kor;kom;buil;piraat;aanpak;nicht;film;;0;0\n"
            + "B;28;3;-14;Target+Foil;4 words;ood;oog;spoor;oostgrens;oog_2;versie;;;3;2\n"
            + "B;29;3;-16;Target-only;4 words;klin;spin;boog;spin_2;pleister;tomaat;;;2;0\n"
            + "B;30;3;-18;NoTarget;6 words;sor;sop;kamer;bles;trots;ven;leraar;deksel;0;0\n";

    public static String CSV_ROUND_3 = "List;Order;Round;SNR;Condition;Length_list;Cue_word;Target_semantisch;fas_f;fas_a;Word1;Word2;Word3;Word4;Word5;Word6;Position_target\n"
            + "A;0,1;0;0;Target-only;4 words;wol;schaap;0,44;0,25;keus;schaap_2;borstel;cirkel;;;2\n"
            + "A;0,2;0;-6;NoTarget;3 words;tas;zak;0,08;0,05;gieter;stel;erf;;;;0\n"
            + "A;0,3;0;-10;Target-exFoil;5 words;long;hart;0,03;0,02;troep;kuiken;spies;hart_2;badhuis;;4\n"
            + "A;1;1;0;Target-only;6 words;knoop;touw;0,16;0,11;oordeel;staf;rente;arbeid;touw_2;wenk;5\n"
            + "A;2;1;-2;NoTarget;3 words;klem;tang;0,03;0,02;hangmat;riool;sneeuw;;;;0\n"
            + "A;3;1;-4;Target-only;5 words;prins;kroon;0,03;0,02;nijlpaard;regen;druif;kroon_2;ski;;4\n"
            + "A;4;1;-6;Target-exFoil;5 words;vrouw;man;0,29;0,12;kier;beitel;datum;man_2;ren;;4\n"
            + "A;5;1;-8;NoTarget;4 words;dons;eend;0,04;0,07;kwaal;sjeik;ras;zomer;;;0\n"
            + "A;6;1;-10;Target-exFoil;4 words;vlaai;taart;0,37;0,16;wapen;taart_2;sloot;nies;;;2\n"
            + "A;7;1;-12;Target-only;3 words;vlam;kaars;0,05;0,07;slot;kaars_2;lama;;;;2\n"
            + "A;8;1;-14;NoTarget;5 words;kuil;gat;0,15;0,09;masker;citroen;traan;vent;hitte;;0\n"
            + "A;9;1;-16;Target-exFoil;3 words;knal;bom;0,11;0,10;tiener;bom_2;zand;;;;2\n"
            + "A;10;1;-18;Target-only;4 words;vin;haai;0,11;0,10;riet;puzzel;haai_2;weiland;;;3\n"
            + "A;11;2;0;Target-exFoil;6 words;velg;band;0,11;0,12;minuut;heup;eiwit;gras;band_2;sultan;5\n"
            + "A;12;2;-2;Target-only;6 words;graf;kruis;0,03;0,02;loon;dadel;kruis_2;eiland;werk;voeding;3\n"
            + "A;13;2;-4;Target-exFoil;5 words;duim;hand;0,26;0,17;gedicht;premier;wortel;hand_2;keer;;4\n"
            + "A;14;2;-6;NoTarget;4 words;nest;zwaan;0,01;0,00;vel;gebak;sieraad;fontein;;;0\n"
            + "A;15;2;-8;Target-only;4 words;geur;bloem;0,03;0,03;koor;oproep;bloem_2;afspraak;;;3\n"
            + "A;16;2;-10;Target-only;3 words;tong;smaak;0,05;0,06;god;smaak_2;kluis;;;;2\n"
            + "A;17;2;-12;Target-exFoil;4 words;pet;hoed;0,12;0,08;cijfer;striem;hoed_2;bleek;;;3\n"
            + "A;18;2;-14;Target-only;4 words;tap;bier;0,59;0,27;fort;onweer;bier_2;vaarwel;;;3\n"
            + "A;19;2;-16;NoTarget;6 words;biet;peen;0,01;0,00;hek;wijs;ziel;tractor;ketel;stop;0\n"
            + "A;20;2;-18;Target-exFoil;3 words;krijt;bord;0,59;0,29;najaar;bord_2;land;;;;2\n"
            + "A;21;3;0;NoTarget;3 words;glas;loep;0,00;0,00;baan;middel;struik;;;;0\n"
            + "A;22;3;-2;Target-exFoil;6 words;zus;broer;0,32;0,15;leerling;raket;pad;broer_2;afloop;harnas;4\n"
            + "A;23;3;-4;NoTarget;4 words;zeil;doek;0,06;0,05;eerbied;por;stank;buslijn;;;0\n"
            + "A;24;3;-6;Target-only;5 words;kaas;muis;0,04;0,04;plan;muis_2;berg;emmer;schouder;;2\n"
            + "A;25;3;-8;Target-exFoil;5 words;kat;hond;0,21;0,09;aanbod;bamboe;hond_2;tooi;roe;;3\n"
            + "A;26;3;-10;NoTarget;5 words;wild;beest;0,04;0,02;luik;slof;voeg;kraag;room;;0\n"
            + "A;27;3;-12;NoTarget;5 words;gal;blaas;0,08;0,04;ontzag;chef;kogel;leer;prijs;;0\n"
            + "A;28;3;-14;Target-exFoil;4 words;korst;brood;0,64;0,26;duik;accent;brood_2;vleugel;;;3\n"
            + "A;29;3;-16;Target-only;4 words;kind;pop;0,00;0,00;rijtuig;pop_2;zwerm;goot;;;2\n"
            + "A;30;3;-18;NoTarget;6 words;schets;klad;0,02;0,01;voetbal;laars;rook;breuk;haven;omgang;0\n"
            + "B;0,1;0;0;Target-only;4 words;pit;kers;0,08;0,04;zebra;kers_2;zwembad;stap;;;2\n"
            + "B;0,2;0;-6;NoTarget;3 words;ree;hert;0,50;0,20;speld;dijk;lid;;;;0\n"
            + "B;0,3;0;-10;Target-exFoil;5 words;jeuk;zalf;0,02;0,03;pomp;matroos;hof;zalf_2;bosjes;;4\n"
            + "B;1;1;0;Target-only;6 words;wiel;fiets;0,25;0,20;ontbijt;bijbel;strop;persoon;fiets_2;voorbeeld;5\n"
            + "B;2;1;-2;NoTarget;3 words;pluim;veer;0,35;0,16;geld;oorlog;slaap;;;;0\n"
            + "B;3;1;-4;Target-only;5 words;geest;spook;0,31;0,18;ruil;vlecht;klomp;spook_2;applaus;;4\n"
            + "B;4;1;-6;Target-exFoil;5 words;doorn;roos;0,41;0,22;bries;veerman;concert;roos_2;klik;;4\n"
            + "B;5;1;-8;NoTarget;4 words;eer;roem;0,05;0,04;tulband;kalf;object;steel;;;0\n"
            + "B;6;1;-10;Target-exFoil;4 words;hemd;mouw;0,03;0,02;patat;mouw_2;kamp;sigaar;;;2\n"
            + "B;7;1;-12;Target-only;3 words;pen;stift;0,00;0,00;zondag;stift_2;tijd;;;;2\n"
            + "B;8;1;-14;NoTarget;5 words;veld;haas;0,00;0,00;pand;koffie;scherf;regel;armoe;;0\n"
            + "B;9;1;-16;Target-exFoil;3 words;tram;bus;0,04;0,05;yoghurt;bus_2;wieg;;;;2\n"
            + "B;10;1;-18;Target-only;4 words;hout;bijl;0,00;0,01;schildpad;fortuin;bijl_2;gitaar;;;3\n"
            + "B;11;2;0;Target-exFoil;6 words;geit;sik;0,08;0,06;piloot;hal;winter;ets;sik_2;robot;5\n"
            + "B;12;2;-2;Target-only;6 words;lijk;moord;0,03;0,06;fluit;begrip;moord_2;aalmoes;veulen;sla;3\n"
            + "B;13;2;-4;Target-exFoil;5 words;dak;huis;0,34;0,16;ballet;vest;gebied;huis_2;kameel;;4\n"
            + "B;14;2;-6;NoTarget;4 words;nacht;uil;0,02;0,02;folder;pil;lijn;ballon;;;0\n"
            + "B;15;2;-8;Target-only;4 words;arm;been;0,14;0,09;feestdag;merk;been_2;water;;;3\n"
            + "B;16;2;-10;Target-only;3 words;muts;sjaal;0,09;0,09;gewei;sjaal_2;proef;;;;2\n"
            + "B;17;2;-12;Target-exFoil;4 words;strand;zon;0,18;0,16;grafiek;moed;zon_2;bakker;;;3\n"
            + "B;18;2;-14;Target-only;4 words;jurk;rok;0,03;0,03;schip;waanzin;rok_2;hertog;;;3\n"
            + "B;19;2;-16;NoTarget;6 words;snee;bloed;0,17;0,20;lijm;kegel;zoon;plaats;viool;jager;0\n"
            + "B;20;2;-18;Target-exFoil;3 words;rits;jas;0,15;0,14;bunker;jas_2;print;;;;2\n"
            + "B;21;3;0;NoTarget;3 words;last;zorg;0,02;0,01;naam;sloep;tempel;;;;0\n"
            + "B;22;3;-2;Target-exFoil;6 words;voet;blaar;0,02;0,01;non;framboos;liter;blaar_2;tas;mis;4\n"
            + "B;23;3;-4;NoTarget;4 words;straat;steeg;0,01;0,01;hoofdpijn;actie;meter;gif;;;0\n"
            + "B;24;3;-6;Target-only;5 words;snor;baard;0,27;0,14;voedsel;baard_2;gil;kramp;zadel;;2\n"
            + "B;25;3;-8;Target-exFoil;5 words;smid;hoef;0,02;0,03;maandag;druppel;hoef_2;zaal;worp;;3\n"
            + "B;26;3;-10;NoTarget;5 words;slijm;slak;0,09;0,09;figuur;halm;voorkeur;toets;kan;;0\n"
            + "B;27;3;-12;NoTarget;5 words;schaal;kom;0,07;0,04;buil;piraat;aanpak;nicht;film;;0\n"
            + "B;28;3;-14;Target-exFoil;4 words;lens;oog;0,33;0,18;spoor;cultuur;oog_2;versie;;;3\n"
            + "B;29;3;-16;Target-only;4 words;web;spin;0,68;0,29;boog;spin_2;pleister;tomaat;;;2\n"
            + "B;30;3;-18;NoTarget;6 words;zeep;sop;0,02;0,01;kamer;bles;trots;ven;leraar;deksel;0\n";

}